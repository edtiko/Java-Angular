package co.expertic.training.dao.impl.security;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.security.RoleDao;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.RoleDTO;
import co.expertic.training.model.entities.Role;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* Role Dao Impl <br>
* Info. Creación: <br>
* fecha 28/11/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class RoleDaoImpl extends BaseDAOImpl<Role> implements RoleDao {

    public RoleDaoImpl() {
    }
    
    @Override
    public List<Role> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Role a ");
        builder.append("order by a.roleId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<Role> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Role a ");
        builder.append("WHERE a.stateId = :active ");

        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<RoleDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.expertic.training.model.dto.RoleDTO(a.roleId,");
        builder.append("a.name,a.stateId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from Role a ");

        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("WHERE ( 1!=1 ");
            builder.append("OR UPPER(a.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(select u.name FROM State u WHERE u.stateId = a.stateId) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(select u.login FROM User u WHERE a.userCreate = u.userId) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(select u.login FROM User u WHERE a.userUpdate = u.userId) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append(")");
        }

        builder.append("order by a.");
        builder.append(order);
        int count = this.getEntityManager().createQuery(builder.toString()).getResultList().size();
        
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setFirstResult(first);
        query.setMaxResults(max);
        List<RoleDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<Role> findByRole(Role role) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Role a ");
        builder.append("WHERE a.roleId = :id ");
        setParameter("id", role.getRoleId());
        return createQuery(builder.toString());
    }

    @Override
    public List<Role> findByFiltro(Role role) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Role a ");
        builder.append("WHERE 1=1 ");
        
        if(role.getName() != null && !role.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + role.getName() + "%");
        }




        if(role.getStateId() != null) {
            builder.append("AND a.stateId = :state ");
            setParameter("state", role.getStateId());
        }

        builder.append("order by a.name asc ");
        return createQuery(builder.toString());
    }

    @Override
    public List<Role> findByName(Role role) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Role a ");
        builder.append("WHERE lower(trim(a.name)) = lower(trim(:name)) ");
        setParameter("name", role.getName().trim());
        return createQuery(builder.toString());
    }
}