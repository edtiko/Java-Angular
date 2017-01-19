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
@Table(name = "training_plan_percentaje")
@NamedQueries({
    @NamedQuery(name = "TrainingPlanPercentaje.findAll", query = "SELECT t FROM TrainingPlanPercentaje t"),
    @NamedQuery(name = "TrainingPlanPercentaje.findByTrainingPlanPercentajeId", query = "SELECT t FROM TrainingPlanPercentaje t WHERE t.trainingPlanPercentajeId = :trainingPlanPercentajeId"),
    @NamedQuery(name = "TrainingPlanPercentaje.findByPercentaje", query = "SELECT t FROM TrainingPlanPercentaje t WHERE t.percentaje = :percentaje"),
    @NamedQuery(name = "TrainingPlanPercentaje.findByCreationDate", query = "SELECT t FROM TrainingPlanPercentaje t WHERE t.creationDate = :creationDate")})
public class TrainingPlanPercentaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "training_plan_percentaje_id")
    private Integer trainingPlanPercentajeId;
    @Column(name = "percentaje")
    private Integer percentaje;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @JoinColumn(name = "training_plan_id", referencedColumnName = "training_plan_id")
    @ManyToOne
    private TrainingPlan trainingPlanId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;

    public TrainingPlanPercentaje() {
    }

    public TrainingPlanPercentaje(Integer trainingPlanPercentajeId) {
        this.trainingPlanPercentajeId = trainingPlanPercentajeId;
    }

    public Integer getTrainingPlanPercentajeId() {
        return trainingPlanPercentajeId;
    }

    public void setTrainingPlanPercentajeId(Integer trainingPlanPercentajeId) {
        this.trainingPlanPercentajeId = trainingPlanPercentajeId;
    }

    public Integer getPercentaje() {
        return percentaje;
    }

    public void setPercentaje(Integer percentaje) {
        this.percentaje = percentaje;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public TrainingPlan getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(TrainingPlan trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trainingPlanPercentajeId != null ? trainingPlanPercentajeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrainingPlanPercentaje)) {
            return false;
        }
        TrainingPlanPercentaje other = (TrainingPlanPercentaje) object;
        if ((this.trainingPlanPercentajeId == null && other.trainingPlanPercentajeId != null) || (this.trainingPlanPercentajeId != null && !this.trainingPlanPercentajeId.equals(other.trainingPlanPercentajeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.TrainingPlanPercentaje[ trainingPlanPercentajeId=" + trainingPlanPercentajeId + " ]";
    }
    
}
