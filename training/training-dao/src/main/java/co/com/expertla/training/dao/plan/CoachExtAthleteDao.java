/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.plan;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.CoachExtAthleteDTO;
import co.com.expertla.training.model.entities.CoachExtAthlete;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface CoachExtAthleteDao extends BaseDAO<CoachExtAthlete>{

    public List<CoachExtAthleteDTO> getAthletes(Integer trainingPlanUserId, String state) throws DAOException;

    public CoachExtAthlete findById(Integer coachExtAthleteId) throws DAOException;

    public CoachExtAthleteDTO findByAthleteUserId(Integer athleteUserId) throws DAOException;
    
}
