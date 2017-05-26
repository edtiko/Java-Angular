/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
    @SequenceGenerator(name = "user_fitting_user_fitting_id_seq", sequenceName = "user_fitting_user_fitting_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_fitting_user_fitting_id_seq")
    @Column(name = "user_fitting_id")
    private Integer userFittingId;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "state_id")
    private Short stateId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User userId;
    @Basic(optional = false)
    @Column(name = "order_id")
    private int orderId;
    @Basic(optional = false)
    @Column(name = "order_item_id")
    private int orderItemId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userFittingId")
    private Collection<UserFittingHistory> userFittingHistoryCollection;

    public UserFitting() {
    }

    public UserFitting(Integer userFittingId) {
        this.userFittingId = userFittingId;
    }

    public UserFitting(Integer userFittingId, Date creationDate) {
        this.userFittingId = userFittingId;
        this.creationDate = creationDate;
    }

    public Integer getUserFittingId() {
        return userFittingId;
    }

    public void setUserFittingId(Integer userFittingId) {
        this.userFittingId = userFittingId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
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
