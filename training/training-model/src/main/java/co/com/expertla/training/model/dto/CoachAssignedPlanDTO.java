/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.CoachAssignedPlan;
import co.com.expertla.training.model.entities.TrainingPlan;
import co.com.expertla.training.model.entities.User;

/**
 *
 * @author Edwin G
 */
public class CoachAssignedPlanDTO {
    
    private Integer id;
    private UserDTO athleteUserId;
    private UserDTO coachUserId;
    private UserDTO starUserId;
    private Integer startTeamId;
    private Integer videoCount;
    private Integer messageCount;
    private Integer callCount;
    private Integer emailCount;
    
     public CoachAssignedPlanDTO(){
         
     }
    
    public CoachAssignedPlanDTO(Integer id, User athleteUserId, User coachUserId, User starUserId, Integer startTeamId,  TrainingPlan trainingPlan){
        this.id = id;
        this.athleteUserId = UserDTO.mapFromUserEntity(athleteUserId);
        this.coachUserId = UserDTO.mapFromUserEntity(coachUserId);
        this.starUserId = UserDTO.mapFromUserEntity(starUserId);
        this.startTeamId = startTeamId;
        if(trainingPlan != null){
            this.videoCount = trainingPlan.getVideoCount();
            this.messageCount = trainingPlan.getMessageCount();
            this.emailCount = trainingPlan.getEmailCount();
            this.callCount = trainingPlan.getCallCount();
        }
    }
    
     public static CoachAssignedPlanDTO mapFromCoachAssignedPlanEntity(CoachAssignedPlan e) {
        if (e != null) {
            return new CoachAssignedPlanDTO(e.getCoachAssignedPlanId(), e.getTrainingPlanUserId().getUserId(), 
                    e.getStartTeamId().getCoachUserId(), e.getStartTeamId().getStartUserId(), e.getStartTeamId().getStartTeamId(), e.getTrainingPlanUserId().getTrainingPlanId());
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

    public UserDTO getStarUserId() {
        return starUserId;
    }

    public void setStarUserId(UserDTO starUserId) {
        this.starUserId = starUserId;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public Integer getCallCount() {
        return callCount;
    }

    public void setCallCount(Integer callCount) {
        this.callCount = callCount;
    }

    public Integer getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(Integer emailCount) {
        this.emailCount = emailCount;
    }
    
    
    
    
    
}
