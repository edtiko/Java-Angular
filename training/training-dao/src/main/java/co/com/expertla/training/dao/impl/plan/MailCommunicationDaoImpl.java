package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.plan.MailCommunicationDao;
import co.com.expertla.training.model.dto.MailCommunicationDTO;
import co.com.expertla.training.model.entities.MailCommunication;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MailCommunicationDaoImpl extends BaseDAOImpl<MailCommunication> implements MailCommunicationDao {

    @Override
    public MailCommunication findById(MailCommunication mailCommunication) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m ");   
        sql.append("FROM MailCommunication m ");
        sql.append("Where m.mailCommunicationId = :id ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", mailCommunication.getMailCommunicationId());
        List<MailCommunication> list =query.getResultList(); 
        return list == null ? null : list.get(0);
    }
    
    @Override
    public List<MailCommunicationDTO> getMailsByUserId(Integer userId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.MailCommunicationDTO(m.mailCommunicationId, m.subject,m.message, m.stateId, ");   
        sql.append("m.creationDate, m.read,m.receivingUser,m.sendingUser ) ");
        sql.append("FROM MailCommunication m ");
        sql.append("Where m.receivingUser.userId = :id ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", userId);
        return query.getResultList();
    }
    
    @Override
    public List<MailCommunicationDTO> getMailsByReceivingUserIdFromSendingUser(Integer receivingUserId, Integer sendingUserId) throws DAOException {
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.MailCommunicationDTO(m.mailCommunicationId, m.subject,m.message, m.stateId, ");   
        sql.append("m.creationDate, m.read,m.receivingUser,m.sendingUser ) ");
        sql.append("FROM MailCommunication m ");
        sql.append("Where m.receivingUser.userId = :receivingUser ");
        sql.append("and m.sendingUser.userId = :sendingUser ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("receivingUser", receivingUserId);
        query.setParameter("sendingUser", sendingUserId);
        return query.getResultList();
    }
    
    @Override
    public List<MailCommunicationDTO> getSentMailsByUserId(Integer userId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.MailCommunicationDTO(m.mailCommunicationId, m.subject,m.message, m.stateId, ");   
        sql.append("m.creationDate, m.read,m.receivingUser,m.sendingUser ) ");
        sql.append("FROM MailCommunication m ");
        sql.append("Where m.sendingUser.userId = :id ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", userId);
        return query.getResultList();
    }
}
