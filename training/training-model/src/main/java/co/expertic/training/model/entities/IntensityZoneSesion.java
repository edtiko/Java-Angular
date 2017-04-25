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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "intensity_zone_sesion")
@NamedQueries({
    @NamedQuery(name = "IntensityZoneSesion.findAll", query = "SELECT i FROM IntensityZoneSesion i")})
public class IntensityZoneSesion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "intensity_zone_sesion_id")
    private Integer intensityZoneSesionId;
    @Column(name = "num_sesion")
    private Integer numSesion;
    @Column(name = "sesion")
    private Integer sesion;
    @Column(name = "daily_percentaje")
    private Integer dailyPercentaje;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "intensityZoneSesionId")
    private Collection<IntensityZoneSesionDist> intensityZoneSesionDistCollection;

    public IntensityZoneSesion() {
    }

    public IntensityZoneSesion(Integer intensityZoneSesionId) {
        this.intensityZoneSesionId = intensityZoneSesionId;
    }

    public Integer getIntensityZoneSesionId() {
        return intensityZoneSesionId;
    }

    public void setIntensityZoneSesionId(Integer intensityZoneSesionId) {
        this.intensityZoneSesionId = intensityZoneSesionId;
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

    public TrainingLevel getTrainingLevelId() {
        return trainingLevelId;
    }

    public void setTrainingLevelId(TrainingLevel trainingLevelId) {
        this.trainingLevelId = trainingLevelId;
    }
    
    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    public Collection<IntensityZoneSesionDist> getIntensityZoneSesionDistCollection() {
        return intensityZoneSesionDistCollection;
    }

    public void setIntensityZoneSesionDistCollection(Collection<IntensityZoneSesionDist> intensityZoneSesionDistCollection) {
        this.intensityZoneSesionDistCollection = intensityZoneSesionDistCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intensityZoneSesionId != null ? intensityZoneSesionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntensityZoneSesion)) {
            return false;
        }
        IntensityZoneSesion other = (IntensityZoneSesion) object;
        if ((this.intensityZoneSesionId == null && other.intensityZoneSesionId != null) || (this.intensityZoneSesionId != null && !this.intensityZoneSesionId.equals(other.intensityZoneSesionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.IntensityZoneSesion[ intensityZoneSesionId=" + intensityZoneSesionId + " ]";
    }
    
}
