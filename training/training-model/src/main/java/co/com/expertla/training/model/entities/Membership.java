/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "membership")
@NamedQueries({
    @NamedQuery(name = "Membership.findAll", query = "SELECT m FROM Membership m"),
    @NamedQuery(name = "Membership.findByMembershipId", query = "SELECT m FROM Membership m WHERE m.membershipId = :membershipId"),
    @NamedQuery(name = "Membership.findByName", query = "SELECT m FROM Membership m WHERE m.name = :name"),
    @NamedQuery(name = "Membership.findByDescription", query = "SELECT m FROM Membership m WHERE m.description = :description"),
    @NamedQuery(name = "Membership.findByInitialDate", query = "SELECT m FROM Membership m WHERE m.initialDate = :initialDate"),
    @NamedQuery(name = "Membership.findByCreationDate", query = "SELECT m FROM Membership m WHERE m.creationDate = :creationDate")})
public class Membership implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "membership_id")
    private Integer membershipId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "initial_date")
    @Temporal(TemporalType.DATE)
    private Date initialDate;
    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "membership_promo_id", referencedColumnName = "membership_promo_id")
    @ManyToOne
    private MembershipPromotion membershipPromoId;
    @JoinColumn(name = "state_id", referencedColumnName = "state_id")
    @ManyToOne
    private State stateId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "membershipId")
    private Collection<MembershipPrice> membershipPriceCollection;

    public Membership() {
    }

    public Membership(Integer membershipId) {
        this.membershipId = membershipId;
    }

    public Membership(Integer membershipId, String name, Date initialDate, Date creationDate) {
        this.membershipId = membershipId;
        this.name = name;
        this.initialDate = initialDate;
        this.creationDate = creationDate;
    }

    public Integer getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Integer membershipId) {
        this.membershipId = membershipId;
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

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public MembershipPromotion getMembershipPromoId() {
        return membershipPromoId;
    }

    public void setMembershipPromoId(MembershipPromotion membershipPromoId) {
        this.membershipPromoId = membershipPromoId;
    }

    public State getStateId() {
        return stateId;
    }

    public void setStateId(State stateId) {
        this.stateId = stateId;
    }

    public Collection<MembershipPrice> getMembershipPriceCollection() {
        return membershipPriceCollection;
    }

    public void setMembershipPriceCollection(Collection<MembershipPrice> membershipPriceCollection) {
        this.membershipPriceCollection = membershipPriceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (membershipId != null ? membershipId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Membership)) {
            return false;
        }
        Membership other = (Membership) object;
        if ((this.membershipId == null && other.membershipId != null) || (this.membershipId != null && !this.membershipId.equals(other.membershipId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Membership[ membershipId=" + membershipId + " ]";
    }
    
}
