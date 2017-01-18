package co.expertic.training.model.dto;

import co.expertic.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer roleId;
    private String name;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date creationDate;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date lastUpdate;
    private Integer userCreate;
    private Integer userUpdate;
    private String userCreateName;
    private String userUpdateName;
    private Short stateId;
    private int count;    

    public RoleDTO() {
    }

    public RoleDTO(Integer roleId, String name, Short stateId, Date creationDate, Date lastUpdate, 
            String userCreateName, String userUpdateName, 
            Integer userCreate, Integer userUpdate
            ) {
        this.roleId = roleId;
        this.name = name;
        this.creationDate = creationDate;
        this.lastUpdate = lastUpdate;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
        this.userCreateName = userCreateName;
        this.userUpdateName = userUpdateName;
        this.stateId = stateId;
    }

    public RoleDTO(Integer roleId) {
        this.roleId = roleId;
    }

    public RoleDTO(Integer roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
