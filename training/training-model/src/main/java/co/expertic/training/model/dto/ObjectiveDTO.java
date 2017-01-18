package co.expertic.training.model.dto;

import co.expertic.training.model.entities.Discipline;
import co.expertic.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

public class ObjectiveDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer objectiveId;
    private String name;
    private int level;
    private Discipline disciplineId;
    private String disciplineName;
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

    public ObjectiveDTO() {
    }

    public ObjectiveDTO(Integer objectiveId) {
        this.objectiveId = objectiveId;
    }

    public ObjectiveDTO(Integer objectiveId, String name, int level) {
        this.objectiveId = objectiveId;
        this.name = name;
        this.level = level;
    }

    public ObjectiveDTO(Integer objectiveId, String name, int level, Discipline disciplineId, String disciplineName, Short stateId, Date creationDate,
            Date lastUpdate, String userCreateName, String userUpdateName, Integer userCreate, Integer userUpdate) {
        this.objectiveId = objectiveId;
        this.name = name;
        this.level = level;
        this.creationDate = creationDate;
        this.lastUpdate = lastUpdate;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
        this.userCreateName = userCreateName;
        this.userUpdateName = userUpdateName;
        this.stateId = stateId;
        this.disciplineId = disciplineId;
        this.disciplineName = disciplineName;
    }

    public Integer getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Integer objectiveId) {
        this.objectiveId = objectiveId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public Discipline getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Discipline disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }
    
}
