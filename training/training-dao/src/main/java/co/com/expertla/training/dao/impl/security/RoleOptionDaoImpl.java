package co.com.expertla.training.dao.impl.security;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.security.RoleOptionDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.entities.RoleOption;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* RoleOption Dao Impl <br>
* Info. Creación: <br>
* fecha 15/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class RoleOptionDaoImpl extends BaseDAOImpl<RoleOption> implements RoleOptionDao {

    public RoleOptionDaoImpl() {
    }
    
    @Override
    public List<RoleOption> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from RoleOption a ");
        builder.append("order by a.roleOptionId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<RoleOption> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from RoleOption a ");
        return createQuery(builder.toString());
    }

    
    @Override
    public List<RoleOption> findByRoleOption(RoleOption roleOption) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from RoleOption a ");
        builder.append("WHERE a.roleOptionId = :id ");
        setParameter("id", roleOption.getRoleOptionId());
        return createQuery(builder.toString());
    }

    @Override
    public List<RoleOption> findByFiltro(RoleOption roleOption) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from RoleOption a ");
        builder.append("WHERE 1=1 ");
        
        if(roleOption.getRoleId() != null && roleOption.getRoleId().getRoleId() != null) {
            builder.append("AND a.roleId.roleId = :roleId ");
            setParameter("roleId", roleOption.getRoleId().getRoleId());
        }

        return createQuery(builder.toString());
    }
}