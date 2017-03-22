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
@Table(name = "intensity_zone_sesion_dist")
@NamedQueries({
    @NamedQuery(name = "IntensityZoneSesionDist.findAll", query = "SELECT i FROM IntensityZoneSesionDist i")})
public class IntensityZoneSesionDist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "intensity_zone_sesion_dist_id")
    private Integer intensityZoneSesionDistId;
    @Column(name = "num_sesion")
    private Integer numSesion;
    @Column(name = "sesion")
    private Integer sesion;
    @Column(name = "daily_percentaje")
    private Integer dailyPercentaje;
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

    public IntensityZoneSesionDist() {
    }

    public IntensityZoneSesionDist(Integer intensityZoneSesionDistId) {
        this.intensityZoneSesionDistId = intensityZoneSesionDistId;
    }

    public Integer getIntensityZoneSesionDistId() {
        return intensityZoneSesionDistId;
    }

    public void setIntensityZoneSesionDistId(Integer intensityZoneSesionDistId) {
        this.intensityZoneSesionDistId = intensityZoneSesionDistId;
    }

    public Integer getNumSesion() {
        return numSesion;
    }

    public void setNumSesion(Integer numSesion) {
        this.numSesion = numSesion;
    }

    public Integer getSesion() {
        return sesion;
    }

    public void setSesion(Integer sesion) {
        this.sesion = sesion;
    }

    public Integer getDailyPercentaje() {
        return dailyPercentaje;
    }

    public void setDailyPercentaje(Integer dailyPercentaje) {
        this.dailyPercentaje = dailyPercentaje;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intensityZoneSesionDistId != null ? intensityZoneSesionDistId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntensityZoneSesionDist)) {
            return false;
        }
        IntensityZoneSesionDist other = (IntensityZoneSesionDist) object;
        if ((this.intensityZoneSesionDistId == null && other.intensityZoneSesionDistId != null) || (this.intensityZoneSesionDistId != null && !this.intensityZoneSesionDistId.equals(other.intensityZoneSesionDistId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.IntensityZoneSesionDist[ intensityZoneSesionDistId=" + intensityZoneSesionDistId + " ]";
    }
    
}
