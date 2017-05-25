/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.impl.plan;

import co.expertic.training.dao.plan.CoachAssignedPlanDao;
import co.expertic.training.dao.user.UserDao;
import co.expertic.training.model.dto.CoachAssignedPlanDTO;
import co.expertic.training.model.dto.MailCommunicationDTO;
import co.expertic.training.model.dto.NotificationDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.PlanMessageDTO;
import co.expertic.training.model.dto.ReportCountDTO;
import co.expertic.training.model.dto.SemaforoDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.entities.CoachAssignedPlan;
import co.expertic.training.model.entities.ColourIndicator;
import co.expertic.training.service.configuration.ColourIndicatorService;
import co.expertic.training.service.plan.CoachAssignedPlanService;
import co.expertic.training.service.plan.MailCommunicationService;
import co.expertic.training.service.plan.PlanMessageService;
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
public class CoachAssignedPlanServiceImpl implements CoachAssignedPlanService {

    @Autowired
    CoachAssignedPlanDao dao;

    @Autowired
    UserDao userDao;

    @Autowired
    ColourIndicatorService colourIndicatorService;

    @Autowired
    MailCommunicationService mailCommunicationService;

    @Autowired
    PlanMessageService planMessageService;

    @Override
    public List<CoachAssignedPlanDTO> findAthletesByUserRole(Integer userId, Integer roleId, PaginateDto paginateDto) throws Exception {
        return dao.findAthletesByUserRole(userId, roleId, paginateDto);
    }

    @Override
    public CoachAssignedPlanDTO findByAthleteUserId(Integer userId) throws Exception {
        return dao.findByAthleteUserId(userId);
    }

    @Override
    public CoachAssignedPlan create(CoachAssignedPlan coachAssignedPlan) throws Exception {
        return dao.create(coachAssignedPlan);
    }

    @Override
    public List<CoachAssignedPlanDTO> findByStarUserId(Integer userId) throws Exception {
        return dao.findByStarUserId(userId);
    }

    @Override
    public List<UserDTO> findCoachByStarUserId(Integer userId) throws Exception {
        return UserDTO.mapFromUsersEntities(dao.findCoachByStarUserId(userId));
    }

    @Override
    public List<UserResumeDTO> findStarByCoachUserId(Integer userId) throws Exception {
        return dao.findStarByCoachUserId(userId);
    }

    @Override
    public List<SemaforoDTO> findSemaforoByUserId(Integer userId) throws Exception {
        return dao.findSemaforoByUserId(userId);
    }

    @Override
    public List<ReportCountDTO> getCountByPlanRole(Integer userId, Integer roleId) throws Exception {
        return dao.getCountByPlanRole(userId, roleId);
    }

    @Override
    public List<UserResumeDTO> findAthletesByCoachUserId(Integer coachUserId) throws Exception {
        List<UserResumeDTO> list = dao.findAthletesByCoachUserId(coachUserId);
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
        for (UserResumeDTO athlete : list) {
            List<NotificationDTO> notificationList = userDao.getUserCountNotification(athlete.getUserId(), coachUserId);
            Long msgReceived = notificationList.stream().filter(n -> "chat".equals(n.getModule())).mapToLong(n -> n.getCount()).sum();
            Long mailReceived = notificationList.stream().filter(n -> "mail".equals(n.getModule())).mapToLong(n -> n.getCount()).sum();
            Long audioReceived = notificationList.stream().filter(n -> "audio".equals(n.getModule())).mapToLong(n -> n.getCount()).sum();
            Long videoReceived = notificationList.stream().filter(n -> "video".equals(n.getModule())).mapToLong(n -> n.getCount()).sum();

            athlete.setMsgReceivedCount(msgReceived.intValue());
            athlete.setMailReceivedCount(mailReceived.intValue());
            athlete.setAudioReceivedCount(audioReceived.intValue());
            athlete.setVideoReceivedCount(videoReceived.intValue());

            int countFirstColour = 0;
            int countSecondColour = 0;
            int countThirdColour = 0;
            List<MailCommunicationDTO> mails = mailCommunicationService.getMailsByReceivingUserIdFromSendingUser(coachUserId, athlete.getUserId());

            List<PlanMessageDTO> messages = planMessageService.getMessagesNotReadedByReceivingUserAndSendingUser(coachUserId, athlete.getUserId());

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
                athlete.setColor(thirdColour.replaceAll("\\{", "").replaceAll("}", "").replaceAll("'", ""));
            } else if (countSecondColour > 0) {
                athlete.setColor(secondColour.replaceAll("\\{", "").replaceAll("}", "").replaceAll("'", ""));
            } else if (countFirstColour > 0) {
                athlete.setColor(firstColour.replaceAll("\\{", "").replaceAll("}", "").replaceAll("'", ""));
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

    @Override
    public CoachAssignedPlanDTO findByStarAthleteUserId(Integer athleteUserId, Integer starUserId) throws Exception {
        return dao.findByStarAthleteUserId(athleteUserId, starUserId);
    }

    @Override
    public void setStarManageMessages(Integer planId) throws Exception {
        CoachAssignedPlan plan = dao.findById(planId);
        if (plan.getStarManageMessages() != null) {
            plan.setStarManageMessages(!plan.getStarManageMessages());
        } else {
            plan.setStarManageMessages(Boolean.TRUE);
        }
        dao.merge(plan);
    }

}
