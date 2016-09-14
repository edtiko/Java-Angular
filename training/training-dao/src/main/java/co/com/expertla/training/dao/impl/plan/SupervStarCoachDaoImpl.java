package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.plan.SupervStarCoachDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.SupervStarCoachDTO;
import co.com.expertla.training.model.entities.SupervStarCoach;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* SupervStarCoach Dao Impl <br>
* Info. Creación: <br>
* fecha Sep 13, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class SupervStarCoachDaoImpl extends BaseDAOImpl<SupervStarCoach> implements SupervStarCoachDao {

    public SupervStarCoachDaoImpl() {
    }
    
    @Override
    public List<SupervStarCoach> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from SupervStarCoach a ");
        builder.append("order by a.supervStarCoachId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<SupervStarCoach> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from SupervStarCoach a ");
        builder.append("WHERE a.stateId = :active ");

        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<SupervStarCoachDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.SupervStarCoachDTO(a.supervStarCoachId,");
        builder.append("a.starTeamId,a.supervisorId,a.stateId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from SupervStarCoach a ");

        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("WHERE ( 1=1 ");
            builder.append("OR UPPER(a.starTeamId.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.supervisorId.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(select u.name FROM State u WHERE u.stateId = a.stateId) like '%");
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
        List<SupervStarCoachDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<SupervStarCoach> findBySupervStarCoach(SupervStarCoach supervStarCoach) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from SupervStarCoach a ");
        builder.append("WHERE a.supervStarCoachId = :id ");
        setParameter("id", supervStarCoach.getSupervStarCoachId());
        return createQuery(builder.toString());
    }

    @Override
    public List<SupervStarCoach> findByFiltro(SupervStarCoach supervStarCoach) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from SupervStarCoach a ");
        builder.append("WHERE 1=1 ");
        

        if(supervStarCoach.getStarTeamId() != null && supervStarCoach.getStarTeamId().getStarTeamId() != null) {
            builder.append("AND a.starTeamId.starTeamId = :starTeam ");
            setParameter("starTeam", supervStarCoach.getStarTeamId().getStarTeamId());
        }


        if(supervStarCoach.getSupervisorId() != null && supervStarCoach.getSupervisorId().getUserId()!= null) {
            builder.append("AND a.supervisorId.supervisorId = :supervisor ");
            setParameter("supervisor", supervStarCoach.getSupervisorId().getUserId());
        }



        if(supervStarCoach.getStateId() != null) {
            builder.append("AND a.stateId = :state ");
            setParameter("state", supervStarCoach.getStateId());
        }

        return createQuery(builder.toString());
    }

    @Override
    public List<SupervStarCoach> findByCoachId(Integer coachId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from SupervStarCoach a ");
        builder.append("where a.starTeamId.coachUserId.userId = :id ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setParameter("id", coachId);
        return query.getResultList();
    }
}