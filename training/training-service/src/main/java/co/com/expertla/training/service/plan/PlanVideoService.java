/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.plan;

import co.com.expertla.training.model.dto.PlanVideoDTO;
import co.com.expertla.training.model.entities.PlanVideo;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface PlanVideoService {

    public PlanVideoDTO create(PlanVideo video)throws Exception;

   public PlanVideoDTO getByVideoPath(String fileName)throws Exception;

    public List<PlanVideoDTO> getVideosByUser(Integer userId, String fromto)throws Exception;

    public PlanVideoDTO getVideoById(Integer id)throws Exception;
    
}
