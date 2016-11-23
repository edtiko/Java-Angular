/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.ConfigurationPlan;
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
    private int videoCount;
    private int videoDuration;
    private int videoEmergency;
    private int messageCount;
    private int messageEmergency;
    private int emailCount;
    private int emailEmergency;
    private int audioCount;
    private int audioEmergency;
    private int audioDuration;
    private Double price;

    private int count;

    public TrainingPlanDTO() {
    }

    //Short
    public TrainingPlanDTO(Integer trainingPlanId, String name, String description,
           Date endDate, Short stateId, Double price, Date creationDate,
            Date lastUpdate, Integer userCreate, Integer userUpdate, ConfigurationPlan cplan) {
        this.trainingPlanId = trainingPlanId;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.lastUpdate = lastUpdate;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
        this.videoCount = cplan.getVideoCount();
        this.videoDuration = cplan.getVideoDuration();
        this.videoEmergency = cplan.getVideoEmergency();
        this.messageCount = cplan.getMessageCount();
        this.messageEmergency = cplan.getMessageEmergency();
        this.emailCount = cplan.getEmailCount();
        this.emailEmergency = cplan.getEmailEmergency();
        this.audioCount = cplan.getAudioCount();
        this.audioDuration = cplan.getAudioDuration();
        this.audioEmergency = cplan.getAudioEmergency();
        this.endDate = endDate;
        this.stateId = stateId;
        this.price = price;
    }

    public TrainingPlanDTO(Integer trainingPlanId, String name, String description,
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
       /* this.videoCount = videoCount;
        this.messageCount = messageCount;
        this.emailCount = emailCount;
        this.audioCount = audioCount;*/
        this.endDate = endDate;
        this.stateId = stateId;
        this.price = price;
    }

    public static TrainingPlanDTO mapFromTrainingPlanEntity(ConfigurationPlan cplan) {
        if (cplan != null) {
            TrainingPlan plan = cplan.getTrainingPlanId();
            return new TrainingPlanDTO(plan.getTrainingPlanId(), plan.getName(), plan.getDescription(),
                    plan.getEndDate(), plan.getStateId(), plan.getPrice(), plan.getCreationDate(),
                    plan.getLastUpdate(), plan.getUserCreate(), plan.getUserUpdate(), cplan);
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
        return getAudioCount() + getAudioEmergency();
    }

    public Integer getTotalMessageCount() {
        return getMessageCount() + getMessageEmergency();
    }

    public Integer getTotalVideoCount() {
        return getVideoCount() + getVideoEmergency();
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
