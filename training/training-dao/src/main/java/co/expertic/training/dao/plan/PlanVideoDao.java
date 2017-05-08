/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.plan;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.dto.PlanVideoDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.PlanVideo;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Edwin G
 */
public interface PlanVideoDao extends BaseDAO<PlanVideo> {

    public PlanVideo getByVideoPath(String fileName) throws DAOException;

    public List<PlanVideoDTO> getVideosByUser(Map parameters) throws DAOException;

    public PlanVideo getVideoById(Integer id) throws DAOException;

    public Integer getCountVideoByPlan(Integer coachAssignedPlanId, Integer userId, Integer toUserId, Integer roleSelected) throws DAOException;
    
    public Integer getCountVideoByPlanExt(Integer planId, Integer userId) throws DAOException;
    
    public Integer getCountVideosReceived(Integer coachAssignedPlanId, Integer fromUserId,  Integer toUserId, Integer roleSelected) throws DAOException;
    
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
     * @param users
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

    public int getCountVideoEmergencyIn(Integer planId, Integer fromUserId, Integer toUserId, Integer roleSelected) throws DAOException;

    public int getCountVideoEmergencyExt(Integer planId, Integer fromUserId) throws DAOException;
}
