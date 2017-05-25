/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import java.io.Serializable;
import java.util.Collection;
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

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "user_fitting")
@NamedQueries({
    @NamedQuery(name = "UserFitting.findAll", query = "SELECT u FROM UserFitting u")})
public class UserFitting implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_fitting_id")
    private Integer userFittingId;
    @Basic(optional = false)
    @Column(name = "creation_date")
    private int creationDate;
    @Column(name = "state_id")
    private Short stateId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userFittingId")
    private Collection<UserFittingHistory> userFittingHistoryCollection;

    public UserFitting() {
    }

    public UserFitting(Integer userFittingId) {
        this.userFittingId = userFittingId;
    }

    public UserFitting(Integer userFittingId, int creationDate) {
        this.userFittingId = userFittingId;
        this.creationDate = creationDate;
    }

    public Integer getUserFittingId() {
        return userFittingId;
    }

    public void setUserFittingId(Integer userFittingId) {
        this.userFittingId = userFittingId;
    }

    public int getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(int creationDate) {
        this.creationDate = creationDate;
    }

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Collection<UserFittingHistory> getUserFittingHistoryCollection() {
        return userFittingHistoryCollection;
    }

    public void setUserFittingHistoryCollection(Collection<UserFittingHistory> userFittingHistoryCollection) {
        this.userFittingHistoryCollection = userFittingHistoryCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userFittingId != null ? userFittingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserFitting)) {
            return false;
        }
        UserFitting other = (UserFitting) object;
        if ((this.userFittingId == null && other.userFittingId != null) || (this.userFittingId != null && !this.userFittingId.equals(other.userFittingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.UserFitting[ userFittingId=" + userFittingId + " ]";
    }
    
}
