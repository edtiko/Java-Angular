/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.plan;

import co.com.expertla.training.exception.TrainingException;
import co.com.expertla.training.model.dto.ChartReportDTO;
import co.com.expertla.training.model.dto.PlanMessageDTO;
import co.com.expertla.training.model.dto.UserDTO;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface PlanMessageService {

    public List<PlanMessageDTO> getMessagesByPlan(Integer coachAssignedPlanId)throws  Exception, TrainingException;

    public PlanMessageDTO saveMessage(PlanMessageDTO message)throws  Exception, TrainingException;

    public Integer getCountMessagesByPlan(Integer coachAssignedPlanId, Integer userId)throws  Exception, TrainingException;
    
    public Integer getCountMessagesReceived(Integer coachAssignedPlanId, Integer userId)throws  Exception;

    public void readMessages(Integer coachAssignedPlanId, Integer userId) throws  Exception;
    
    public void readMessage(Integer planMessageId) throws  Exception;
    
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
}
