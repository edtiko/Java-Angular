/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.user;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.entities.UserFitting;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface UserFittingDao extends BaseDAO<UserFitting> {

    public UserFitting findByUser(Integer userId) throws DAOException;

    public UserFitting getFittingActiveByUser(Integer userId) throws DAOException;

    public List<UserResumeDTO> findAthletesByUserPaginate(Integer userId, PaginateDto paginateDto) throws DAOException;
    
    public List<UserResumeDTO> getAthletes() throws DAOException; 
    
}
