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
@Table(name = "coach_assigned_plan")
@NamedQueries({
    @NamedQuery(name = "CoachAssignedPlan.findAll", query = "SELECT c FROM CoachAssignedPlan c"),
    @NamedQuery(name = "CoachAssignedPlan.findByCoachAssignedPlanId", query = "SELECT c FROM CoachAssignedPlan c WHERE c.coachAssignedPlanId = :coachAssignedPlanId"),
    @NamedQuery(name = "CoachAssignedPlan.findByStateId", query = "SELECT c FROM CoachAssignedPlan c WHERE c.stateId = :stateId"),
    @NamedQuery(name = "CoachAssignedPlan.findByCreationDate", query = "SELECT c FROM CoachAssignedPlan c WHERE c.creationDate = :creationDate")})
public class CoachAssignedPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "coach_assigned_plan_id")
    private Integer coachAssignedPlanId;
    @Column(name = "state_id")
    private Integer stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "start_team_id", referencedColumnName = "start_team_id")
    @ManyToOne(optional = false)
    private StartTeam startTeamId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coachAssignedPlanId")
    private Collection<PlanMessage> planMessageCollection;

    public CoachAssignedPlan() {
    }

    public CoachAssignedPlan(Integer coachAssignedPlanId) {
        this.coachAssignedPlanId = coachAssignedPlanId;
    }

    public Integer getCoachAssignedPlanId() {
        return coachAssignedPlanId;
    }

    public void setCoachAssignedPlanId(Integer coachAssignedPlanId) {
        this.coachAssignedPlanId = coachAssignedPlanId;
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

    public StartTeam getStartTeamId() {
        return startTeamId;
    }

    public void setStartTeamId(StartTeam startTeamId) {
        this.startTeamId = startTeamId;
    }

    public Collection<PlanMessage> getPlanMessageCollection() {
        return planMessageCollection;
    }

    public void setPlanMessageCollection(Collection<PlanMessage> planMessageCollection) {
        this.planMessageCollection = planMessageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coachAssignedPlanId != null ? coachAssignedPlanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoachAssignedPlan)) {
            return false;
        }
        CoachAssignedPlan other = (CoachAssignedPlan) object;
        if ((this.coachAssignedPlanId == null && other.coachAssignedPlanId != null) || (this.coachAssignedPlanId != null && !this.coachAssignedPlanId.equals(other.coachAssignedPlanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.CoachAssignedPlan[ coachAssignedPlanId=" + coachAssignedPlanId + " ]";
    }
    
}
