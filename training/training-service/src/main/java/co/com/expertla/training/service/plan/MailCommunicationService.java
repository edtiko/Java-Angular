package co.com.expertla.training.service.plan;

import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.ChartReportDTO;
import co.com.expertla.training.model.dto.MailCommunicationDTO;
import co.com.expertla.training.model.dto.PlanMessageDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.MailCommunication;
import java.util.List;

/**
* Service for MailCommunication<br>
* Creation Date : <br>
* date 12/09/2016 <br>
* @author Angela Ramírez
**/
public interface MailCommunicationService {
    
    /**
     * Crea mailCommunication <br>
     * Info. Creación: <br>
     * fecha 12/09/2016 <br>
     * @author Angela Ramirez
     * @param mailCommunication
     * @return 
     * @throws Exception 
     */
    public MailCommunication create(MailCommunication mailCommunication) throws Exception;
    /**
     * Modifica mailCommunication <br>
     * Info. Creación: <br>
     * fecha 12/09/2016 <br>
     * @author Angela Ramirez
     * @param mailCommunication
     * @return 
     * @throws Exception 
     */
    public MailCommunication store(MailCommunication mailCommunication) throws Exception;
    /**
     * Elimina mailCommunication<br>
     * Info. Creación: <br>
     * fecha 12/09/2016 <br>
     * @author Angela Ramirez
     * @param mailCommunication
     * @return 
     * @throws Exception 
     */
    public void remove(MailCommunication mailCommunication) throws Exception;
    /**
     * Busca mailCommunication por su id <br>
     * Info. Creación: <br>
     * fecha 12/09/2016 <br>
     * @author Angela Ramirez
     * @param mailCommunication
     * @return 
     * @throws Exception 
     */
    public MailCommunication findById(MailCommunication mailCommunication) throws Exception; 

    /**
     * Trae todos los mails por userId <br>
     * Creation Date : <br>
     * date 12/09/2016 <br>
     * @author Angela Ramírez
     * @param userId
     * @return
     * @throws java.lang.Exception
     */
    public List<MailCommunicationDTO> getMailsByUserId(Integer userId) throws Exception;

    /**
     * Trae todos los mails por receiving user and sendingUser <br>
     * Creation Date : <br>
     * date 12/09/2016 <br>
     * @author Angela Ramírez
     * @param receivingUserId
     * @param sendingUserId
     * @return
     * @throws java.lang.Exception
     */
    public List<MailCommunicationDTO> getMailsByReceivingUserIdFromSendingUser(Integer receivingUserId, Integer sendingUserId) throws Exception;
    
    /**
     * Trae todos los mails enviados por el user Id <br>
     * Creation Date : <br>
     * date 12/09/2016 <br>
     * @author Angela Ramírez
     * @param userId
     * @throws java.lang.Exception
     * @return
     */
    public List<MailCommunicationDTO> getSentMailsByUserId(Integer userId) throws Exception;
    
    /**
     * Trae todos los mails enviados por el user Id <br>
     * Creation Date : <br>
     * date 19/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @param read
     * @throws co.com.expertla.base.jpa.DAOException
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
     * Trae todos destinatarios asociados al coach <br>
     * Creation Date : <br>
     * date 22/09/2016 <br>
     * @author  Angela Ramirez
     * @param userId
     * @throws co.com.expertla.base.jpa.DAOException
     * @return
     */
    public List<UserDTO> getAllRecipientsByCoachId(Integer userId) throws Exception;
    
    /**
     * Trae todos destinatarios asociados a la estrella <br>
     * Creation Date : <br>
     * date 22/09/2016 <br>
     * @author  Angela Ramirez
     * @param userId
     * @throws co.com.expertla.base.jpa.DAOException
     * @return
     */
    public List<UserDTO> getAllRecipientsByStarId(Integer userId) throws Exception;
    
    /**
     * Consulta los tiempos de respuesta de los mails <br>
     * Info. Creación: <br>
     * fecha Sep 28, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @param roleId
     * @return 
     * @throws Exception 
     */
    public List<PlanMessageDTO> getResponseTimeMails(Integer userId, Integer roleId)throws  Exception;
    
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
    public List<ChartReportDTO> getResponseCountMails(Integer userId,Integer roleId) throws Exception;
}
