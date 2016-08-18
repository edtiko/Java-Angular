/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

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
@Table(name = "start_team")
@NamedQueries({
    @NamedQuery(name = "StartTeam.findAll", query = "SELECT s FROM StartTeam s"),
    @NamedQuery(name = "StartTeam.findByStartTeamId", query = "SELECT s FROM StartTeam s WHERE s.startTeamId = :startTeamId"),
    @NamedQuery(name = "StartTeam.findByStateId", query = "SELECT s FROM StartTeam s WHERE s.stateId = :stateId"),
    @NamedQuery(name = "StartTeam.findByCreationDate", query = "SELECT s FROM StartTeam s WHERE s.creationDate = :creationDate")})
public class StartTeam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "start_team_id")
    private Integer startTeamId;
    @Column(name = "state_id")
    private Integer stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "startTeamId")
    private Collection<CoachAssignedPlan> coachAssignedPlanCollection;

    public StartTeam() {
    }

    public StartTeam(Integer startTeamId) {
        this.startTeamId = startTeamId;
    }

    public Integer getStartTeamId() {
        return startTeamId;
    }

    public void setStartTeamId(Integer startTeamId) {
        this.startTeamId = startTeamId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Collection<CoachAssignedPlan> getCoachAssignedPlanCollection() {
        return coachAssignedPlanCollection;
    }

    public void setCoachAssignedPlanCollection(Collection<CoachAssignedPlan> coachAssignedPlanCollection) {
        this.coachAssignedPlanCollection = coachAssignedPlanCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (startTeamId != null ? startTeamId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StartTeam)) {
            return false;
        }
        StartTeam other = (StartTeam) object;
        if ((this.startTeamId == null && other.startTeamId != null) || (this.startTeamId != null && !this.startTeamId.equals(other.startTeamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.StartTeam[ startTeamId=" + startTeamId + " ]";
    }
    
}
