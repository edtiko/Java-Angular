package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.configuration.TrainingPlanDao;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.TrainingPlanDTO;
import co.expertic.training.model.entities.TrainingPlan;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* TrainingPlan Dao Impl <br>
* Info. Creación: <br>
* fecha 23/11/2016 <br>
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
        builder.append("WHERE a.typePlan = '1' ");//Obtiene solo los planes de entrenamiento
        builder.append("order by a.trainingPlanId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<TrainingPlan> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlan a ");
        builder.append("WHERE a.stateId = :active AND a.price > 0 ");
        builder.append("AND a.typePlan = '1' ");//Obtiene solo los planes de entrenamiento
        builder.append("order by a.price asc ");
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<TrainingPlanDTO> findPaginate(int first, int max, String order, String filter, String typePlan) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.expertic.training.model.dto.TrainingPlanDTO(a.trainingPlanId,");
        builder.append("a.name,a.description,a.endDate, a.stateId, a.price, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from TrainingPlan a ");
        builder.append("WHERE a.typePlan = '");builder.append(typePlan);builder.append("'");
        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("AND ( 1=1 ");
            builder.append("OR UPPER(a.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.description) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.duration) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.price) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(select u.login FROM User u WHERE a.userCreate = u.userId) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(select u.login FROM User u WHERE a.userUpdate = u.userId) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append(")");
        }

        builder.append("order by a.");
        builder.append(order);
        int count = this.getEntityManager().createQuery(builder.toString()).getResultList().size();
        
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
        builder.append("AND a.typePlan = '1' ");//Obtiene solo los planes de entrenamiento
        builder.append("order by a.name asc ");
        return createQuery(builder.toString());
    }

    @Override
    public List<TrainingPlan> findByName(TrainingPlan trainingPlan) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlan a ");
        builder.append("WHERE lower(trim(a.name)) = lower(trim(:name)) ");
        builder.append("AND a.typePlan = '1' ");//Obtiene solo los planes de entrenamiento
        setParameter("name", trainingPlan.getName().trim());
        return createQuery(builder.toString());
    }
    
    @Override
    public List<TrainingPlan> findPlaformAllActive(String typePlan) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlan a ");
        builder.append("WHERE a.stateId = :active AND a.price > 0 ");
        
        if(typePlan != null && !typePlan.isEmpty()) {
            builder.append("AND a.typePlan = :typePlan ");
        } else {
            builder.append("AND a.typePlan != :typePlan ");
        }
        
        
        builder.append("order by a.price asc ");
        
        if(typePlan != null && !typePlan.isEmpty()) {
            setParameter("typePlan", typePlan);
        } else {
            setParameter("typePlan", "1");
        }
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }
}