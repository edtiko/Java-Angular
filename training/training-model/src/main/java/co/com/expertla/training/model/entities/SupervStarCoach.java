package co.com.expertla.training.model.entities;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "superv_star_coach")
public class SupervStarCoach implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "superv_star_coach_id")
    private Integer supervStarCoachId;
    @Column(name = "state_id")
    private Short stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "star_team_id", referencedColumnName = "star_team_id")
    @ManyToOne(optional = false)
    private StarTeam starTeamId;
    @JoinColumn(name = "supervisor_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User supervisorId;

    public SupervStarCoach() {
    }

    public SupervStarCoach(Integer supervStarCoachId) {
        this.supervStarCoachId = supervStarCoachId;
    }

    public Integer getSupervStarCoachId() {
        return supervStarCoachId;
    }

    public void setSupervStarCoachId(Integer supervStarCoachId) {
        this.supervStarCoachId = supervStarCoachId;
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

    public void setStarTeamId(StarTeam starTeamId) {
        this.starTeamId = starTeamId;
    }

    public User getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(User supervisorId) {
        this.supervisorId = supervisorId;
    } 
    
}
