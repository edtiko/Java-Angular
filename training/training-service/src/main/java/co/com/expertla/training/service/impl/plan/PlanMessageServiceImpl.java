/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.impl.plan;

import co.com.expertla.training.dao.plan.CoachAssignedPlanDao;
import co.com.expertla.training.dao.plan.PlanMessageDao;
import co.com.expertla.training.dao.user.UserDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.exception.TrainingException;
import co.com.expertla.training.model.dto.PlanMessageDTO;
import co.com.expertla.training.model.entities.CoachAssignedPlan;
import co.com.expertla.training.model.entities.PlanMessage;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.service.plan.PlanMessageService;
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

    @Override
    public List<PlanMessageDTO> getMessagesByPlan(Integer coachAssignedPlanId) throws Exception, TrainingException {
        return planMessageDao.getMessagesByPlan(coachAssignedPlanId);
    }

    @Override
    public PlanMessageDTO saveMessage(PlanMessageDTO message) throws Exception, TrainingException {
        PlanMessage planMessage = new PlanMessage();
        User messageUser = userDao.findById(message.getMessageUserId().getUserId());
        CoachAssignedPlan plan = coachAssignedPlanDao.findById(message.getCoachAssignedPlanId().getId());
        planMessage.setCoachAssignedPlanId(plan);
        planMessage.setMessage(message.getMessage());
        planMessage.setMessageUserId(messageUser);
        planMessage.setStateId(new Integer(Status.ACTIVE.getId()));
        planMessage.setCreationDate(new Date());
        PlanMessageDTO dto = PlanMessageDTO.mapFromPlanMessageEntity(planMessageDao.create(planMessage));
        
        if (plan.getTrainingPlanUserId() != null && plan.getStartTeamId() != null) {
            dto.setCountMessagesAthlete(planMessageDao.getCountMessagesReceived(plan.getCoachAssignedPlanId(), plan.getTrainingPlanUserId().getUserId().getUserId()));
            dto.setCountMessagesCoach(planMessageDao.getCountMessagesReceived(plan.getCoachAssignedPlanId(), plan.getStartTeamId().getCoachUserId().getUserId()));
        }
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
    
}
