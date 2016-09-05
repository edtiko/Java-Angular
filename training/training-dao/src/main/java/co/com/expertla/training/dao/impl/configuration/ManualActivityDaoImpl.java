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
        sql.append("SELECT new co.com.expertla.training.model.dto.ActivityCalendarDTO(ma.manualActivityId, ma.name, ma.description, ma.modalityId.modalityId, ma.userId.userId  )");
        sql.append("FROM ManualActivity ma ");
        sql.append("WHERE ma.userId.userId = :userId ");
        sql.append("ORDER BY ma.name ASC ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        List<ActivityCalendarDTO> list = query.getResultList();
        return list;
    }

}
