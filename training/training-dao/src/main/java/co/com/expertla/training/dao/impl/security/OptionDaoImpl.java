package co.com.expertla.training.dao.impl.security;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.security.OptionDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.OptionDTO;
import co.com.expertla.training.model.entities.Option;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* Option Dao Impl <br>
* Info. Creación: <br>
* fecha 29/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class OptionDaoImpl extends BaseDAOImpl<Option> implements OptionDao {

    public OptionDaoImpl() {
    }
    
    @Override
    public List<Option> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Option a ");
        builder.append("order by a.optionId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<Option> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Option a ");
        builder.append("WHERE a.stateId = :active ");

        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<OptionDTO> findPaginate(int first, int max, String order) throws Exception {
        
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.OptionDTO(a.optionId,");
        builder.append("a.name,a.description,a.url,a.stateId,a.moduleId.moduleId,a.moduleId.name, a.creationDate, a.lastUpdate,");
        builder.append("(select u.optionId FROM Option u WHERE a.masterOptionId.optionId = u.optionId), (select u.name FROM Option u WHERE a.masterOptionId.optionId = u.optionId),");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from Option a ");
        builder.append("order by a.");
        builder.append(order);
        int count = findAll().size();
        
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setFirstResult(first-1);
        query.setMaxResults(max);
        List<OptionDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<Option> findByOption(Option option) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Option a ");
        builder.append("WHERE a.optionId = :id ");
        setParameter("id", option.getOptionId());
        return createQuery(builder.toString());
    }

    @Override
    public List<Option> findByFiltro(Option option) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Option a ");
        builder.append("WHERE 1=1 ");
        
        if(option.getName() != null && !option.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + option.getName() + "%");
        }


        if(option.getUrl() != null && !option.getUrl().trim().isEmpty()) {
            builder.append("AND lower(a.url) like lower(:url) ");
            setParameter("url", "%" + option.getUrl() + "%");
        }


        if(option.getDescription() != null && !option.getDescription().trim().isEmpty()) {
            builder.append("AND lower(a.description) like lower(:description) ");
            setParameter("description", "%" + option.getDescription() + "%");
        }



        if(option.getMasterOptionId() != null && option.getMasterOptionId().getMasterOptionId() != null) {
            builder.append("AND a.masterOptionId.masterOptionId = :masterOption ");
            setParameter("masterOption", option.getMasterOptionId().getMasterOptionId());
        }



        if(option.getStateId() != null) {
            builder.append("AND a.stateId = :state ");
            setParameter("state", option.getStateId());
        }

        if(option.getModuleId() != null && option.getModuleId().getModuleId() != null) {
            builder.append("AND a.moduleId.moduleId = :module ");
            setParameter("module", option.getModuleId().getModuleId());
        }


        return createQuery(builder.toString());
    }

}