package co.com.expertla.training.service.impl.plan;

import co.com.expertla.training.dao.plan.CoachAssignedPlanDao;
import co.com.expertla.training.dao.plan.PlanMessageDao;
import co.com.expertla.training.dao.user.UserDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.exception.TrainingException;
import co.com.expertla.training.model.dto.ChartReportDTO;
import co.com.expertla.training.model.dto.PlanMessageDTO;
import co.com.expertla.training.model.dto.PlanVideoDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.CoachAssignedPlan;
import co.com.expertla.training.model.entities.PlanMessage;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.service.plan.MailCommunicationService;
import co.com.expertla.training.service.plan.PlanMessageService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    CoachAssignedPlanDao coachAssignedPlanDao;
    
    @Autowired
    private MailCommunicationService mailCommunicationService;

    @Override
    public List<PlanMessageDTO> getMessagesByPlan(Integer coachAssignedPlanId) throws Exception, TrainingException {
        return planMessageDao.getMessagesByPlan(coachAssignedPlanId);
    }

    @Override
    public PlanMessageDTO saveMessage(PlanMessageDTO message) throws Exception, TrainingException {
        PlanMessage planMessage = new PlanMessage();
        User messageUser = userDao.findById(message.getMessageUserId().getUserId());
        User receivingUser = userDao.findById(message.getReceivingUserId().getUserId());
        CoachAssignedPlan plan = coachAssignedPlanDao.findById(message.getCoachAssignedPlanId().getId());
        planMessage.setCoachAssignedPlanId(plan);
        planMessage.setMessage(message.getMessage());
        planMessage.setMessageUserId(messageUser);
        planMessage.setReceivingUserId(receivingUser);
        planMessage.setStateId(new Integer(Status.ACTIVE.getId()));
        planMessage.setCreationDate(new Date());
        PlanMessageDTO dto = PlanMessageDTO.mapFromPlanMessageEntity(planMessageDao.create(planMessage));

        return dto;
    }

    @Override
    public Integer getCountMessagesByPlan(Integer coachAssignedPlanId, Integer userId) throws Exception, TrainingException {
        return planMessageDao.getCountMessagesByPlan(coachAssignedPlanId, userId);
    }

    @Override
    public Integer getCountMessagesReceived(Integer coachAssignedPlanId, Integer userId) throws Exception {
        return planMessageDao.getCountMessagesReceived(coachAssignedPlanId, userId);
    }

    @Override
    public void readMessages(Integer coachAssignedPlanId, Integer userId) throws Exception {
       planMessageDao.readMessages(coachAssignedPlanId, userId);
    }

    @Override
    public void readMessage(Integer planMessageId) throws Exception {
        planMessageDao.readMessage(planMessageId);
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
    
}
