package co.com.expertla.training.user.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.user.dao.UserAvailabilityDao;
import co.com.expertla.training.model.entities.UserAvailability;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
* Dao Implementation for User Availability <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class UserAvailabilityDaoImpl extends BaseDAOImpl<UserAvailability> implements UserAvailabilityDao {

    @Override
    public List<UserAvailability> findByUserId(Integer id) throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u ");
        sql.append("FROM UserAvailability u ");
        sql.append("WHERE u.userProfileId.userId.userId = :id ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        return query.getResultList();
    }

}
