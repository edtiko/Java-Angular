package co.com.expertla.training.user.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.model.entities.UserAvailability;
import co.com.expertla.training.model.entities.VisibleFieldsUser;
import co.com.expertla.training.user.dao.VisibleFieldsUserDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
* Dao Implementation for Visible Fields User <br>
* Creation Date : <br>
* date 10/08/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class VisibleFieldsUserDaoImpl extends BaseDAOImpl<VisibleFieldsUser> implements VisibleFieldsUserDao {

    @Override
    public List<VisibleFieldsUser> findByUserId(Integer id) throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u ");
        sql.append("FROM VisibleFieldsUser u ");
        sql.append("WHERE u.userId = :id ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        return query.getResultList();
    }

}
