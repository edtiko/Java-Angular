/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "objective")
@NamedQueries({
    @NamedQuery(name = "Objective.findAll", query = "SELECT o FROM Objective o"),
    @NamedQuery(name = "Objective.findByObjectiveId", query = "SELECT o FROM Objective o WHERE o.objectiveId = :objectiveId"),
    @NamedQuery(name = "Objective.findByName", query = "SELECT o FROM Objective o WHERE o.name = :name"),
    @NamedQuery(name = "Objective.findByLevel", query = "SELECT o FROM Objective o WHERE o.level = :level"),
    @NamedQuery(name = "Objective.findByMinSessions", query = "SELECT o FROM Objective o WHERE o.minSessions = :minSessions"),
    @NamedQuery(name = "Objective.findByMaxSessions", query = "SELECT o FROM Objective o WHERE o.maxSessions = :maxSessions")})
public class Objective implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "objective_id")
    private Integer objectiveId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "level")
    private int level;
    @Basic(optional = false)
    @Column(name = "min_sessions")
    private int minSessions;
    @Basic(optional = false)
    @Column(name = "max_sessions")
    private int maxSessions;
    @OneToMany(mappedBy = "objectiveId")
    private List<Activity> activityList;
    @OneToMany(mappedBy = "objectiveId")
    private List<Dcf> dcfList;
    @OneToMany(mappedBy = "objectiveId")
    private List<UserProfile> userProfileList;

    public Objective() {
    }

    public Objective(Integer objectiveId) {
        this.objectiveId = objectiveId;
    }

    public Objective(Integer objectiveId, String name, int level, int minSessions, int maxSessions) {
        this.objectiveId = objectiveId;
        this.name = name;
        this.level = level;
        this.minSessions = minSessions;
        this.maxSessions = maxSessions;
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

    public int getMinSessions() {
        return minSessions;
    }

    public void setMinSessions(int minSessions) {
        this.minSessions = minSessions;
    }

    public int getMaxSessions() {
        return maxSessions;
    }

    public void setMaxSessions(int maxSessions) {
        this.maxSessions = maxSessions;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public List<Dcf> getDcfList() {
        return dcfList;
    }

    public void setDcfList(List<Dcf> dcfList) {
        this.dcfList = dcfList;
    }

    public List<UserProfile> getUserProfileList() {
        return userProfileList;
    }

    public void setUserProfileList(List<UserProfile> userProfileList) {
        this.userProfileList = userProfileList;
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
