/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.impl.plan;

import co.com.expertla.training.dao.plan.CoachAssignedPlanDao;
import co.com.expertla.training.model.dto.CoachAssignedPlanDTO;
import co.com.expertla.training.service.plan.CoachAssignedPlanService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin G
 */
@Service
@Transactional
public class CoachAssignedPlanServiceImpl implements CoachAssignedPlanService{
    
    @Autowired
    CoachAssignedPlanDao dao;

    @Override
    public List<CoachAssignedPlanDTO> findByCoachUserId(Integer userId) throws Exception {
        return dao.findByCoachUserId(userId);
    }
    
}
