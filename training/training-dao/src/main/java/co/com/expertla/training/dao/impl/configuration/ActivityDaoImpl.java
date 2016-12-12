package co.com.expertla.training.dao.impl.configuration;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.configuration.ActivityDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.ActivityCalendarDTO;
import co.com.expertla.training.model.dto.ActivityDTO;
import co.com.expertla.training.model.dto.ActivityMovilDTO;
import co.com.expertla.training.model.entities.Activity;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 * Activity Dao Impl <br>
 * Info. Creación: <br>
 * fecha 5/08/2016 <br>
 *
 * @author Andres Felipe Lopez Rodriguez
 *
 */
@Repository
public class ActivityDaoImpl extends BaseDAOImpl<Activity> implements ActivityDao {

    public ActivityDaoImpl() {
    }
    
    @Override
    public List<Activity> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Activity a ");
        builder.append("order by a.activityId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<Activity> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Activity a ");

        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<ActivityDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.ActivityDTO(a.activityId,");
        builder.append("a.physiologicalCapacityId,a.modalityId,a.objectiveId,a.name,a.description,a.sportId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("a.stateId) from Activity a ");
        
        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("WHERE ( 1!=1 ");
            builder.append("OR UPPER(a.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.description) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.physiologicalCapacityId.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.modalityId.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.objectiveId.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.sportId.name) like '%");
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
        int count = findAll().size();
        
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setFirstResult(first);
        query.setMaxResults(max);
        List<ActivityDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<Activity> findByActivity(Activity activity) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Activity a ");
        builder.append("WHERE a.activityId = :id ");
        setParameter("id", activity.getActivityId());
        return createQuery(builder.toString());
    }

    @Override
    public List<Activity> findByFiltro(Activity activity) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Activity a ");
        builder.append("WHERE 1=1 ");
        

        if(activity.getPhysiologicalCapacityId() != null && activity.getPhysiologicalCapacityId().getPhysiologicalCapacityId() != null) {
            builder.append("AND a.physiologicalCapacityId.physiologicalCapacityId = :physiologicalCapacity ");
            setParameter("physiologicalCapacity", activity.getPhysiologicalCapacityId().getPhysiologicalCapacityId());
        }


        if(activity.getModalityId() != null && activity.getModalityId().getModalityId() != null) {
            builder.append("AND a.modalityId.modalityId = :modality ");
            setParameter("modality", activity.getModalityId().getModalityId());
        }


        if(activity.getObjectiveId() != null && activity.getObjectiveId().getObjectiveId() != null) {
            builder.append("AND a.objectiveId.objectiveId = :objective ");
            setParameter("objective", activity.getObjectiveId().getObjectiveId());
        }

        if(activity.getName() != null && !activity.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", activity.getName());
        }


        if(activity.getDescription() != null && !activity.getDescription().trim().isEmpty()) {
            builder.append("AND lower(a.description) like lower(:description) ");
            setParameter("description", "%" + activity.getDescription() + "%");
        }



        if(activity.getSportId() != null && activity.getSportId().getSportId() != null) {
            builder.append("AND a.sportId.sportId = :sport ");
            setParameter("sport", activity.getSportId().getSportId());
        }


        return createQuery(builder.toString());
    }
    
    @Override
    public List<ActivityCalendarDTO> findByUserDiscipline(Integer usuarioId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT new co.com.expertla.training.model.dto.ActivityCalendarDTO(a.activityId, a.name, a.description, a.modalityId.modalityId, a.objectiveId.objectiveId, a.sportId.sportId, a.physiologicalCapacityId.name ) ");
        builder.append("FROM Activity a, DisciplineUser du ");
        builder.append("WHERE a.modalityId.disciplineId.disciplineId = du.discipline.disciplineId ");
        builder.append("AND du.userId.userId = :userId ");

        Query query = getEntityManager().createQuery(builder.toString());
        query.setParameter("userId", usuarioId);
        List<ActivityCalendarDTO> list = query.getResultList();
        return list;
    }

    @Override
    public List<Activity> findByObjectiveIdAndModalityIdAndEnvironmentId(Integer objectiveId, Integer modalityId, Integer environmentId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a ");
        sql.append("FROM Activity a ");
        sql.append("WHERE a.objectiveId.objectiveId = :objectiveId ");
        sql.append("AND a.modalityId.modalityId = :modalityId ");
        sql.append("AND a.environmentId.environmentId = :environmentId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("objectiveId", objectiveId);
        query.setParameter("modalityId", modalityId);
        query.setParameter("environmentId", environmentId);
        return query.getResultList();
    }

    @Override
    public List<Activity> findActivityReplaceByActivity(Integer activityId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.replaceId ");
        sql.append("FROM ReplaceActivity a ");
        sql.append("WHERE a.activityId.activityId = :activityId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("activityId", activityId);
        return query.getResultList();
    }
    
    @Override
    public List<ActivityMovilDTO> findActivityDefaultByUserDiscipline(Integer usuarioId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT new co.com.expertla.training.model.dto.ActivityMovilDTO( ");
        builder.append("a.activityId, a.name, a.description, a.modalityId.modalityId, a.modalityId.name, a.objectiveId.objectiveId, a.sportId.sportId, ");
        builder.append("a.sportId.icon, a.physiologicalCapacityId.name  ) ");
        
        builder.append("FROM Activity a, DisciplineUser du ");
        builder.append("WHERE a.modalityId.disciplineId.disciplineId = du.discipline.disciplineId ");
        builder.append("AND du.userId.userId = :userId ");

        Query query = getEntityManager().createQuery(builder.toString());
        query.setParameter("userId", usuarioId);
        List<ActivityMovilDTO> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<ActivityMovilDTO> findActivityReplaceByActivityMovil(Integer activityId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT DISTINCT new co.com.expertla.training.model.dto.ActivityMovilDTO( a ) ");
        
        builder.append("FROM ReplaceActivity a, DisciplineUser du ");
        builder.append("WHERE a.replaceId.modalityId.disciplineId.disciplineId = du.discipline.disciplineId ");
        builder.append("AND a.activityId.activityId = :activityId ");

        Query query = getEntityManager().createQuery(builder.toString());
        query.setParameter("activityId", activityId);
        List<ActivityMovilDTO> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<ActivityMovilDTO> findManualActivitiesMovilByUserId(Integer userId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.ActivityMovilDTO(ma.manualActivityId, ma.name, ma.description, ma.sportId.sportId, ma.sportId.icon  )");
        sql.append("FROM ManualActivity ma ");
        sql.append("WHERE ma.userId.userId = :userId ");
        sql.append("ORDER BY ma.name ASC ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        List<ActivityMovilDTO> list = query.getResultList();
        return list;
    }
}
