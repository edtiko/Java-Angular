/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.plan;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.dto.PlanMessageDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.PlanMessage;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public interface PlanMessageDao extends BaseDAO<PlanMessage> {

    public List<PlanMessageDTO> getMessagesByPlan(Integer coachAssignedPlanId, String tipoPlan, Integer roleSelected) throws DAOException;

    public Integer getCountMessagesByPlan(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws DAOException;

    public Integer getCountMessagesReceived(Integer coachAssignedPlanId, Integer userId, Integer toUserId, Integer roleSelected) throws DAOException;

    public void readMessages(Integer coachAssignedPlanId, Integer userId, Integer toUserId, Integer roleSelected) throws DAOException;
    
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
    

    public void readMessage(Integer planMessageId) throws DAOException;

    public Integer getCountMessagesByPlanExt(Integer planId, Integer userId) throws DAOException;

    public Integer getCountMessagesReceivedExt(Integer planId, Integer userId) throws DAOException;

    public void readMessagesExt(Integer planId, Integer userId) throws DAOException;


    /**
     * Consulta los tiempos de respuesta de los mensajes <br>
     * Info. Creación: <br>
     * fecha Sep 28, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @param users
     * @return 
     * @throws Exception 
     */
    public List<PlanMessageDTO> getResponseTimeMessages(Integer userId, List<UserDTO> users)throws  Exception;
    
    /**
     * Consulta los tiempos de respuesta en horas <br>
     * Info. Creación: <br>
     * fecha Sep 28, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @param users
     * @return 
     * @throws Exception 
     */
    public List<PlanMessageDTO> getResponseCountMessages(Integer userId,List<UserDTO> users) throws Exception;
    
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
    
    public int getCountMessageEmergencyIn(Integer planId, Integer fromUserId, Integer roleSelected) throws DAOException;

    public int getCountMessageEmergencyExt(Integer planId, Integer fromUserId) throws DAOException;

}
