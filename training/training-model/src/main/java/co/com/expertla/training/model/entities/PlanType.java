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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "plan_type")
@NamedQueries({
    @NamedQuery(name = "PlanType.findAll", query = "SELECT p FROM PlanType p"),
    @NamedQuery(name = "PlanType.findByPlanTypeId", query = "SELECT p FROM PlanType p WHERE p.planTypeId = :planTypeId"),
    @NamedQuery(name = "PlanType.findByName", query = "SELECT p FROM PlanType p WHERE p.name = :name"),
    @NamedQuery(name = "PlanType.findByDescription", query = "SELECT p FROM PlanType p WHERE p.description = :description"),
    @NamedQuery(name = "PlanType.findByCreationDate", query = "SELECT p FROM PlanType p WHERE p.creationDate = :creationDate"),
    @NamedQuery(name = "PlanType.findByUserCreate", query = "SELECT p FROM PlanType p WHERE p.userCreate = :userCreate")})
public class PlanType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "plan_type_id")
    private Integer planTypeId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column(name = "user_create")
    private Integer userCreate;
    @OneToMany(mappedBy = "planTypeId")
    private Collection<TrainingPlan> trainingPlanCollection;

    public PlanType() {
    }

    public PlanType(Integer planTypeId) {
        this.planTypeId = planTypeId;
    }

    public PlanType(Integer planTypeId, String name, Date creationDate) {
        this.planTypeId = planTypeId;
        this.name = name;
        this.creationDate = creationDate;
    }

    public Integer getPlanTypeId() {
        return planTypeId;
    }

    public void setPlanTypeId(Integer planTypeId) {
        this.planTypeId = planTypeId;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(Integer userCreate) {
        this.userCreate = userCreate;
    }

    public Collection<TrainingPlan> getTrainingPlanCollection() {
        return trainingPlanCollection;
    }

    public void setTrainingPlanCollection(Collection<TrainingPlan> trainingPlanCollection) {
        this.trainingPlanCollection = trainingPlanCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planTypeId != null ? planTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanType)) {
            return false;
        }
        PlanType other = (PlanType) object;
        if ((this.planTypeId == null && other.planTypeId != null) || (this.planTypeId != null && !this.planTypeId.equals(other.planTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.PlanType[ planTypeId=" + planTypeId + " ]";
    }
    
}
