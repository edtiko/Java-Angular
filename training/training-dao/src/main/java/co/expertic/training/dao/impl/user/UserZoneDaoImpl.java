package co.expertic.training.dao.impl.user;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.model.entities.UserZone;
import co.expertic.training.dao.user.UserZoneDao;
import co.expertic.training.model.entities.DisciplineUser;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* UserZone Dao Impl <br>
* Info. Creación: <br>
* fecha Aug 29, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class UserZoneDaoImpl extends BaseDAOImpl<UserZone> implements UserZoneDao {

    public UserZoneDaoImpl() {
    }
    
    @Override
    public List<UserZone> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from UserZone a ");
        builder.append("order by a.UserZoneId desc ");
        return createQuery(builder.toString());
    }

    
    @Override
    public List<UserZone> findByUserZone(UserZone userZone) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from UserZone a ");
        builder.append("WHERE a.userZoneId = :id ");
        setParameter("id", userZone.getUserZoneId());
        return createQuery(builder.toString());
    }
    
    @Override
    public List<UserZone> findByUserId(Integer userId) throws Exception {    
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a ");
        sql.append("FROM UserZone a ");
        sql.append("WHERE a.userId.userId = :id");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id",userId);
        return query.getResultList();
        
    }

}