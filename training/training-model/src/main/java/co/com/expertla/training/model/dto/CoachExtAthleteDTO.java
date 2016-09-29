/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.State;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.util.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class CoachExtAthleteDTO {

    private Integer id;
    private UserDTO athleteUserId;
    private Integer trainingPlanUserId;
    private String state;
    private Integer stateId;
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date creationDate;

    public CoachExtAthleteDTO() {

    }

    public CoachExtAthleteDTO(Integer id, User athleteUserId, Date creationDate, State stateId) {
        this.id = id;
        this.athleteUserId = UserDTO.mapFromUserEntity(athleteUserId);
        this.creationDate = creationDate;
        //this.trainingPlanUserId = trainingPlanUserId;
        this.stateId = stateId.getStateId();
        this.state = stateId.getName();
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
      

}
