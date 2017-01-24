package co.expertic.training.model.dto;

import co.expertic.training.model.entities.Discipline;
import co.expertic.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

public class ModalityDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer modalityId;
    private String name;
    private Discipline disciplineId;
    private Short stateId;
    private int count;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date creationDate;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date lastUpdate;
    private Integer userCreate;
    private String userCreateName;
    private Integer userUpdate;
    private String userUpdateName;

    public ModalityDTO(Integer modalityId, String name, Discipline discipline,Short stateId,Date creationDate, Date lastUpdate,String userCreateName, 
            String userUpdateName,Integer userCreate,Integer userUpdate ) {
        this.modalityId = modalityId;
        this.name = name;
        this.disciplineId = discipline;
        this.stateId = stateId;
        this.creationDate = creationDate;
        this.lastUpdate = lastUpdate;
        this.userCreate = userCreate;
        this.userCreateName = userCreateName;
        this.userUpdate = userUpdate;
        this.userUpdateName = userUpdateName;
    }
    
    
    
    public ModalityDTO() {
    }

    public ModalityDTO(Integer modalityId) {
        this.modalityId = modalityId;
    }

    public ModalityDTO(Integer modalityId, String name) {
        this.modalityId = modalityId;
        this.name = name;
    }

    public Integer getModalityId() {
        return modalityId;
    }

    public void setModalityId(Integer modalityId) {
        this.modalityId = modalityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public String getUserCreateName() {
        return userCreateName;
    }

    public void setUserCreateName(String userCreateName) {
        this.userCreateName = userCreateName;
    }

    public Integer getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(Integer userUpdate) {
        this.userUpdate = userUpdate;
    }

    public String getUserUpdateName() {
        return userUpdateName;
    }

    public void setUserUpdateName(String userUpdateName) {
        this.userUpdateName = userUpdateName;
    }

    public Discipline getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Discipline disciplineId) {
        this.disciplineId = disciplineId;
    }

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }
    
}
