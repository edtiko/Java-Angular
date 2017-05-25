package co.expertic.training.service.impl.configuration;

import co.expertic.training.dao.configuration.StartTeamDao;
import co.expertic.training.dao.user.UserDao;
import co.expertic.training.model.dto.MailCommunicationDTO;
import co.expertic.training.model.dto.NotificationDTO;
import co.expertic.training.model.dto.PlanMessageDTO;
import co.expertic.training.model.dto.StarTeamDTO;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.entities.ColourIndicator;
import co.expertic.training.model.entities.StarTeam;
import co.expertic.training.service.configuration.ColourIndicatorService;
import co.expertic.training.service.configuration.StartTeamService;
import co.expertic.training.service.plan.MailCommunicationService;
import co.expertic.training.service.plan.PlanMessageService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * StartTeam Service Impl <br>
 * Info. Creación: <br>
 * fecha 1/09/2016 <br>
 *
 * @author Andres Felipe Lopez Rodriguez
*
 */
@Service("startTeamService")
@Transactional
public class StartTeamServiceImpl implements StartTeamService {

    @Autowired
    private StartTeamDao startTeamDao;

    @Autowired
    ColourIndicatorService colourIndicatorService;

    @Autowired
    MailCommunicationService mailCommunicationService;

    @Autowired
    PlanMessageService planMessageService;
    
    @Autowired
    UserDao userDao;


    @Override
    public StarTeam create(StarTeam startTeam) throws Exception {
        return startTeamDao.create(startTeam);
    }

    @Override
    public StarTeam store(StarTeam startTeam) throws Exception {
        return startTeamDao.merge(startTeam);
    }

    @Override
    public void remove(StarTeam startTeam) throws Exception {
        startTeamDao.remove(startTeam, startTeam.getStarTeamId());
    }

    @Override
    public List<StarTeam> findAll() throws Exception {
        return startTeamDao.findAll();
    }

    @Override
    public List<StarTeam> findAllActive() throws Exception {
        return startTeamDao.findAllActive();
    }

    @Override
    public List<StarTeamDTO> findPaginate(int first, int max, String order) throws Exception {
        return startTeamDao.findPaginate(first, max, order);
    }

    @Override
    public List<StarTeam> findByStartTeam(StarTeam startTeam) throws Exception {
        return startTeamDao.findByStartTeam(startTeam);
    }

    @Override
    public List<StarTeam> findByFiltro(StarTeam startTeam) throws Exception {
        return startTeamDao.findByFiltro(startTeam);
    }

    @Override
    public StarTeam findBySupervisor(Integer supervisorUserId) throws Exception {
        return startTeamDao.findBySupervisor(supervisorUserId);
    }

    @Override
    public List<UserResumeDTO> findAsesoresByStarUserId(Integer starUserId) throws Exception {
        List<UserResumeDTO> list = startTeamDao.findAsesoresByStarUserId(starUserId);
        List<ColourIndicator> colours = colourIndicatorService.findAll();

        int firstOrder = 0;
        int secondOrder = 0;
        String firstColour = "{'background-color':'white'}";
        String secondColour = "{'background-color':'white'}";
        String thirdColour = "{'background-color':'white'}";
        for (ColourIndicator colour : colours) {
            if (colour.getColourOrder().equals(1)) {
                firstOrder = colour.getHoursSpent();
                firstColour = colour.getColour();
            }
            if (colour.getColourOrder().equals(2)) {
                secondOrder = colour.getHoursSpent();
                secondColour = colour.getColour();
            }
            if (colour.getColourOrder().equals(3)) {
                thirdColour = colour.getColour();
            }
        }
        for (UserResumeDTO asesor : list) {
            List<NotificationDTO> notificationList = userDao.getUserCountNotification(asesor.getUserId(), starUserId);
            Long msgReceived = notificationList.stream().filter(n -> "chat".equals(n.getModule())).mapToLong(n -> n.getCount()).sum();
            Long mailReceived = notificationList.stream().filter(n -> "mail".equals(n.getModule())).mapToLong(n -> n.getCount()).sum();

            asesor.setMsgReceivedCount(msgReceived.intValue());
            asesor.setMailReceivedCount(mailReceived.intValue());
            
            int countFirstColour = 0;
            int countSecondColour = 0;
            int countThirdColour = 0;
            List<MailCommunicationDTO> mails = mailCommunicationService.getMailsByReceivingUserIdFromSendingUser(starUserId, asesor.getUserId());

            List<PlanMessageDTO> messages = planMessageService.getMessagesNotReadedByReceivingUserAndSendingUser(starUserId, asesor.getUserId());

            for (MailCommunicationDTO mail : mails) {
                if (!mail.getRead()) {
                    mail.setHoursSpent(calculateHourDifference(mail.getCreationDate()));
                    if (mail.getHoursSpent() >= 0 && mail.getHoursSpent() <= firstOrder) {
                        countFirstColour++;
                    } else if (mail.getHoursSpent() > firstOrder && mail.getHoursSpent() <= secondOrder) {
                        countSecondColour++;
                    } else {
                        countThirdColour++;
                    }
                }
            }

            for (PlanMessageDTO mail : messages) {
                long hours = (calculateHourDifference(mail.getCreationDate()));
                if (hours >= 0 && hours <= firstOrder) {
                    countFirstColour++;
                } else if (hours > firstOrder && hours <= secondOrder) {
                    countSecondColour++;
                } else {
                    countThirdColour++;
                }
            }
            if (countThirdColour > 0) {
                asesor.setColor(thirdColour.replaceAll("\\{", "").replaceAll("}", "").replaceAll("'", ""));
            } else if (countSecondColour > 0) {
                asesor.setColor(secondColour.replaceAll("\\{", "").replaceAll("}", "").replaceAll("'", ""));
            } else if (countFirstColour > 0) {
                asesor.setColor(firstColour.replaceAll("\\{", "").replaceAll("}", "").replaceAll("'", ""));
            }
        }
        return list;
    }
    
       private long calculateHourDifference(Date creationDate) {
        Date now = new Date();
        long diff = now.getTime() - creationDate.getTime();
        long hoursSpent = diff / (60 * 60 * 1000);
        return hoursSpent;
    }

}
