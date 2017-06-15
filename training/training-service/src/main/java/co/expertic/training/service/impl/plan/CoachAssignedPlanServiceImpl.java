/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.impl.plan;

import co.expertic.training.dao.plan.CoachAssignedPlanDao;
import co.expertic.training.dao.plan.TrainingUserSerieDao;
import co.expertic.training.dao.user.UserActivityPerformanceDao;
import co.expertic.training.dao.user.UserDao;
import co.expertic.training.enums.RoleEnum;
import co.expertic.training.model.dto.CoachAssignedPlanDTO;
import co.expertic.training.model.dto.MailCommunicationDTO;
import co.expertic.training.model.dto.NotificationDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.PlanAudioDTO;
import co.expertic.training.model.dto.PlanMessageDTO;
import co.expertic.training.model.dto.PlanVideoDTO;
import co.expertic.training.model.dto.ReportCountDTO;
import co.expertic.training.model.dto.SemaforoDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.entities.CoachAssignedPlan;
import co.expertic.training.model.entities.ColourIndicator;
import co.expertic.training.service.configuration.ColourIndicatorService;
import co.expertic.training.service.plan.CoachAssignedPlanService;
import co.expertic.training.service.plan.MailCommunicationService;
import co.expertic.training.service.plan.PlanAudioService;
import co.expertic.training.service.plan.PlanMessageService;
import co.expertic.training.service.plan.PlanVideoService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    UserActivityPerformanceDao userActivityPerformanceDao;

    @Autowired
    TrainingUserSerieDao trainingUserSerieDao;

    @Autowired
    ColourIndicatorService colourIndicatorService;

    @Autowired
    MailCommunicationService mailCommunicationService;

    @Autowired
    PlanMessageService planMessageService;

    @Autowired
    PlanVideoService planVideoService;

    @Autowired
    PlanAudioService planAudioService;

    @Override
    public List<CoachAssignedPlanDTO> findAthletesByUserRole(Integer userId, Integer roleId, PaginateDto paginateDto) throws Exception {

        paginateDto.setPage((paginateDto.getPage() - 1) * paginateDto.getLimit());
        List<CoachAssignedPlanDTO> athletes = dao.findAthletesByUserRole(userId, roleId, paginateDto);
        List<ColourIndicator> colours = colourIndicatorService.findAll();

        int firstOrder = 0;
        int secondOrder = 0;
        int thirdOrder = 0;
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
                thirdOrder = colour.getHoursSpent();
                thirdColour = colour.getColour();
            }
        }

        for (CoachAssignedPlanDTO athlete : athletes) {
            int countFirstColour = 0;
            int countSecondColour = 0;
            int countThirdColour = 0;
            Date now = Calendar.getInstance().getTime();
            Integer athleteUserId = athlete.getAthleteUserId().getUserId();
            Integer numActivities = userActivityPerformanceDao.getNumActivities(athleteUserId, athlete.getCreationDate(), now);
            Integer goalActivities = trainingUserSerieDao.getCountPlanWorkoutByUser(athleteUserId);
            Integer percentaje = 0;
            Integer coachUserId = athlete.getUserCoachId();

            if (goalActivities > 0) {
                percentaje = (numActivities / goalActivities) * 100;
            }
            athlete.setNumActivities(numActivities);
            athlete.setGoalActivities(goalActivities);
            athlete.setPercentaje(percentaje);

            List<MailCommunicationDTO> mails = mailCommunicationService.getMailsByReceivingUserIdFromSendingUser(userId, athleteUserId);
            List<PlanMessageDTO> messages = planMessageService.getMessagesNotReadedByReceivingUserAndSendingUser(userId, athleteUserId);
            Map<String, Object> parameters = new HashMap<>();
            List<PlanAudioDTO> audios = new ArrayList<>();
            parameters.put("planId", athlete.getId());
            if (roleId == RoleEnum.ESTRELLA.getId()) {
                parameters.put("userId", coachUserId);
                parameters.put("toUserId", userId);

                audios = planAudioService.getAudiosByUser(athlete.getId(), coachUserId, userId, "from", "IN", -1);
            } else {
                parameters.put("userId", athleteUserId);
                parameters.put("toUserId", userId);

                audios = planAudioService.getAudiosByUser(athlete.getId(), athleteUserId, userId, "from", "IN", -1);
            }
            parameters.put("fromto", "from");
            parameters.put("tipoPlan", "IN");
            parameters.put("roleSelected", -1);
            List<PlanVideoDTO> videos = planVideoService.getVideosByUser(parameters);

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

            for (PlanMessageDTO msg : messages) {
                if (!msg.getRead()) {
                    long hours = (calculateHourDifference(msg.getCreationDate()));
                    if (hours >= 0 && hours <= firstOrder) {
                        countFirstColour++;
                    } else if (hours > firstOrder && hours <= secondOrder) {
                        countSecondColour++;
                    } else {
                        countThirdColour++;
                    }
                }
            }

            for (PlanVideoDTO video : videos) {
                if (!video.getRead()) {
                    long hours = (calculateHourDifference(video.getCreateDate()));
                    if (hours >= 0 && hours <= firstOrder) {
                        countFirstColour++;
                    } else if (hours > firstOrder && hours <= secondOrder) {
                        countSecondColour++;
                    } else {
                        countThirdColour++;
                    }
                }
            }

            for (PlanAudioDTO audio : audios) {
                if (!audio.getRead()) {
                long hours = (calculateHourDifference(audio.getCreateDate()));
                if (hours >= 0 && hours <= firstOrder) {
                    countFirstColour++;
                } else if (hours > firstOrder && hours <= secondOrder) {
                    countSecondColour++;
                } else {
                    countThirdColour++;
                }
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
        return athletes;
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

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("planId", athlete.getPlanId());
            parameters.put("userId", athlete.getUserId());
            parameters.put("toUserId", coachUserId);
            parameters.put("fromto", "from");
            parameters.put("tipoPlan", "IN");
            parameters.put("roleSelected", -1);
            List<PlanVideoDTO> videos = planVideoService.getVideosByUser(parameters);
            List<PlanAudioDTO> audios = planAudioService.getAudiosByUser(athlete.getPlanId(), athlete.getUserId(), coachUserId, "from", "IN", -1);

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

            for (PlanMessageDTO msg : messages) {
                if (!msg.getRead()) {
                    long hours = (calculateHourDifference(msg.getCreationDate()));
                    if (hours >= 0 && hours <= firstOrder) {
                        countFirstColour++;
                    } else if (hours > firstOrder && hours <= secondOrder) {
                        countSecondColour++;
                    } else {
                        countThirdColour++;
                    }
                }
            }

            for (PlanVideoDTO video : videos) {
                if (!video.getRead()) {
                    long hours = (calculateHourDifference(video.getCreateDate()));
                    if (hours >= 0 && hours <= firstOrder) {
                        countFirstColour++;
                    } else if (hours > firstOrder && hours <= secondOrder) {
                        countSecondColour++;
                    } else {
                        countThirdColour++;
                    }
                }
            }

            for (PlanAudioDTO audio : audios) {
                if (!audio.getRead()) {
                long hours = (calculateHourDifference(audio.getCreateDate()));
                if (hours >= 0 && hours <= firstOrder) {
                    countFirstColour++;
                } else if (hours > firstOrder && hours <= secondOrder) {
                    countSecondColour++;
                } else {
                    countThirdColour++;
                }
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

    @Override
    public List<UserResumeDTO> findAthletesByCoachStar(Integer coachUserId, Integer starUserId) throws Exception {
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
            List<NotificationDTO> notificationList = userDao.getUserCountNotificationStar(athlete.getUserId(), coachUserId, starUserId);
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
            List<MailCommunicationDTO> mails = mailCommunicationService.getMailsByReceivingUserIdFromSendingUser(starUserId, athlete.getUserId());

            List<PlanMessageDTO> messages = planMessageService.getMessagesNotReadedByReceivingUserAndSendingUser(starUserId, athlete.getUserId());

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("planId", athlete.getPlanId());
            parameters.put("userId", coachUserId);
            parameters.put("toUserId", starUserId);
            parameters.put("fromto", "from");
            parameters.put("tipoPlan", "IN");
            parameters.put("roleSelected", RoleEnum.ESTRELLA.getId());
            List<PlanVideoDTO> videos = planVideoService.getVideosByUser(parameters);
            List<PlanAudioDTO> audios = planAudioService.getAudiosByUser(athlete.getPlanId(),coachUserId, starUserId, "from", "IN", RoleEnum.ESTRELLA.getId());
            
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

            for (PlanMessageDTO msg : messages) {
                if (!msg.getRead()) {
                    long hours = (calculateHourDifference(msg.getCreationDate()));
                    if (hours >= 0 && hours <= firstOrder) {
                        countFirstColour++;
                    } else if (hours > firstOrder && hours <= secondOrder) {
                        countSecondColour++;
                    } else {
                        countThirdColour++;
                    }
                }
            }

            for (PlanVideoDTO video : videos) {
                if (!video.getRead()) {
                    long hours = (calculateHourDifference(video.getCreateDate()));
                    if (hours >= 0 && hours <= firstOrder) {
                        countFirstColour++;
                    } else if (hours > firstOrder && hours <= secondOrder) {
                        countSecondColour++;
                    } else {
                        countThirdColour++;
                    }
                }
            }

            for (PlanAudioDTO audio : audios) {
                if (!audio.getRead()) {
                    long hours = (calculateHourDifference(audio.getCreateDate()));
                    if (hours >= 0 && hours <= firstOrder) {
                        countFirstColour++;
                    } else if (hours > firstOrder && hours <= secondOrder) {
                        countSecondColour++;
                    } else {
                        countThirdColour++;
                    }
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

}
