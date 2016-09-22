package co.com.expertla.training.dao.impl.configuration;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.configuration.MembershipDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.MembershipDTO;
import co.com.expertla.training.model.entities.Membership;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* Membership Dao Impl <br>
* Info. Creación: <br>
* fecha 21/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class MembershipDaoImpl extends BaseDAOImpl<Membership> implements MembershipDao {

    public MembershipDaoImpl() {
    }
    
    @Override
    public List<Membership> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Membership a ");
        builder.append("order by a.membershipId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<MembershipDTO> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.MembershipDTO(a.membershipId,");
        builder.append("a.name,a.description,a.stateId, mp.price");
        builder.append(") from Membership a, MembershipPrice mp ");
        builder.append("WHERE a.stateId = :active ");
        builder.append("AND mp.membershipId.membershipId = a.membershipId ");

        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        List<MembershipDTO> list = query.getResultList();
        return list;
    }

    @Override
    public List<MembershipDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.MembershipDTO(a.membershipId,");
        builder.append("a.name,a.description,a.stateId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from Membership a ");

        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("WHERE ( 1=1 ");
            builder.append("OR UPPER(a.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.description) like '%");
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
        List<MembershipDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<Membership> findByMembership(Membership membership) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Membership a ");
        builder.append("WHERE a.membershipId = :id ");
        setParameter("id", membership.getMembershipId());
        return createQuery(builder.toString());
    }

    @Override
    public List<Membership> findByFiltro(Membership membership) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Membership a ");
        builder.append("WHERE 1=1 ");
        
        if(membership.getName() != null && !membership.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + membership.getName() + "%");
        }

        if(membership.getDescription() != null && !membership.getDescription().trim().isEmpty()) {
            builder.append("AND lower(a.description) like lower(:description) ");
            setParameter("description", "%" + membership.getDescription() + "%");
        }

        if(membership.getStateId() != null) {
            builder.append("AND a.stateId = :state ");
            setParameter("state", membership.getStateId());
        }

        builder.append("order by a.name asc ");
        return createQuery(builder.toString());
    }

    @Override
    public List<Membership> findByName(Membership membership) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Membership a ");
        builder.append("WHERE lower(trim(a.name)) = lower(trim(:name)) ");
        setParameter("name", membership.getName().trim());
        return createQuery(builder.toString());
    }
}