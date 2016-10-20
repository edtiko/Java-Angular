/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.impl.configuration;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.configuration.ManualActivityDao;
import co.com.expertla.training.model.dto.ActivityCalendarDTO;
import co.com.expertla.training.model.entities.ManualActivity;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class ManualActivityDaoImpl extends BaseDAOImpl<ManualActivity> implements ManualActivityDao{

    @Override
    public List<ActivityCalendarDTO> findByUserId(Integer userId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.ActivityCalendarDTO(ma.manualActivityId, ma.name, ma.description, ma.sportId.sportId, ma.userId.userId  )");
        sql.append("FROM ManualActivity ma ");
        sql.append("WHERE ma.userId.userId = :userId ");
        sql.append("ORDER BY ma.name ASC ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        List<ActivityCalendarDTO> list = query.getResultList();
        return list;
    }

    @Override
    public ActivityCalendarDTO findByManualActivityId(Integer manualActivityId) throws DAOException {
       StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.ActivityCalendarDTO(ma.manualActivityId, ma.name, ma.description, ma.sportId.sportId, ma.userId.userId  )");
        sql.append("FROM ManualActivity ma ");
        sql.append("WHERE ma.manualActivityId = :manualActivityId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("manualActivityId", manualActivityId);
        List<ActivityCalendarDTO> list = query.getResultList();
        
        return list!=null?list.get(0):null;
    }

    @Override
    public boolean existManualActivity(ActivityCalendarDTO activity) throws DAOException {
        
     StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(manual_activity_id)  ");
        sql.append(" FROM manual_activity ");
        sql.append(" WHERE name = '").append(activity.getName()).append("'");
        sql.append(" And sport_id = ").append(activity.getSportId());
        sql.append(" And user_id  = ").append(activity.getUserId());
        if(activity.getId() != null){
        sql.append(" And manual_activity_id  != ").append(activity.getId());
        }
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.get(0).intValue() > 0;
    }

}
