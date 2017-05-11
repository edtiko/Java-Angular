/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.plan;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.dto.CoachExtAthleteDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.entities.CoachExtAthlete;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface CoachExtAthleteDao extends BaseDAO<CoachExtAthlete>{
    
     public List<UserResumeDTO> findAthletesByUserPaginate(Integer userId, PaginateDto paginateDto) throws DAOException;

    public List<UserResumeDTO> getAthletes(Integer userId, String state) throws DAOException;

    public CoachExtAthlete findById(Integer coachExtAthleteId) throws DAOException;

    public CoachExtAthleteDTO findByAthleteUserId(Integer athleteUserId) throws DAOException;

    public List<UserDTO> getUserAthletes(String query) throws DAOException;

    public CoachExtAthleteDTO getInvitation(Integer userId)throws DAOException;

    public Integer getCountAthletesAvailable(Integer trainingPlanUserId)throws DAOException;
    
}
