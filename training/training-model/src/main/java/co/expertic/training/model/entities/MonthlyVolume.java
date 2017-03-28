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
@Table(name = "monthly_volume")
@NamedQueries({
    @NamedQuery(name = "MonthlyVolume.findAll", query = "SELECT m FROM MonthlyVolume m")})
public class MonthlyVolume implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "monthly_volume_id")
    private Integer monthlyVolumeId;
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

    public MonthlyVolume() {
    }

    public MonthlyVolume(Integer monthlyVolumeId) {
        this.monthlyVolumeId = monthlyVolumeId;
    }

    public MonthlyVolume(Integer monthlyVolumeId, int initialValue, int increase) {
        this.monthlyVolumeId = monthlyVolumeId;
        this.initialValue = initialValue;
        this.increase = increase;
    }

    public Integer getMonthlyVolumeId() {
        return monthlyVolumeId;
    }

    public void setMonthlyVolumeId(Integer monthlyVolumeId) {
        this.monthlyVolumeId = monthlyVolumeId;
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
        hash += (monthlyVolumeId != null ? monthlyVolumeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MonthlyVolume)) {
            return false;
        }
        MonthlyVolume other = (MonthlyVolume) object;
        if ((this.monthlyVolumeId == null && other.monthlyVolumeId != null) || (this.monthlyVolumeId != null && !this.monthlyVolumeId.equals(other.monthlyVolumeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.MonthlyVolume[ monthlyVolumeId=" + monthlyVolumeId + " ]";
    }
    
}
