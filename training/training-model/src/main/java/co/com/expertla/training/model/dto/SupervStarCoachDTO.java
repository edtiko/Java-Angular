package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.StarTeam;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
* SupervStarCoach Controller <br>
* Info. Creación: <br>
* fecha Sep 13, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public class SupervStarCoachDTO {

    private Integer supervStarCoachId;
    private StarTeam starTeamId;    
    private User supervisorId;    
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
    
    public SupervStarCoachDTO(Integer supervStarCoachId,StarTeam starTeamId,User supervisorId,Short stateId,
     Date creationDate,Date lastUpdate,
        String userCreateName, String userUpdateName,Integer userCreate, Integer userUpdate) {
       this.supervStarCoachId = supervStarCoachId;
       this.starTeamId = starTeamId;    
       this.supervisorId = supervisorId;    
       this.stateId = stateId;    
       this.creationDate = creationDate;
       this.lastUpdate = lastUpdate;
       this.userCreate = userCreate;
       this.userCreateName = userCreateName;
       this.userUpdate = userUpdate;
       this.userUpdateName = userUpdateName;     
    }


    public Integer getSupervStarCoachId() {
        return supervStarCoachId;
    }

    public void setSupervStarCoachId(Integer supervStarCoachId) {
        this.supervStarCoachId = supervStarCoachId;
    }  

    public StarTeam getStarTeamId() {
        return starTeamId;
    }

    public void setStarTeamId(StarTeam starTeamId) {
        this.starTeamId = starTeamId;
    }    
    public User getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(User supervisorId) {
        this.supervisorId = supervisorId;
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
