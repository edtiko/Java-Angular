package co.com.expertla.training.plan.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.model.dto.TrainingPlanWorkoutDto;
import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.plan.dao.TrainingPlanUserDao;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;


/**
* Dao Implementation for TrainingPlan User <br>
* Creation Date : <br>
* date 21/07/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class TrainingPlanUserDaoImpl extends BaseDAOImpl<TrainingPlanUser> implements TrainingPlanUserDao {
    
    @Override
    public List<TrainingPlanUser> getTrainingPlanUserByUser(User user) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t from TrainingPlanUser t ");
        sql.append("t.userId.userId = :userId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", user.getUserId());
        List<TrainingPlanUser> list = query.getResultList();
        return list;
    }
    
}
