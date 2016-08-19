/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.plan;

import co.com.expertla.training.model.dto.CoachAssignedPlanDTO;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface CoachAssignedPlanService {
    
    List<CoachAssignedPlanDTO> findByCoachUserId(Integer userId) throws Exception;
    
}
