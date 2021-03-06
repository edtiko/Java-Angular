package co.com.expertla.training.dao.impl.security;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.RoleUser;
import co.com.expertla.training.dao.security.RoleUserDao;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* RoleUser Dao Impl <br>
* Info. Creación: <br>
* fecha 10/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class RoleUserDaoImpl extends BaseDAOImpl<RoleUser> implements RoleUserDao {

    public RoleUserDaoImpl() {
    }
    
    @Override
    public List<RoleUser> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from RoleUser a ");
        builder.append("order by a.RoleUserId desc ");
        return createQuery(builder.toString());
    }


    @Override
    public List<RoleUser> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from RoleUser a ");
        return createQuery(builder.toString());
    }

    
    @Override
    public List<RoleUser> findByRoleUser(RoleUser roleUser) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from RoleUser a ");
        builder.append("WHERE a.roleUserId = :id ");
        setParameter("id", roleUser.getRoleUserId());
        return createQuery(builder.toString());
    }

    @Override
    public List<RoleUser> findByFiltro(RoleUser roleUser) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from RoleUser a ");
        builder.append("WHERE 1=1 ");
        

        return createQuery(builder.toString());
    }
    
    @Override
    public RoleUser findByUserId(Integer userId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from RoleUser a ");
        builder.append("WHERE a.userId.userId = :userId ");
        Query query = getEntityManager().createQuery(builder.toString());
        query.setParameter("userId", userId);

        List<RoleUser> list = query.getResultList();

        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

}