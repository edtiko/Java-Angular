package co.expertic.training.dao.impl.security;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.security.ModuleDao;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.ModuleDTO;
import co.expertic.training.model.entities.Module;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* Module Dao Impl <br>
* Info. Creación: <br>
* fecha 26/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class ModuleDaoImpl extends BaseDAOImpl<Module> implements ModuleDao {

    public ModuleDaoImpl() {
    }
    
    @Override
    public List<Module> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Module a ");
        builder.append("order by a.moduleId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<Module> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Module a ");
        builder.append("WHERE a.stateId = :active ");
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<ModuleDTO> findPaginate(int first, int max, String order) throws Exception {
        
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.expertic.training.model.dto.ModuleDTO(a.moduleId,");
        builder.append("a.name,a.description,a.stateId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from Module a ");
        builder.append("order by a.");
        builder.append(order);
        int count = findAll().size();
        
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setFirstResult(first);
        query.setMaxResults(max);
        List<ModuleDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<Module> findByModule(Module module) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Module a ");
        builder.append("WHERE a.moduleId = :id ");
        setParameter("id", module.getModuleId());
        return createQuery(builder.toString());
    }

    @Override
    public List<Module> findByFiltro(Module module) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Module a ");
        builder.append("WHERE 1=1 ");
        
        if(module.getName() != null && !module.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + module.getName() + "%");
        }


        if(module.getDescription() != null && !module.getDescription().trim().isEmpty()) {
            builder.append("AND lower(a.description) like lower(:description) ");
            setParameter("description", "%" + module.getDescription() + "%");
        }




        if(module.getStateId() != null && module.getStateId() != null) {
            builder.append("AND a.stateId = :state ");
            setParameter("state", module.getStateId());
        }

        return createQuery(builder.toString());
    }

    @Override
    public List<Module> findByUserId(Integer userId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select Distinct a from Module a, RoleUser ru, RoleOption ro ");
        builder.append("WHERE a.stateId = :active ");
        builder.append("AND ro.roleId.roleId = ru.roleId.roleId ");
        builder.append("AND ru.userId.userId = :userId ");
        builder.append("AND ro.optionId.moduleId.moduleId = a.moduleId ");
        builder.append("AND ro.optionId.stateId = :active ");
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        setParameter("userId", userId);
        return createQuery(builder.toString());
    }

}