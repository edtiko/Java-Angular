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
@Table(name = "coach_ext_athlete")
@NamedQueries({
    @NamedQuery(name = "CoachExtAthlete.findAll", query = "SELECT c FROM CoachExtAthlete c"),
    @NamedQuery(name = "CoachExtAthlete.findByCoachExtAthleteId", query = "SELECT c FROM CoachExtAthlete c WHERE c.coachExtAthleteId = :coachExtAthleteId"),
    @NamedQuery(name = "CoachExtAthlete.findByCreationDate", query = "SELECT c FROM CoachExtAthlete c WHERE c.creationDate = :creationDate")})
public class CoachExtAthlete implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "coach_ext_athlete_coach_ext_athlete_id_seq", sequenceName = "coach_ext_athlete_coach_ext_athlete_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coach_ext_athlete_coach_ext_athlete_id_seq")
    @Column(name = "coach_ext_athlete_id")
    private Integer coachExtAthleteId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @JoinColumn(name = "state_id", referencedColumnName = "state_id")
    @ManyToOne(optional = false)
    private State stateId;
    @JoinColumn(name = "training_plan_user_id", referencedColumnName = "training_plan_user_id")
    @ManyToOne(optional = false)
    private TrainingPlanUser trainingPlanUserId;
    @JoinColumn(name = "user_training_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User userTrainingId;

    public CoachExtAthlete() {
    }

    public CoachExtAthlete(Integer coachExtAthleteId) {
        this.coachExtAthleteId = coachExtAthleteId;
    }

    public Integer getCoachExtAthleteId() {
        return coachExtAthleteId;
    }

    public void setCoachExtAthleteId(Integer coachExtAthleteId) {
        this.coachExtAthleteId = coachExtAthleteId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public State getStateId() {
        return stateId;
    }

    public void setStateId(State stateId) {
        this.stateId = stateId;
    }

    public TrainingPlanUser getTrainingPlanUserId() {
        return trainingPlanUserId;
    }

    public void setTrainingPlanUserId(TrainingPlanUser trainingPlanUserId) {
        this.trainingPlanUserId = trainingPlanUserId;
    }

    public User getUserTrainingId() {
        return userTrainingId;
    }

    public void setUserTrainingId(User userTrainingId) {
        this.userTrainingId = userTrainingId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coachExtAthleteId != null ? coachExtAthleteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoachExtAthlete)) {
            return false;
        }
        CoachExtAthlete other = (CoachExtAthlete) object;
        if ((this.coachExtAthleteId == null && other.coachExtAthleteId != null) || (this.coachExtAthleteId != null && !this.coachExtAthleteId.equals(other.coachExtAthleteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.CoachExtAthlete[ coachExtAthleteId=" + coachExtAthleteId + " ]";
    }
    
}
