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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "training_user_serie")
@NamedQueries({
    @NamedQuery(name = "TrainingUserSerie.findAll", query = "SELECT t FROM TrainingUserSerie t")})
public class TrainingUserSerie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "training_user_serie_training_user_serie_id_seq", sequenceName = "training_user_serie_training_user_serie_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_user_serie_training_user_serie_id_seq")
    @Column(name = "training_user_serie_id")
    private Integer trainingUserSerieId;
    @Column(name = "work_date")
    @Temporal(TemporalType.DATE)
    private Date workDate;
    @Column(name = "num_series")
    private Integer numSeries;
    @Column(name = "serie_time")
    private Double serieTime;
    @Column(name = "num_zona")
    private Integer numZona;
    @Column(name = "user_create")
    private Integer userCreate;
    @Column(name = "user_update")
    private Integer userUpdate;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column(name = "last_update")
    @Temporal(TemporalType.DATE)
    private Date lastUpdate;
    @JoinColumn(name = "training_plan_user_id", referencedColumnName = "training_plan_user_id")
    @ManyToOne
    private TrainingPlanUser trainingPlanUserId;
    @Column(name = "sesion")
    private Integer sesion;
     @Column(name = "week")
    private Integer week;

    public TrainingUserSerie() {
    }

    public TrainingUserSerie(Integer trainingUserSerieId) {
        this.trainingUserSerieId = trainingUserSerieId;
    }

    public Integer getTrainingUserSerieId() {
        return trainingUserSerieId;
    }

    public void setTrainingUserSerieId(Integer trainingUserSerieId) {
        this.trainingUserSerieId = trainingUserSerieId;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Integer getNumSeries() {
        return numSeries;
    }

    public void setNumSeries(Integer numSeries) {
        this.numSeries = numSeries;
    }

    public Double getSerieTime() {
        return serieTime;
    }

    public void setSerieTime(Double serieTime) {
        this.serieTime = serieTime;
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

    public TrainingPlanUser getTrainingPlanUserId() {
        return trainingPlanUserId;
    }

    public void setTrainingPlanUserId(TrainingPlanUser trainingPlanUserId) {
        this.trainingPlanUserId = trainingPlanUserId;
    }

    public Integer getNumZona() {
        return numZona;
    }

    public void setNumZona(Integer numZona) {
        this.numZona = numZona;
    }

    public Integer getSesion() {
        return sesion;
    }

    public void setSesion(Integer sesion) {
        this.sesion = sesion;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trainingUserSerieId != null ? trainingUserSerieId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrainingUserSerie)) {
            return false;
        }
        TrainingUserSerie other = (TrainingUserSerie) object;
        if ((this.trainingUserSerieId == null && other.trainingUserSerieId != null) || (this.trainingUserSerieId != null && !this.trainingUserSerieId.equals(other.trainingUserSerieId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.TrainingUserSerie[ trainingUserSerieId=" + trainingUserSerieId + " ]";
    }

}
