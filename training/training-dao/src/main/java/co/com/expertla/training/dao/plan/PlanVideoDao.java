/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.plan;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.PlanVideoDTO;
import co.com.expertla.training.model.entities.PlanVideo;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface PlanVideoDao extends BaseDAO<PlanVideo> {

    public PlanVideo getByVideoPath(String fileName) throws DAOException;

    public List<PlanVideoDTO> getVideosByUser(Integer userId, String fromto) throws DAOException;

    public PlanVideo getVideoById(Integer id) throws DAOException;

    public Integer getCountVideoByPlan(Integer coachAssignedPlanId, Integer userId) throws DAOException;
    
    public Integer getCountVideosReceived(Integer coachAssignedPlanId, Integer userId) throws DAOException;

    public void readVideos(Integer coachAssignedPlanId, Integer userId) throws DAOException;

    public void readVideo(Integer planVideoId) throws DAOException;

}
