/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

/**
 *
 * @author Edwin G
 */
public class ActivityDTO {
    
    private Integer id;
    private String name;
    private String description;
    private Integer modalityId;
    private Integer userId;
    private Integer objectiveId;
    private Integer sportId;
    
    public ActivityDTO(){
        
    }
    
      public ActivityDTO(Integer id, String name, String description, Integer modalityId, Integer userId){
        this.id= id;
        this.name= name;
        this.description = description;
        this.modalityId = modalityId;
        this.userId = userId;
    }
    
     public ActivityDTO(Integer id, String name, String description, Integer modalityId, Integer objectiveId, Integer sportId){
        this.id= id;
        this.name= name;
        this.description = description;
        this.modalityId = modalityId;
        this.objectiveId = objectiveId;
        this.sportId = sportId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    
    
    
}
