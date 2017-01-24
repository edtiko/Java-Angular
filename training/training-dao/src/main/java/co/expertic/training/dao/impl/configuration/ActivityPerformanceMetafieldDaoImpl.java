package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.configuration.ActivityPerformanceMetafieldDao;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.ActivityPerformanceMetafieldDTO;
import co.expertic.training.model.entities.ActivityPerformanceMetafield;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* ActivityPerformanceMetafield Dao Impl <br>
* Info. Creación: <br>
* fecha Sep 15, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class ActivityPerformanceMetafieldDaoImpl extends BaseDAOImpl<ActivityPerformanceMetafield> implements ActivityPerformanceMetafieldDao {

    public ActivityPerformanceMetafieldDaoImpl() {
    }
    
    @Override
    public List<ActivityPerformanceMetafield> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ActivityPerformanceMetafield a ");
        builder.append("order by a.activityPerformanceMetafieldId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<ActivityPerformanceMetafield> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ActivityPerformanceMetafield a ");

        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<ActivityPerformanceMetafieldDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.expertic.training.model.dto.ActivityPerformanceMetafieldDTO(a.activityPerformanceMetafieldId,");
        builder.append("a.name,a.label,a.dataType, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from ActivityPerformanceMetafield a ");

        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("WHERE ( 1=1 ");
            builder.append("OR UPPER(a.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.label) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.dataType) like '%");
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
        List<ActivityPerformanceMetafieldDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<ActivityPerformanceMetafield> findByActivityPerformanceMetafield(ActivityPerformanceMetafield activityPerformanceMetafield) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ActivityPerformanceMetafield a ");
        builder.append("WHERE a.activityPerformanceMetafieldId = :id ");
        setParameter("id", activityPerformanceMetafield.getActivityPerformanceMetafieldId());
        return createQuery(builder.toString());
    }

    @Override
    public List<ActivityPerformanceMetafield> findByFiltro(ActivityPerformanceMetafield activityPerformanceMetafield) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ActivityPerformanceMetafield a ");
        builder.append("WHERE 1=1 ");
        
        if(activityPerformanceMetafield.getName() != null && !activityPerformanceMetafield.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + activityPerformanceMetafield.getName() + "%");
        }


        if(activityPerformanceMetafield.getLabel() != null && !activityPerformanceMetafield.getLabel().trim().isEmpty()) {
            builder.append("AND lower(a.label) like lower(:label) ");
            setParameter("label", "%" + activityPerformanceMetafield.getLabel() + "%");
        }


        if(activityPerformanceMetafield.getDataType() != null && !activityPerformanceMetafield.getDataType().trim().isEmpty()) {
            builder.append("AND lower(a.dataType) like lower(:dataType) ");
            setParameter("dataType", "%" + activityPerformanceMetafield.getDataType() + "%");
        }



        builder.append("order by a.name asc ");
        return createQuery(builder.toString());
    }

    @Override
    public List<ActivityPerformanceMetafield> findByName(ActivityPerformanceMetafield activityPerformanceMetafield) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ActivityPerformanceMetafield a ");
        builder.append("WHERE lower(trim(a.name)) = lower(trim(:name)) ");
        setParameter("name", activityPerformanceMetafield.getName().trim());
        return createQuery(builder.toString());
    }
}