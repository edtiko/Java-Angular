/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.plan;

import co.com.expertla.training.model.dto.ChartReportDTO;
import co.com.expertla.training.model.dto.PlanVideoDTO;
import co.com.expertla.training.model.entities.PlanVideo;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Edwin G
 */
public interface PlanVideoService {

    public PlanVideoDTO create(PlanVideo video) throws Exception;

    public PlanVideoDTO getByVideoPath(String fileName) throws Exception;

    public List<PlanVideoDTO> getVideosByUser(Map parameters) throws Exception;

    public PlanVideoDTO getVideoById(Integer id) throws Exception;

    public Integer getCountVideoByPlan(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws Exception;

    public Integer getCountVideosReceived(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws Exception;

    public void readVideos(Integer coachAssignedPlanId, Integer userId) throws Exception;

    public void readVideo(Integer planMessageId) throws Exception;
    
    public void rejectedVideo(Integer planVideoId) throws Exception;

    public Integer getCountVideoByPlanExt(Integer planId, Integer userId) throws Exception;

    public Integer getCountVideosReceivedExt(Integer planId, Integer userId) throws Exception;

    public void readVideosExt(Integer planId, Integer userId) throws Exception;

    
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
    public List<ChartReportDTO> getResponseCountVideo(Integer userId, Integer roleId) throws Exception;
    
    /**
     * Consulta los tiempos de respuesta de los videos <br>
     * Info. Creación: <br>
     * fecha Sep 28, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @param roleId
     * @return 
     * @throws Exception 
     */
    public List<PlanVideoDTO> getResponseTimeVideos(Integer userId, Integer roleId)throws  Exception;
    
    public PlanVideo getPlanVideoById(Integer id) throws Exception;
    
    public PlanVideo store(PlanVideo video) throws Exception;

    public int getCountVideoEmergencyIn(Integer planId, Integer fromUserId, Integer roleSelected) throws Exception;

    public int getCountVideoEmergencyExt(Integer planId, Integer fromUserId)throws Exception;
}
