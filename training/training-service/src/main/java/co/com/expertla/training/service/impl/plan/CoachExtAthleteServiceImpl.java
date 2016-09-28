/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.impl.plan;

import co.com.expertla.training.dao.plan.CoachExtAthleteDao;
import co.com.expertla.training.model.dto.CoachExtAthleteDTO;
import co.com.expertla.training.model.entities.CoachExtAthlete;
import co.com.expertla.training.service.plan.CoachExtAthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin G
 */
@Service
@Transactional
public class CoachExtAthleteServiceImpl implements CoachExtAthleteService{
    
    @Autowired
    CoachExtAthleteDao coachExtAthleteDao;

    @Override
    public void create(CoachExtAthleteDTO dto) throws Exception {
        CoachExtAthlete entity = new CoachExtAthlete();
        
        coachExtAthleteDao.create(entity);
    }
    
}
