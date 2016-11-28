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
* fecha 25/11/2016 <br>
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
        builder.append("order by a.name asc ");
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<OptionDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.OptionDTO(a.optionId,");
        builder.append("a.name,a.description,a.url,a.moduleId,a.masterOptionId,a.stateId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from Option a ");

        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("WHERE ( 1!=1 ");
            builder.append("OR UPPER(a.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.url) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.description) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.moduleId.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.masterOptionId.name) like '%");
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



        if(option.getModuleId() != null && option.getModuleId().getModuleId() != null) {
            builder.append("AND a.moduleId.moduleId = :module ");
            setParameter("module", option.getModuleId().getModuleId());
        }


        if(option.getMasterOptionId() != null && option.getMasterOptionId().getMasterOptionId() != null) {
            builder.append("AND a.masterOptionId.masterOptionId = :masterOption ");
            setParameter("masterOption", option.getMasterOptionId().getMasterOptionId());
        }



        if(option.getStateId() != null) {
            builder.append("AND a.stateId = :state ");
            setParameter("state", option.getStateId());
        }

        builder.append("order by a.name asc ");
        return createQuery(builder.toString());
    }

    @Override
    public List<Option> findByName(Option option) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Option a ");
        builder.append("WHERE lower(trim(a.name)) = lower(trim(:name)) ");
        setParameter("name", option.getName().trim());
        return createQuery(builder.toString());
    }
    
    @Override
    public List<Option> findByUserId(Integer userId, Integer moduleId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select Distinct o from Option o, Module a, RoleUser ru, RoleOption ro ");
        builder.append("WHERE a.stateId = :active ");
        builder.append("AND a.moduleId = o.moduleId.moduleId ");
        builder.append("AND ro.roleId.roleId = ru.roleId.roleId ");
        builder.append("AND ru.userId.userId = :userId ");
        builder.append("AND ro.optionId.optionId = o.optionId ");
        builder.append("AND ro.optionId.stateId = :active ");
        builder.append("AND a.moduleId = :moduleId ");
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        setParameter("userId", userId);
        setParameter("moduleId", moduleId);
        return createQuery(builder.toString());
    }
}