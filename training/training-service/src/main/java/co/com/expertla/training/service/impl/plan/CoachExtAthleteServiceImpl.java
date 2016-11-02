/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.impl.plan;

import co.com.expertla.training.dao.plan.CoachExtAthleteDao;
import co.com.expertla.training.enums.RoleEnum;
import co.com.expertla.training.enums.StateEnum;
import co.com.expertla.training.model.dto.CoachExtAthleteDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.CoachExtAthlete;
import co.com.expertla.training.model.entities.State;
import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.service.configuration.DisciplineService;
import co.com.expertla.training.service.plan.CoachExtAthleteService;
import co.com.expertla.training.service.user.UserService;
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
public class CoachExtAthleteServiceImpl implements CoachExtAthleteService{
    
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

    @Override
    public void create(CoachExtAthleteDTO dto) throws Exception {
        
        dto.getAthleteUserId().setRoleId(RoleEnum.ATLETA.getId());
        User user = userService.createInternalUser(dto.getAthleteUserId());
        
        CoachExtAthlete entity = new CoachExtAthlete();
        entity.setTrainingPlanUserId(new TrainingPlanUser(dto.getTrainingPlanUserId()));
        entity.setUserTrainingId(user);
        entity.setStateId(new State(StateEnum.ACTIVE.getId()));
        entity.setCreationDate(Calendar.getInstance().getTime());
        coachExtAthleteDao.create(entity);
    }

    @Override
    public List<CoachExtAthleteDTO> getAthletes(Integer trainingPlanUserId, String state) throws Exception {
        return coachExtAthleteDao.getAthletes(trainingPlanUserId, state);
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
    public List<UserDTO> getUserAthletes(String query) throws Exception {
       return coachExtAthleteDao.getUserAthletes(query);
    }

    @Override
    public void sendInvitation(CoachExtAthleteDTO dto) throws Exception {
        
        UserDTO user = userService.findById(dto.getAthleteUserId().getUserId());
        UserDTO coach = userService.findById(dto.getCoachUserId().getUserId());
        CoachExtAthlete entity = new CoachExtAthlete();
        entity.setTrainingPlanUserId(new TrainingPlanUser(dto.getTrainingPlanUserId()));
        entity.setUserTrainingId(new User(dto.getAthleteUserId().getUserId()));
        entity.setStateId(new State(StateEnum.PENDING.getId()));
        entity.setCreationDate(Calendar.getInstance().getTime());
        coachExtAthleteDao.create(entity);
        
        // Create a thread safe "copy" of the template message and customize it
        String message = 
            "Sr(a) " + user.getFullName()
                + ", El Coach: "+coach.getFullName()+ " te invito a ser parte de su equipo en Pro-Custom-Training. ";
        sendEmail(message, user.getEmail());
    }
    
    @Override
    public boolean sendEmail(String message, String email) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(email);
        msg.setText(message);
        try{
            this.mailSender.send(msg);
            return true;
        }
        catch (MailException ex) {
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
    public void acceptInvitation(Integer coachExtAthleteId) throws Exception {
        CoachExtAthlete e = coachExtAthleteDao.findById(coachExtAthleteId);
        e.setStateId(new State(StateEnum.ACTIVE.getId()));
        coachExtAthleteDao.merge(e);
    }

    @Override
    public void rejectInvitation(Integer coachExtAthleteId) throws Exception {
        CoachExtAthlete e = coachExtAthleteDao.findById(coachExtAthleteId);
        e.setStateId(new State(StateEnum.REJECTED.getId()));
        coachExtAthleteDao.merge(e);
    }
    
    
}
