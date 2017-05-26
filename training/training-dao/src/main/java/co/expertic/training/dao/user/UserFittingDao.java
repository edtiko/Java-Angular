/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.user;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.entities.UserFitting;

/**
 *
 * @author Edwin G
 */
public interface UserFittingDao extends BaseDAO<UserFitting> {

    public UserFitting findByUser(Integer userId) throws DAOException;

    public UserFitting getFittingActiveByUser(Integer userId) throws DAOException;
    
}
