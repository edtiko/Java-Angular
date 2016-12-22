package co.com.expertla.training.model.entities;

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
@Table(name = "activity")
@NamedQueries({
    @NamedQuery(name = "Activity.findAll", query = "SELECT a FROM Activity a"),
    @NamedQuery(name = "Activity.findByActivityId", query = "SELECT a FROM Activity a WHERE a.activityId = :activityId"),
    @NamedQuery(name = "Activity.findByName", query = "SELECT a FROM Activity a WHERE a.name = :name")})
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "activity_seq", sequenceName = "activity_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_seq")
    @Column(name = "activity_id")
    private Integer activityId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activityId")
    private Collection<ReplaceActivity> replaceActivityCollection;
    @JoinColumn(name = "sport_id", referencedColumnName = "sport_id")
    @ManyToOne
    private Sport sportId;
    @Column(name = "state_id")
    private Short stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column(name = "last_update")
    @Temporal(TemporalType.DATE)
    private Date lastUpdate;
    @Column(name = "user_create")
    private Integer userCreate;
    @Column(name = "user_update")
    private Integer userUpdate;
    @JoinColumn(name = "environment_id", referencedColumnName = "environment_id")
    @ManyToOne
    private Environment environmentId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "replaceId")
    private Collection<ReplaceActivity> replaceCollection;
    @Column(name = "planned_time")
    private Integer plannedTime;
    @Column(name = "planned_distance")
    private Integer plannedDistance;

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

    @JsonIgnore
    public Collection<ReplaceActivity> getReplaceActivityCollection() {
        return replaceActivityCollection;
    }

    public void setReplaceActivityCollection(Collection<ReplaceActivity> replaceActivityCollection) {
        this.replaceActivityCollection = replaceActivityCollection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sport getSportId() {
        return sportId;
    }

    public void setSportId(Sport sportId) {
        this.sportId = sportId;
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

    @JsonIgnore
    public Collection<TrainingPlanWorkout> getTrainingPlanWorkoutCollection() {
        return trainingPlanWorkoutCollection;
    }

    public void setTrainingPlanWorkoutCollection(Collection<TrainingPlanWorkout> trainingPlanWorkoutCollection) {
        this.trainingPlanWorkoutCollection = trainingPlanWorkoutCollection;
    }

    @JsonIgnore
    public Collection<ReplaceActivity> getReplaceCollection() {
        return replaceCollection;
    }

    public void setReplaceCollection(Collection<ReplaceActivity> replaceCollection) {
        this.replaceCollection = replaceCollection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Environment getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Environment environmentId) {
        this.environmentId = environmentId;
    }

    public Integer getPlannedTime() {
        return plannedTime;
    }

    public void setPlannedTime(Integer plannedTime) {
        this.plannedTime = plannedTime;
    }

    public Integer getPlannedDistance() {
        return plannedDistance;
    }

    public void setPlannedDistance(Integer plannedDistance) {
        this.plannedDistance = plannedDistance;
    }

}
