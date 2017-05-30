/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.impl.user;

import co.expertic.training.dao.user.UserFittingDao;
import co.expertic.training.dao.user.UserFittingHistoryDao;
import co.expertic.training.enums.StateEnum;
import co.expertic.training.model.dto.UserFittingVideoDTO;
import co.expertic.training.model.entities.State;
import co.expertic.training.model.entities.UserFitting;
import co.expertic.training.model.entities.UserFittingHistory;
import co.expertic.training.service.user.UserFittingService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin G
 */
@Service
@Transactional
public class UserFittingServiceImpl implements UserFittingService{
    
    @Autowired
    UserFittingDao userFittingDao;
    
    @Autowired
    UserFittingHistoryDao userFittingHistoryDao;

    @Override
    public UserFitting findByUser(Integer userId) throws Exception {
        return userFittingDao.findByUser(userId);
    }

    @Override
    public void create(UserFitting userFitting) throws Exception {
        userFittingDao.create(userFitting);
    }
    
    @Override
    public void update(UserFitting userFitting) throws Exception {
        userFittingDao.merge(userFitting);
    }

    @Override
    public UserFitting getFittingActiveByUser(Integer userId) throws Exception {
        return userFittingDao.getFittingActiveByUser(userId);
    }

    @Override
    public UserFittingHistory getByVideoName(String fileName) throws Exception {
       return  userFittingHistoryDao.getByVideoName(fileName);
    }

    @Override
    public void createVideo(UserFittingHistory video) throws Exception {
       userFittingHistoryDao.create(video);
    }

    @Override
    public UserFittingVideoDTO getLastVideo(Integer userFittingId) throws Exception {
        return userFittingHistoryDao.getLastVideo(userFittingId);
    }

    @Override
    public List<UserFittingVideoDTO> getUserFittingHistory(Integer userId) throws Exception {
        return userFittingHistoryDao.getUserFittingHistory(userId);
    }

    @Override
    public void changeState(Integer stateId, Integer id) throws Exception {
       UserFittingHistory userFittingHistory =  userFittingHistoryDao.findById(id);
       UserFitting userFitting = userFittingHistory.getUserFittingId();
       userFittingHistory.setStateId(new State(stateId));
       userFittingHistoryDao.merge(userFittingHistory);
       
       if(Objects.equals(stateId, StateEnum.APPROVED.getId())){
           userFitting.setStateId(StateEnum.INACTIVE.getId().shortValue());
           userFittingDao.merge(userFitting);
       }
      
    }
    
}
