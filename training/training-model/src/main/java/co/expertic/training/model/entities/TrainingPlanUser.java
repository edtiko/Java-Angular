package co.expertic.training.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "training_plan_user")
@NamedQueries({
    @NamedQuery(name = "TrainingPlanUser.findAll", query = "SELECT t FROM TrainingPlanUser t"),
    @NamedQuery(name = "TrainingPlanUser.findByTrainingPlanUserId", query = "SELECT t FROM TrainingPlanUser t WHERE t.trainingPlanUserId = :trainingPlanUserId")})
public class TrainingPlanUser implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainingPlanUserId")
    private Collection<CoachAssignedPlan> coachAssignedPlanCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)@SequenceGenerator(name = "training_plan_user_seq", sequenceName = "training_plan_user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_plan_user_seq")
    @Column(name = "training_plan_user_id")
    private Integer trainingPlanUserId;
    @Column(name = "state_id")
    private Integer stateId;
    @JoinColumn(name = "training_plan_id", referencedColumnName = "training_plan_id")
    @ManyToOne
    private TrainingPlan trainingPlanId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainingPlanUserId")
    private Collection<TrainingPlanWorkout> trainingPlanWorkoutCollection;
    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public TrainingPlanUser() {
    }

    public TrainingPlanUser(Integer trainingPlanUserId) {
        this.trainingPlanUserId = trainingPlanUserId;
    }
    @JsonIgnore
    public Collection<TrainingPlanWorkout> getTrainingPlanWorkoutCollection() {
        return trainingPlanWorkoutCollection;
    }

    public void setTrainingPlanWorkoutCollection(Collection<TrainingPlanWorkout> trainingPlanWorkoutCollection) {
        this.trainingPlanWorkoutCollection = trainingPlanWorkoutCollection;
    }

    public Integer getTrainingPlanUserId() {
        return trainingPlanUserId;
    }

    public void setTrainingPlanUserId(Integer trainingPlanUserId) {
        this.trainingPlanUserId = trainingPlanUserId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
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
        hash += (trainingPlanUserId != null ? trainingPlanUserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrainingPlanUser)) {
            return false;
        }
        TrainingPlanUser other = (TrainingPlanUser) object;
        if ((this.trainingPlanUserId == null && other.trainingPlanUserId != null) || (this.trainingPlanUserId != null && !this.trainingPlanUserId.equals(other.trainingPlanUserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.TrainingPlanUser[ trainingPlanUserId=" + trainingPlanUserId + " ]";
    }
    @JsonIgnore
    public Collection<CoachAssignedPlan> getCoachAssignedPlanCollection() {
        return coachAssignedPlanCollection;
    }

    public void setCoachAssignedPlanCollection(Collection<CoachAssignedPlan> coachAssignedPlanCollection) {
        this.coachAssignedPlanCollection = coachAssignedPlanCollection;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
        
}
