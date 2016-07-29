/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import java.io.Serializable;
import java.util.Collection;
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
    @OneToMany(mappedBy = "objectiveId")
    private Collection<Activity> activityCollection;
    @OneToMany(mappedBy = "objectiveId")
    private Collection<Dcf> dcfCollection;
    @OneToMany(mappedBy = "objectiveId")
    private Collection<UserProfile> userProfileCollection;

    public Objective() {
    }

    public Objective(Integer objectiveId) {
        this.objectiveId = objectiveId;
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

    public Collection<Activity> getActivityCollection() {
        return activityCollection;
    }

    public void setActivityCollection(Collection<Activity> activityCollection) {
        this.activityCollection = activityCollection;
    }

    public Collection<Dcf> getDcfCollection() {
        return dcfCollection;
    }

    public void setDcfCollection(Collection<Dcf> dcfCollection) {
        this.dcfCollection = dcfCollection;
    }

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
        return "co.com.expertla.training.model.entities.Objetive[ objectiveId=" + objectiveId + " ]";
    }
    
}
