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
@Table(name = "zone_time_serie")
@NamedQueries({
    @NamedQuery(name = "ZoneTimeSerie.findAll", query = "SELECT z FROM ZoneTimeSerie z")})
public class ZoneTimeSerie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "zone_time_serie_id")
    private Integer zoneTimeSerieId;
    @Column(name = "num_zone")
    private Integer numZone;
    @Column(name = "num_interval")
    private Double numInterval;
    @Column(name = "num_min")
    private Integer numMin;
    @Column(name = "num_max")
    private Integer numMax;
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

    public ZoneTimeSerie() {
    }

    public ZoneTimeSerie(Integer zoneTimeSerieId) {
        this.zoneTimeSerieId = zoneTimeSerieId;
    }

    public Integer getZoneTimeSerieId() {
        return zoneTimeSerieId;
    }

    public void setZoneTimeSerieId(Integer zoneTimeSerieId) {
        this.zoneTimeSerieId = zoneTimeSerieId;
    }

    public Integer getNumZone() {
        return numZone;
    }

    public void setNumZone(Integer numZone) {
        this.numZone = numZone;
    }

    public Double getNumInterval() {
        return numInterval;
    }

    public void setNumInterval(Double numInterval) {
        this.numInterval = numInterval;
    }

    public Integer getNumMin() {
        return numMin;
    }

    public void setNumMin(Integer numMin) {
        this.numMin = numMin;
    }

    public Integer getNumMax() {
        return numMax;
    }

    public void setNumMax(Integer numMax) {
        this.numMax = numMax;
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
        hash += (zoneTimeSerieId != null ? zoneTimeSerieId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZoneTimeSerie)) {
            return false;
        }
        ZoneTimeSerie other = (ZoneTimeSerie) object;
        if ((this.zoneTimeSerieId == null && other.zoneTimeSerieId != null) || (this.zoneTimeSerieId != null && !this.zoneTimeSerieId.equals(other.zoneTimeSerieId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.ZoneTimeSerie[ zoneTimeSerieId=" + zoneTimeSerieId + " ]";
    }
    
}
