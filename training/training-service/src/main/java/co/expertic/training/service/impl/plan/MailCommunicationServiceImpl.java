package co.expertic.training.service.impl.plan;

import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.plan.CoachAssignedPlanDao;
import co.expertic.training.dao.plan.MailCommunicationDao;
import co.expertic.training.dao.plan.SupervStarCoachDao;
import co.expertic.training.enums.RoleEnum;
import co.expertic.training.enums.StateEnum;
import co.expertic.training.model.dto.ChartReportDTO;
import co.expertic.training.model.dto.CoachAssignedPlanDTO;
import co.expertic.training.model.dto.MailCommunicationDTO;
import co.expertic.training.model.dto.PlanMessageDTO;
import co.expertic.training.model.dto.PlanVideoDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.CoachAssignedPlan;
import co.expertic.training.model.entities.CoachExtAthlete;
import co.expertic.training.model.entities.MailCommunication;
import co.expertic.training.model.entities.SupervStarCoach;
import co.expertic.training.model.entities.User;
import co.expertic.training.service.plan.MailCommunicationService;
import co.expertic.training.service.plan.PlanMessageService;
import co.expertic.training.service.plan.PlanVideoService;
import co.expertic.training.service.plan.SupervStarCoachService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Service Impl for MailCommunication<br>
* Creation Date : <br>
* date 12/09/2016 <br>
* @author Angela Ramírez
**/
@Service("mailCommunicationService")
@Transactional
public class MailCommunicationServiceImpl implements MailCommunicationService {
    
    @Autowired
    private MailCommunicationDao mailCommunicationDao;
    @Autowired
    private CoachAssignedPlanDao coachAssignedPlanDao;
    @Autowired
    private SupervStarCoachDao supervStarCoachDao;
    @Autowired
    private SupervStarCoachService supervStarCoachService;
    @Autowired
    private PlanMessageService planMessageService;
    @Autowired
    private PlanVideoService planVideoService;

    @Override
    public MailCommunication create(MailCommunicationDTO dto) throws Exception {
        MailCommunication mailCommunication = new MailCommunication();
        mailCommunication.setMessage(dto.getMessage());
        mailCommunication.setSubject(dto.getSubject());
        mailCommunication.setReceivingUser(new User(dto.getReceivingUser().getUserId()));
        mailCommunication.setSendingUser(new User(dto.getSendingUser().getUserId()));
        mailCommunication.setRead(Boolean.FALSE);
        mailCommunication.setStateId(StateEnum.ACTIVE.getId());
        mailCommunication.setCreationDate(new Date());
        if (dto.getRoleSelected() != null && dto.getRoleSelected() != -1 && Objects.equals(dto.getRoleSelected(), RoleEnum.COACH_INTERNO.getId())) {
            mailCommunication.setToStar(Boolean.FALSE);
        } else if (dto.getRoleSelected() != null && dto.getRoleSelected() != -1 && Objects.equals(dto.getRoleSelected(), RoleEnum.ESTRELLA.getId())) {
            mailCommunication.setToStar(Boolean.TRUE);
        }

        if (dto.getCoachAssignedPlanId() != null) {
            mailCommunication.setCoachAssignedPlanId(new CoachAssignedPlan(dto.getCoachAssignedPlanId()));
        } else if (dto.getCoachExtAthleteId() != null) {
            mailCommunication.setCoachExtAthleteId(new CoachExtAthlete(dto.getCoachExtAthleteId()));
        }
        return mailCommunicationDao.create(mailCommunication);
    }

    @Override
    public MailCommunication store(MailCommunication mailCommunication) throws Exception {
        return mailCommunicationDao.merge(mailCommunication);
    }

    @Override
    public void remove(MailCommunication mailCommunication) throws Exception {
        mailCommunicationDao.remove(mailCommunication, mailCommunication.getMailCommunicationId());
    }
    
    @Override
    public MailCommunication findById(Integer mailCommunicationId) throws Exception {
        return mailCommunicationDao.findById(mailCommunicationId);
    }

    @Override
    public List<MailCommunicationDTO> getMailsByUserId(Integer userId) throws Exception {
        return mailCommunicationDao.getMailsByUserId(userId);
    }

    @Override
    public List<MailCommunicationDTO> getMailsByReceivingUserIdFromSendingUser(Integer receivingUserId, Integer sendingUserId) throws Exception {
        return mailCommunicationDao.getMailsByReceivingUserIdFromSendingUser(receivingUserId, sendingUserId);
    }
    
    @Override
    public List<MailCommunicationDTO> getSentMailsByUserId(Integer userId) throws Exception {
        return mailCommunicationDao.getSentMailsByUserId(userId);
    }

    @Override
    public List<MailCommunicationDTO> getMailsByUserIdRead(Integer userId, boolean read) throws DAOException {
        return mailCommunicationDao.getMailsByUserIdRead(userId, read);
    }

    @Override
    public List<MailCommunicationDTO> getMailsByReceivingUserIdFromSendingUserRead(Integer receivingUserId, Integer sendingUserId, boolean read) throws Exception {
        return mailCommunicationDao.getMailsByReceivingUserIdFromSendingUserRead(receivingUserId, sendingUserId, read);
    }
    
    @Override
    public List<UserDTO> getAllRecipientsByCoachId(Integer userId) throws Exception {
        List<UserDTO> recipients = new ArrayList<>();
        List<CoachAssignedPlanDTO> starsAndAthletes = coachAssignedPlanDao.findByCoachUserId(userId);
        List<SupervStarCoach> supervisors = supervStarCoachDao.findByCoachId(userId);
        for (SupervStarCoach supervisor : supervisors) {
            recipients.add(UserDTO.mapFromUserEntity(supervisor.getSupervisorId()));
        }
        for (CoachAssignedPlanDTO u : starsAndAthletes) {
            recipients.add(u.getAthleteUserId());
            recipients.add(u.getStarUserId());
        }
        return recipients;
    }
    
    @Override
    public List<UserDTO> getAllRecipientsByStarId(Integer userId) throws Exception {
        List<UserDTO> users = supervStarCoachService.findByStarId(userId);
        List<CoachAssignedPlanDTO> coachesAndAthletes = coachAssignedPlanDao.findByStarUserId(userId);
        for (CoachAssignedPlanDTO u : coachesAndAthletes) {
            users.add(u.getAthleteUserId());
            users.add(u.getCoachUserId());
        }
        return users;
    }
    
    @Override
    public List<PlanMessageDTO> getResponseTimeMails(Integer userId, Integer roleId) throws Exception {
        List<UserDTO> users = new ArrayList<>();
        if(roleId == 5) {
           users = getAllRecipientsByStarId(userId);
        } else {
           users = getAllRecipientsByCoachId(userId);           
        }   
        return mailCommunicationDao.getResponseTimeMails(userId, users);
    }
    
    @Override
    public List<ChartReportDTO> getResponseCountMails(Integer userId,Integer roleId) throws Exception {
        List<UserDTO> users = new ArrayList<>();
        if(roleId == 5) {
           users = getAllRecipientsByStarId(userId);
        } else {
           users = getAllRecipientsByCoachId(userId);           
        }
        List<PlanMessageDTO> planMessageList = mailCommunicationDao.getResponseCountMails(userId,users);
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
    public List<ChartReportDTO> getPerformance(Integer userId,Integer roleId) throws Exception {
        List<ChartReportDTO> planMessageList = planMessageService.getResponseCountMessages(userId,roleId);
        List<ChartReportDTO> videoList = planVideoService.getResponseCountVideo(userId,roleId);
        List<ChartReportDTO> charList = getResponseCountMails(userId, roleId);
        ChartReportDTO chartReportDTO = null;
        Integer redCount = 0;
        Integer yellowCount = 0;
        Integer greenCount = 0;
        String colour = "";
        for (ChartReportDTO msg : planMessageList) {
            if(msg.getStyle().equals("red")) {
                redCount = redCount + msg.getValue();
            } else if (msg.getStyle().equals("yellow")) {
                yellowCount = yellowCount + msg.getValue();
            } else {
                greenCount = greenCount + msg.getValue();
            }
        }
        
        for (ChartReportDTO msg : videoList) {
            if(msg.getStyle().equals("red")) {
                redCount = redCount + msg.getValue();
            } else if (msg.getStyle().equals("yellow")) {
                yellowCount = yellowCount + msg.getValue();
            } else {
                greenCount = greenCount + msg.getValue();
            }
        }
        
        
        
        for (ChartReportDTO msg : charList) {
            if(msg.getStyle().equals("red")) {
                redCount = redCount + msg.getValue();
            } else if (msg.getStyle().equals("yellow")) {
                yellowCount = yellowCount + msg.getValue();
            } else {
                greenCount = greenCount + msg.getValue();
            }
        }
        
        charList = new ArrayList<>();

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
    
    @Override
    public PlanMessageDTO getResponseTime(Integer userId, Integer roleId)throws  Exception {
        List<PlanMessageDTO> planMessageList = planMessageService.getResponseTimeMessages(userId,roleId);
        List<PlanVideoDTO> videoList = planVideoService.getResponseTimeVideos(userId,roleId);
        List<PlanMessageDTO> mailList = getResponseTimeMails(userId, roleId);
        Double i = 0d;
        Double sum = 0d;
        for (PlanMessageDTO msg : planMessageList) {
            i++;
            sum = sum + msg.getHours();
        }
        i--;
        sum = sum - planMessageList.get(planMessageList.size() -1 ).getHours();
        for (PlanVideoDTO msg : videoList) {
            i++;
            sum = sum + msg.getHours();
        }
        i--;
        sum = sum - videoList.get(videoList.size() -1 ).getHours();
     
        
        for (PlanMessageDTO msg : mailList) {
            i++;
            sum = sum + msg.getHours();
        }
        i--;
        sum = sum - mailList.get(mailList.size() -1 ).getHours();

        PlanMessageDTO plan = new PlanMessageDTO();
        plan.setHours(sum/i);
        return plan;
        
    }

    @Override
    public Integer getCountMailsByPlan(Integer planId, Integer userId, Integer roleSelected) throws Exception {
       return mailCommunicationDao.getCountMailsByPlan(planId, userId,roleSelected);
    }

    @Override
    public Integer getCountMailsByPlanExt(Integer planId, Integer userId) throws Exception {
          return mailCommunicationDao.getCountMailsByPlanExt(planId, userId);
    }

    @Override
    public Integer getCountMailsReceived(Integer planId, Integer userId, Integer receiveUserId, Integer roleSelected) throws Exception {
        return mailCommunicationDao.getCountMailsReceived(planId, userId,receiveUserId,roleSelected);
    }

    @Override
    public Integer getCountMailsReceivedExt(Integer planId, Integer userId) throws Exception {
        return mailCommunicationDao.getCountMailsReceivedExt(planId, userId);
    }

    @Override
    public List<MailCommunicationDTO> getMailsByPlan(String tipoPlan, Integer userId, Integer planId, Integer roleSelected) throws Exception {
        return mailCommunicationDao.getMailsByPlan(tipoPlan, userId, planId,roleSelected);
    }

    @Override
    public Integer getMailsEmergencyByPlan(Integer planId, Integer fromUserId, Integer roleSelected) throws Exception {
        return mailCommunicationDao.getMailsEmergencyByPlan(planId, fromUserId,roleSelected);
    }
    
    @Override
    public Integer getMailsEmergencyByPlanExt(Integer planId, Integer fromUserId) throws Exception {
        return mailCommunicationDao.getMailsEmergencyByPlanExt(planId, fromUserId);
    }
}
