/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "membership_price")
@NamedQueries({
    @NamedQuery(name = "MembershipPrice.findAll", query = "SELECT m FROM MembershipPrice m"),
    @NamedQuery(name = "MembershipPrice.findByMembershipPriceId", query = "SELECT m FROM MembershipPrice m WHERE m.membershipPriceId = :membershipPriceId"),
    @NamedQuery(name = "MembershipPrice.findByPrice", query = "SELECT m FROM MembershipPrice m WHERE m.price = :price"),
    @NamedQuery(name = "MembershipPrice.findByDuration", query = "SELECT m FROM MembershipPrice m WHERE m.duration = :duration"),
    @NamedQuery(name = "MembershipPrice.findByStartDate", query = "SELECT m FROM MembershipPrice m WHERE m.startDate = :startDate"),
    @NamedQuery(name = "MembershipPrice.findByEndDate", query = "SELECT m FROM MembershipPrice m WHERE m.endDate = :endDate"),
    @NamedQuery(name = "MembershipPrice.findByCreationDate", query = "SELECT m FROM MembershipPrice m WHERE m.creationDate = :creationDate")})
public class MembershipPrice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "membership_price_id")
    private Integer membershipPriceId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "duration")
    private BigDecimal duration;
    @Basic(optional = false)
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "membership_id", referencedColumnName = "membership_id")
    @ManyToOne(optional = false)
    private Membership membershipId;
    @JoinColumn(name = "membership_discount_id", referencedColumnName = "membership_discount_id")
    @ManyToOne
    private MembershipDiscount membershipDiscountId;

    public MembershipPrice() {
    }

    public MembershipPrice(Integer membershipPriceId) {
        this.membershipPriceId = membershipPriceId;
    }

    public MembershipPrice(Integer membershipPriceId, BigDecimal price, Date startDate) {
        this.membershipPriceId = membershipPriceId;
        this.price = price;
        this.startDate = startDate;
    }

    public Integer getMembershipPriceId() {
        return membershipPriceId;
    }

    public void setMembershipPriceId(Integer membershipPriceId) {
        this.membershipPriceId = membershipPriceId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Membership getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Membership membershipId) {
        this.membershipId = membershipId;
    }

    public MembershipDiscount getMembershipDiscountId() {
        return membershipDiscountId;
    }

    public void setMembershipDiscountId(MembershipDiscount membershipDiscountId) {
        this.membershipDiscountId = membershipDiscountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (membershipPriceId != null ? membershipPriceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MembershipPrice)) {
            return false;
        }
        MembershipPrice other = (MembershipPrice) object;
        if ((this.membershipPriceId == null && other.membershipPriceId != null) || (this.membershipPriceId != null && !this.membershipPriceId.equals(other.membershipPriceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.MembershipPrice[ membershipPriceId=" + membershipPriceId + " ]";
    }
    
}
