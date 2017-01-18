package co.expertic.training.model.dto;

import co.expertic.training.model.entities.Modality;
import co.expertic.training.model.entities.Objective;
import co.expertic.training.model.entities.PhysiologicalCapacity;
import co.expertic.training.model.entities.Sport;
import co.expertic.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

public class ActivityDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer activityId;
    private String name;
    private String description;
    private Modality modalityId;
    private Objective objectiveId;
    private PhysiologicalCapacity physiologicalCapacityId;
    private Sport sportId;
    private int count;
    private Short stateId;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date creationDate;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date lastUpdate;
    private Integer userCreate;
    private String userCreateName;
    private Integer userUpdate;
    private String userUpdateName;

    public ActivityDTO() {
    }

    public ActivityDTO(Integer activityId, PhysiologicalCapacity physiologicalCapacity, Modality modality, Objective objective, String name,
            String description, Sport sport, Date creationDate, Date lastUpdate, String userCreateName, String userUpdateName,
            Integer userCreate, Integer userUpdate, Short stateId) {
        this.activityId = activityId;
        this.name = name;
        this.description = description;
        this.modalityId = modality;
        this.objectiveId = objective;
        this.physiologicalCapacityId = physiologicalCapacity;
        this.sportId = sport;
        this.creationDate = creationDate;
        this.lastUpdate = lastUpdate;
        this.userCreate = userCreate;
        this.userUpdate = userUpdate;
        this.userCreateName = userCreateName;
        this.userUpdateName = userUpdateName;
        this.stateId = stateId;
    }

    public ActivityDTO(Integer activityId) {
        this.activityId = activityId;
    }

    public ActivityDTO(Integer activityId, String name) {
        this.activityId = activityId;
        this.name = name;
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

    public Modality getModalityId() {
        return modalityId;
    }

    public void setModalityId(Modality modalityId) {
        this.modalityId = modalityId;
    }

    public Objective getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Objective objectiveId) {
        this.objectiveId = objectiveId;
    }

    public PhysiologicalCapacity getPhysiologicalCapacityId() {
        return physiologicalCapacityId;
    }

    public void setPhysiologicalCapacityId(PhysiologicalCapacity physiologicalCapacityId) {
        this.physiologicalCapacityId = physiologicalCapacityId;
    }

    public Sport getSportId() {
        return sportId;
    }

    public void setSportId(Sport sportId) {
        this.sportId = sportId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.Activity[ activityId=" + activityId + " ]";
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

}
