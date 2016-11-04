/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.Modality;

/**
 *
 * @author Edwin G
 */
public class ActivityMovilDTO {

    private Integer activityId;
    private String name;
    private String description;
    private Integer modalityId;
    private String modality;
    private Integer userId;
    private Integer objectiveId;
    private Integer sportId;
    private String capacity;
    
    public ActivityMovilDTO(){
        
    }

    public ActivityMovilDTO(Integer activityId, String name, String description, 
            Integer sportId, Integer userId) {
        this.activityId = activityId;
        this.name = name;
        this.description = description;
        this.sportId = sportId;
        this.userId = userId;
    }

    public ActivityMovilDTO(Integer activityId, String name, String description,
            Integer modalityId, String modalityName, Integer objectiveId, Integer sportId, String capacity) {
        this.activityId = activityId;
        this.name = name;
        this.description = description;
        this.modalityId = modalityId;
        this.modality = modalityName;
        this.objectiveId = objectiveId;
        this.sportId = sportId;
        this.capacity = capacity;
    }
    
    public ActivityMovilDTO(Integer activityId, String name, String description,
            Integer sportId) {
        this.activityId = activityId;
        this.name = name;
        this.description = description;
        this.sportId = sportId;
    }
    
    public ActivityMovilDTO(Integer activityId, String name, String description, 
            Modality modalityId, Integer objectiveId, Integer sportId, String capacity) {
        this.activityId = activityId;
        this.name = name;
        this.description = description;
        this.modalityId = modalityId.getModalityId();
        this.modality = modalityId.getName();
        this.objectiveId = objectiveId;
        this.sportId = sportId;
        this.capacity = capacity;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
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

    public Integer getModalityId() {
        return modalityId;
    }

    public void setModalityId(Integer modalityId) {
        this.modalityId = modalityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Integer objectiveId) {
        this.objectiveId = objectiveId;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }
}