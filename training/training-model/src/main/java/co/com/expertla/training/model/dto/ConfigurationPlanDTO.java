/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.Role;
import co.com.expertla.training.model.entities.TrainingPlan;
import co.com.expertla.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author Andres Lopez
 */
public class ConfigurationPlanDTO {
    private Integer configurationPlanId;
    private int videoCount;
    private int videoEmergency;
    private int videoDuration;
    private int messageCount;
    private int messageEmergency;
    private int emailCount;
    private int emailEmergency;
    private int audioCount;
    private int audioEmergency;
    private int audioDuration;
    private int athletesCount;
    private Role communicationRoleId;
    private TrainingPlan trainingPlanId;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date creationDate;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date lastUpdate;
    private Integer userCreate;
    private Integer userUpdate;
    private String userCreateName;
    private String userUpdateName;
    private int count;

    public ConfigurationPlanDTO() {
    }

    public ConfigurationPlanDTO(Integer configurationPlanId, 
            TrainingPlan trainingPlanId, Role communicationRoleId,
            int audioDuration, int audioEmergency,             
            int audioCount,int emailEmergency,int emailCount,
            int messageEmergency,int messageCount,
            int videoDuration,int videoEmergency,int videoCount, int athletesCount,
            Date creationDate, 
            Date lastUpdate, String userCreateName, String userUpdateName,
            Integer userCreate, Integer userUpdate
            ) {
        this.configurationPlanId = configurationPlanId;
        this.videoCount = videoCount;
        this.videoEmergency = videoEmergency;
        this.videoDuration = videoDuration;
        this.messageCount = messageCount;
        this.messageEmergency = messageEmergency;
        this.emailCount = emailCount;
        this.emailEmergency = emailEmergency;
        this.audioCount = audioCount;
        this.audioEmergency = audioEmergency;
        this.audioDuration = audioDuration;
        this.athletesCount = athletesCount;
        this.communicationRoleId = communicationRoleId;
        this.trainingPlanId = trainingPlanId;
        this.creationDate = creationDate;
        this.lastUpdate = lastUpdate;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
        this.userCreateName = userCreateName;
        this.userUpdateName = userUpdateName;
    }
    
    

    public Integer getConfigurationPlanId() {
        return configurationPlanId;
    }

    public void setConfigurationPlanId(Integer configurationPlanId) {
        this.configurationPlanId = configurationPlanId;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public int getVideoEmergency() {
        return videoEmergency;
    }

    public void setVideoEmergency(int videoEmergency) {
        this.videoEmergency = videoEmergency;
    }

    public int getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(int videoDuration) {
        this.videoDuration = videoDuration;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public int getMessageEmergency() {
        return messageEmergency;
    }

    public void setMessageEmergency(int messageEmergency) {
        this.messageEmergency = messageEmergency;
    }

    public int getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(int emailCount) {
        this.emailCount = emailCount;
    }

    public int getEmailEmergency() {
        return emailEmergency;
    }

    public void setEmailEmergency(int emailEmergency) {
        this.emailEmergency = emailEmergency;
    }

    public int getAudioCount() {
        return audioCount;
    }

    public void setAudioCount(int audioCount) {
        this.audioCount = audioCount;
    }

    public int getAudioEmergency() {
        return audioEmergency;
    }

    public void setAudioEmergency(int audioEmergency) {
        this.audioEmergency = audioEmergency;
    }

    public int getAudioDuration() {
        return audioDuration;
    }

    public void setAudioDuration(int audioDuration) {
        this.audioDuration = audioDuration;
    }

    public Role getCommunicationRoleId() {
        return communicationRoleId;
    }

    public void setCommunicationRoleId(Role communicationRoleId) {
        this.communicationRoleId = communicationRoleId;
    }

    public TrainingPlan getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(TrainingPlan trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public int getAthletesCount() {
        return athletesCount;
    }

    public void setAthletesCount(int athletesCount) {
        this.athletesCount = athletesCount;
    }
    
    
}
