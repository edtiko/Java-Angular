/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

import co.expertic.training.model.entities.State;
import co.expertic.training.model.entities.User;
import co.expertic.training.model.util.CustomerDateAndTimeDeserialize;
import co.expertic.training.model.util.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class UserFittingVideoDTO {
    
    private Integer id;
    private String videoName;
    private Integer stateId;
    private String state;
    @JsonSerialize(using=JsonDateTimeSerializer.class)
    @JsonDeserialize(using=CustomerDateAndTimeDeserialize.class)
    private Date creationDate;
    private Integer userId;
    
    public UserFittingVideoDTO() {

    }

    public UserFittingVideoDTO(Integer id, String videoName, State stateId, Date creationDate, User userId) {
        this.id = id;
        this.videoName = videoName;
        this.creationDate = creationDate;
        this.stateId = stateId.getStateId();
        this.state = stateId.getName();
        this.userId = userId.getUserId();
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    
    
    
}
