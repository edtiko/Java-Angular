/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "training_load_type")
@NamedQueries({
    @NamedQuery(name = "TrainingLoadType.findAll", query = "SELECT t FROM TrainingLoadType t")})
public class TrainingLoadType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "training_load_type_id")
    private Integer trainingLoadTypeId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "state_id")
    private Short stateId;
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
    @OneToMany(mappedBy = "trainingLoadTypeId")
    private Collection<IntensityZone> intensityZoneCollection;

    public TrainingLoadType() {
    }

    public TrainingLoadType(Integer trainingLoadTypeId) {
        this.trainingLoadTypeId = trainingLoadTypeId;
    }

    public TrainingLoadType(Integer trainingLoadTypeId, String name) {
        this.trainingLoadTypeId = trainingLoadTypeId;
        this.name = name;
    }

    public Integer getTrainingLoadTypeId() {
        return trainingLoadTypeId;
    }

    public void setTrainingLoadTypeId(Integer trainingLoadTypeId) {
        this.trainingLoadTypeId = trainingLoadTypeId;
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

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
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

    public Collection<IntensityZone> getIntensityZoneCollection() {
        return intensityZoneCollection;
    }

    public void setIntensityZoneCollection(Collection<IntensityZone> intensityZoneCollection) {
        this.intensityZoneCollection = intensityZoneCollection;
    }
    
   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trainingLoadTypeId != null ? trainingLoadTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrainingLoadType)) {
            return false;
        }
        TrainingLoadType other = (TrainingLoadType) object;
        if ((this.trainingLoadTypeId == null && other.trainingLoadTypeId != null) || (this.trainingLoadTypeId != null && !this.trainingLoadTypeId.equals(other.trainingLoadTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.TrainingLoadType[ trainingLoadTypeId=" + trainingLoadTypeId + " ]";
    }
    
}
