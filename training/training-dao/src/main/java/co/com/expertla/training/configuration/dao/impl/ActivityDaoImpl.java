package co.com.expertla.training.configuration.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.configuration.dao.ActivityDao;
import co.com.expertla.training.model.entities.Activity;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
* Dao Implementation for Activity <br>
* Creation Date : <br>
* date 21/07/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class ActivityDaoImpl extends BaseDAOImpl<Activity> implements ActivityDao {

    @Override
    public List<Activity> findAll() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a");
        sql.append("FROM Activity a ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }
    
    @Override
    public List<Activity> findByObjetiveIdAndModalityId(Integer objetiveId, Integer modalityId) throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a ");
        sql.append("FROM Activity a ");
        sql.append("WHERE a.objetiveId.objetiveId = :objetiveId ");
        sql.append("AND a.modalityId.modalityId = :modalityId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("objetiveId", objetiveId);
        query.setParameter("modalityId", modalityId);
        return query.getResultList();
    }

}
