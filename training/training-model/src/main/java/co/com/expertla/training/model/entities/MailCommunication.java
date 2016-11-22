/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
* MailCommunication <br>
* Creation Date : <br>
* date 12/09/2016 <br>
* @author Angela Ramírez
**/
@Entity
@Table(name = "mail_communication")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MailCommunication.findAll", query = "SELECT m FROM MailCommunication m"),
    @NamedQuery(name = "MailCommunication.findByMailCommunicationId", query = "SELECT m FROM MailCommunication m WHERE m.mailCommunicationId = :mailCommunicationId"),
    @NamedQuery(name = "MailCommunication.findBySubject", query = "SELECT m FROM MailCommunication m WHERE m.subject = :subject"),
    @NamedQuery(name = "MailCommunication.findByMessage", query = "SELECT m FROM MailCommunication m WHERE m.message = :message"),
    @NamedQuery(name = "MailCommunication.findByStateId", query = "SELECT m FROM MailCommunication m WHERE m.stateId = :stateId"),
    @NamedQuery(name = "MailCommunication.findByCreationDate", query = "SELECT m FROM MailCommunication m WHERE m.creationDate = :creationDate"),
    @NamedQuery(name = "MailCommunication.findByRead", query = "SELECT m FROM MailCommunication m WHERE m.read = :read")})
public class MailCommunication implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "mail_communication_seq", sequenceName = "mail_communication_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_communication_seq")
    @Basic(optional = false)
    @Column(name = "mail_communication_id")
    private Integer mailCommunicationId;
    @Column(name = "subject")
    private String subject;
    @Column(name = "message")
    private String message;
    @Column(name = "state_id")
    private Integer stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column(name = "read")
    private Boolean read;
    @JoinColumn(name = "receiving_user", referencedColumnName = "user_id")
    @ManyToOne
    private User receivingUser;
    @JoinColumn(name = "sending_user", referencedColumnName = "user_id")
    @ManyToOne
    private User sendingUser;
    @JoinColumn(name = "coach_assigned_plan_id", referencedColumnName = "coach_assigned_plan_id")
    @ManyToOne(optional = false)
    private CoachAssignedPlan coachAssignedPlanId;
    @JoinColumn(name = "coach_ext_athlete_id", referencedColumnName = "coach_ext_athlete_id")
    @ManyToOne(optional = false)
    private CoachExtAthlete coachExtAthleteId;
    @Column(name = "to_star")
    private Boolean toStar;

    public MailCommunication() {
    }

    public MailCommunication(Integer mailCommunicationId) {
        this.mailCommunicationId = mailCommunicationId;
    }

    public Integer getMailCommunicationId() {
        return mailCommunicationId;
    }

    public void setMailCommunicationId(Integer mailCommunicationId) {
        this.mailCommunicationId = mailCommunicationId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public User getReceivingUser() {
        return receivingUser;
    }

    public void setReceivingUser(User receivingUser) {
        this.receivingUser = receivingUser;
    }

    public User getSendingUser() {
        return sendingUser;
    }

    public void setSendingUser(User sendingUser) {
        this.sendingUser = sendingUser;
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

    public Boolean getToStar() {
        return toStar;
    }

    public void setToStar(Boolean toStar) {
        this.toStar = toStar;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mailCommunicationId != null ? mailCommunicationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MailCommunication)) {
            return false;
        }
        MailCommunication other = (MailCommunication) object;
        if ((this.mailCommunicationId == null && other.mailCommunicationId != null) || (this.mailCommunicationId != null && !this.mailCommunicationId.equals(other.mailCommunicationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.MailCommunication[ mailCommunicationId=" + mailCommunicationId + " ]";
    }

}
