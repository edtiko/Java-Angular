/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Andres Felipe Lopez
 */
public class StarTeamDTO implements Serializable {

    private Integer starTeamId;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date creationDate;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date lastUpdate;
    private User starUserId;
    private String starUserName;
    private User coachUserId;
    private String coachUserName;
    private Integer userCreate;
    private Integer userUpdate;
    private String userCreateName;
    private String userUpdateName;
    private Short stateId;
    private int count;  

    public StarTeamDTO() {
    }

    public StarTeamDTO(Integer startTeamId, User startUserId, 
            User coachUserId,
            Short stateId, Date creationDate, Date lastUpdate, 
            String userCreateName, String userUpdateName,
            Integer userCreate, Integer userUpdate) {
        this.starTeamId = startTeamId;
        this.stateId = stateId;
        this.lastUpdate = lastUpdate;
        this.starUserId = startUserId;
        this.coachUserId = coachUserId;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
        this.userCreateName = userCreateName;
        this.userUpdateName = userUpdateName;
        this.creationDate = creationDate;
    }

    public StarTeamDTO(Integer startTeamId) {
        this.starTeamId = startTeamId;
    }

    public Integer getStarTeamId() {
        return starTeamId;
    }

    public void setStarTeamId(Integer startTeamId) {
        this.starTeamId = startTeamId;
    }

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserCreateName() {
        return userCreateName;
    }

    public void setUserCreateName(String userCreateName) {
        this.userCreateName = userCreateName;
    }

    public String getUserUpdateName() {
        return userUpdateName;
    }

    public void setUserUpdateName(String userUpdateName) {
        this.userUpdateName = userUpdateName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public User getStarUserId() {
        return starUserId;
    }

    public void setStarUserId(User startUserId) {
        this.starUserId = startUserId;
    }

    public String getStarUserName() {
        return starUserName;
    }

    public void setStarUserName(String startUserName) {
        this.starUserName = startUserName;
    }

    public User getCoachUserId() {
        return coachUserId;
    }

    public void setCoachUserId(User coachUserId) {
        this.coachUserId = coachUserId;
    }

    public String getCoachUserName() {
        return coachUserName;
    }

    public void setCoachUserName(String coachUserName) {
        this.coachUserName = coachUserName;
    }


    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(Integer userCreate) {
        this.userCreate = userCreate;
    }

    public Integer getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(Integer userUpdate) {
        this.userUpdate = userUpdate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (starTeamId != null ? starTeamId.hashCode() : 0);
        return hash;
    }
    
}
