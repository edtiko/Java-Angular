/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.plan.PlanVideoDao;
import co.com.expertla.training.model.dto.PlanVideoDTO;
import co.com.expertla.training.model.entities.PlanVideo;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class PlanVideoDaoImpl extends BaseDAOImpl<PlanVideo> implements PlanVideoDao{

    @Override
    public Integer countByVideoPath(String fileName) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(v.plan_video_id) ");     
        sql.append(" FROM plan_video v ");
        sql.append(" Where v.video_path = ").append("'").append(fileName).append("'");
        Query query = getEntityManager().createNativeQuery(sql.toString());
       
        List<Number> count = (List<Number>) query.getResultList();

        return count.get(0).intValue();
    }

    @Override
    public List<PlanVideoDTO> getVideosByUser(Integer userId, String fromto) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.PlanVideoDTO(m.planVideoId, m.name, m.fromUserId, m.toUserId, m.creationDate) ");        
        sql.append("FROM PlanVideo m ");
        if(fromto.equals("from")){
        sql.append("Where m.fromUserId.userId = :userId ");
        }else{
          sql.append("Where m.toUserId.userId = :userId ");  
        }
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
}
