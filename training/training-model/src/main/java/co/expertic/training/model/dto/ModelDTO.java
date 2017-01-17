package co.expertic.training.model.dto;

import co.expertic.training.model.entities.SportEquipmentType;
import co.expertic.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
* Model Controller <br>
* Info. Creación: <br>
* fecha Oct 19, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public class ModelDTO {

    private Integer modelId;
    private SportEquipmentType sportEquipmentTypeId;    
    private String name;    
    private Short stateId;    
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date creationDate;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date lastUpdate;
    private Integer userCreate;
    private Integer userUpdate;
    private String userCreateName;
    private String userUpdateName;
    private int count;
    
    public ModelDTO(Integer modelId,SportEquipmentType sportEquipmentTypeId, String name,
Short stateId,
     Date creationDate,Date lastUpdate,
        String userCreateName, String userUpdateName,Integer userCreate, Integer userUpdate) {
       this.modelId = modelId;
       this.sportEquipmentTypeId = sportEquipmentTypeId;    
       this.name = name;    
       this.stateId = stateId;    
       this.creationDate = creationDate;
       this.lastUpdate = lastUpdate;
       this.userCreate = userCreate;
       this.userCreateName = userCreateName;
       this.userUpdate = userUpdate;
       this.userUpdateName = userUpdateName;     
    }


    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }  

    public SportEquipmentType getSportEquipmentTypeId() {
        return sportEquipmentTypeId;
    }

    public void setSportEquipmentTypeId(SportEquipmentType sportEquipmentTypeId) {
        this.sportEquipmentTypeId = sportEquipmentTypeId;
    }    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
