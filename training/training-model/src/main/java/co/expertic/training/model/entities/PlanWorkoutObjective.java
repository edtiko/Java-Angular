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
@Table(name = "plan_workout_objective")
@NamedQueries({
    @NamedQuery(name = "PlanWorkoutObjective.findAll", query = "SELECT p FROM PlanWorkoutObjective p"),
    @NamedQuery(name = "PlanWorkoutObjective.findByPlanWorkoutObjectiveId", query = "SELECT p FROM PlanWorkoutObjective p WHERE p.planWorkoutObjectiveId = :planWorkoutObjectiveId"),
    @NamedQuery(name = "PlanWorkoutObjective.findByCreationDate", query = "SELECT p FROM PlanWorkoutObjective p WHERE p.creationDate = :creationDate")})
public class PlanWorkoutObjective implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "plan_workout_objective_plan_workout_objective_id_seq", sequenceName = "plan_workout_objective_plan_workout_objective_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_workout_objective_plan_workout_objective_id_seq")
    @Basic(optional = false)
    @Column(name = "plan_workout_objective_id")
    private Integer planWorkoutObjectiveId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "objective_id", referencedColumnName = "objective_id")
    @ManyToOne(optional = false)
    private Objective objectiveId;
    @JoinColumn(name = "training_plan_user_id", referencedColumnName = "training_plan_user_id")
    @ManyToOne(optional = false)
    private TrainingPlanUser trainingPlanUserId;
    @Column(name = "from_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromDate;
    @Column(name = "to_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toDate;
    @Column(name = "active")
    private Boolean active;

    public PlanWorkoutObjective() {
    }

    public PlanWorkoutObjective(Integer planWorkoutObjectiveId) {
        this.planWorkoutObjectiveId = planWorkoutObjectiveId;
    }

    public Integer getPlanWorkoutObjectiveId() {
        return planWorkoutObjectiveId;
    }

    public void setPlanWorkoutObjectiveId(Integer planWorkoutObjectiveId) {
        this.planWorkoutObjectiveId = planWorkoutObjectiveId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Objective getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Objective objectiveId) {
        this.objectiveId = objectiveId;
    }

    public TrainingPlanUser getTrainingPlanUserId() {
        return trainingPlanUserId;
    }

    public void setTrainingPlanUserId(TrainingPlanUser trainingPlanUserId) {
        this.trainingPlanUserId = trainingPlanUserId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
      

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planWorkoutObjectiveId != null ? planWorkoutObjectiveId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanWorkoutObjective)) {
            return false;
        }
        PlanWorkoutObjective other = (PlanWorkoutObjective) object;
        if ((this.planWorkoutObjectiveId == null && other.planWorkoutObjectiveId != null) || (this.planWorkoutObjectiveId != null && !this.planWorkoutObjectiveId.equals(other.planWorkoutObjectiveId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.PlanWorkoutObjective[ planWorkoutObjectiveId=" + planWorkoutObjectiveId + " ]";
    }
    
}
