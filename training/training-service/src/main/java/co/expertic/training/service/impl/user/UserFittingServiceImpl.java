/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.impl.user;

import co.expertic.training.dao.user.UserFittingDao;
import co.expertic.training.model.entities.UserFitting;
import co.expertic.training.service.user.UserFittingService;
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
    
}
