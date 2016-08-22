package co.com.expertla.training.dao.impl.user;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.model.entities.TrainingPlanWorkout;
import co.com.expertla.training.model.entities.UserAvailability;
import co.com.expertla.training.model.entities.VisibleFieldsUser;
import co.com.expertla.training.dao.user.VisibleFieldsUserDao;
import java.util.ArrayList;
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

    private final int batchSize = 10;
    
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
    
    @Override
    public List<VisibleFieldsUser> createList(List<VisibleFieldsUser> visibleFieldsUser) throws Exception {
        List<VisibleFieldsUser> list = bulkSave(visibleFieldsUser);
        return list;
    }
    
    private <T extends VisibleFieldsUser> List<T> bulkSave(List<T> entities) {
        final List<T> savedEntities = new ArrayList<>(entities.size());
        int i = 0;
        for (T t : entities) {
            savedEntities.add(persistOrMerge(t));
            i++;
            if (i % batchSize == 0) {
                // Flush a batch of inserts and release memory.
                this.getEntityManager().flush();
                this.getEntityManager().clear();
            }
        }
        return savedEntities;
    }
 
    private <T extends VisibleFieldsUser> T persistOrMerge(T t) {
        if (t.getVisibleFieldsUserId()== null) {
            getEntityManager().persist(t);
            return t;
        } else {
            getEntityManager().merge(t);
            return t;
        }
    }
    
    @Override
    public void deleteFieldsByUser(Integer id) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("Delete from VisibleFieldsUser u ");
        sql.append("WHERE u.userId = :id ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
