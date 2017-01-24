package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.configuration.BikeTypeDao;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.BikeTypeDTO;
import co.expertic.training.model.entities.BikeType;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* BikeType Dao Impl <br>
* Info. Creación: <br>
* fecha Sep 9, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class BikeTypeDaoImpl extends BaseDAOImpl<BikeType> implements BikeTypeDao {

    public BikeTypeDaoImpl() {
    }
    
    @Override
    public List<BikeType> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from BikeType a ");
        builder.append("order by a.bikeTypeId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<BikeType> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from BikeType a ");
        builder.append("WHERE a.stateId = :active ");

        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<BikeTypeDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.expertic.training.model.dto.BikeTypeDTO(a.bikeTypeId,");
        builder.append("a.name,a.stateId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from BikeType a ");

        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("WHERE ( 1=1 ");
            builder.append("OR UPPER(a.name) like '%");
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
        List<BikeTypeDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<BikeType> findByBikeType(BikeType bikeType) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from BikeType a ");
        builder.append("WHERE a.bikeTypeId = :id ");
        setParameter("id", bikeType.getBikeTypeId());
        return createQuery(builder.toString());
    }

    @Override
    public List<BikeType> findByFiltro(BikeType bikeType) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from BikeType a ");
        builder.append("WHERE 1=1 ");
        
        if(bikeType.getName() != null && !bikeType.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + bikeType.getName() + "%");
        }

        if(bikeType.getStateId() != null) {
            builder.append("AND a.stateId = :state ");
            setParameter("state", bikeType.getStateId());
        }

        builder.append("order by a.name asc ");
        return createQuery(builder.toString());
    }

    @Override
    public List<BikeType> findByName(BikeType bikeType) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from BikeType a ");
        builder.append("WHERE lower(trim(a.name)) = lower(trim(:name)) ");
        setParameter("name", bikeType.getName().trim());
        return createQuery(builder.toString());
    }
}