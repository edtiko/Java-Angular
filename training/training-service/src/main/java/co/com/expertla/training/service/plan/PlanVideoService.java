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

/**
 *
 * @author Edwin G
 */
public interface PlanVideoService {

    public PlanVideoDTO create(PlanVideo video) throws Exception;

    public PlanVideoDTO getByVideoPath(String fileName) throws Exception;

    public List<PlanVideoDTO> getVideosByUser(Integer coachAssignedPlanId, Integer userId, String fromto) throws Exception;

    public PlanVideoDTO getVideoById(Integer id) throws Exception;
    
    public Integer getCountVideoByPlan(Integer coachAssignedPlanId, Integer userId) throws Exception;
    
    public Integer getCountVideosReceived(Integer coachAssignedPlanId, Integer userId) throws Exception;
    
    public void readVideos(Integer coachAssignedPlanId, Integer userId) throws Exception;
    
    public void readVideo(Integer planMessageId) throws Exception;
    
    public List<PlanVideo> getPlanVideoStarByCoach(Integer userId) throws Exception;
    
    /**
     * Consulta la cantidad de videos repondidos por el usuario <br>
     * Info. Creación: <br>
     * fecha 13/10/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return 
     * @throws Exception 
     */
    public List<ChartReportDTO> getResponseCountVideo(Integer userId) throws Exception;
    
}
