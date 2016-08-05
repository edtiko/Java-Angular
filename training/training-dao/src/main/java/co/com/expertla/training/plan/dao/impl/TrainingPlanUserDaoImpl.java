package co.com.expertla.training.plan.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.enums.StateEnum;
import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.plan.dao.TrainingPlanUserDao;
import java.util.List;

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
    public List<TrainingPlanUser> getPlanWorkoutByUser(Integer userId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u FROM TrainingPlanUser u ");
        sql.append("WHERE u.userId.userId = :userId ");
        sql.append("AND u.stateId.stateId = :stateId ");
        setParameter("userId", userId);
        setParameter("stateId", StateEnum.ACTIVE.getId());
        List<TrainingPlanUser> list = createQuery(sql.toString());
        return list;
    }
}
