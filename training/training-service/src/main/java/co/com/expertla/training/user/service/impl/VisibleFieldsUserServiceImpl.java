package co.com.expertla.training.user.service.impl;

import co.com.expertla.training.user.dao.VisibleFieldsUserDao;
import co.com.expertla.training.model.entities.VisibleFieldsUser;
import co.com.expertla.training.user.service.VisibleFieldsUserService;
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
}