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
@Table(name = "membership_promotion")
@NamedQueries({
    @NamedQuery(name = "MembershipPromotion.findAll", query = "SELECT m FROM MembershipPromotion m"),
    @NamedQuery(name = "MembershipPromotion.findByMembershipPromoId", query = "SELECT m FROM MembershipPromotion m WHERE m.membershipPromoId = :membershipPromoId"),
    @NamedQuery(name = "MembershipPromotion.findByCode", query = "SELECT m FROM MembershipPromotion m WHERE m.code = :code"),
    @NamedQuery(name = "MembershipPromotion.findByPercentage", query = "SELECT m FROM MembershipPromotion m WHERE m.percentage = :percentage"),
    @NamedQuery(name = "MembershipPromotion.findByType", query = "SELECT m FROM MembershipPromotion m WHERE m.type = :type"),
    @NamedQuery(name = "MembershipPromotion.findByValue", query = "SELECT m FROM MembershipPromotion m WHERE m.value = :value"),
    @NamedQuery(name = "MembershipPromotion.findByStartDate", query = "SELECT m FROM MembershipPromotion m WHERE m.startDate = :startDate"),
    @NamedQuery(name = "MembershipPromotion.findByEndDate", query = "SELECT m FROM MembershipPromotion m WHERE m.endDate = :endDate"),
    @NamedQuery(name = "MembershipPromotion.findByUsedDate", query = "SELECT m FROM MembershipPromotion m WHERE m.usedDate = :usedDate"),
    @NamedQuery(name = "MembershipPromotion.findByPublishedDate", query = "SELECT m FROM MembershipPromotion m WHERE m.publishedDate = :publishedDate"),
    @NamedQuery(name = "MembershipPromotion.findByCreationDate", query = "SELECT m FROM MembershipPromotion m WHERE m.creationDate = :creationDate")})
public class MembershipPromotion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "membership_promo_id")
    private Integer membershipPromoId;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "percentage")
    private BigDecimal percentage;
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @Column(name = "value")
    private String value;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "used_date")
    @Temporal(TemporalType.DATE)
    private Date usedDate;
    @Column(name = "published_date")
    @Temporal(TemporalType.DATE)
    private Date publishedDate;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @OneToMany(mappedBy = "membershipPromoId")
    private Collection<Membership> membershipCollection;

    public MembershipPromotion() {
    }

    public MembershipPromotion(Integer membershipPromoId) {
        this.membershipPromoId = membershipPromoId;
    }

    public MembershipPromotion(Integer membershipPromoId, String code, String value) {
        this.membershipPromoId = membershipPromoId;
        this.code = code;
        this.value = value;
    }

    public Integer getMembershipPromoId() {
        return membershipPromoId;
    }

    public void setMembershipPromoId(Integer membershipPromoId) {
        this.membershipPromoId = membershipPromoId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Date getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Collection<Membership> getMembershipCollection() {
        return membershipCollection;
    }

    public void setMembershipCollection(Collection<Membership> membershipCollection) {
        this.membershipCollection = membershipCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (membershipPromoId != null ? membershipPromoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MembershipPromotion)) {
            return false;
        }
        MembershipPromotion other = (MembershipPromotion) object;
        if ((this.membershipPromoId == null && other.membershipPromoId != null) || (this.membershipPromoId != null && !this.membershipPromoId.equals(other.membershipPromoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.MembershipPromotion[ membershipPromoId=" + membershipPromoId + " ]";
    }
    
}
