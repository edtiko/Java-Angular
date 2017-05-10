/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

import co.expertic.training.model.entities.ConfigurationPlan;
import co.expertic.training.model.entities.State;
import co.expertic.training.model.entities.User;
import co.expertic.training.model.util.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class CoachExtAthleteDTO {

    private Integer id;
    private UserDTO athleteUserId;
    private UserDTO coachUserId;
    private Integer trainingPlanUserId;
    private TrainingPlanDTO trainingPlanId;
    private String state;
    private Integer stateId;
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date creationDate;
    private boolean external;
    private int count;

    public CoachExtAthleteDTO() {

    }

    public CoachExtAthleteDTO(Integer id, User athleteUserId, User coachUserId, ConfigurationPlan cplan, Date creationDate, State stateId) {
        this.id = id;
        this.athleteUserId = UserDTO.mapFromUserEntity(athleteUserId);
        this.coachUserId = UserDTO.mapFromUserEntity(coachUserId);
        this.creationDate = creationDate;
        //this.trainingPlanUserId = trainingPlanUserId;
        this.stateId = stateId.getStateId();
        this.state = stateId.getName();
        this.external = true;
        this.trainingPlanId = TrainingPlanDTO.mapFromTrainingPlanEntity(cplan);
    }
    
    
     public CoachExtAthleteDTO(Integer id, User athleteUserId, User coachUserId, Date creationDate, State stateId) {
        this.id = id;
        this.athleteUserId = UserDTO.mapFromUserEntity(athleteUserId);
        this.coachUserId = UserDTO.mapFromUserEntity(coachUserId);
        this.creationDate = creationDate;
        //this.trainingPlanUserId = trainingPlanUserId;
        this.stateId = stateId.getStateId();
        this.state = stateId.getName();
        this.external = true;
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

    public void setAthleteUserId(UserDTO athleteUser) {
        this.athleteUserId = athleteUser;
    }

    public Integer getTrainingPlanUserId() {
        return trainingPlanUserId;
    }

    public void setTrainingPlanUserId(Integer trainingPlanUserId) {
        this.trainingPlanUserId = trainingPlanUserId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public TrainingPlanDTO getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(TrainingPlanDTO trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public UserDTO getCoachUserId() {
        return coachUserId;
    }

    public void setCoachUserId(UserDTO coachUserId) {
        this.coachUserId = coachUserId;
    }

    public boolean isExternal() {
        return external;
    }

    public void setExternal(boolean external) {
        this.external = external;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    
}
