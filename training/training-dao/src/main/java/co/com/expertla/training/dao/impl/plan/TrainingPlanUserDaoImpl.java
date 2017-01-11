package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.dao.plan.TrainingPlanUserDao;
import co.com.expertla.training.enums.StateEnum;
import java.util.List;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
* TrainingPlanUser Dao Impl <br>
* Info. Creación: <br>
* fecha 10/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class TrainingPlanUserDaoImpl extends BaseDAOImpl<TrainingPlanUser> implements TrainingPlanUserDao {
    
    @Override
    public TrainingPlanUser getTrainingPlanUserByUser(User user) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t from TrainingPlanUser t ");
        sql.append("WHERE t.userId.userId = :userId ");
        sql.append("AND t.stateId = :stateId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", user.getUserId());
        query.setParameter("stateId", StateEnum.ACTIVE.getId().shortValue());
        List<TrainingPlanUser> list = query.getResultList();
        return list.size()>0?list.get(0):null;
    }
    
    public TrainingPlanUserDaoImpl() {
    }
    
    @Override
    public List<TrainingPlanUser> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlanUser a ");
        builder.append("where a.stateId = :stateId ");
        builder.append("order by a.TrainingPlanUserId desc ");
        setParameter("stateId", StateEnum.ACTIVE.getId().shortValue());
        return createQuery(builder.toString());
    }


    @Override
    public List<TrainingPlanUser> getPlanWorkoutByUser(Integer userId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u FROM TrainingPlanUser u ");
        sql.append("WHERE u.userId.userId = :userId ");
        sql.append("AND u.stateId = :stateId ");
        setParameter("userId", userId);
        setParameter("stateId", StateEnum.ACTIVE.getId().shortValue());
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