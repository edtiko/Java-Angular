/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.TrainingPlan;
import co.com.expertla.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author Andres Lopez
 */
public class TrainingPlanDTO {

    private Integer trainingPlanId;
    private String name;
    private String description;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date creationDate;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date lastUpdate;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date endDate;
    private Integer userCreate;
    private Integer userUpdate;
    private String userCreateName;
    private String userUpdateName;
    private Short stateId;
    private Integer videoCount;
    private Integer videoDuration;
    private Integer videoEmergency;
    private Integer messageCount;
    private Integer messageEmergency;
    private Integer emailCount;
    private Integer emailEmergency;
    private Integer audioCount;
    private Integer audioEmergency;
    private Integer audioDuration;
    private Double price;

    private int count;

    public TrainingPlanDTO() {
    }

    //Short
    public TrainingPlanDTO(Integer trainingPlanId, String name, String description,
           Date endDate, Short stateId, Double price, Date creationDate,
            Date lastUpdate, Integer userCreate, Integer userUpdate) {
        this.trainingPlanId = trainingPlanId;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.lastUpdate = lastUpdate;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
       /* this.videoCount = videoCount;
        this.videoDuration = videoDuration;
        this.videoEmergency = videoEmergency;
        this.messageCount = messageCount;
        this.messageEmergency = messageEmergency;
        this.emailCount = emailCount;
        this.emailEmergency = emailEmergency;
        this.audioCount = audioCount;
        this.audioDuration = audioDuration;
        this.audioEmergency = audioEmergency;*/
        this.endDate = endDate;
        this.stateId = stateId;
        this.price = price;
    }

    public TrainingPlanDTO(Integer trainingPlanId, String name, String description,
            //Integer videoCount, Integer messageCount, Integer emailCount, Integer audioCount,
            Date endDate, Short stateId, Double price, Date creationDate, Date lastUpdate,
            String userCreateName, String userUpdateName,
            Integer userCreate, Integer userUpdate) {
        this.trainingPlanId = trainingPlanId;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.lastUpdate = lastUpdate;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
        this.userCreateName = userCreateName;
        this.userUpdateName = userUpdateName;
        /*this.videoCount = videoCount;
        this.messageCount = messageCount;
        this.emailCount = emailCount;
        this.audioCount = audioCount;*/
        this.endDate = endDate;
        this.stateId = stateId;
        this.price = price;
    }

    public static TrainingPlanDTO mapFromTrainingPlanEntity(TrainingPlan plan) {
        if (plan != null) {
            return new TrainingPlanDTO(plan.getTrainingPlanId(), plan.getName(), plan.getDescription(),
                    plan.getEndDate(), plan.getStateId(), plan.getPrice(), plan.getCreationDate(),
                    plan.getLastUpdate(), plan.getUserCreate(), plan.getUserUpdate());
        }
        return null;
    }

    public Integer getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(Integer trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getVideoCount() {
        if(videoCount == 0){
            return videoEmergency;
        }
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public Integer getMessageCount() {
        if(messageCount==0){
            return messageEmergency;
        }
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public Integer getEmailCount() {
        if (emailCount == 0) {
            return emailEmergency;
        }
        return emailCount;
    }

    public void setEmailCount(Integer emailCount) {
        this.emailCount = emailCount;
    }

    public Integer getAudioCount() {
        if(audioCount == 0){
            return audioEmergency;
        }
        return audioCount;
    }

    public void setAudioCount(Integer audioCount) {
        this.audioCount = audioCount;
    }

    public Integer getTotalAudioCount() {
        return audioCount + audioEmergency;
    }

    public Integer getTotalMessageCount() {
        return messageCount + messageEmergency;
    }

    public Integer getTotalVideoCount() {
        return videoCount + videoEmergency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUserCreateName() {
        return userCreateName;
    }

    public void setUserCreateName(String userCreate) {
        this.userCreateName = userCreate;
    }

    public String getUserUpdateName() {
        return userUpdateName;
    }

    public void setUserUpdateName(String userUpdate) {
        this.userUpdateName = userUpdate;
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

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(Integer videoDuration) {
        this.videoDuration = videoDuration;
    }

    public Integer getVideoEmergency() {
        return videoEmergency;
    }

    public void setVideoEmergency(Integer videoEmergency) {
        this.videoEmergency = videoEmergency;
    }

    public Integer getMessageEmergency() {
        return messageEmergency;
    }

    public void setMessageEmergency(Integer messageEmergency) {
        this.messageEmergency = messageEmergency;
    }

    public Integer getAudioEmergency() {
        return audioEmergency;
    }

    public void setAudioEmergency(Integer audioEmergency) {
        this.audioEmergency = audioEmergency;
    }

    public Integer getAudioDuration() {
        return audioDuration;
    }

    public void setAudioDuration(Integer audioDuration) {
        this.audioDuration = audioDuration;
    }

}
