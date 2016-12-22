package co.com.expertla.training.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "objective")
public class Objective implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "objective_seq", sequenceName = "objective_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "objective_seq")
    @Basic(optional = false)
    @Column(name = "objective_id")
    private Integer objectiveId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "level")
    private int level;
    @JoinColumn(name = "discipline_id", referencedColumnName = "discipline_id")
    @ManyToOne
    private Discipline disciplineId;
    @OneToMany(mappedBy = "objectiveId")
    private Collection<Activity> activityCollection;
    @OneToMany(mappedBy = "objectiveId")
    private Collection<Dcf> dcfCollection;
    @OneToMany(mappedBy = "objectiveId")
    private Collection<UserProfile> userProfileCollection;
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
    @Column(name = "objective_parent_id")
    private Integer objectiveParentId;

    public Objective() {
    }

    public Objective(Integer objectiveId) {
        this.objectiveId = objectiveId;
    }

    public Objective(Integer objectiveId, String name, int level, int minSessions, int maxSessions) {
        this.objectiveId = objectiveId;
        this.name = name;
        this.level = level;
    }

    public Integer getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Integer objectiveId) {
        this.objectiveId = objectiveId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public Discipline getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Discipline disciplineId) {
        this.disciplineId = disciplineId;
    }

    public Integer getObjectiveParentId() {
        return objectiveParentId;
    }

    public void setObjectiveParentId(Integer objectiveParentId) {
        this.objectiveParentId = objectiveParentId;
    }
    
    @JsonIgnore
    public Collection<Activity> getActivityCollection() {
        return activityCollection;
    }

    public void setActivityCollection(Collection<Activity> activityCollection) {
        this.activityCollection = activityCollection;
    }
    @JsonIgnore
    public Collection<Dcf> getDcfCollection() {
        return dcfCollection;
    }

    public void setDcfCollection(Collection<Dcf> dcfCollection) {
        this.dcfCollection = dcfCollection;
    }
    @JsonIgnore
    public Collection<UserProfile> getUserProfileCollection() {
        return userProfileCollection;
    }

    public void setUserProfileCollection(Collection<UserProfile> userProfileCollection) {
        this.userProfileCollection = userProfileCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (objectiveId != null ? objectiveId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Objective)) {
            return false;
        }
        Objective other = (Objective) object;
        if ((this.objectiveId == null && other.objectiveId != null) || (this.objectiveId != null && !this.objectiveId.equals(other.objectiveId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Objective[ objectiveId=" + objectiveId + " ]";
    }
}
