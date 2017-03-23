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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "intensity_zone")
@NamedQueries({
    @NamedQuery(name = "IntensityZone.findAll", query = "SELECT i FROM IntensityZone i")})
public class IntensityZone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "intensity_zone_id")
    private Integer intensityZoneId;
    @Column(name = "user_create")
    private Integer userCreate;
    @Column(name = "user_update")
    private Integer userUpdate;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIME)
    private Date creationDate;
    @Column(name = "last_update")
    @Temporal(TemporalType.TIME)
    private Date lastUpdate;
    @Column(name = "state_id")
    private Short stateId;
    @JoinColumn(name = "training_level_id", referencedColumnName = "training_level_id")
    @ManyToOne
    private TrainingLevel trainingLevelId;

    public IntensityZone() {
    }

    public IntensityZone(Integer intensityZoneId) {
        this.intensityZoneId = intensityZoneId;
    }

    public Integer getIntensityZoneId() {
        return intensityZoneId;
    }

    public void setIntensityZoneId(Integer intensityZoneId) {
        this.intensityZoneId = intensityZoneId;
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

    public TrainingLevel getTrainingLevelId() {
        return trainingLevelId;
    }

    public void setTrainingLevelId(TrainingLevel trainingLevelId) {
        this.trainingLevelId = trainingLevelId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intensityZoneId != null ? intensityZoneId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntensityZone)) {
            return false;
        }
        IntensityZone other = (IntensityZone) object;
        if ((this.intensityZoneId == null && other.intensityZoneId != null) || (this.intensityZoneId != null && !this.intensityZoneId.equals(other.intensityZoneId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.IntensityZone[ intensityZoneId=" + intensityZoneId + " ]";
    }
    
}
