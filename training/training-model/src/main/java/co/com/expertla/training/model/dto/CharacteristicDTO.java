/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author Andres Lopez
 */
public class CharacteristicDTO {
    private Integer characteristicId;
    private String name;
    private String valueType;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date creationDate;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date lastUpdate;
    private String userCreateName;
    private String userUpdateName;
    private Integer userCreate;
    private Integer userUpdate;
    private Short stateId;
    private int count;

    public CharacteristicDTO() {
    }

    public CharacteristicDTO(Integer characteristicId, String name, String valueType, Short stateId,
            Date creationDate, Date lastUpdate, String userCreateName, 
            String userUpdateName, Integer userCreate, Integer userUpdate) {
        this.characteristicId = characteristicId;
        this.name = name;
        this.valueType = valueType;
        this.creationDate = creationDate;
        this.lastUpdate = lastUpdate;
        this.userCreateName = userCreateName;
        this.userUpdateName = userUpdateName;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
        this.stateId = stateId;
    }
    
    public Integer getCharacteristicId() {
        return characteristicId;
    }

    public void setCharacteristicId(Integer characteristicId) {
        this.characteristicId = characteristicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
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

    public void setUserCreateName(String userCreateName) {
        this.userCreateName = userCreateName;
    }

    public String getUserUpdateName() {
        return userUpdateName;
    }

    public void setUserUpdateName(String userUpdateName) {
        this.userUpdateName = userUpdateName;
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
    
    
}
