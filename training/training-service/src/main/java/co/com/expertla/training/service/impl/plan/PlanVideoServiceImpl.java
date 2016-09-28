/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.impl.plan;

import co.com.expertla.training.dao.plan.PlanVideoDao;
import co.com.expertla.training.model.dto.PlanVideoDTO;
import co.com.expertla.training.model.entities.PlanVideo;
import co.com.expertla.training.service.plan.PlanVideoService;
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
public class PlanVideoServiceImpl implements PlanVideoService{
    
    @Autowired
    PlanVideoDao planVideoDao;

    @Override
    public PlanVideoDTO create(PlanVideo video) throws Exception {
       return PlanVideoDTO.mapFromPlanVideoEntity(planVideoDao.create(video));
    }

    @Override
     public PlanVideoDTO getByVideoPath(String fileName) throws Exception {
        return PlanVideoDTO.mapFromPlanVideoEntity(planVideoDao.getByVideoPath(fileName));
    }

    @Override
    public List<PlanVideoDTO> getVideosByUser(Integer coachAssignedPlanId, Integer userId, String fromto) throws Exception {
        return planVideoDao.getVideosByUser(coachAssignedPlanId, userId, fromto);
    }

    @Override
    public PlanVideoDTO getVideoById(Integer id) throws Exception {
      return PlanVideoDTO.mapFromPlanVideoEntity(planVideoDao.getVideoById(id));
    }

    @Override
    public Integer getCountVideoByPlan(Integer coachAssignedPlanId, Integer userId) throws Exception {
      return planVideoDao.getCountVideoByPlan(coachAssignedPlanId, userId);
    }


    @Override
    public Integer getCountVideosReceived(Integer coachAssignedPlanId, Integer userId) throws Exception {
        return planVideoDao.getCountVideosReceived(coachAssignedPlanId, userId);
    }

    @Override
    public void readVideos(Integer coachAssignedPlanId, Integer userId) throws Exception {
       planVideoDao.readVideos(coachAssignedPlanId, userId);
    }

    @Override
    public void readVideo(Integer planMessageId) throws Exception {
        planVideoDao.readVideo(planMessageId);
    }
    
}
