/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "training_level_type")
@NamedQueries({
    @NamedQuery(name = "TrainingLevelType.findAll", query = "SELECT t FROM TrainingLevelType t")})
public class TrainingLevelType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "training_level_type_id")
    private Integer trainingLevelTypeId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "user_create")
    private Integer userCreate;
    @Column(name = "user_update")
    private Integer userUpdate;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Column(name = "state_id")
    private Short stateId;

    public TrainingLevelType() {
    }

    public TrainingLevelType(Integer trainingLevelTypeId) {
        this.trainingLevelTypeId = trainingLevelTypeId;
    }

    public TrainingLevelType(Integer trainingLevelTypeId, String name) {
        this.trainingLevelTypeId = trainingLevelTypeId;
        this.name = name;
    }

    public Integer getTrainingLevelTypeId() {
        return trainingLevelTypeId;
    }

    public void setTrainingLevelTypeId(Integer trainingLevelTypeId) {
        this.trainingLevelTypeId = trainingLevelTypeId;
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

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trainingLevelTypeId != null ? trainingLevelTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrainingLevelType)) {
            return false;
        }
        TrainingLevelType other = (TrainingLevelType) object;
        if ((this.trainingLevelTypeId == null && other.trainingLevelTypeId != null) || (this.trainingLevelTypeId != null && !this.trainingLevelTypeId.equals(other.trainingLevelTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.TrainingLevelType[ trainingLevelTypeId=" + trainingLevelTypeId + " ]";
    }
    
}
