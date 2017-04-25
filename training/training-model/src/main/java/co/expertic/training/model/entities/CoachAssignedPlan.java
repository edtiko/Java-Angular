/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    private Short stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "star_team_id", referencedColumnName = "star_team_id")
    @ManyToOne(optional = false)
    private StarTeam starTeamId;
    @JoinColumn(name = "training_plan_user_id", referencedColumnName = "training_plan_user_id")
    @ManyToOne(optional = false)
    private TrainingPlanUser trainingPlanUserId;
    @Column(name = "star_manage_messages")
    private Boolean starManageMessages;

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

    public StarTeam getStarTeamId() {
        return starTeamId;
    }

    public void setStarTeamId(StarTeam startTeamId) {
        this.starTeamId = startTeamId;
    }

    public TrainingPlanUser getTrainingPlanUserId() {
        return trainingPlanUserId;
    }

    public void setTrainingPlanUserId(TrainingPlanUser trainingPlanUserId) {
        this.trainingPlanUserId = trainingPlanUserId;
    }

    public Boolean getStarManageMessages() {
        return starManageMessages;
    }

    public void setStarManageMessages(Boolean starManageMessages) {
        this.starManageMessages = starManageMessages;
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
