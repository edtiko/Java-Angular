/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.plan;

import co.com.expertla.training.exception.TrainingException;
import co.com.expertla.training.model.dto.PlanMessageDTO;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface PlanMessageService {

    public List<PlanMessageDTO> getMessagesByPlan(Integer coachAssignedPlanId, String tipoPlan)throws  Exception, TrainingException;

    public PlanMessageDTO saveMessage(PlanMessageDTO message)throws  Exception, TrainingException;

    public Integer getCountMessagesByPlan(Integer coachAssignedPlanId, Integer userId)throws  Exception, TrainingException;
    
    public Integer getCountMessagesReceived(Integer coachAssignedPlanId, Integer userId)throws  Exception;

    public void readMessages(Integer coachAssignedPlanId, Integer userId) throws  Exception;
    
    public void readMessage(Integer planMessageId) throws  Exception;

    public Integer getCountMessagesByPlanExt(Integer coachAssignedPlanId, Integer userId)throws  Exception;
    
    public Integer getCountMessagesReceivedExt(Integer coachAssignedPlanId, Integer userId)throws  Exception;
    
    public void readMessagesExt(Integer planId, Integer userId)throws  Exception;
    
}
