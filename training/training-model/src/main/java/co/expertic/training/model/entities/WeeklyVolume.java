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
@Table(name = "weekly_volume")
@NamedQueries({
    @NamedQuery(name = "WeeklyVolume.findAll", query = "SELECT w FROM WeeklyVolume w")})
public class WeeklyVolume implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "weekly_volume_id")
    private Integer weeklyVolumeId;
    @Basic(optional = false)
    @Column(name = "initial_value")
    private int initialValue;
    @Basic(optional = false)
    @Column(name = "increase")
    private int increase;
    @Basic(optional = false)
    @Column(name = "discharge")
    private int discharge;
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
    @JoinColumn(name = "modality_id", referencedColumnName = "modality_id")
    @ManyToOne
    private Modality modalityId;

    public WeeklyVolume() {
    }

    public WeeklyVolume(Integer weeklyVolumeId) {
        this.weeklyVolumeId = weeklyVolumeId;
    }

    public WeeklyVolume(Integer weeklyVolumeId, int initialValue, int increase) {
        this.weeklyVolumeId = weeklyVolumeId;
        this.initialValue = initialValue;
        this.increase = increase;
    }

    public Integer getWeeklyVolumeId() {
        return weeklyVolumeId;
    }

    public void setWeeklyVolumeId(Integer weeklyVolumeId) {
        this.weeklyVolumeId = weeklyVolumeId;
    }

    public int getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(int initialValue) {
        this.initialValue = initialValue;
    }

    public int getIncrease() {
        return increase;
    }

    public void setIncrease(int increase) {
        this.increase = increase;
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

    public Modality getModalityId() {
        return modalityId;
    }

    public void setModalityId(Modality modalityId) {
        this.modalityId = modalityId;
    }

    public int getDischarge() {
        return discharge;
    }

    public void setDischarge(int discharge) {
        this.discharge = discharge;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weeklyVolumeId != null ? weeklyVolumeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WeeklyVolume)) {
            return false;
        }
        WeeklyVolume other = (WeeklyVolume) object;
        if ((this.weeklyVolumeId == null && other.weeklyVolumeId != null) || (this.weeklyVolumeId != null && !this.weeklyVolumeId.equals(other.weeklyVolumeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.WeeklyVolume[ weeklyVolumeId=" + weeklyVolumeId + " ]";
    }
    
}
