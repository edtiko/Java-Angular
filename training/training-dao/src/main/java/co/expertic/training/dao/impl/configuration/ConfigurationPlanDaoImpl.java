package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.configuration.ConfigurationPlanDao;
import co.expertic.training.enums.StateEnum;
import co.expertic.training.model.dto.ConfigurationPlanDTO;
import co.expertic.training.model.entities.ConfigurationPlan;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* ConfigurationPlan Dao Impl <br>
* Info. Creación: <br>
* fecha 24/11/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class ConfigurationPlanDaoImpl extends BaseDAOImpl<ConfigurationPlan> implements ConfigurationPlanDao {

    public ConfigurationPlanDaoImpl() {
    }
    
    @Override
    public List<ConfigurationPlan> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ConfigurationPlan a ");
        builder.append("order by a.configurationPlanId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<ConfigurationPlan> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ConfigurationPlan a ");
        builder.append("order by a.configurationPlanId desc ");
        return createQuery(builder.toString());
    }

    @Override
    public List<ConfigurationPlanDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.expertic.training.model.dto.ConfigurationPlanDTO(a.configurationPlanId,");
        builder.append("a.trainingPlanId,a.communicationRoleId,a.audioDuration,a.audioEmergency,a.audioCount,a.emailEmergency,a.emailCount,a.messageEmergency,a.messageCount,a.videoDuration,a.videoEmergency,a.videoCount,a.athletesCount, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from ConfigurationPlan a ");

        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("WHERE ( ");
            builder.append(" UPPER(a.trainingPlanId.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.communicationRoleId.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.audioDuration as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.audioEmergency as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.audioCount as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.emailEmergency as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.emailCount as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.messageEmergency as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.messageCount as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.videoDuration as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.videoEmergency as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.videoCount as character)) like '%");
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
        List<ConfigurationPlanDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }
    
    @Override
    public List<ConfigurationPlanDTO> findPaginate(int first, int max, 
            String order, String filter, Integer planId) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.expertic.training.model.dto.ConfigurationPlanDTO(a.configurationPlanId,");
        builder.append("a.trainingPlanId,a.communicationRoleId,a.audioDuration,a.audioEmergency,a.audioCount,a.emailEmergency,a.emailCount,a.messageEmergency,a.messageCount,a.videoDuration,a.videoEmergency,a.videoCount, a.athletesCount, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from ConfigurationPlan a ");
        builder.append("WHERE a.trainingPlanId.trainingPlanId = :planId ");

        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("AND ( ");
            builder.append(" UPPER(a.trainingPlanId.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.communicationRoleId.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.audioDuration as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.audioEmergency as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.audioCount as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.emailEmergency as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.emailCount as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.messageEmergency as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.messageCount as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.videoDuration as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.videoEmergency as character)) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(cast(a.videoCount as character)) like '%");
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
        int count = this.getEntityManager().createQuery(builder.toString())
                .setParameter("planId", planId)
                .getResultList().size();
        
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setParameter("planId", planId);
        query.setFirstResult(first);
        query.setMaxResults(max);
        List<ConfigurationPlanDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<ConfigurationPlan> findByConfigurationPlan(ConfigurationPlan configurationPlan) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ConfigurationPlan a ");
        builder.append("WHERE a.configurationPlanId = :id ");
        setParameter("id", configurationPlan.getConfigurationPlanId());
        return createQuery(builder.toString());
    }

    @Override
    public List<ConfigurationPlan> findByFiltro(ConfigurationPlan configurationPlan) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from ConfigurationPlan a ");
        builder.append("WHERE 1=1 ");
        

        if(configurationPlan.getTrainingPlanId() != null && configurationPlan.getTrainingPlanId().getTrainingPlanId() != null) {
            builder.append("AND a.trainingPlanId.trainingPlanId = :trainingPlan ");
            setParameter("trainingPlan", configurationPlan.getTrainingPlanId().getTrainingPlanId());
        }


        if(configurationPlan.getCommunicationRoleId() != null && configurationPlan.getCommunicationRoleId().getRoleId() != null) {
            builder.append("AND a.communicationRoleId.communicationRoleId = :communicationRole ");
            setParameter("communicationRole", configurationPlan.getCommunicationRoleId().getRoleId());
        }
        return createQuery(builder.toString());
    }
    

    @Override
    public ConfigurationPlan findByAthleteUserId(Integer userId, Integer roleSelected) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT cp ");
        sql.append(" FROM CoachAssignedPlan  m , ConfigurationPlan cp ");    
        sql.append(" WHERE m.trainingPlanUserId.userId.userId = :userId ");
        sql.append(" AND  m.trainingPlanUserId.trainingPlanId.trainingPlanId = cp.trainingPlanId.trainingPlanId ");
        sql.append(" AND m.trainingPlanUserId.stateId = ").append(StateEnum.ACTIVE.getId());
        sql.append(" AND cp.communicationRoleId.roleId = ").append(roleSelected);
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        List<ConfigurationPlan> list = query.getResultList();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
    
    @Override
    public ConfigurationPlan findByUserRole(Integer userId, Integer roleId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT cp ");
        sql.append(" FROM TrainingPlanUser  m , ConfigurationPlan cp ");
        sql.append(" WHERE m.userId.userId = :userId ");
        sql.append(" AND  m.trainingPlanId.trainingPlanId = cp.trainingPlanId.trainingPlanId ");
        sql.append(" AND m.stateId = ").append(StateEnum.ACTIVE.getId());
        sql.append(" AND cp.communicationRoleId.roleId = ").append(roleId);
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        List<ConfigurationPlan> list = query.getResultList();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

}