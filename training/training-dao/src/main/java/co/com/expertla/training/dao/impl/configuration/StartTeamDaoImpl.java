package co.com.expertla.training.dao.impl.configuration;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.configuration.StartTeamDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.StarTeamDTO;
import co.com.expertla.training.model.entities.StarTeam;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* StartTeam Dao Impl <br>
* Info. Creación: <br>
* fecha 1/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class StartTeamDaoImpl extends BaseDAOImpl<StarTeam> implements StartTeamDao {

    public StartTeamDaoImpl() {
    }
    
    @Override
    public List<StarTeam> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from StarTeam a ");
        builder.append("order by a.starTeamId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<StarTeam> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from StarTeam a ");
        builder.append("WHERE a.stateId = :active ");

        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<StarTeamDTO> findPaginate(int first, int max, String order) throws Exception {
        
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.StarTeamDTO(a.starTeamId,");
        builder.append("a.starUserId.userId,a.starUserId.name,a.coachUserId.userId,a.coachUserId.name,a.stateId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from StarTeam a ");
        builder.append("order by a.");
        builder.append(order);
        int count = findAll().size();
        
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setFirstResult(first);
        query.setMaxResults(max);
        List<StarTeamDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<StarTeam> findByStartTeam(StarTeam startTeam) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from StarTeam a ");
        builder.append("WHERE a.starTeamId = :id ");
        setParameter("id", startTeam.getStarTeamId());
        return createQuery(builder.toString());
    }

    @Override
    public List<StarTeam> findByFiltro(StarTeam startTeam) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from StarTeam a ");
        builder.append("WHERE 1=1 ");
        
        if(startTeam.getStarUserId() != null && startTeam.getStarUserId().getUserId()!= null) {
            builder.append("AND a.starUserId.userId = :starUser ");
            setParameter("starUser", startTeam.getStarUserId().getUserId());
        }

        if(startTeam.getCoachUserId() != null && startTeam.getCoachUserId().getUserId() != null) {
            builder.append("AND a.coachUserId.userId = :coachUser ");
            setParameter("coachUser", startTeam.getCoachUserId().getUserId());
        }

        if(startTeam.getStateId() != null) {
            builder.append("AND a.stateId = :state ");
            setParameter("state", startTeam.getStateId());
        }

        return createQuery(builder.toString());
    }

}