package co.com.expertla.training.dao.impl.security;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.model.dto.RoleDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.Role;
import co.com.expertla.training.dao.security.RoleDao;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* Role Dao Impl <br>
* Creation Date: <br>
* date 19/08/2016 <br>
* @author Angela Ramirez O
**/
@Repository
public class RoleDaoImpl extends BaseDAOImpl<Role> implements RoleDao {

    public RoleDaoImpl() {
    }
    
    @Override
    public List<RoleDTO> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.RoleDTO(r.roleId, r.name) ");
        builder.append("FROM Role r ");
        builder.append("order by r.name desc ");
        Query query = getEntityManager().createQuery(builder.toString());
        List<RoleDTO> list = query.getResultList();
        return list;
    }

}