package co.com.expertla.training.dao.impl.configuration;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.configuration.TrainingPlanDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.TrainingPlanDTO;
import co.com.expertla.training.model.entities.TrainingPlan;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* TrainingPlan Dao Impl <br>
* Info. Creación: <br>
* fecha 30/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class TrainingPlanDaoImpl extends BaseDAOImpl<TrainingPlan> implements TrainingPlanDao {

    public TrainingPlanDaoImpl() {
    }
    
    @Override
    public List<TrainingPlan> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlan a ");
        builder.append("order by a.trainingPlanId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<TrainingPlan> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlan a ");
        builder.append("WHERE a.stateId = :active ");
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<TrainingPlanDTO> findPaginate(int first, int max, String order) throws Exception {
        
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.TrainingPlanDTO(a.trainingPlanId,");
        builder.append("a.name,a.description,a.videoCount,a.messageCount,a.emailCount,a.callCount,a.endDate, a.stateId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from TrainingPlan a ");
        builder.append("order by a.");
        builder.append(order);
        int count = findAll().size();
        
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setFirstResult(first);
        query.setMaxResults(max);
        List<TrainingPlanDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<TrainingPlan> findByTrainingPlan(TrainingPlan trainingPlan) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlan a ");
        builder.append("WHERE a.trainingPlanId = :id ");
        setParameter("id", trainingPlan.getTrainingPlanId());
        return createQuery(builder.toString());
    }

    @Override
    public List<TrainingPlan> findByFiltro(TrainingPlan trainingPlan) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlan a ");
        builder.append("WHERE 1=1 ");
        
        if(trainingPlan.getName() != null && !trainingPlan.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + trainingPlan.getName() + "%");
        }


        if(trainingPlan.getDescription() != null && !trainingPlan.getDescription().trim().isEmpty()) {
            builder.append("AND lower(a.description) like lower(:description) ");
            setParameter("description", "%" + trainingPlan.getDescription() + "%");
        }

        return createQuery(builder.toString());
    }

}