/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.Module;
import co.com.expertla.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author Andres Lopez
 */
public class OptionDTO {

    private Integer optionId;
    private String name;
    private String description;
    private String url;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date creationDate;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date lastUpdate;
    private Integer userCreate;
    private Integer userUpdate;
    private Module moduleId;
    private String module;
    private Integer masterOptionId;
    private String masterOption;
    private String userCreateName;
    private String userUpdateName;
    private Short stateId;
    private int count;

    public OptionDTO() {
    }

    public OptionDTO(Integer optionId, String name, String description, String url, Short stateId,
            Module moduleId, String module, 
            Date creationDate, Date lastUpdate, 
            Integer masterOptionId, String masterOption,            
            String userCreateName, String userUpdateName,
            Integer userCreate, Integer userUpdate) {
        this.optionId = optionId;
        this.name = name;
        this.description = description;
        this.url = url;
        this.creationDate = creationDate;
        this.lastUpdate = lastUpdate;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
        this.moduleId = moduleId;
        this.module = module;
        this.userCreateName = userCreateName;
        this.userUpdateName = userUpdateName;
        this.masterOptionId = masterOptionId;
        this.masterOption = masterOption;
        this.stateId = stateId;
    }
    
    public OptionDTO(Integer optionId) {
        this.optionId = optionId;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Module getModuleId() {
        return moduleId;
    }

    public void setModuleId(Module moduleId) {
        this.moduleId = moduleId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getMasterOptionId() {
        return masterOptionId;
    }

    public void setMasterOptionId(Integer masterOptionId) {
        this.masterOptionId = masterOptionId;
    }

    public String getMasterOption() {
        return masterOption;
    }

    public void setMasterOption(String masterOption) {
        this.masterOption = masterOption;
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
