package co.expertic.training.service.impl.user;

import co.expertic.training.dao.user.VisibleFieldsUserDao;
import co.expertic.training.model.entities.VisibleFieldsUser;
import co.expertic.training.service.user.VisibleFieldsUserService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Service for VisibleFieldsUser <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
@Service("visibleFieldsUserService")
@Transactional
public class VisibleFieldsUserServiceImpl implements VisibleFieldsUserService {
           
    @Autowired
    private VisibleFieldsUserDao visibleFieldsUserDao;
    
    @Override
    public List<VisibleFieldsUser> findByUserId(Integer id) throws Exception {
        return visibleFieldsUserDao.findByUserId(id);
    }
    
    @Override
    public VisibleFieldsUser create(VisibleFieldsUser visibleFieldsUser) throws Exception {
        return visibleFieldsUserDao.create(visibleFieldsUser);
    }
 
    @Override
    public VisibleFieldsUser merge(VisibleFieldsUser visibleFieldsUser) throws Exception {
        return visibleFieldsUserDao.merge(visibleFieldsUser);
    }
    
    @Override
    public List<VisibleFieldsUser> createList(List<VisibleFieldsUser> visibleFieldsUser,Integer userId) throws Exception {
        visibleFieldsUserDao.deleteFieldsByUser(userId);
        List<VisibleFieldsUser> list = visibleFieldsUserDao.createList(visibleFieldsUser);
        return list;
    }
}