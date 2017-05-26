/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.user;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.dto.UserFittingVideoDTO;
import co.expertic.training.model.entities.UserFittingHistory;

/**
 *
 * @author Edwin G
 */
public interface UserFittingHistoryDao extends BaseDAO<UserFittingHistory> {

    public UserFittingHistory getByVideoName(String fileName) throws DAOException;

    public UserFittingVideoDTO getLastVideo(Integer userFittingId) throws DAOException;
    
    
    
    
    
}
