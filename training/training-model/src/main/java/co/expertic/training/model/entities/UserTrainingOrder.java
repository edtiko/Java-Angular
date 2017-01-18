/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andres Lopez
 */
@Entity
@Table(name = "user_training_order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserTrainingOrder.findAll", query = "SELECT u FROM UserTrainingOrder u"),
    @NamedQuery(name = "UserTrainingOrder.findByUserTrainingOrderId", query = "SELECT u FROM UserTrainingOrder u WHERE u.userTrainingOrderId = :userTrainingOrderId"),
    @NamedQuery(name = "UserTrainingOrder.findByOrderId", query = "SELECT u FROM UserTrainingOrder u WHERE u.orderId = :orderId"),
    @NamedQuery(name = "UserTrainingOrder.findByOrderItemId", query = "SELECT u FROM UserTrainingOrder u WHERE u.orderItemId = :orderItemId"),
    @NamedQuery(name = "UserTrainingOrder.findByStatus", query = "SELECT u FROM UserTrainingOrder u WHERE u.status = :status")})
public class UserTrainingOrder implements Serializable {

    @SequenceGenerator(name = "user_training_order_user_training_order_id_seq", sequenceName = "user_training_order_user_training_order_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_training_order_user_training_order_id_seq")
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_training_order_id")
    private Integer userTrainingOrderId;
    @Basic(optional = false)
    @Column(name = "order_id")
    private int orderId;
    @Column(name = "user_id")
    private Integer userId;
    @Basic(optional = false)
    @Column(name = "order_item_id")
    private int orderItemId;
    @Column(name = "status")
    private String status;
    

    public UserTrainingOrder() {
    }

    public UserTrainingOrder(Integer userTrainingOrderId) {
        this.userTrainingOrderId = userTrainingOrderId;
    }

    public UserTrainingOrder(Integer userTrainingOrderId, int orderId, int orderItemId) {
        this.userTrainingOrderId = userTrainingOrderId;
        this.orderId = orderId;
        this.orderItemId = orderItemId;
    }

    public Integer getUserTrainingOrderId() {
        return userTrainingOrderId;
    }

    public void setUserTrainingOrderId(Integer userTrainingOrderId) {
        this.userTrainingOrderId = userTrainingOrderId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userTrainingOrderId != null ? userTrainingOrderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserTrainingOrder)) {
            return false;
        }
        UserTrainingOrder other = (UserTrainingOrder) object;
        if ((this.userTrainingOrderId == null && other.userTrainingOrderId != null) || (this.userTrainingOrderId != null && !this.userTrainingOrderId.equals(other.userTrainingOrderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.UserTrainingOrder[ userTrainingOrderId=" + userTrainingOrderId + " ]";
    }
    
}
