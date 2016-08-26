/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

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
    private Date creationDate;
    private Date lastUpdate;
    private Integer userCreate;
    private Integer userUpdate;
    private Integer moduleId;
    private String module;
    private Integer masterOptionId;
    private String masterOption;
    private Integer stateId;
    private int count;

    public OptionDTO() {
    }

    public OptionDTO(Integer optionId, String name, String description, String url, Date creationDate, Date lastUpdate, Integer userCreate, Integer userUpdate, Integer moduleId, String module, Integer masterOptionId, String masterOption, Integer stateId) {
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

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
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

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    
}
