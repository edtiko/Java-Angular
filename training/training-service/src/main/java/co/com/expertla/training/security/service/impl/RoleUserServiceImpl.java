package co.com.expertla.training.security.service.impl;

import co.com.expertla.training.model.entities.RoleUser;
import co.com.expertla.training.security.dao.RoleUserDao;
import co.com.expertla.training.security.service.RoleUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* RoleUser Service Impl <br>
* Info. Creación: <br>
* fecha 10/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("roleUserService")
@Transactional
public class RoleUserServiceImpl implements RoleUserService {

    @Autowired
    private RoleUserDao roleUserDao;

    @Override
    public RoleUser create(RoleUser roleUser) throws Exception {
        return roleUserDao.create(roleUser);
    }

    @Override
    public RoleUser store(RoleUser roleUser) throws Exception {
       return roleUserDao.merge(roleUser);
    }

    @Override
    public void remove(RoleUser roleUser) throws Exception {
        roleUserDao.remove(roleUser, roleUser.getRoleUserId());
    }

    @Override
    public List<RoleUser> findAll() throws Exception {
        return roleUserDao.findAll();
    }

    @Override
    public List<RoleUser> findAllActive() throws Exception {
        return roleUserDao.findAllActive();
    }

    @Override
    public List<RoleUser> findByRoleUser(RoleUser roleUser) throws Exception {
        return roleUserDao.findByRoleUser(roleUser);
    }

    @Override
    public List<RoleUser> findByFiltro(RoleUser roleUser) throws Exception {
        return roleUserDao.findByFiltro(roleUser);
    }

}