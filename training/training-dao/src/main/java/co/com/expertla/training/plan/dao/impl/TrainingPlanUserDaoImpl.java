package co.com.expertla.training.plan.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.enums.StateEnum;
import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.plan.dao.TrainingPlanUserDao;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
* TrainingPlanUser Dao Impl <br>
* Info. Creación: <br>
* fecha 10/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class TrainingPlanUserDaoImpl extends BaseDAOImpl<TrainingPlanUser> implements TrainingPlanUserDao {

    public TrainingPlanUserDaoImpl() {
    }
    
    @Override
    public List<TrainingPlanUser> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlanUser a ");
        builder.append("order by a.TrainingPlanUserId desc ");
        return createQuery(builder.toString());
    }


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

    
    @Override
    public List<TrainingPlanUser> findByTrainingPlanUser(TrainingPlanUser trainingPlanUser) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlanUser a ");
        builder.append("WHERE a.trainingPlanUserId = :id ");
        setParameter("id", trainingPlanUser.getTrainingPlanUserId());
        return createQuery(builder.toString());
    }

    @Override
    public List<TrainingPlanUser> findByFiltro(TrainingPlanUser trainingPlanUser) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlanUser a ");
        builder.append("WHERE 1=1 ");
        

        return createQuery(builder.toString());
    }

}