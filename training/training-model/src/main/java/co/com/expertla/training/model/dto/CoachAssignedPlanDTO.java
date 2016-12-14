/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.ConfigurationPlan;
import co.com.expertla.training.model.entities.User;
import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class CoachAssignedPlanDTO{
    
    private Integer id;
    private UserDTO athleteUserId;
    private UserDTO coachUserId;
    private UserDTO starUserId;
    private Integer starTeamId;
    private TrainingPlanDTO trainingPlanId; 
    private Date creationDate;
    private boolean external;
    private String color;
    
     public CoachAssignedPlanDTO(){
         
     }
    
    public CoachAssignedPlanDTO(Integer id, User athleteUserId, User coachUserId, User starUserId, Integer startTeamId,  ConfigurationPlan plan){
        this.id = id;
        this.athleteUserId = UserDTO.mapFromUserEntity(athleteUserId);
        this.coachUserId = UserDTO.mapFromUserEntity(coachUserId);
        this.starUserId = UserDTO.mapFromUserEntity(starUserId);
        this.starTeamId = startTeamId;
        this.external = false;
        if(plan != null){
           trainingPlanId = TrainingPlanDTO.mapFromTrainingPlanEntity(plan);
        }
    }

    /* public static CoachAssignedPlanDTO mapFromCoachAssignedPlanEntity(CoachAssignedPlan e) {
        if (e != null) {
            return new CoachAssignedPlanDTO(e.getCoachAssignedPlanId(), e.getTrainingPlanUserId().getUserId(), 
                    e.getStarTeamId().getCoachUserId(), e.getStarTeamId().getStarUserId(), e.getStarTeamId().getStarTeamId(), e.getTrainingPlanUserId().getTrainingPlanId());
        }
        return null;
    }*/
    

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

    public Integer getStarTeamId() {
        return starTeamId;
    }

    public void setStarTeamId(Integer startTeamId) {
        this.starTeamId = startTeamId;
    }

    public UserDTO getCoachUserId() {
        return coachUserId;
    }

    public void setCoachUserId(UserDTO coachUserId) {
        this.coachUserId = coachUserId;
    }

    public UserDTO getStarUserId() {
        return starUserId;
    }

    public void setStarUserId(UserDTO starUserId) {
        this.starUserId = starUserId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isExternal() {
        return external;
    }

    public void setExternal(boolean external) {
        this.external = external;
    }

    public TrainingPlanDTO getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(TrainingPlanDTO trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    
}
