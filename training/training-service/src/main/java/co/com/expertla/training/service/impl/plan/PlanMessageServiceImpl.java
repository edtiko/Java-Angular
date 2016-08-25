/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.impl.plan;

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

    @Override
    public List<PlanMessageDTO> getMessagesByPlan(Integer coachAssignedPlanId) throws Exception, TrainingException {
        return planMessageDao.getMessagesByPlan(coachAssignedPlanId);
    }

    @Override
    public PlanMessageDTO saveMessage(PlanMessageDTO message) throws Exception, TrainingException {
        PlanMessage planMessage = new PlanMessage();
        User messageUser = userDao.findById(message.getMessageUserId().getUserId());
        planMessage.setCoachAssignedPlanId(new CoachAssignedPlan(message.getCoachAssignedPlanId().getId()));
        planMessage.setMessage(message.getMessage());
        planMessage.setMessageUserId(messageUser);
        planMessage.setStateId(new Integer(Status.ACTIVE.getName()));
        planMessage.setCreationDate(new Date());
        return PlanMessageDTO.mapFromPlanMessageEntity(planMessageDao.create(planMessage));
    }
    
}
