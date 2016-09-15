package co.com.expertla.training.service.impl.plan;

import co.com.expertla.training.dao.plan.MailCommunicationDao;
import co.com.expertla.training.model.dto.MailCommunicationDTO;
import co.com.expertla.training.model.entities.MailCommunication;
import co.com.expertla.training.service.plan.MailCommunicationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Service Impl for MailCommunication<br>
* Creation Date : <br>
* date 12/09/2016 <br>
* @author Angela Ramírez
**/
@Service("mailCommunicationService")
@Transactional
public class MailCommunicationServiceImpl implements MailCommunicationService {
    
    @Autowired
    private MailCommunicationDao mailCommunicationDao;
    

    @Override
    public MailCommunication create(MailCommunication mailCommunication) throws Exception {
        return mailCommunicationDao.create(mailCommunication);
    }

    @Override
    public MailCommunication store(MailCommunication mailCommunication) throws Exception {
        return mailCommunicationDao.merge(mailCommunication);
    }

    @Override
    public void remove(MailCommunication mailCommunication) throws Exception {
        mailCommunicationDao.remove(mailCommunication, mailCommunication.getMailCommunicationId());
    }
    
    @Override
    public MailCommunication findById(MailCommunication mailCommunication) throws Exception {
        return mailCommunicationDao.findById(mailCommunication);
    }

    @Override
    public List<MailCommunicationDTO> getMailsByUserId(Integer userId) throws Exception {
        return mailCommunicationDao.getMailsByUserId(userId);
    }

    @Override
    public List<MailCommunicationDTO> getMailsByReceivingUserIdFromSendingUser(Integer receivingUserId, Integer sendingUserId) throws Exception {
        return mailCommunicationDao.getMailsByReceivingUserIdFromSendingUser(receivingUserId, sendingUserId);
    }
    
    @Override
    public List<MailCommunicationDTO> getSentMailsByUserId(Integer userId) throws Exception {
        return mailCommunicationDao.getSentMailsByUserId(userId);
    }
    
}
