package co.expertic.training.model.dto;

import co.expertic.training.model.entities.Objective;
import co.expertic.training.model.entities.Modality;
import java.io.Serializable;
import java.util.Date;

/**
* Dcf <br>
* Creation Date : <br>
* date 21/07/2016 <br>
* @author Angela Ramírez
**/
public class DcfDTO implements Serializable {


    private static final long serialVersionUID = 1L;
    private Integer dcfId;
    private String pattern;
    private int sessions;
    private Modality modalityId;
    private Objective objectiveId;
    private int count;
    private Short stateId;
    private Date creationDate;
    private Date lastUpdate;
    private Integer userCreate;
    private Integer userUpdate;

    public DcfDTO() {
    }

    public DcfDTO(Integer dcfId) {
        this.dcfId = dcfId;
    }

    public DcfDTO(Integer dcfId, String pattern, int sessions) {
        this.dcfId = dcfId;
        this.pattern = pattern;
        this.sessions = sessions;
    }

    public Integer getDcfId() {
        return dcfId;
    }

    public void setDcfId(Integer dcfId) {
        this.dcfId = dcfId;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public int getSessions() {
        return sessions;
    }

    public void setSessions(int sessions) {
        this.sessions = sessions;
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
