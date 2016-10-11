/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.plan;

import co.com.expertla.training.model.dto.CoachExtAthleteDTO;
import co.com.expertla.training.model.dto.UserDTO;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface CoachExtAthleteService {

    public void create(CoachExtAthleteDTO dto) throws Exception;

    public List<CoachExtAthleteDTO> getAthletes(Integer trainingPlanUserId, String state)throws Exception;

    public void retireAthlete(Integer userId)throws Exception;

    public CoachExtAthleteDTO findByAthleteUserId(Integer athleteUserId)throws Exception;

    public List<UserDTO> getUserAthletes(String query)throws Exception;

    public void sendInvitation(CoachExtAthleteDTO dto)throws Exception;

    public CoachExtAthleteDTO getInvitation(Integer userId)throws Exception;

    public void acceptInvitation(Integer coachExtAthleteId)throws Exception;

    public void rejectInvitation(Integer coachExtAthleteId)throws Exception;
    
}