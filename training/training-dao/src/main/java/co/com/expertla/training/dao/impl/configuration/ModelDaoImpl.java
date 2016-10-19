package co.com.expertla.training.dao.impl.configuration;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.configuration.ModelDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.ModelDTO;
import co.com.expertla.training.model.entities.Model;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* Model Dao Impl <br>
* Info. Creación: <br>
* fecha Oct 19, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class ModelDaoImpl extends BaseDAOImpl<Model> implements ModelDao {

    public ModelDaoImpl() {
    }
    
    @Override
    public List<Model> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Model a ");
        builder.append("order by a.modelId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<Model> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Model a ");
        builder.append("WHERE a.stateId = :active ");

        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<ModelDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.ModelDTO(a.modelId,");
        builder.append("a.sportEquipmentTypeId,a.name,a.stateId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from Model a ");

        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("WHERE ( 1=1 ");
            builder.append("OR UPPER(a.sportEquipmentTypeId.name) like '%");
            builder.append(filter);
            builder.append("%'");
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
        List<ModelDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<Model> findByModel(Model model) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Model a ");
        builder.append("WHERE a.modelId = :id ");
        setParameter("id", model.getModelId());
        return createQuery(builder.toString());
    }

    @Override
    public List<Model> findByFiltro(Model model) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Model a ");
        builder.append("WHERE 1=1 ");
        

        if(model.getSportEquipmentTypeId() != null && model.getSportEquipmentTypeId().getSportEquipmentTypeId() != null) {
            builder.append("AND a.sportEquipmentTypeId.sportEquipmentTypeId = :sportEquipmentType ");
            setParameter("sportEquipmentType", model.getSportEquipmentTypeId().getSportEquipmentTypeId());
        }

        if(model.getName() != null && !model.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + model.getName() + "%");
        }




        if(model.getStateId() != null) {
            builder.append("AND a.stateId = :state ");
            setParameter("state", model.getStateId());
        }

        builder.append("order by a.name asc ");
        return createQuery(builder.toString());
    }

    @Override
    public List<Model> findByName(Model model) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Model a ");
        builder.append("WHERE lower(trim(a.name)) = lower(trim(:name)) ");
        setParameter("name", model.getName().trim());
        return createQuery(builder.toString());
    }
}