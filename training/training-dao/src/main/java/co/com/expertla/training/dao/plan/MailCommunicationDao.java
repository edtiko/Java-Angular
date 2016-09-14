package co.com.expertla.training.dao.plan;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.MailCommunicationDTO;
import co.com.expertla.training.model.entities.MailCommunication;
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
    public MailCommunication findById(MailCommunication mailCommunication) throws Exception; 
    
    /**
     * Trae todos los mails por userId <br>
     * Creation Date : <br>
     * date 12/09/2016 <br>
     * @author Angela Ramírez
     * @param userId
     * @throws co.com.expertla.base.jpa.DAOException
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
     * @throws co.com.expertla.base.jpa.DAOException
     * @return
     */
    public List<MailCommunicationDTO> getMailsByReceivingUserIdFromSendingUser(Integer receivingUserId, Integer sendingUserId) throws DAOException;
    
    /**
     * Trae todos los mails enviados por el user Id <br>
     * Creation Date : <br>
     * date 12/09/2016 <br>
     * @author Angela Ramírez
     * @param userId
     * @throws co.com.expertla.base.jpa.DAOException
     * @return
     */
    public List<MailCommunicationDTO> getSentMailsByUserId(Integer userId) throws DAOException;

    
}
