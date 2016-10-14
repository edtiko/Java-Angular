package co.com.expertla.training.service.impl.plan;

import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.plan.CoachAssignedPlanDao;
import co.com.expertla.training.dao.plan.MailCommunicationDao;
import co.com.expertla.training.dao.plan.SupervStarCoachDao;
import co.com.expertla.training.model.dto.ChartReportDTO;
import co.com.expertla.training.model.dto.CoachAssignedPlanDTO;
import co.com.expertla.training.model.dto.MailCommunicationDTO;
import co.com.expertla.training.model.dto.PlanMessageDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.MailCommunication;
import co.com.expertla.training.model.entities.SupervStarCoach;
import co.com.expertla.training.service.plan.MailCommunicationService;
import co.com.expertla.training.service.plan.SupervStarCoachService;
import java.util.ArrayList;
import java.util.List;
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
    

    @Override
    public MailCommunication create(MailCommunication mailCommunication) throws Exception {
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
    public MailCommunication findById(MailCommunication mailCommunication) throws Exception {
        return mailCommunicationDao.findById(mailCommunication);
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
}
