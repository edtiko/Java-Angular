package co.expertic.training.service.impl.plan;

import co.expertic.training.dao.plan.CoachAssignedPlanDao;
import co.expertic.training.dao.plan.PlanMessageDao;
import co.expertic.training.dao.security.RoleUserDao;
import co.expertic.training.dao.user.UserDao;
import co.expertic.training.enums.RoleEnum;
import co.expertic.training.enums.Status;
import co.expertic.training.exception.TrainingException;
import co.expertic.training.model.dto.ChartReportDTO;
import co.expertic.training.model.dto.PlanMessageDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.entities.CoachAssignedPlan;
import co.expertic.training.model.entities.CoachExtAthlete;
import co.expertic.training.model.entities.PlanMessage;
import co.expertic.training.model.entities.RoleUser;
import co.expertic.training.model.entities.User;
import co.expertic.training.service.plan.MailCommunicationService;
import co.expertic.training.service.plan.PlanMessageService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin G
 */
@Service
@Transactional
public class PlanMessageServiceImpl implements PlanMessageService{
    
    @Autowired
    PlanMessageDao planMessageDao;
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    RoleUserDao roleUserDao;
    
    @Autowired
    CoachAssignedPlanDao coachAssignedPlanDao;
    
    @Autowired
    private MailCommunicationService mailCommunicationService;

    @Override
    public List<PlanMessageDTO> getMessagesByPlan(Integer coachAssignedPlanId, String tipoPlan, Integer roleSelected, Integer userId, Integer receivingUserId) throws Exception, TrainingException {
        return planMessageDao.getMessagesByPlan(coachAssignedPlanId, tipoPlan, roleSelected, userId, receivingUserId);
    }

    @Override
    public PlanMessageDTO saveMessage(PlanMessageDTO message) throws Exception, TrainingException {
        PlanMessage planMessage = new PlanMessage();
        User messageUser = userDao.findById(message.getMessageUserId().getUserId());

        if (message.getReceivingUserId() != null && message.getReceivingUserId().getUserId() != null) {
            User receivingUser = userDao.findById(message.getReceivingUserId().getUserId());
            planMessage.setReceivingUserId(receivingUser);
        }

        if (message.getCoachAssignedPlanId() != null && message.getCoachAssignedPlanId().getId() != null) {
            planMessage.setCoachAssignedPlanId(new CoachAssignedPlan(message.getCoachAssignedPlanId().getId()));
        } else if (message.getCoachExtAthleteId() != null && message.getCoachExtAthleteId().getId() != null) {
            planMessage.setCoachExtAthleteId(new CoachExtAthlete(message.getCoachExtAthleteId().getId()));
        }
        
        if(message.getRoleSelected() != -1 && Objects.equals(message.getRoleSelected(), RoleEnum.COACH_INTERNO.getId())){
            planMessage.setToStar(Boolean.FALSE);
        }
        else  if(message.getRoleSelected() != -1 && Objects.equals(message.getRoleSelected(), RoleEnum.ESTRELLA.getId())){
            planMessage.setToStar(Boolean.TRUE);
        }

        planMessage.setMessage(message.getMessage());
        planMessage.setMessageUserId(messageUser);
        planMessage.setStateId(new Integer(Status.ACTIVE.getId()));
        planMessage.setCreationDate(new Date());
        planMessage.setReaded(Boolean.FALSE);
        PlanMessageDTO dto = PlanMessageDTO.mapFromPlanMessageEntity(planMessageDao.create(planMessage));
        dto.setRoleSelected(message.getRoleSelected());
        if (dto.getMessageUserId() != null) {
            RoleUser roleUserMsg = roleUserDao.findByUserId(dto.getMessageUserId().getUserId());
            dto.getMessageUserId().setRoleId(roleUserMsg.getRoleId().getRoleId());
        }
        return dto;
    }

    @Override
    public Integer getCountMessagesByPlan(Integer coachAssignedPlanId, Integer userId,Integer toUserId, Integer roleSelected) throws Exception, TrainingException {
        return planMessageDao.getCountMessagesByPlan(coachAssignedPlanId, userId, toUserId, roleSelected);
    }

    @Override
    public Integer getCountMessagesReceived(Integer coachAssignedPlanId, Integer userId,Integer toUserId, Integer roleSelected) throws Exception {
        return planMessageDao.getCountMessagesReceived(coachAssignedPlanId, userId, toUserId, roleSelected);
    }

    @Override
    public void readMessages(Integer coachAssignedPlanId, Integer userId,Integer toUserId, Integer roleSelected) throws Exception {
       planMessageDao.readMessages(coachAssignedPlanId, userId, toUserId, roleSelected);
    }

    @Override
    public void readMessage(Integer planMessageId) throws Exception {
        planMessageDao.readMessage(planMessageId);
    }

    @Override
    public Integer getCountMessagesByPlanExt(Integer planId, Integer userId) throws Exception {
       return planMessageDao.getCountMessagesByPlanExt(planId, userId);
    }

    @Override
    public Integer getCountMessagesReceivedExt(Integer planId, Integer userId) throws Exception {
       return planMessageDao.getCountMessagesReceivedExt(planId, userId);
    }

    @Override
    public void readMessagesExt(Integer planId, Integer userId) throws Exception {
          planMessageDao.readMessagesExt(planId, userId);
    }
    
    @Override
    public List<PlanMessageDTO> getMessagesByReceivingUserAndSendingUser(Integer receivingUserId, Integer sendingUserId)throws  Exception {
        return planMessageDao.getMessagesByReceivingUserAndSendingUser(receivingUserId, sendingUserId);
    }

    @Override
    public List<PlanMessageDTO> getResponseTimeMessages(Integer userId, Integer roleId) throws Exception {
        List<UserDTO> users = new ArrayList<>();
        if(roleId == 5) {
           users = mailCommunicationService.getAllRecipientsByStarId(userId);
        } else {
           users = mailCommunicationService.getAllRecipientsByCoachId(userId);           
        }   
        return planMessageDao.getResponseTimeMessages(userId, users);
    }
    
    @Override
    public List<ChartReportDTO> getResponseCountMessages(Integer userId,Integer roleId) throws Exception {
        List<UserDTO> users = new ArrayList<>();
        if(roleId == 5) {
           users = mailCommunicationService.getAllRecipientsByStarId(userId);
        } else {
           users = mailCommunicationService.getAllRecipientsByCoachId(userId);           
        }
        List<PlanMessageDTO> planMessageList = planMessageDao.getResponseCountMessages(userId,users);
        List<ChartReportDTO> charList = new ArrayList<>();
        ChartReportDTO chartReportDTO = null;
        Integer redCount = 0;
        Integer yellowCount = 0;
        Integer greenCount = 0;
        String colour = "";
        for (PlanMessageDTO msg : planMessageList) {
            colour = getColour(msg);
            if(colour.equals("red")) {
                redCount++;
            } else if (colour.equals("yellow")) {
                yellowCount++;
            } else {
                greenCount++;
            }
        }
        
            chartReportDTO = new ChartReportDTO();
            chartReportDTO.setName("Rojo");
            chartReportDTO.setValue(redCount);
            chartReportDTO.setStyle("red");
            charList.add(chartReportDTO);
            
            chartReportDTO = new ChartReportDTO();
            chartReportDTO.setName("Amarillo");
            chartReportDTO.setValue(yellowCount);
            chartReportDTO.setStyle("yellow");
            charList.add(chartReportDTO);
            
            chartReportDTO = new ChartReportDTO();
            chartReportDTO.setName("Verde");
            chartReportDTO.setValue(greenCount);
            chartReportDTO.setStyle("green");
            charList.add(chartReportDTO);
        return charList;
    }
    
    private String getColour(PlanMessageDTO msg) {
        if(msg.getHours() <= 8) {
            return "green";
        } else if (msg.getHours() > 16) {
            return "red";
        } else {
            return "yellow";
        }
    }

    @Override
    public List<PlanMessageDTO> getMessagesNotReadedByReceivingUserAndSendingUser(Integer receivingUserId, Integer sendingUserId) throws Exception {
        return planMessageDao.getMessagesNotReadedByReceivingUserAndSendingUser(receivingUserId, sendingUserId);
    }

    @Override
    public Integer getCountMessagesEmergency(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws Exception {
        return planMessageDao.getCountMessageEmergencyIn(coachAssignedPlanId, userId, roleSelected);
    }

    @Override
    public Integer getCountMessagesEmergencyExt(Integer coachAssignedPlanId, Integer userId) throws Exception {
           return planMessageDao.getCountMessageEmergencyExt(coachAssignedPlanId, userId);
    }

    @Override
    public void resendStarMessages(Integer planId, List<Integer> messages) throws Exception {
        
        CoachAssignedPlan plan = coachAssignedPlanDao.findById(planId);
        Integer starUserId = null;
        if (plan.getStarTeamId() != null && plan.getStarTeamId().getStarUserId() != null) {
            starUserId = plan.getStarTeamId().getStarUserId().getUserId();
        } else {
            throw new Exception("No existe una estrella asignada, comuniquese con el administrador.");
        }
        
        
        for (Integer planMessageId : messages) {
            PlanMessage planMessage = planMessageDao.findById(planMessageId);
            planMessage.setReceivingUserId(new User(starUserId));
            planMessage.setToStar(Boolean.TRUE);
            planMessage.setReaded(Boolean.FALSE);
            planMessage.setCreationDate(Calendar.getInstance().getTime());
            planMessageDao.merge(planMessage);
        }
        
    }

    @Override
    public List<UserResumeDTO> getMessageUsersByUserId(Integer userId) throws Exception {
        List<UserResumeDTO> list = planMessageDao.getMessageUsersByUserId(userId);
        
        for (UserResumeDTO userResumeDTO : list) {
            userResumeDTO.setMsgReceivedCount(planMessageDao.getCountMessagesReceived(-1, userResumeDTO.getUserId(), userId, -1));
        }
        
        
        return list;
    }

    @Override
    public Integer getCountMessagesReceivedByUser(Integer receivingUserId) throws Exception {
       return planMessageDao.getCountMessagesReceivedByUser(receivingUserId);
    }

    @Override
    public PlanMessageDTO findById(Integer id) throws Exception {
         PlanMessageDTO dto = PlanMessageDTO.mapFromPlanMessageEntity(planMessageDao.findById(id));
       
        if (dto.getMessageUserId() != null) {
            RoleUser roleUserMsg = roleUserDao.findByUserId(dto.getMessageUserId().getUserId());
            dto.getMessageUserId().setRoleId(roleUserMsg.getRoleId().getRoleId());
        }
        return dto;
    }
    
}
