package co.expertic.training.model.entities;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "plan_message")
@NamedQueries({
    @NamedQuery(name = "PlanMessage.findAll", query = "SELECT p FROM PlanMessage p"),
    @NamedQuery(name = "PlanMessage.findByPlanMessageId", query = "SELECT p FROM PlanMessage p WHERE p.planMessageId = :planMessageId"),
    @NamedQuery(name = "PlanMessage.findByMessage", query = "SELECT p FROM PlanMessage p WHERE p.message = :message"),
    @NamedQuery(name = "PlanMessage.findByStateId", query = "SELECT p FROM PlanMessage p WHERE p.stateId = :stateId"),
    @NamedQuery(name = "PlanMessage.findByCreationDate", query = "SELECT p FROM PlanMessage p WHERE p.creationDate = :creationDate")})
public class PlanMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "plan_message_plan_message_id_seq", sequenceName = "plan_message_plan_message_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_message_plan_message_id_seq")
    @Column(name = "plan_message_id")
    private Integer planMessageId;
    @Column(name = "message")
    private String message;
    @Column(name = "state_id")
    private Integer stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @JoinColumn(name = "coach_assigned_plan_id", referencedColumnName = "coach_assigned_plan_id")
    @ManyToOne(optional = false)
    private CoachAssignedPlan coachAssignedPlanId;
    @JoinColumn(name = "coach_ext_athlete_id", referencedColumnName = "coach_ext_athlete_id")
    @ManyToOne(optional = false)
    private CoachExtAthlete coachExtAthleteId;
    @JoinColumn(name = "message_user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User messageUserId;
    @JoinColumn(name = "receiving_user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User receivingUserId;
    @Column(name = "readed")
    private Boolean readed;
    @Column(name = "to_star")
    private Boolean toStar;

    public PlanMessage() {
    }

    public PlanMessage(Integer planMessageId) {
        this.planMessageId = planMessageId;
    }

    public Integer getPlanMessageId() {
        return planMessageId;
    }

    public void setPlanMessageId(Integer planMessageId) {
        this.planMessageId = planMessageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public CoachAssignedPlan getCoachAssignedPlanId() {
        return coachAssignedPlanId;
    }

    public void setCoachAssignedPlanId(CoachAssignedPlan coachAssignedPlanId) {
        this.coachAssignedPlanId = coachAssignedPlanId;
    }

    public User getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(User messageUserId) {
        this.messageUserId = messageUserId;
    }

    public CoachExtAthlete getCoachExtAthleteId() {
        return coachExtAthleteId;
    }

    public void setCoachExtAthleteId(CoachExtAthlete coachExtAthleteId) {
        this.coachExtAthleteId = coachExtAthleteId;
    }
    
    public Boolean getToStar() {
        return toStar;
    }

    public void setToStar(Boolean toStar) {
        this.toStar = toStar;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planMessageId != null ? planMessageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanMessage)) {
            return false;
        }
        PlanMessage other = (PlanMessage) object;
        if ((this.planMessageId == null && other.planMessageId != null) || (this.planMessageId != null && !this.planMessageId.equals(other.planMessageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.PlanMessage[ planMessageId=" + planMessageId + " ]";
    }

    public User getReceivingUserId() {
        return receivingUserId;
    }

    public void setReceivingUserId(User receivingUserId) {
        this.receivingUserId = receivingUserId;
    }

    public Boolean getReaded() {
        return readed;
    }

    public void setReaded(Boolean readed) {
        this.readed = readed;
    }
    
}
