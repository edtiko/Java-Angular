package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.configuration.PhysiologicalCapacityDao;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.PhysiologicalCapacityDTO;
import co.expertic.training.model.entities.PhysiologicalCapacity;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* PhysiologicalCapacity Dao Impl <br>
* Info. Creación: <br>
* fecha Sep 2, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class PhysiologicalCapacityDaoImpl extends BaseDAOImpl<PhysiologicalCapacity> implements PhysiologicalCapacityDao {

    public PhysiologicalCapacityDaoImpl() {
    }
    
    @Override
    public List<PhysiologicalCapacity> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from PhysiologicalCapacity a ");
        builder.append("order by a.physiologicalCapacityId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<PhysiologicalCapacity> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from PhysiologicalCapacity a ");
        builder.append("where a.stateId = :active");
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<PhysiologicalCapacityDTO> findPaginate(int first, int max, String order) throws Exception {
        
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.expertic.training.model.dto.PhysiologicalCapacityDTO(a.physiologicalCapacityId,");
        builder.append("a.name,a.code, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from PhysiologicalCapacity a ");
        builder.append("order by a.");
        builder.append(order);
        int count = findAll().size();
        
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setFirstResult(first);
        query.setMaxResults(max);
        List<PhysiologicalCapacityDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<PhysiologicalCapacity> findByPhysiologicalCapacity(PhysiologicalCapacity physiologicalCapacity) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from PhysiologicalCapacity a ");
        builder.append("WHERE a.physiologicalCapacityId = :id ");
        setParameter("id", physiologicalCapacity.getPhysiologicalCapacityId());
        return createQuery(builder.toString());
    }

    @Override
    public List<PhysiologicalCapacity> findByFiltro(PhysiologicalCapacity physiologicalCapacity) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from PhysiologicalCapacity a ");
        builder.append("WHERE 1=1 ");
        
        if(physiologicalCapacity.getName() != null && !physiologicalCapacity.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + physiologicalCapacity.getName() + "%");
        }


        if(physiologicalCapacity.getCode() != null && !physiologicalCapacity.getCode().trim().isEmpty()) {
            builder.append("AND lower(a.code) like lower(:code) ");
            setParameter("code", "%" + physiologicalCapacity.getCode() + "%");
        }



        return createQuery(builder.toString());
    }

    @Override
    public List<PhysiologicalCapacity> findAllAvailable() throws Exception {
           StringBuilder builder = new StringBuilder();
        builder.append(" SELECT DISTINCT c FROM PhysiologicalCapacity c, Activity a ");
        builder.append(" WHERE a.physiologicalCapacityId.physiologicalCapacityId  = c.physiologicalCapacityId ");
        builder.append(" AND a.stateId = :active ");
        builder.append(" AND c.stateId = :active ");
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        
        return createQuery(builder.toString());
    }

}