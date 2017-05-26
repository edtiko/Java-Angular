/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.user;

import co.expertic.training.model.dto.UserFittingVideoDTO;
import co.expertic.training.model.entities.UserFitting;
import co.expertic.training.model.entities.UserFittingHistory;

/**
 *
 * @author Edwin G
 */
public interface UserFittingService {

    public UserFitting findByUser(Integer userId) throws Exception;

    public void create(UserFitting userFitting) throws Exception;
    
    public void update(UserFitting userFitting) throws Exception;

    public UserFitting getFittingActiveByUser(Integer userId) throws Exception;

    public UserFittingHistory getByVideoName(String fileName)  throws Exception;

    public void createVideo(UserFittingHistory video)  throws Exception;

    public UserFittingVideoDTO getLastVideo(Integer userFittingId)  throws Exception;
    
}
