/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
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
    @SequenceGenerator(name = "training_level_id_seq", sequenceName = "training_level_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_level_id_seq")
    @Column(name = "training_level_id")
    private Integer trainingLevelId;
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
    @Column(name = "user_create")
    private Integer userCreate;
    @Column(name = "user_update")
    private Integer userUpdate;
    @Column(name = "state_id")
    private Short stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @JoinColumn(name = "modality_id", referencedColumnName = "modality_id")
    @ManyToOne
    private Modality modalityId;
    @JoinColumn(name = "training_level_type_id", referencedColumnName = "training_level_type_id")
    @ManyToOne
    private TrainingLevelType trainingLevelTypeId;

    public TrainingLevel() {
    }

    public TrainingLevel(Integer trainingLevelId) {
        this.trainingLevelId = trainingLevelId;
    }

    public Integer getTrainingLevelId() {
        return trainingLevelId;
    }

    public void setTrainingLevelId(Integer trainingLevelId) {
        this.trainingLevelId = trainingLevelId;
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

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
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

    public Modality getModalityId() {
        return modalityId;
    }

    public void setModalityId(Modality modalityId) {
        this.modalityId = modalityId;
    }

    public TrainingLevelType getTrainingLevelTypeId() {
        return trainingLevelTypeId;
    }

    public void setTrainingLevelTypeId(TrainingLevelType trainingLevelTypeId) {
        this.trainingLevelTypeId = trainingLevelTypeId;
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
