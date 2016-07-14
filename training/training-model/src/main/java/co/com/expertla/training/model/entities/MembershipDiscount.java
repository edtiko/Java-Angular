/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "membership_discount")
@NamedQueries({
    @NamedQuery(name = "MembershipDiscount.findAll", query = "SELECT m FROM MembershipDiscount m"),
    @NamedQuery(name = "MembershipDiscount.findByMembershipDiscountId", query = "SELECT m FROM MembershipDiscount m WHERE m.membershipDiscountId = :membershipDiscountId"),
    @NamedQuery(name = "MembershipDiscount.findByDiscount", query = "SELECT m FROM MembershipDiscount m WHERE m.discount = :discount"),
    @NamedQuery(name = "MembershipDiscount.findByPercentage", query = "SELECT m FROM MembershipDiscount m WHERE m.percentage = :percentage"),
    @NamedQuery(name = "MembershipDiscount.findByStartDate", query = "SELECT m FROM MembershipDiscount m WHERE m.startDate = :startDate"),
    @NamedQuery(name = "MembershipDiscount.findByEndDate", query = "SELECT m FROM MembershipDiscount m WHERE m.endDate = :endDate"),
    @NamedQuery(name = "MembershipDiscount.findByCreationDate", query = "SELECT m FROM MembershipDiscount m WHERE m.creationDate = :creationDate")})
public class MembershipDiscount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "membership_discount_id")
    private Integer membershipDiscountId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "discount")
    private BigDecimal discount;
    @Column(name = "percentage")
    private BigDecimal percentage;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @OneToMany(mappedBy = "membershipDiscountId")
    private Collection<MembershipPrice> membershipPriceCollection;

    public MembershipDiscount() {
    }

    public MembershipDiscount(Integer membershipDiscountId) {
        this.membershipDiscountId = membershipDiscountId;
    }

    public MembershipDiscount(Integer membershipDiscountId, BigDecimal discount) {
        this.membershipDiscountId = membershipDiscountId;
        this.discount = discount;
    }

    public Integer getMembershipDiscountId() {
        return membershipDiscountId;
    }

    public void setMembershipDiscountId(Integer membershipDiscountId) {
        this.membershipDiscountId = membershipDiscountId;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
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

    public Collection<MembershipPrice> getMembershipPriceCollection() {
        return membershipPriceCollection;
    }

    public void setMembershipPriceCollection(Collection<MembershipPrice> membershipPriceCollection) {
        this.membershipPriceCollection = membershipPriceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (membershipDiscountId != null ? membershipDiscountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MembershipDiscount)) {
            return false;
        }
        MembershipDiscount other = (MembershipDiscount) object;
        if ((this.membershipDiscountId == null && other.membershipDiscountId != null) || (this.membershipDiscountId != null && !this.membershipDiscountId.equals(other.membershipDiscountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.MembershipDiscount[ membershipDiscountId=" + membershipDiscountId + " ]";
    }
    
}
