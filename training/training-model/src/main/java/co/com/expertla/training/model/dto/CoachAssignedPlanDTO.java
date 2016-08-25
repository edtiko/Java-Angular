/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.CoachAssignedPlan;
import co.com.expertla.training.model.entities.User;

/**
 *
 * @author Edwin G
 */
public class CoachAssignedPlanDTO {
    
    private Integer id;
    private UserDTO athleteUserId;
    private UserDTO coachUserId;
    private Integer startTeamId;
    
     public CoachAssignedPlanDTO(){
         
     }
    
    public CoachAssignedPlanDTO(Integer id, User athleteUserId, User coachUserId, Integer startTeamId){
        this.id = id;
        this.athleteUserId = UserDTO.mapFromUserEntity(athleteUserId);
        this.coachUserId = UserDTO.mapFromUserEntity(coachUserId);
        this.startTeamId = startTeamId;
    }
    
     public static CoachAssignedPlanDTO mapFromCoachAssignedPlanEntity(CoachAssignedPlan e) {
        if (e != null) {
            return new CoachAssignedPlanDTO(e.getCoachAssignedPlanId(), e.getTrainingPlanUserId().getUserId(), 
                    e.getStartTeamId().getCoachUserId(), e.getStartTeamId().getStartTeamId());
        }
        return null;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTO getAthleteUserId() {
        return athleteUserId;
    }

    public void setAthleteUserId(UserDTO athleteUserId) {
        this.athleteUserId = athleteUserId;
    }

    public Integer getStartTeamId() {
        return startTeamId;
    }

    public void setStartTeamId(Integer startTeamId) {
        this.startTeamId = startTeamId;
    }

    public UserDTO getCoachUserId() {
        return coachUserId;
    }

    public void setCoachUserId(UserDTO coachUserId) {
        this.coachUserId = coachUserId;
    }
    
    
    
    
    
}
