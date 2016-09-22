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
    public void create(PlanVideo video) throws Exception {
       planVideoDao.create(video);
    }

    @Override
     public Integer countByVideoPath(String fileName) throws Exception {
        return planVideoDao.countByVideoPath(fileName);
    }

    @Override
    public List<PlanVideoDTO> getVideosByUser(Integer userId, String fromto) throws Exception {
        return planVideoDao.getVideosByUser(userId, fromto);
    }
    
}
