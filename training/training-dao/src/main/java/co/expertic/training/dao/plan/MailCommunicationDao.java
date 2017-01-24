package co.expertic.training.dao.plan;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.dto.MailCommunicationDTO;
import co.expertic.training.model.dto.PlanMessageDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.MailCommunication;
import java.util.List;

/**
* Dao for MailCommunication<br>
* Creation Date : <br>
* date 12/09/2016 <br>
* @author Angela Ramírez
**/
public interface MailCommunicationDao extends BaseDAO<MailCommunication>{
    
    
    /**
     * Busca mailCommunication por su id <br>
     * Info. Creación: <br>
     * fecha 12/09/2016 <br>
     * @author Angela Ramirez
     * @param mailCommunication
     * @return 
     * @throws Exception 
     */
    public MailCommunication findById(Integer mailCommunication) throws Exception; 
    
    /**
     * Trae todos los mails por userId <br>
     * Creation Date : <br>
     * date 12/09/2016 <br>
     * @author Angela Ramírez
     * @param userId
     * @throws co.expertic.base.jpa.DAOException
     * @return
     */
    public List<MailCommunicationDTO> getMailsByUserId(Integer userId) throws DAOException;
    
    /**
     * Trae todos los mails por receiving user and sendingUser <br>
     * Creation Date : <br>
     * date 12/09/2016 <br>
     * @author Angela Ramírez
     * @param receivingUserId
     * @param sendingUserId
     * @throws co.expertic.base.jpa.DAOException
     * @return
     */
    public List<MailCommunicationDTO> getMailsByReceivingUserIdFromSendingUser(Integer receivingUserId, Integer sendingUserId) throws DAOException;
    
    /**
     * Trae todos los mails enviados por el user Id <br>
     * Creation Date : <br>
     * date 12/09/2016 <br>
     * @author Angela Ramírez
     * @param userId
     * @throws co.expertic.base.jpa.DAOException
     * @return
     */
    public List<MailCommunicationDTO> getSentMailsByUserId(Integer userId) throws DAOException;
    
    /**
     * Trae todos los mails enviados por el user Id <br>
     * Creation Date : <br>
     * date 19/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @param read
     * @throws co.expertic.base.jpa.DAOException
     * @return
     */
    public List<MailCommunicationDTO> getMailsByUserIdRead(Integer userId, boolean read) throws DAOException;
    
    
    /**
     * Trae todos los mails por receiving user and sendingUser <br>
     * Creation Date : <br>
     * date 19/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param receivingUserId
     * @param sendingUserId
     * @param read
     * @return
     * @throws java.lang.Exception
     */
    public List<MailCommunicationDTO> getMailsByReceivingUserIdFromSendingUserRead(Integer receivingUserId, Integer sendingUserId, boolean read) throws Exception;

     
    /**
     * Consulta los tiempos de respuesta de los mails <br>
     * Info. Creación: <br>
     * fecha Sep 28, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @param users
     * @return 
     * @throws Exception 
     */
    public List<PlanMessageDTO> getResponseTimeMails(Integer userId, List<UserDTO> users)throws  Exception;
    
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
    public List<PlanMessageDTO> getResponseCountMails(Integer userId,List<UserDTO> users) throws Exception;

    public Integer getCountMailsByPlan(Integer planId, Integer userId, Integer roleSelected)throws DAOException;

    public Integer getCountMailsByPlanExt(Integer planId, Integer userId)throws DAOException;

    public Integer getCountMailsReceived(Integer planId, Integer userId, Integer receiveUserId, Integer roleSelected)throws DAOException;

    public Integer getCountMailsReceivedExt(Integer planId, Integer userId)throws DAOException;

    public List<MailCommunicationDTO> getMailsByPlan(String tipoPlan, Integer userId, Integer planId, Integer roleSelected)throws DAOException;
    
    public int getMailsEmergencyByPlan(Integer planId, Integer fromUserId, Integer roleSelected) throws DAOException;

    public int getMailsEmergencyByPlanExt(Integer planId, Integer fromUserId) throws DAOException;
}
