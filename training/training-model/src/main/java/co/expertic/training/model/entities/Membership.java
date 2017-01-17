/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @SequenceGenerator(name = "membership_membership_id_seq", sequenceName = "membership_membership_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membership_membership_id_seq")
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
        @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private Date lastUpdate;
    @Column(name = "user_create")
    private Integer userCreate;
    @Column(name = "user_update")
    private Integer userUpdate;
    @JoinColumn(name = "membership_promo_id", referencedColumnName = "membership_promo_id")
    @ManyToOne
    private MembershipPromotion membershipPromoId;
    @Column(name = "state_id")
    private Short stateId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "membershipId")
    private Collection<MembershipPrice> membershipPriceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "membershipId")
    private Collection<TrainingPlanCharact> trainingPlanCharactCollection;

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

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(Integer userCreate) {
        this.userCreate = userCreate;
    }

    public Integer getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(Integer userUpdate) {
        this.userUpdate = userUpdate;
    }
    
    
    @JsonIgnore
    public Collection<MembershipPrice> getMembershipPriceCollection() {
        return membershipPriceCollection;
    }

    public void setMembershipPriceCollection(Collection<MembershipPrice> membershipPriceCollection) {
        this.membershipPriceCollection = membershipPriceCollection;
    }
    @JsonIgnore
    public Collection<TrainingPlanCharact> getTrainingPlanCharactCollection() {
        return trainingPlanCharactCollection;
    }

    public void setTrainingPlanCharactCollection(Collection<TrainingPlanCharact> trainingPlanCharactCollection) {
        this.trainingPlanCharactCollection = trainingPlanCharactCollection;
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
