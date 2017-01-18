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
@Table(name = "payment_method")
@NamedQueries({
    @NamedQuery(name = "PaymentMethod.findAll", query = "SELECT p FROM PaymentMethod p"),
    @NamedQuery(name = "PaymentMethod.findByPaymentMetodId", query = "SELECT p FROM PaymentMethod p WHERE p.paymentMetodId = :paymentMetodId"),
    @NamedQuery(name = "PaymentMethod.findByName", query = "SELECT p FROM PaymentMethod p WHERE p.name = :name"),
    @NamedQuery(name = "PaymentMethod.findByDescription", query = "SELECT p FROM PaymentMethod p WHERE p.description = :description"),
    @NamedQuery(name = "PaymentMethod.findByCardNumber", query = "SELECT p FROM PaymentMethod p WHERE p.cardNumber = :cardNumber"),
    @NamedQuery(name = "PaymentMethod.findByExpirationDate", query = "SELECT p FROM PaymentMethod p WHERE p.expirationDate = :expirationDate"),
    @NamedQuery(name = "PaymentMethod.findByCvvCode", query = "SELECT p FROM PaymentMethod p WHERE p.cvvCode = :cvvCode"),
    @NamedQuery(name = "PaymentMethod.findByFranchise", query = "SELECT p FROM PaymentMethod p WHERE p.franchise = :franchise"),
    @NamedQuery(name = "PaymentMethod.findByCreationDate", query = "SELECT p FROM PaymentMethod p WHERE p.creationDate = :creationDate")})
public class PaymentMethod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "payment_metod_id")
    private Integer paymentMetodId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "expiration_date")
    @Temporal(TemporalType.DATE)
    private Date expirationDate;
    @Column(name = "cvv_code")
    private String cvvCode;
    @Column(name = "franchise")
    private String franchise;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    @ManyToOne(optional = false)
    private Country countryId;

    public PaymentMethod() {
    }

    public PaymentMethod(Integer paymentMetodId) {
        this.paymentMetodId = paymentMetodId;
    }

    public PaymentMethod(Integer paymentMetodId, String name) {
        this.paymentMetodId = paymentMetodId;
        this.name = name;
    }

    public Integer getPaymentMetodId() {
        return paymentMetodId;
    }

    public void setPaymentMetodId(Integer paymentMetodId) {
        this.paymentMetodId = paymentMetodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvvCode() {
        return cvvCode;
    }

    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Country getCountryId() {
        return countryId;
    }

    public void setCountryId(Country countryId) {
        this.countryId = countryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentMetodId != null ? paymentMetodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentMethod)) {
            return false;
        }
        PaymentMethod other = (PaymentMethod) object;
        if ((this.paymentMetodId == null && other.paymentMetodId != null) || (this.paymentMetodId != null && !this.paymentMetodId.equals(other.paymentMetodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.PaymentMethod[ paymentMetodId=" + paymentMetodId + " ]";
    }
    
}
