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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "plan_audio")
@NamedQueries({
    @NamedQuery(name = "PlanAudio.findAll", query = "SELECT p FROM PlanAudio p"),
    @NamedQuery(name = "PlanAudio.findByPlanAudioId", query = "SELECT p FROM PlanAudio p WHERE p.planAudioId = :planAudioId"),
    @NamedQuery(name = "PlanAudio.findByName", query = "SELECT p FROM PlanAudio p WHERE p.name = :name"),
    @NamedQuery(name = "PlanAudio.findByReaded", query = "SELECT p FROM PlanAudio p WHERE p.readed = :readed"),
    @NamedQuery(name = "PlanAudio.findByCreationDate", query = "SELECT p FROM PlanAudio p WHERE p.creationDate = :creationDate")})
public class PlanAudio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "plan_audio_plan_audio_id_seq", sequenceName = "plan_audio_plan_audio_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_audio_plan_audio_id_seq")
    @Column(name = "plan_audio_id")
    private Integer planAudioId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "readed")
    private Boolean readed;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @JoinColumn(name = "coach_assigned_plan_id", referencedColumnName = "coach_assigned_plan_id")
    @ManyToOne
    private CoachAssignedPlan coachAssignedPlanId;
    @JoinColumn(name = "coach_ext_athlete_id", referencedColumnName = "coach_ext_athlete_id")
    @ManyToOne
    private CoachExtAthlete coachExtAthleteId;
    @JoinColumn(name = "from_user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User fromUserId;
    @JoinColumn(name = "to_user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User toUserId;
    @Column(name = "to_star")
    private Boolean toStar;
    @Column(name = "state_id")
    private Short stateId;

    public PlanAudio() {
    }

    public PlanAudio(Integer planAudioId) {
        this.planAudioId = planAudioId;
    }

    public PlanAudio(Integer planAudioId, String name) {
        this.planAudioId = planAudioId;
        this.name = name;
    }

    public Integer getPlanAudioId() {
        return planAudioId;
    }

    public void setPlanAudioId(Integer planAudioId) {
        this.planAudioId = planAudioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getReaded() {
        return readed;
    }

    public void setReaded(Boolean readed) {
        this.readed = readed;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public CoachAssignedPlan getCoachAssignedPlanId() {
        return coachAssignedPlanId;
    }

    public void setCoachAssignedPlanId(CoachAssignedPlan coachAssignedPlanId) {
        this.coachAssignedPlanId = coachAssignedPlanId;
    }

    public CoachExtAthlete getCoachExtAthleteId() {
        return coachExtAthleteId;
    }

    public void setCoachExtAthleteId(CoachExtAthlete coachExtAthleteId) {
        this.coachExtAthleteId = coachExtAthleteId;
    }

    public User getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(User fromUserId) {
        this.fromUserId = fromUserId;
    }

    public User getToUserId() {
        return toUserId;
    }

    public void setToUserId(User toUserId) {
        this.toUserId = toUserId;
    }

    public Boolean getToStar() {
        return toStar;
    }

    public void setToStar(Boolean toStar) {
        this.toStar = toStar;
    }

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planAudioId != null ? planAudioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanAudio)) {
            return false;
        }
        PlanAudio other = (PlanAudio) object;
        if ((this.planAudioId == null && other.planAudioId != null) || (this.planAudioId != null && !this.planAudioId.equals(other.planAudioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.PlanAudio[ planAudioId=" + planAudioId + " ]";
    }

}
