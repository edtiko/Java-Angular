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
@Table(name = "training_level")
@NamedQueries({
    @NamedQuery(name = "TrainingLevel.findAll", query = "SELECT t FROM TrainingLevel t")})
public class TrainingLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "training_level_id")
    private Integer trainingLevelId;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Column(name = "min_sesion")
    private Integer minSesion;
    @Column(name = "max_sesion")
    private Integer maxSesion;
    @Column(name = "min_hour_week")
    private Integer minHourWeek;
    @Column(name = "max_hour_week")
    private Integer maxHourWeek;
    @Column(name = "min_week_plan")
    private Integer minWeekPlan;
    @Column(name = "max_week_plan")
    private Integer maxWeekPlan;
    @JoinColumn(name = "modality_id", referencedColumnName = "modality_id")
    @ManyToOne
    private Modality modalityId;
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
    @OneToMany(mappedBy = "trainingLevelId")
    private Collection<IntensityZoneDist> intensityZoneDistCollection;

    public TrainingLevel() {
    }

    public TrainingLevel(Integer trainingLevelId) {
        this.trainingLevelId = trainingLevelId;
    }

    public TrainingLevel(Integer trainingLevelId, String description) {
        this.trainingLevelId = trainingLevelId;
        this.description = description;
    }

    public Integer getTrainingLevelId() {
        return trainingLevelId;
    }

    public void setTrainingLevelId(Integer trainingLevelId) {
        this.trainingLevelId = trainingLevelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinSesion() {
        return minSesion;
    }

    public void setMinSesion(Integer minSesion) {
        this.minSesion = minSesion;
    }

    public Integer getMaxSesion() {
        return maxSesion;
    }

    public void setMaxSesion(Integer maxSesion) {
        this.maxSesion = maxSesion;
    }

    public Integer getMinHourWeek() {
        return minHourWeek;
    }

    public void setMinHourWeek(Integer minHourWeek) {
        this.minHourWeek = minHourWeek;
    }

    public Integer getMaxHourWeek() {
        return maxHourWeek;
    }

    public void setMaxHourWeek(Integer maxHourWeek) {
        this.maxHourWeek = maxHourWeek;
    }

    public Integer getMinWeekPlan() {
        return minWeekPlan;
    }

    public void setMinWeekPlan(Integer minWeekPlan) {
        this.minWeekPlan = minWeekPlan;
    }

    public Integer getMaxWeekPlan() {
        return maxWeekPlan;
    }

    public void setMaxWeekPlan(Integer maxWeekPlan) {
        this.maxWeekPlan = maxWeekPlan;
    }

    public Modality getModalityId() {
        return modalityId;
    }

    public void setModalityId(Modality modalityId) {
        this.modalityId = modalityId;
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

    public Collection<IntensityZoneDist> getIntensityZoneDistCollection() {
        return intensityZoneDistCollection;
    }

    public void setIntensityZoneDistCollection(Collection<IntensityZoneDist> intensityZoneDistCollection) {
        this.intensityZoneDistCollection = intensityZoneDistCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trainingLevelId != null ? trainingLevelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrainingLevel)) {
            return false;
        }
        TrainingLevel other = (TrainingLevel) object;
        if ((this.trainingLevelId == null && other.trainingLevelId != null) || (this.trainingLevelId != null && !this.trainingLevelId.equals(other.trainingLevelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.TrainingLevel[ trainingLevelId=" + trainingLevelId + " ]";
    }
    
}
