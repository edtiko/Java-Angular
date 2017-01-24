package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.configuration.ObjectiveDao;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.ObjectiveDTO;
import co.expertic.training.model.entities.Objective;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* Objective Dao Impl <br>
* Info. Creación: <br>
* fecha 30/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class ObjectiveDaoImpl extends BaseDAOImpl<Objective> implements ObjectiveDao {

    public ObjectiveDaoImpl() {
    }
    
    @Override
    public List<ObjectiveDTO> findAll() throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.ObjectiveDTO(o.objectiveId,o.name, o.level) ");
        sql.append("FROM Objective o ");
        sql.append("order by o.level ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }


    @Override
    public List<Objective> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Objective a ");
        builder.append("WHERE a.stateId = :active ");
        builder.append("AND a.objectiveParentId is null ");
        builder.append("order by a.level ");
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<ObjectiveDTO> findPaginate(int first, int max, String order) throws Exception {
        
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.expertic.training.model.dto.ObjectiveDTO(a.objectiveId,");
        builder.append("a.name,a.level,a.disciplineId.disciplineId,a.disciplineId.name, a.stateId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from Objective a ");
        builder.append("order by a.");
        builder.append(order);
        int count = findAll().size();
        
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setFirstResult(first);
        query.setMaxResults(max);
        List<ObjectiveDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<Objective> findByObjective(Objective objective) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Objective a ");
        builder.append("WHERE a.objectiveId = :id ");
        setParameter("id", objective.getObjectiveId());
        return createQuery(builder.toString());
    }

    @Override
    public List<Objective> findByFiltro(Objective objective) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append(" select a from Objective a ");
        builder.append(" WHERE 1=1 ");
        builder.append(" AND a.objectiveParentId is null ");
        
        if(objective.getName() != null && !objective.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + objective.getName() + "%");
        }

        if(objective.getDisciplineId() != null && objective.getDisciplineId().getDisciplineId() != null) {
            builder.append("AND a.disciplineId.disciplineId = :discipline ");
            setParameter("discipline", objective.getDisciplineId().getDisciplineId());
        }
            builder.append(" ORDER BY a.level ");


        return createQuery(builder.toString());
    }
    
    @Override
    public List<ObjectiveDTO> findByDiscipline(Integer disciplineId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.ObjectiveDTO(o.objectiveId,o.name, o.level) ");
        sql.append("FROM Objective o ");
        sql.append("WHERE o.disciplineId.disciplineId = :disciplineId ");
        sql.append("AND o.objectiveParentId is null ");
        sql.append(" ORDER BY o.level ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("disciplineId", disciplineId);
        return query.getResultList();
    }

    @Override
    public Integer findNextObjective(Integer trainingUserPlanId) throws Exception {
        StringBuilder sql = new StringBuilder();
        Integer objectiveId = null;
        sql.append(" SELECT (select objective_id from objective where objective_parent_id = o.objective_parent_id and level = (o.level + 1)) ");
        sql.append(" FROM plan_workout_objective e, objective o ");
        sql.append(" Where e.objective_id  = o.objective_id  ");
        sql.append(" And e.training_plan_user_id = ").append(trainingUserPlanId);
        Query query = getEntityManager().createNativeQuery(sql.toString());
        List<Object> objective = query.getResultList();
        if (objective.size() > 0) {
            objectiveId = (Integer) objective.get(0);
        } else {
            StringBuilder sql2 = new StringBuilder();
            sql2.append(" SELECT o.objective_id ");
            sql2.append(" FROM user_profile up, training_plan_user tu, objective o ");
            sql2.append(" Where up.user_id  = tu.user_id ");
            sql2.append(" And up.objective_id = o.objective_parent_id ");
            sql2.append(" And o.level = 1 ");
            sql2.append(" And tu.training_plan_user_id = ").append(trainingUserPlanId);
            Query query2 = getEntityManager().createNativeQuery(sql2.toString());
            List<Object> objective2 = query2.getResultList();
            if (objective2.size() > 0) {
                objectiveId = (Integer) objective2.get(0);
            }
        }

        return objectiveId;
    }
    
    @Override
    public Integer findCurrentObjective(Integer trainingUserPlanId) throws Exception {
        StringBuilder sql = new StringBuilder();
        Integer objectiveId = null;
        sql.append(" SELECT e.objective_id ");
        sql.append(" FROM plan_workout_objective e ");
        sql.append(" Where e.training_plan_user_id = ").append(trainingUserPlanId);
        Query query = getEntityManager().createNativeQuery(sql.toString());
        objectiveId = (Integer) query.getSingleResult();

        return objectiveId;
    }

}