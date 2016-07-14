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
@Table(name = "objetive")
@NamedQueries({
    @NamedQuery(name = "Objetive.findAll", query = "SELECT o FROM Objetive o"),
    @NamedQuery(name = "Objetive.findByObjetiveId", query = "SELECT o FROM Objetive o WHERE o.objetiveId = :objetiveId"),
    @NamedQuery(name = "Objetive.findByName", query = "SELECT o FROM Objetive o WHERE o.name = :name"),
    @NamedQuery(name = "Objetive.findByLevel", query = "SELECT o FROM Objetive o WHERE o.level = :level"),
    @NamedQuery(name = "Objetive.findByMinSessions", query = "SELECT o FROM Objetive o WHERE o.minSessions = :minSessions"),
    @NamedQuery(name = "Objetive.findByMaxSessions", query = "SELECT o FROM Objetive o WHERE o.maxSessions = :maxSessions")})
public class Objetive implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "objetive_id")
    private Integer objetiveId;
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
    @OneToMany(mappedBy = "objetiveId")
    private Collection<Activity> activityCollection;
    @OneToMany(mappedBy = "objetiveId")
    private Collection<Dcf> dcfCollection;
    @OneToMany(mappedBy = "objetiveId")
    private Collection<UserProfile> userProfileCollection;

    public Objetive() {
    }

    public Objetive(Integer objetiveId) {
        this.objetiveId = objetiveId;
    }

    public Objetive(Integer objetiveId, String name, int level, int minSessions, int maxSessions) {
        this.objetiveId = objetiveId;
        this.name = name;
        this.level = level;
        this.minSessions = minSessions;
        this.maxSessions = maxSessions;
    }

    public Integer getObjetiveId() {
        return objetiveId;
    }

    public void setObjetiveId(Integer objetiveId) {
        this.objetiveId = objetiveId;
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
        hash += (objetiveId != null ? objetiveId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Objetive)) {
            return false;
        }
        Objetive other = (Objetive) object;
        if ((this.objetiveId == null && other.objetiveId != null) || (this.objetiveId != null && !this.objetiveId.equals(other.objetiveId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Objetive[ objetiveId=" + objetiveId + " ]";
    }
    
}
