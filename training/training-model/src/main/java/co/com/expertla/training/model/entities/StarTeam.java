/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "star_team")
public class StarTeam implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "starTeamId")
    private Collection<CoachAssignedPlan> coachAssignedPlanCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "star_team_id")
    private Integer starTeamId;
    @Column(name = "state_id")
    private Short stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "star_user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User starUserId;
    @JoinColumn(name = "coach_user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User coachUserId;
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Column(name = "user_create")
    private Integer userCreate;
    @Column(name = "user_update")
    private Integer userUpdate;

    public StarTeam() {
    }

    public StarTeam(Integer startTeamId) {
        this.starTeamId = startTeamId;
    }

    public Integer getStarTeamId() {
        return starTeamId;
    }

    public void setStarTeamId(Integer startTeamId) {
        this.starTeamId = startTeamId;
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

    public User getStarUserId() {
        return starUserId;
    }

    public void setStarUserId(User startUserId) {
        this.starUserId = startUserId;
    }

    public User getCoachUserId() {
        return coachUserId;
    }

    public void setCoachUserId(User coachUserId) {
        this.coachUserId = coachUserId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (starTeamId != null ? starTeamId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StarTeam)) {
            return false;
        }
        StarTeam other = (StarTeam) object;
        if ((this.starTeamId == null && other.starTeamId != null) || (this.starTeamId != null && !this.starTeamId.equals(other.starTeamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.StartTeam[ startTeamId=" + starTeamId + " ]";
    }
    @JsonIgnore
    public Collection<CoachAssignedPlan> getCoachAssignedPlanCollection() {
        return coachAssignedPlanCollection;
    }

    public void setCoachAssignedPlanCollection(Collection<CoachAssignedPlan> coachAssignedPlanCollection) {
        this.coachAssignedPlanCollection = coachAssignedPlanCollection;
    }
    
}
