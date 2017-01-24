package co.expertic.training.model.dto;

import java.io.Serializable;
import java.util.Date;

/**
* PhysiologicalCapacity <br>
* Creation Date : <br>
* date 21/07/2016 <br>
* @author Angela Ramírez
**/
public class PhysiologicalCapacityDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer physiologicalCapacityId;
    private String name;
    private String code; 
    private int count;
    private Short stateId;
    private Date creationDate;
    private Date lastUpdate;
    private Integer userCreate;
    private Integer userUpdate;

    public PhysiologicalCapacityDTO() {
    }

    public PhysiologicalCapacityDTO(Integer physiologicalCapacityId) {
        this.physiologicalCapacityId = physiologicalCapacityId;
    }

    public PhysiologicalCapacityDTO(Integer physiologicalCapacityId, String name) {
        this.physiologicalCapacityId = physiologicalCapacityId;
        this.name = name;
    }

    public Integer getPhysiologicalCapacityId() {
        return physiologicalCapacityId;
    }

    public void setPhysiologicalCapacityId(Integer physiologicalCapacityId) {
        this.physiologicalCapacityId = physiologicalCapacityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.PhysiologicalCapacity[ physiologicalCapacityId=" + physiologicalCapacityId + " ]";
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
    
}
