/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.impl.plan;

import co.expertic.training.dao.plan.CoachExtAthleteDao;
import co.expertic.training.enums.RoleEnum;
import co.expertic.training.enums.StateEnum;
import co.expertic.training.model.dto.CoachExtAthleteDTO;
import co.expertic.training.model.dto.MailCommunicationDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.PlanMessageDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.entities.CoachExtAthlete;
import co.expertic.training.model.entities.ColourIndicator;
import co.expertic.training.model.entities.State;
import co.expertic.training.model.entities.TrainingPlanUser;
import co.expertic.training.model.entities.User;
import co.expertic.training.model.util.UtilDate;
import co.expertic.training.service.configuration.ColourIndicatorService;
import co.expertic.training.service.configuration.DisciplineService;
import co.expertic.training.service.plan.CoachExtAthleteService;
import co.expertic.training.service.plan.MailCommunicationService;
import co.expertic.training.service.plan.PlanAudioService;
import co.expertic.training.service.plan.PlanMessageService;
import co.expertic.training.service.plan.PlanVideoService;
import co.expertic.training.service.plan.TrainingPlanUserService;
import co.expertic.training.service.user.UserService;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin G
 */
@Service
@Transactional
public class CoachExtAthleteServiceImpl implements CoachExtAthleteService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SimpleMailMessage templateMessage;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

    @Autowired
    CoachExtAthleteDao coachExtAthleteDao;

    @Autowired
    UserService userService;

    @Autowired
    DisciplineService disciplineUserService;

    @Autowired
    MailCommunicationService mailCommunicationService;

    @Autowired
    ColourIndicatorService colourIndicatorService;

    @Autowired
    PlanMessageService planMessageService;

    @Autowired
    PlanVideoService planVideoService;

    @Autowired
    PlanAudioService planAudioService;
    
    @Autowired
    TrainingPlanUserService trainingPlanUserService;

    @Override
    public void create(CoachExtAthleteDTO dto) throws Exception {

        dto.getAthleteUserId().setRoleId(RoleEnum.ATLETA.getId());
        User user = userService.createInternalUser(dto.getAthleteUserId());

        UserDTO coach = userService.findById(dto.getCoachUserId().getUserId());
       TrainingPlanUser tplanUser = trainingPlanUserService.getTrainingPlanUserByUser(new User(coach.getUserId()));
        CoachExtAthlete entity = new CoachExtAthlete();
        entity.setTrainingPlanUserId(tplanUser);
        entity.setUserTrainingId(user);
        entity.setStateId(new State(StateEnum.ACTIVE.getId()));
        entity.setCreationDate(Calendar.getInstance().getTime());
        coachExtAthleteDao.create(entity);

        String message
                = "Sr(a) " + dto.getAthleteUserId().getFullName()
                + ", El Coach: " + coach.getFullName() + " te invito a ser parte de su equipo en Pro-Custom-Training. ";
        sendEmail(message, user.getEmail());
    }

    @Override
    public List<UserResumeDTO> getAthletes(Integer userId, String state) throws Exception {
    
        List<UserResumeDTO> athletes = coachExtAthleteDao.getAthletes(userId, state);
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

        for (UserResumeDTO athlete : athletes) {
            
            Integer athleteUserId = athlete.getUserId();
            Integer coachUserId = userId;    
            Integer coachExtAthleteId = athlete.getCoachExtAthleteId();
            athlete.setMsgReceivedCount(planMessageService.getCountMessagesReceivedExt(coachExtAthleteId, athleteUserId));
            athlete.setMailReceivedCount(mailCommunicationService.getCountMailsReceivedExt(coachExtAthleteId, athleteUserId));
            athlete.setVideoReceivedCount(planVideoService.getCountVideosReceivedExt(coachExtAthleteId, athleteUserId));
            athlete.setAudioReceivedCount(planAudioService.getCountAudiosReceivedExt(coachExtAthleteId, athleteUserId));
            int countFirstColour = 0;
            int countSecondColour = 0;
            int countThirdColour = 0;
            List<MailCommunicationDTO> mails = mailCommunicationService.getMailsByReceivingUserIdFromSendingUser(coachUserId, athleteUserId);
            List<PlanMessageDTO> messages = planMessageService.getMessagesNotReadedByReceivingUserAndSendingUser(coachUserId, athleteUserId);

            for (MailCommunicationDTO mail : mails) {
                if (!mail.getRead()) {
                    mail.setHoursSpent(UtilDate.calculateHourDifference(mail.getCreationDate()));
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
                long hours = (UtilDate.calculateHourDifference(mail.getCreationDate()));
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
        return athletes;
    }

    @Override
    public void retireAthlete(Integer coachExtAthleteId) throws Exception {
        CoachExtAthlete e = coachExtAthleteDao.findById(coachExtAthleteId);
        e.setStateId(new State(StateEnum.RETIRED.getId()));
        coachExtAthleteDao.merge(e);
    }

    @Override
    public CoachExtAthleteDTO findByAthleteUserId(Integer athleteUserId) throws Exception {
        return coachExtAthleteDao.findByAthleteUserId(athleteUserId);
    }

    @Override
    public List<UserResumeDTO> getUserAthletes(String query) throws Exception {
        return coachExtAthleteDao.getUserAthletes(query);
    }

    @Override
    public void sendInvitation(CoachExtAthleteDTO dto) throws Exception {

        UserDTO user = userService.findById(dto.getAthleteUserId().getUserId());
        UserDTO coach = userService.findById(dto.getCoachUserId().getUserId());
        TrainingPlanUser tplanUser = trainingPlanUserService.getTrainingPlanUserByUser(new User(coach.getUserId()));
        CoachExtAthlete entity = new CoachExtAthlete();
        entity.setTrainingPlanUserId(tplanUser);
        entity.setUserTrainingId(new User(dto.getAthleteUserId().getUserId()));
        entity.setStateId(new State(StateEnum.PENDING.getId()));
        entity.setCreationDate(Calendar.getInstance().getTime());
        coachExtAthleteDao.create(entity);

        // Create a thread safe "copy" of the template message and customize it
        String message
                = "Sr(a) " + user.getFullName()
                + ", El Coach: " + coach.getFullName() + " te invito a ser parte de su equipo en Pro-Custom-Training. ";
        sendEmail(message, user.getEmail());
    }

    @Override
    public boolean sendEmail(String message, String email) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(email);
        msg.setText(message);
        try {
            this.mailSender.send(msg);
            return true;
        } catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public CoachExtAthleteDTO getInvitation(Integer userId) throws Exception {
        return coachExtAthleteDao.getInvitation(userId);
    }

    @Override
    public Integer acceptInvitation(Integer coachExtAthleteId) throws Exception {
        CoachExtAthlete e = coachExtAthleteDao.findById(coachExtAthleteId);
        e.setStateId(new State(StateEnum.ACTIVE.getId()));
        coachExtAthleteDao.merge(e);
        return e.getTrainingPlanUserId().getTrainingPlanUserId();
    }

    @Override
    public Integer rejectInvitation(Integer coachExtAthleteId) throws Exception {
        CoachExtAthlete e = coachExtAthleteDao.findById(coachExtAthleteId);
        e.setStateId(new State(StateEnum.REJECTED.getId()));
        coachExtAthleteDao.merge(e);
        return e.getTrainingPlanUserId().getTrainingPlanUserId();
    }

    @Override
    public Integer getCountAthletesAvailable(Integer userId) throws Exception {
        return coachExtAthleteDao.getCountAthletesAvailable(userId);
    }

    @Override
    public List<UserResumeDTO> findAthletesByUserPaginate(Integer userId, PaginateDto paginateDto) throws Exception {

        paginateDto.setPage((paginateDto.getPage() - 1) * paginateDto.getLimit());
        List<UserResumeDTO> athletes = coachExtAthleteDao.findAthletesByUserPaginate(userId, paginateDto);
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

        for (UserResumeDTO athlete : athletes) {
            int countFirstColour = 0;
            int countSecondColour = 0;
            int countThirdColour = 0;
            List<MailCommunicationDTO> mails = mailCommunicationService.getMailsByReceivingUserIdFromSendingUser(userId, athlete.getUserId());

            List<PlanMessageDTO> messages = planMessageService.getMessagesNotReadedByReceivingUserAndSendingUser(userId, athlete.getUserId());

            for (MailCommunicationDTO mail : mails) {
                if (!mail.getRead()) {
                    mail.setHoursSpent(UtilDate.calculateHourDifference(mail.getCreationDate()));
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
                long hours = (UtilDate.calculateHourDifference(mail.getCreationDate()));
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
        return athletes;
    }

}
