package co.com.expertla.training.dao.impl.configuration;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.configuration.ObjectiveDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.ObjectiveDTO;
import co.com.expertla.training.model.entities.Objective;
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
        sql.append("SELECT new co.com.expertla.training.model.dto.ObjectiveDTO(o.objectiveId,o.name, o.level) ");
        sql.append("FROM Objective o ");
        sql.append("order by o.name ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }


    @Override
    public List<Objective> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Objective a ");
        builder.append("WHERE a.stateId = :active ");
        builder.append("AND a.objectiveParentId is null ");
        builder.append("order by a.name ");
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<ObjectiveDTO> findPaginate(int first, int max, String order) throws Exception {
        
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.ObjectiveDTO(a.objectiveId,");
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
            builder.append(" ORDER BY a.name ");


        return createQuery(builder.toString());
    }
    
    @Override
    public List<ObjectiveDTO> findByDiscipline(Integer disciplineId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.ObjectiveDTO(o.objectiveId,o.name, o.level) ");
        sql.append("FROM Objective o ");
        sql.append("WHERE o.disciplineId.disciplineId = :disciplineId ");
        sql.append("AND o.objectiveParentId is null ");
        sql.append(" ORDER BY o.name ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("disciplineId", disciplineId);
        return query.getResultList();
    }

}