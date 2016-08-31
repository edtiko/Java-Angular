package co.com.expertla.training.service.impl.security;

import co.com.expertla.training.dao.security.RoleDao;
import co.com.expertla.training.model.dto.RoleDTO;
import co.com.expertla.training.model.entities.Role;
import co.com.expertla.training.service.security.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Role Service Impl <br>
* Info. Creacion: <br>
* fecha 30/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role create(Role role) throws Exception {
        return roleDao.create(role);
    }

    @Override
    public Role store(Role role) throws Exception {
       return roleDao.merge(role);
    }

    @Override
    public void remove(Role role) throws Exception {
        roleDao.remove(role, role.getRoleId());
    }

    @Override
    public List<Role> findAll() throws Exception {
        return roleDao.findAll();
    }

    @Override
    public List<Role> findAllActive() throws Exception {
        return roleDao.findAllActive();
    }

    @Override
    public List<RoleDTO> findPaginate(int first, int max, String order) throws Exception {
        return roleDao.findPaginate(first, max, order);
    }

    @Override
    public List<Role> findByRole(Role role) throws Exception {
        return roleDao.findByRole(role);
    }

    @Override
    public List<Role> findByFiltro(Role role) throws Exception {
        return roleDao.findByFiltro(role);
    }

}