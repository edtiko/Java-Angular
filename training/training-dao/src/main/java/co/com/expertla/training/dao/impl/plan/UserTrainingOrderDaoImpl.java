package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.plan.UserTrainingOrderDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.entities.UserTrainingOrder;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* UserTrainingOrder Dao Impl <br>
* Info. Creación: <br>
* fecha 12/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class UserTrainingOrderDaoImpl extends BaseDAOImpl<UserTrainingOrder> implements UserTrainingOrderDao {

    public UserTrainingOrderDaoImpl() {
    }
    
    @Override
    public List<UserTrainingOrder> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from UserTrainingOrder a ");
        builder.append("order by a.userTrainingOrderId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<UserTrainingOrder> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from UserTrainingOrder a ");
        builder.append("where a.status = :active ");
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<UserTrainingOrder> findByUserTrainingOrder(UserTrainingOrder userTrainingOrder) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from UserTrainingOrder a ");
        builder.append("WHERE a.userTrainingOrderId = :id ");
        setParameter("id", userTrainingOrder.getUserTrainingOrderId());
        return createQuery(builder.toString());
    }

    @Override
    public List<UserTrainingOrder> findByFiltro(UserTrainingOrder userTrainingOrder) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from UserTrainingOrder a ");
        builder.append("WHERE 1=1 ");
        
        if(userTrainingOrder.getUserId() != null) {
            builder.append("AND a.userId = :userId ");
            setParameter("userId", userTrainingOrder.getUserId());
        }
        
        if(userTrainingOrder.getStatus()!= null && !userTrainingOrder.getStatus().isEmpty()) {
            builder.append("AND a.status = :status ");
            setParameter("status", userTrainingOrder.getStatus());
        }
        

        return createQuery(builder.toString());
    }

    @Override
    public UserTrainingOrder findByUserId(Integer userId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from UserTrainingOrder a ");
        builder.append("WHERE a.userId = :userId ");
        builder.append("AND a.status = :integrated ");
        builder.append("ORDER BY a.userTrainingOrderId DESC ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setParameter("userId", userId);
        query.setParameter("integrated", "integrated");
        List<UserTrainingOrder> list = query.getResultList();
        return list.size() > 0 ? list.get(0) : null;
    }

}