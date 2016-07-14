/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "training_plan_workout")
@NamedQueries({
    @NamedQuery(name = "TrainingPlanWorkout.findAll", query = "SELECT t FROM TrainingPlanWorkout t"),
    @NamedQuery(name = "TrainingPlanWorkout.findByTrainingPlanWorkoutId", query = "SELECT t FROM TrainingPlanWorkout t WHERE t.trainingPlanWorkoutId = :trainingPlanWorkoutId"),
    @NamedQuery(name = "TrainingPlanWorkout.findByWorkoutDate", query = "SELECT t FROM TrainingPlanWorkout t WHERE t.workoutDate = :workoutDate")})
public class TrainingPlanWorkout implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "training_plan_workout_id")
    private Integer trainingPlanWorkoutId;
    @Basic(optional = false)
    @Column(name = "workout_date")
    @Temporal(TemporalType.DATE)
    private Date workoutDate;
    @JoinColumn(name = "activity_id", referencedColumnName = "activity_id")
    @ManyToOne(optional = false)
    private Activity activityId;
    @JoinColumn(name = "training_plan_id", referencedColumnName = "training_plan_id")
    @ManyToOne(optional = false)
    private TrainingPlan trainingPlanId;

    public TrainingPlanWorkout() {
    }

    public TrainingPlanWorkout(Integer trainingPlanWorkoutId) {
        this.trainingPlanWorkoutId = trainingPlanWorkoutId;
    }

    public TrainingPlanWorkout(Integer trainingPlanWorkoutId, Date workoutDate) {
        this.trainingPlanWorkoutId = trainingPlanWorkoutId;
        this.workoutDate = workoutDate;
    }

    public Integer getTrainingPlanWorkoutId() {
        return trainingPlanWorkoutId;
    }

    public void setTrainingPlanWorkoutId(Integer trainingPlanWorkoutId) {
        this.trainingPlanWorkoutId = trainingPlanWorkoutId;
    }

    public Date getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(Date workoutDate) {
        this.workoutDate = workoutDate;
    }

    public Activity getActivityId() {
        return activityId;
    }

    public void setActivityId(Activity activityId) {
        this.activityId = activityId;
    }

    public TrainingPlan getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(TrainingPlan trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trainingPlanWorkoutId != null ? trainingPlanWorkoutId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrainingPlanWorkout)) {
            return false;
        }
        TrainingPlanWorkout other = (TrainingPlanWorkout) object;
        if ((this.trainingPlanWorkoutId == null && other.trainingPlanWorkoutId != null) || (this.trainingPlanWorkoutId != null && !this.trainingPlanWorkoutId.equals(other.trainingPlanWorkoutId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.TrainingPlanWorkout[ trainingPlanWorkoutId=" + trainingPlanWorkoutId + " ]";
    }
    
}
