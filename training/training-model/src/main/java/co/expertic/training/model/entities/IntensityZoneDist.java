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
@Table(name = "intensity_zone_dist")
@NamedQueries({
    @NamedQuery(name = "IntensityZoneDist.findAll", query = "SELECT i FROM IntensityZoneDist i")})
public class IntensityZoneDist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "intensity_zone_dist_id")
    private Integer intensityZoneDistId;
    @Column(name = "z1")
    private Integer z1;
    @Column(name = "z2")
    private Integer z2;
    @Column(name = "z3")
    private Integer z3;
    @Column(name = "z4")
    private Integer z4;
    @Column(name = "z5")
    private Integer z5;
    @Column(name = "z6")
    private Integer z6;
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

    public IntensityZoneDist() {
    }

    public IntensityZoneDist(Integer intensityZoneDistId) {
        this.intensityZoneDistId = intensityZoneDistId;
    }

    public Integer getIntensityZoneDistId() {
        return intensityZoneDistId;
    }

    public void setIntensityZoneDistId(Integer intensityZoneDistId) {
        this.intensityZoneDistId = intensityZoneDistId;
    }

    public Integer getZ1() {
        return z1;
    }

    public void setZ1(Integer z1) {
        this.z1 = z1;
    }

    public Integer getZ2() {
        return z2;
    }

    public void setZ2(Integer z2) {
        this.z2 = z2;
    }

    public Integer getZ3() {
        return z3;
    }

    public void setZ3(Integer z3) {
        this.z3 = z3;
    }

    public Integer getZ4() {
        return z4;
    }

    public void setZ4(Integer z4) {
        this.z4 = z4;
    }

    public Integer getZ5() {
        return z5;
    }

    public void setZ5(Integer z5) {
        this.z5 = z5;
    }

    public Integer getZ6() {
        return z6;
    }

    public void setZ6(Integer z6) {
        this.z6 = z6;
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
        hash += (intensityZoneDistId != null ? intensityZoneDistId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntensityZoneDist)) {
            return false;
        }
        IntensityZoneDist other = (IntensityZoneDist) object;
        if ((this.intensityZoneDistId == null && other.intensityZoneDistId != null) || (this.intensityZoneDistId != null && !this.intensityZoneDistId.equals(other.intensityZoneDistId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.IntensityZoneDist[ intensityZoneDistId=" + intensityZoneDistId + " ]";
    }
    
}
