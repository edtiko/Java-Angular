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
@Table(name = "training_plan_renovation")
@NamedQueries({
    @NamedQuery(name = "TrainingPlanRenovation.findAll", query = "SELECT t FROM TrainingPlanRenovation t"),
    @NamedQuery(name = "TrainingPlanRenovation.findByTrainingPlanRenovationId", query = "SELECT t FROM TrainingPlanRenovation t WHERE t.trainingPlanRenovationId = :trainingPlanRenovationId"),
    @NamedQuery(name = "TrainingPlanRenovation.findByCreationDate", query = "SELECT t FROM TrainingPlanRenovation t WHERE t.creationDate = :creationDate")})
public class TrainingPlanRenovation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "training_plan_renovation_id")
    private Integer trainingPlanRenovationId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @JoinColumn(name = "training_plan_user_id", referencedColumnName = "training_plan_user_id")
    @ManyToOne
    private TrainingPlanUser trainingPlanUserId;

    public TrainingPlanRenovation() {
    }

    public TrainingPlanRenovation(Integer trainingPlanRenovationId) {
        this.trainingPlanRenovationId = trainingPlanRenovationId;
    }

    public Integer getTrainingPlanRenovationId() {
        return trainingPlanRenovationId;
    }

    public void setTrainingPlanRenovationId(Integer trainingPlanRenovationId) {
        this.trainingPlanRenovationId = trainingPlanRenovationId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public TrainingPlanUser getTrainingPlanUserId() {
        return trainingPlanUserId;
    }

    public void setTrainingPlanUserId(TrainingPlanUser trainingPlanUserId) {
        this.trainingPlanUserId = trainingPlanUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trainingPlanRenovationId != null ? trainingPlanRenovationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrainingPlanRenovation)) {
            return false;
        }
        TrainingPlanRenovation other = (TrainingPlanRenovation) object;
        if ((this.trainingPlanRenovationId == null && other.trainingPlanRenovationId != null) || (this.trainingPlanRenovationId != null && !this.trainingPlanRenovationId.equals(other.trainingPlanRenovationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.TrainingPlanRenovation[ trainingPlanRenovationId=" + trainingPlanRenovationId + " ]";
    }
    
}
