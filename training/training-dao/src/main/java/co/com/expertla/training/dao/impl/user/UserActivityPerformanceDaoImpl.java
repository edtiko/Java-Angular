package co.com.expertla.training.dao.impl.user;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.user.UserActivityPerformanceDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.ChartDTO;
import co.com.expertla.training.model.dto.UserActivityPerformanceDTO;
import co.com.expertla.training.model.entities.UserActivityPerformance;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* UserActivityPerformance Dao Impl <br>
* Info. Creación: <br>
* fecha Sep 15, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class UserActivityPerformanceDaoImpl extends BaseDAOImpl<UserActivityPerformance> implements UserActivityPerformanceDao {

    public UserActivityPerformanceDaoImpl() {
    }
    
    @Override
    public List<UserActivityPerformance> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from UserActivityPerformance a ");
        builder.append("order by a.userActivityPerformanceId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<UserActivityPerformance> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from UserActivityPerformance a ");

        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<UserActivityPerformanceDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.UserActivityPerformanceDTO(a.userActivityPerformanceId,");
        builder.append("a.userId,a.activityId,a.value,a.activityPerformanceMetafieldId,a.executedDate, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from UserActivityPerformance a ");

        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("WHERE ( 1=1 ");
            builder.append("OR UPPER(a.userId.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.activityId.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.value) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.activityPerformanceMetafieldId.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.executedDate) like '%");
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
        List<UserActivityPerformanceDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<UserActivityPerformance> findByUserActivityPerformance(UserActivityPerformance userActivityPerformance) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from UserActivityPerformance a ");
        builder.append("WHERE a.userActivityPerformanceId = :id ");
        setParameter("id", userActivityPerformance.getUserActivityPerformanceId());
        return createQuery(builder.toString());
    }

    @Override
    public List<UserActivityPerformance> findByFiltro(UserActivityPerformance userActivityPerformance) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from UserActivityPerformance a ");
        builder.append("WHERE 1=1 ");
        

        if(userActivityPerformance.getUserId() != null && userActivityPerformance.getUserId().getUserId() != null) {
            builder.append("AND a.userId.userId = :user ");
            setParameter("user", userActivityPerformance.getUserId().getUserId());
        }


        if(userActivityPerformance.getActivityId() != null && userActivityPerformance.getActivityId().getActivityId() != null) {
            builder.append("AND a.activityId.activityId = :activity ");
            setParameter("activity", userActivityPerformance.getActivityId().getActivityId());
        }

        if(userActivityPerformance.getValue() != null && !userActivityPerformance.getValue().trim().isEmpty()) {
            builder.append("AND lower(a.value) like lower(:value) ");
            setParameter("value", "%" + userActivityPerformance.getValue() + "%");
        }



        if(userActivityPerformance.getActivityPerformanceMetafieldId() != null && userActivityPerformance.getActivityPerformanceMetafieldId().getActivityPerformanceMetafieldId() != null) {
            builder.append("AND a.activityPerformanceMetafieldId.activityPerformanceMetafieldId = :activityPerformanceMetafield ");
            setParameter("activityPerformanceMetafield", userActivityPerformance.getActivityPerformanceMetafieldId().getActivityPerformanceMetafieldId());
        }

        return createQuery(builder.toString());
    }
    
    @Override
    public List<UserActivityPerformanceDTO> findByDateRangeAndUserId( Date fromDate, Date toDate, Integer userId) throws Exception {
         StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.UserActivityPerformanceDTO(a.userActivityPerformanceId,");
        builder.append("a.userId,a.activityId,a.value,a.activityPerformanceMetafieldId,a.executedDate, a.creationDate");
        builder.append(") from UserActivityPerformance a ");
        builder.append(" where a.userId.userId = :id ");
        builder.append(" and a.executedDate BETWEEN :fromDate AND :toDate ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setParameter("id",userId);
        query.setParameter("fromDate",fromDate);
        query.setParameter("toDate",toDate);
        List<UserActivityPerformanceDTO> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<ChartDTO> findByDateRangeAndUserIdAndMetaField(Date fromDate, Date toDate, Integer userId, Integer metafieldId) throws Exception {
        if (metafieldId.equals(1)) {
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT * FROM  (");
            builder.append("select distinct date_trunc('day', (current_date - offs)) as executed_date from generate_series(0,6,1) as offs ) d ");
            builder.append(" LEFT   JOIN (");
            builder.append(" SELECT date_trunc('day', executed_date)::date AS executed_date ,count(distinct(activity_id)) AS value  ");
            builder.append("FROM   user_activity_performance where user_id = ").append(userId).append(" AND executed_date >= date '").append(fromDate).append("' ");
            builder.append("AND executed_date <= date '").append(toDate).append("' GROUP  BY 1");
            builder.append(") t USING (executed_date) ORDER  BY executed_date");
            Query query = this.getEntityManager().createNativeQuery(builder.toString());
            List<Object[]> list = query.getResultList();
            List<ChartDTO> chartList = new ArrayList<>();
            ChartDTO obj = new ChartDTO();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Object[] result : list) {
                obj = new ChartDTO();
                obj.setExecutedDate(sdf.parse(result[0].toString()));
                obj.setValue(result[1] == null ? 0 : (Long) result[1]);
                chartList.add(obj);
            }
            return chartList;
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT * FROM  (");
            builder.append("select distinct date_trunc('day', (current_date - offs)) as executed_date from generate_series(0,6,1) as offs ) d ");
            builder.append(" LEFT   JOIN (");
            builder.append(" SELECT date_trunc('day', executed_date)::date AS executed_date ,sum(CAST(coalesce(value, '0') AS integer)) AS value  ");
            builder.append("FROM   user_activity_performance where user_id = ").append(userId).append(" AND executed_date >= date '").append(fromDate).append("' ");
            builder.append("AND executed_date <= date '").append(toDate).append("'");
            builder.append(" AND activity_performance_metafield_id = ").append(metafieldId);
            builder.append(" GROUP  BY 1");
            builder.append(") t USING (executed_date) ORDER  BY executed_date");
            Query query = this.getEntityManager().createNativeQuery(builder.toString());
            List<Object[]> list = query.getResultList();
            List<ChartDTO> chartList = new ArrayList<>();
            ChartDTO obj = new ChartDTO();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Object[] result : list) {
                obj = new ChartDTO();
                obj.setExecutedDate(sdf.parse(result[0].toString()));
                obj.setValue(result[1] == null ? 0 : (Long) result[1]);
                chartList.add(obj);
            }
            return chartList;
        }
    }
    
    @Override
    public List<ChartDTO> findByDateRangeAndUserIdAndMetaField( Date fromDate, Date toDate, Integer userId, Integer metafieldId, Integer days, boolean weekly) throws Exception {
        if (metafieldId.equals(1)) {
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT * FROM  (");
            if(weekly) {
                builder.append("select distinct date_trunc('week', (current_date - offs)) as executed_date ");
                builder.append("from generate_series(0,28,7) as offs ) d"); 
            } else {
                builder.append("select distinct date_trunc('month', (current_date - offs)) as executed_date ");
                builder.append("from generate_series(0,").append(days).append(",28) as offs ) d"); 
            }
            builder.append(" LEFT   JOIN (");
            if(weekly) {    
                builder.append("SELECT date_trunc('week', executed_date)::date AS executed_date ");
            } else {
                builder.append("SELECT date_trunc('month', executed_date)::date AS executed_date ");   
            }
            builder.append(", coalesce(count(*), '0') AS value ");
            builder.append(" FROM   user_activity_performance");
            builder.append(" where user_id = ").append(userId);
            builder.append(" AND executed_date >= date ").append("'").append(fromDate).append("'");
            builder.append(" AND executed_date <= date ").append("'").append(toDate).append("'");
            builder.append(" GROUP  BY 1");
            builder.append(" ) t USING (executed_date)");
            builder.append(" ORDER  BY executed_date ");
            Query query = this.getEntityManager().createNativeQuery(builder.toString());
            List<Object[]> list = query.getResultList();
            List<ChartDTO> chartList = new ArrayList<>();
            ChartDTO obj = new ChartDTO();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Object[] result : list) {
                obj = new ChartDTO();
                obj.setExecutedDate(sdf.parse(result[0].toString()));
                obj.setValue(result[1] == null ? 0 :(Long) result[1]);
                chartList.add(obj);
            }
            return chartList;
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT * FROM  (");
            if(weekly) {
                builder.append("select distinct date_trunc('week', (current_date - offs)) as executed_date ");
                builder.append("from generate_series(0,28,7) as offs ) d"); 
            } else {
                builder.append("select distinct date_trunc('month', (current_date - offs)) as executed_date ");
                builder.append("from generate_series(0,").append(days).append(",28) as offs ) d"); 
            }
            builder.append(" LEFT   JOIN (");
            if(weekly) {    
                builder.append("SELECT date_trunc('week', executed_date)::date AS executed_date ");
            } else {
                builder.append("SELECT date_trunc('month', executed_date)::date AS executed_date ");   
            }
            builder.append(", sum(CAST(coalesce(value, '0') AS integer)) AS value ");
            builder.append(" FROM   user_activity_performance");
            builder.append(" where user_id = ").append(userId);
            builder.append(" AND executed_date >= date ").append("'").append(fromDate).append("'");
            builder.append(" AND executed_date <= date ").append("'").append(toDate).append("'");
            builder.append(" and  activity_performance_metafield_id = ").append(metafieldId);
            builder.append(" GROUP  BY 1");
            builder.append(" ) t USING (executed_date)");
            builder.append(" ORDER  BY executed_date ");
            Query query = this.getEntityManager().createNativeQuery(builder.toString());
            List<Object[]> list = query.getResultList();
            List<ChartDTO> chartList = new ArrayList<>();
            ChartDTO obj = new ChartDTO();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Object[] result : list) {
                obj = new ChartDTO();
                obj.setExecutedDate(sdf.parse(result[0].toString()));
                obj.setValue(result[1] == null ? 0 :(Long) result[1]);
                chartList.add(obj);
            }
            return chartList;
        }
    }
    
    @Override
    public List<ChartDTO> findActivitiesByDateRangeAndUserId( Date fromDate, Date toDate, Integer userId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM  (");
        builder.append("select distinct date_trunc('day', (current_date - offs)) as executed_date from generate_series(0,6,1) as offs ) d ");
        builder.append(" LEFT   JOIN (");
        builder.append(" SELECT date_trunc('day', executed_date)::date AS executed_date ,count(*) AS value  ");
        builder.append("FROM   user_activity_performance where user_id = ").append(userId).append(" AND executed_date >= date '").append(fromDate).append("' ");
        builder.append("AND executed_date <= date '").append(toDate).append("' GROUP  BY 1");
        builder.append(") t USING (executed_date) ORDER  BY executed_date");
         Query query = this.getEntityManager().createNativeQuery(builder.toString());
            List<Object[]> list = query.getResultList();
            List<ChartDTO> chartList = new ArrayList<>();
            ChartDTO obj = new ChartDTO();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Object[] result : list) {
                obj = new ChartDTO();
                obj.setExecutedDate(sdf.parse(result[0].toString()));
                obj.setValue(result[1] == null ? 0 :(Long) result[1]);
                chartList.add(obj);
            }
            return chartList;
    }

}