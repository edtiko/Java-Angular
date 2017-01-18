package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.configuration.BrandDao;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.BrandDTO;
import co.expertic.training.model.entities.Brand;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* Brand Dao Impl <br>
* Info. Creación: <br>
* fecha Sep 9, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class BrandDaoImpl extends BaseDAOImpl<Brand> implements BrandDao {

    public BrandDaoImpl() {
    }
    
    @Override
    public List<Brand> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Brand a ");
        builder.append("order by a.brandId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<Brand> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Brand a ");
        builder.append("WHERE a.stateId = :active ");

        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<BrandDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.expertic.training.model.dto.BrandDTO(a.brandId,");
        builder.append("a.name,a.stateId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from Brand a ");

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
        List<BrandDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<Brand> findByBrand(Brand brand) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Brand a ");
        builder.append("WHERE a.brandId = :id ");
        setParameter("id", brand.getBrandId());
        return createQuery(builder.toString());
    }

    @Override
    public List<Brand> findByFiltro(Brand brand) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Brand a ");
        builder.append("WHERE 1=1 ");
        
        if(brand.getName() != null && !brand.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + brand.getName() + "%");
        }




        if(brand.getStateId() != null) {
            builder.append("AND a.stateId = :state ");
            setParameter("state", brand.getStateId());
        }

        builder.append("order by a.name asc ");
        return createQuery(builder.toString());
    }

    @Override
    public List<Brand> findByName(Brand brand) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Brand a ");
        builder.append("WHERE lower(trim(a.name)) = lower(trim(:name)) ");
        setParameter("name", brand.getName().trim());
        return createQuery(builder.toString());
    }
}