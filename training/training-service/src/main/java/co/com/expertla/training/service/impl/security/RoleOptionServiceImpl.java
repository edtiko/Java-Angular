package co.com.expertla.training.service.impl.security;

import co.com.expertla.training.dao.security.RoleOptionDao;
import co.com.expertla.training.model.entities.RoleOption;
import co.com.expertla.training.service.security.RoleOptionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* RoleOption Service Impl <br>
* Info. Creación: <br>
* fecha 15/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("roleOptionService")
@Transactional
public class RoleOptionServiceImpl implements RoleOptionService {

    @Autowired
    private RoleOptionDao roleOptionDao;

    @Override
    public RoleOption create(RoleOption roleOption) throws Exception {
        return roleOptionDao.create(roleOption);
    }

    @Override
    public RoleOption store(RoleOption roleOption) throws Exception {
       return roleOptionDao.merge(roleOption);
    }

    @Override
    public void remove(RoleOption roleOption) throws Exception {
        roleOptionDao.remove(roleOption, roleOption.getRoleOptionId());
    }

    @Override
    public List<RoleOption> findAll() throws Exception {
        return roleOptionDao.findAll();
    }

    @Override
    public List<RoleOption> findAllActive() throws Exception {
        return roleOptionDao.findAllActive();
    }

    @Override
    public List<RoleOption> findByRoleOption(RoleOption roleOption) throws Exception {
        return roleOptionDao.findByRoleOption(roleOption);
    }

    @Override
    public List<RoleOption> findByFiltro(RoleOption roleOption) throws Exception {
        return roleOptionDao.findByFiltro(roleOption);
    }
}