/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.plan;

import co.expertic.training.model.dto.CoachExtAthleteDTO;
import co.expertic.training.model.dto.UserDTO;
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

    public Integer acceptInvitation(Integer coachExtAthleteId)throws Exception;

    public Integer rejectInvitation(Integer coachExtAthleteId)throws Exception;
    
    public boolean sendEmail(String message, String email);

    public Integer getCountAthletesAvailable(Integer trainingPlanUserId)throws Exception;
    
}