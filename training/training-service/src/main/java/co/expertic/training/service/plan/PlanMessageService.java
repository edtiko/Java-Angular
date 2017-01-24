/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.plan;

import co.expertic.training.exception.TrainingException;
import co.expertic.training.model.dto.ChartReportDTO;
import co.expertic.training.model.dto.PlanMessageDTO;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface PlanMessageService {

    public List<PlanMessageDTO> getMessagesByPlan(Integer coachAssignedPlanId, String tipoPlan, Integer roleSelected)throws  Exception, TrainingException;

    public PlanMessageDTO saveMessage(PlanMessageDTO message)throws  Exception, TrainingException;

    public Integer getCountMessagesByPlan(Integer coachAssignedPlanId, Integer userId, Integer roleSelected)throws  Exception, TrainingException;
    
    public Integer getCountMessagesReceived(Integer coachAssignedPlanId, Integer userId, Integer toUserId, Integer roleSelected)throws  Exception;

    public void readMessages(Integer coachAssignedPlanId, Integer userId, Integer toUserId, Integer roleSelected) throws  Exception;
    
    public void readMessage(Integer planMessageId) throws  Exception;

    public Integer getCountMessagesByPlanExt(Integer coachAssignedPlanId, Integer userId)throws  Exception;
    
    public Integer getCountMessagesReceivedExt(Integer coachAssignedPlanId, Integer userId)throws  Exception;
    
    public void readMessagesExt(Integer planId, Integer userId)throws  Exception;
    
    /**
     * Consulta los mensajes para el chat por receiving user id and sending user id <br>
     * Info. Creación: <br>
     * fecha Sep 28, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param receivingUserId
     * @param sendingUserId
     * @return 
     * @throws Exception 
     */
    public List<PlanMessageDTO> getMessagesByReceivingUserAndSendingUser(Integer receivingUserId, Integer sendingUserId)throws  Exception;
    
    /**
     * Consulta los tiempos de respuesta de los mensajes <br>
     * Info. Creación: <br>
     * fecha Sep 28, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @param roleId
     * @return 
     * @throws Exception 
     */
    public List<PlanMessageDTO> getResponseTimeMessages(Integer userId, Integer roleId)throws  Exception;
    
    /**
     * Consulta los tiempos de respuesta en horas <br>
     * Info. Creación: <br>
     * fecha Sep 28, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @param roleId
     * @return 
     * @throws Exception 
     */
    public List<ChartReportDTO> getResponseCountMessages(Integer userId,Integer roleId) throws Exception;
    
    /**
     * Consulta los mensajes para el chat por receiving user id and sending user id <br>
     * Info. Creación: <br>
     * fecha Sep 28, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param receivingUserId
     * @param sendingUserId
     * @return 
     * @throws Exception 
     */
    public List<PlanMessageDTO> getMessagesNotReadedByReceivingUserAndSendingUser(Integer receivingUserId, Integer sendingUserId)throws  Exception;

    public Integer getCountMessagesEmergency(Integer coachAssignedPlanId, Integer userId, Integer roleSelected)throws  Exception;

    public Integer getCountMessagesEmergencyExt(Integer coachAssignedPlanId, Integer userId)throws  Exception;
}
