package co.com.expertla.training.dao.impl.configuration;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.configuration.ActivityDao;
import co.com.expertla.training.model.dto.ActivityDTO;
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
        builder.append("order by a.ActivityId desc ");
        return createQuery(builder.toString());
    }

    @Override
    public List<Activity> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Activity a ");

//        setParameter("active", Status.ACTIVE.getId());
        return createQuery(builder.toString());
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

        return createQuery(builder.toString());
    }
    
    @Override
    public List<ActivityDTO> findByUserDiscipline(Integer usuarioId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT new co.com.expertla.training.model.dto.ActivityDTO(a.activityId, a.name, a.description, a.modalityId.modalityId, a.objectiveId.objectiveId, a.sportId.sportId ) ");
        builder.append("FROM Activity a, DisciplineUser du ");
        builder.append("WHERE a.modalityId.disciplineId.disciplineId = du.discipline.disciplineId ");
        builder.append("AND du.userId.userId = :userId ");

        Query query = getEntityManager().createQuery(builder.toString());
        query.setParameter("userId", usuarioId);
        List<ActivityDTO> list = query.getResultList();
        return list;
    }

    @Override
    public List<Activity> findByObjectiveIdAndModalityId(Integer objectiveId, Integer modalityId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a ");
        sql.append("FROM Activity a ");
        sql.append("WHERE a.objectiveId.objectiveId = :objectiveId ");
        sql.append("AND a.modalityId.modalityId = :modalityId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("objectiveId", objectiveId);
        query.setParameter("modalityId", modalityId);
        return query.getResultList();
    }

}
