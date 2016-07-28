/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "activity")
@NamedQueries({
    @NamedQuery(name = "Activity.findAll", query = "SELECT a FROM Activity a"),
    @NamedQuery(name = "Activity.findByActivityId", query = "SELECT a FROM Activity a WHERE a.activityId = :activityId"),
    @NamedQuery(name = "Activity.findByName", query = "SELECT a FROM Activity a WHERE a.name = :name")})
public class Activity implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "activity_id")
    private Integer activityId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "modality_id", referencedColumnName = "modality_id")
    @ManyToOne
    private Modality modalityId;
    @JoinColumn(name = "objective_id", referencedColumnName = "objective_id")
    @ManyToOne
    private Objective objectiveId;
    @JoinColumn(name = "physiological_capacity_id", referencedColumnName = "physiological_capacity_id")
    @ManyToOne
    private PhysiologicalCapacity physiologicalCapacityId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activityId")
    private Collection<TrainingPlanWorkout> trainingPlanWorkoutCollection;

    public Activity() {
    }

    public Activity(Integer activityId) {
        this.activityId = activityId;
    }

    public Activity(Integer activityId, String name) {
        this.activityId = activityId;
        this.name = name;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Modality getModalityId() {
        return modalityId;
    }

    public void setModalityId(Modality modalityId) {
        this.modalityId = modalityId;
    }

    public Objective getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Objective objectiveId) {
        this.objectiveId = objectiveId;
    }

    public PhysiologicalCapacity getPhysiologicalCapacityId() {
        return physiologicalCapacityId;
    }

    public void setPhysiologicalCapacityId(PhysiologicalCapacity physiologicalCapacityId) {
        this.physiologicalCapacityId = physiologicalCapacityId;
    }

    public Collection<TrainingPlanWorkout> getTrainingPlanWorkoutCollection() {
        return trainingPlanWorkoutCollection;
    }

    public void setTrainingPlanWorkoutCollection(Collection<TrainingPlanWorkout> trainingPlanWorkoutCollection) {
        this.trainingPlanWorkoutCollection = trainingPlanWorkoutCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (activityId != null ? activityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Activity)) {
            return false;
        }
        Activity other = (Activity) object;
        if ((this.activityId == null && other.activityId != null) || (this.activityId != null && !this.activityId.equals(other.activityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Activity[ activityId=" + activityId + " ]";
    }

    
}
