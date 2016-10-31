/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.plan;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.PlanVideoDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.PlanVideo;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface PlanVideoDao extends BaseDAO<PlanVideo> {

    public PlanVideo getByVideoPath(String fileName) throws DAOException;

    public List<PlanVideoDTO> getVideosByUser(Integer coachAssignedPlanId, Integer userId, String fromto, String tipoPlan) throws DAOException;

    public PlanVideo getVideoById(Integer id) throws DAOException;

    public Integer getCountVideoByPlan(Integer coachAssignedPlanId, Integer userId) throws DAOException;
    
    public Integer getCountVideoByPlanExt(Integer planId, Integer userId) throws DAOException;
    
    public Integer getCountVideosReceived(Integer coachAssignedPlanId, Integer userId) throws DAOException;
    
    public Integer getCountVideosReceivedExt(Integer planId, Integer userId) throws DAOException;

    public void readVideos(Integer coachAssignedPlanId, Integer userId) throws DAOException;
    
    public void readVideosExt(Integer planId, Integer userId) throws DAOException;

    public void readVideo(Integer planVideoId) throws DAOException;
    
    public void rejectedVideo(Integer planVideoId) throws Exception;
    
    public List<PlanVideo> getPlanVideoStarByCoach(Integer userId) throws Exception;
    
    public List<PlanVideo> getPlanVideoStarByStar(Integer userId) throws Exception;
    
    /**
     * Consulta la cantidad de videos repondidos por el usuario <br>
     * Info. Creación: <br>
     * fecha 13/10/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return 
     * @throws Exception 
     */
    public List<PlanVideoDTO> getResponseCountVideo(Integer userId,List<UserDTO> users) throws Exception;

    
    /**
     * Consulta los tiempos de respuesta de los videos <br>
     * Info. Creación: <br>
     * fecha Sep 28, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @param users
     * @return 
     * @throws Exception 
     */
    public List<PlanVideoDTO> getResponseTimeVideos(Integer userId, List<UserDTO> users)throws  Exception;
}
