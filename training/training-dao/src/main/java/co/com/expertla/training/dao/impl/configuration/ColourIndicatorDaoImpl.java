package co.com.expertla.training.dao.impl.configuration;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.configuration.ColourIndicatorDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.ColourIndicatorDTO;
import co.com.expertla.training.model.entities.ColourIndicator;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* ColourIndicator Dao Impl <br>
* Info. Creación: <br>
* fecha Sep 14, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class ColourIndicatorDaoImpl extends BaseDAOImpl<ColourIndicator> implements ColourIndicatorDao {

    public ColourIndicatorDaoImpl() {
    }
    
    @Override
    public List<ColourIndicator> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ColourIndicator a ");
        builder.append("order by a.colourOrder desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<ColourIndicator> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ColourIndicator a ");
        builder.append("WHERE a.stateId = :active ");

        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<ColourIndicatorDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.ColourIndicatorDTO(a.colourIndicatorId,");
        builder.append("a.name,a.colour,a.hoursSpent,a.colourOrder,a.stateId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from ColourIndicator a ");

        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("WHERE ( 1=1 ");
            builder.append("OR UPPER(a.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.colour) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.hoursSpent) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.order) like '%");
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
        List<ColourIndicatorDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<ColourIndicator> findByColourIndicator(ColourIndicator colourIndicator) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ColourIndicator a ");
        builder.append("WHERE a.colourIndicatorId = :id ");
        setParameter("id", colourIndicator.getColourIndicatorId());
        return createQuery(builder.toString());
    }

    @Override
    public List<ColourIndicator> findByFiltro(ColourIndicator colourIndicator) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ColourIndicator a ");
        builder.append("WHERE 1=1 ");
        
        if(colourIndicator.getName() != null && !colourIndicator.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + colourIndicator.getName() + "%");
        }


        if(colourIndicator.getColour() != null && !colourIndicator.getColour().trim().isEmpty()) {
            builder.append("AND lower(a.colour) like lower(:colour) ");
            setParameter("colour", "%" + colourIndicator.getColour() + "%");
        }

        if(colourIndicator.getStateId() != null) {
            builder.append("AND a.stateId = :state ");
            setParameter("state", colourIndicator.getStateId());
        }

        builder.append("order by a.name asc ");
        return createQuery(builder.toString());
    }

    @Override
    public List<ColourIndicator> findByName(ColourIndicator colourIndicator) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ColourIndicator a ");
        builder.append("WHERE lower(trim(a.name)) = lower(trim(:name)) ");
        setParameter("name", colourIndicator.getName().trim());
        return createQuery(builder.toString());
    }
}