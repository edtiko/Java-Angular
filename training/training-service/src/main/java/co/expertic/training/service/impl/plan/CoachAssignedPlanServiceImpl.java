/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.impl.plan;

import co.expertic.training.dao.plan.CoachAssignedPlanDao;
import co.expertic.training.model.dto.CoachAssignedPlanDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.ReportCountDTO;
import co.expertic.training.model.dto.SemaforoDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.CoachAssignedPlan;
import co.expertic.training.service.plan.CoachAssignedPlanService;
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
    public List<CoachAssignedPlanDTO> findByCoachUserId(Integer userId, PaginateDto paginateDto) throws Exception {
        return dao.findByCoachUserId(userId, paginateDto);
    }

    @Override
    public CoachAssignedPlanDTO findByAthleteUserId(Integer userId) throws Exception {
        return dao.findByAthleteUserId(userId);
    }

    @Override
    public CoachAssignedPlan create(CoachAssignedPlan coachAssignedPlan) throws Exception {
        return dao.create(coachAssignedPlan);
    }
    
    @Override
    public List<CoachAssignedPlanDTO> findByStarUserId(Integer userId) throws Exception {
        return dao.findByStarUserId(userId);
    }
    
    @Override
    public List<UserDTO> findCoachByStarUserId(Integer userId) throws Exception {
        return UserDTO.mapFromUsersEntities(dao.findCoachByStarUserId(userId));
    }
    
    @Override
    public List<UserDTO> findStarByCoachUserId(Integer userId) throws Exception {
        return UserDTO.mapFromUsersEntities(dao.findStarByCoachUserId(userId));
    }

    @Override
    public List<SemaforoDTO> findSemaforoByUserId(Integer userId) throws Exception {
        return dao.findSemaforoByUserId(userId);
    }

    @Override
    public List<ReportCountDTO> getCountByPlanCoach(Integer userId) throws Exception {
        return dao.getCountByPlanCoach(userId);
    }
    
}
