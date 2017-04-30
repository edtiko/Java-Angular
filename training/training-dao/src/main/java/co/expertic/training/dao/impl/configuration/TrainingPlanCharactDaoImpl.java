package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.configuration.TrainingPlanCharactDao;
import co.expertic.training.enums.Status;
import co.expertic.training.model.entities.TrainingPlanCharact;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* TrainingPlanCharact Dao Impl <br>
* Info. Creación: <br>
* fecha 9/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class TrainingPlanCharactDaoImpl extends BaseDAOImpl<TrainingPlanCharact> implements TrainingPlanCharactDao {

    public TrainingPlanCharactDaoImpl() {
    }
    
    @Override
    public List<TrainingPlanCharact> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlanCharact a ");
        builder.append("order by a.trainingPlanCharactId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<TrainingPlanCharact> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlanCharact a ");
        builder.append("WHERE a.stateId = :active ");
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    
    @Override
    public List<TrainingPlanCharact> findByTrainingPlanCharact(TrainingPlanCharact trainingPlanCharact) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlanCharact a ");
        builder.append("WHERE a.trainingPlanCharactId = :id ");
        setParameter("id", trainingPlanCharact.getTrainingPlanCharactId());
        return createQuery(builder.toString());
    }

    @Override
    public List<TrainingPlanCharact> findByFiltro(TrainingPlanCharact trainingPlanCharact) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from TrainingPlanCharact a ");
        builder.append("WHERE 1=1 ");
        
        if(trainingPlanCharact.getValue() != null && !trainingPlanCharact.getValue().trim().isEmpty()) {
            builder.append("AND lower(a.value) like lower(:value) ");
        }
        
        if(trainingPlanCharact.getTrainingPlanId()!= null && trainingPlanCharact.getTrainingPlanId().getTrainingPlanId() != null) {
            builder.append("AND a.trainingPlanId.trainingPlanId = :plan ");
        }
        
        if(trainingPlanCharact.getMembershipId()!= null && trainingPlanCharact.getMembershipId().getMembershipId() != null) {
            builder.append("AND a.membershipId.membershipId = :membershipId ");
        }
        
        if(trainingPlanCharact.getCharacteristicId()!= null && trainingPlanCharact.getCharacteristicId().getCharacteristicId() != null) {
            builder.append("AND a.characteristicId.characteristicId = :charac ");
        }
        
        if(trainingPlanCharact.getUserType() != null && !trainingPlanCharact.getUserType().trim().isEmpty()) {
            builder.append("AND lower(a.value) = lower(:usertype) ");
        }
        
        Query query = this.getEntityManager().createQuery(builder.toString());
        if(trainingPlanCharact.getValue() != null && !trainingPlanCharact.getValue().trim().isEmpty()) {
            query.setParameter("value", "%" + trainingPlanCharact.getValue() + "%");
        }
        
        if(trainingPlanCharact.getTrainingPlanId()!= null && trainingPlanCharact.getTrainingPlanId().getTrainingPlanId() != null) {
            query.setParameter("plan", trainingPlanCharact.getTrainingPlanId().getTrainingPlanId());
        }
        
        if(trainingPlanCharact.getMembershipId()!= null && trainingPlanCharact.getMembershipId().getMembershipId() != null) {
            query.setParameter("membershipId", trainingPlanCharact.getMembershipId().getMembershipId());
        }
        
        if(trainingPlanCharact.getCharacteristicId()!= null && trainingPlanCharact.getCharacteristicId().getCharacteristicId() != null) {
            query.setParameter("charac", trainingPlanCharact.getCharacteristicId().getCharacteristicId());
        }
        
        if(trainingPlanCharact.getUserType() != null && !trainingPlanCharact.getUserType().trim().isEmpty()) {
            query.setParameter("usertype", trainingPlanCharact.getUserType());
        }
        
        return query.getResultList();
    }

}