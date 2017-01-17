package co.expertic.training.dao.impl.user;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.user.UserSportDao;
import co.expertic.training.model.entities.UserSport;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
* Dao Implementation for UserSport <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class UserSportDaoImpl extends BaseDAOImpl<UserSport> implements UserSportDao {

    @Override
    public UserSport findByUserId(Integer id) throws Exception {   
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u ");
        sql.append("FROM UserSport u ");
        sql.append("WHERE u.userProfileId.userId.userId = :id ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        List<UserSport> list = query.getResultList();
        return (list == null || list.isEmpty()) ?  null :list.get(0);
    }

}
