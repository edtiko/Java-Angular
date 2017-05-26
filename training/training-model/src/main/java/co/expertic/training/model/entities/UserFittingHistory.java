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
@Table(name = "user_fitting_history")
@NamedQueries({
    @NamedQuery(name = "UserFittingHistory.findAll", query = "SELECT u FROM UserFittingHistory u")})
public class UserFittingHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "user_fitting_history_user_fitting_history_id_seq", sequenceName = "user_fitting_history_user_fitting_history_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_fitting_history_user_fitting_history_id_seq")
    @Column(name = "user_fitting_history_id")
    private Integer userFittingHistoryId;
    @Column(name = "video_name")
    private String videoName;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;
    @JoinColumn(name = "state_id", referencedColumnName = "state_id")
    @ManyToOne(optional = false)
    private State stateId;
    @JoinColumn(name = "user_fitting_id", referencedColumnName = "user_fitting_id")
    @ManyToOne(optional = false)
    private UserFitting userFittingId;

    public UserFittingHistory() {
    }

    public UserFittingHistory(Integer userFittingHistoryId) {
        this.userFittingHistoryId = userFittingHistoryId;
    }

    public UserFittingHistory(Integer userFittingHistoryId, Date creationDate) {
        this.userFittingHistoryId = userFittingHistoryId;
        this.creationDate = creationDate;
    }

    public Integer getUserFittingHistoryId() {
        return userFittingHistoryId;
    }

    public void setUserFittingHistoryId(Integer userFittingHistoryId) {
        this.userFittingHistoryId = userFittingHistoryId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
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

    public UserFitting getUserFittingId() {
        return userFittingId;
    }

    public void setUserFittingId(UserFitting userFittingId) {
        this.userFittingId = userFittingId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userFittingHistoryId != null ? userFittingHistoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserFittingHistory)) {
            return false;
        }
        UserFittingHistory other = (UserFittingHistory) object;
        if ((this.userFittingHistoryId == null && other.userFittingHistoryId != null) || (this.userFittingHistoryId != null && !this.userFittingHistoryId.equals(other.userFittingHistoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.UserFittingHistory[ userFittingHistoryId=" + userFittingHistoryId + " ]";
    }
    
}
