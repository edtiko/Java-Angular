/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "physiological_capacity")
@NamedQueries({
    @NamedQuery(name = "PhysiologicalCapacity.findAll", query = "SELECT p FROM PhysiologicalCapacity p"),
    @NamedQuery(name = "PhysiologicalCapacity.findByPhysiologicalCapacityId", query = "SELECT p FROM PhysiologicalCapacity p WHERE p.physiologicalCapacityId = :physiologicalCapacityId"),
    @NamedQuery(name = "PhysiologicalCapacity.findByName", query = "SELECT p FROM PhysiologicalCapacity p WHERE p.name = :name")})
public class PhysiologicalCapacity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "physiological_capacity_id")
    private Integer physiologicalCapacityId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "physiologicalCapacityId")
    private Collection<Activity> activityCollection;
    @OneToMany(mappedBy = "physiologicalCapacityId")
    private Collection<Dcf> dcfCollection;

    public PhysiologicalCapacity() {
    }

    public PhysiologicalCapacity(Integer physiologicalCapacityId) {
        this.physiologicalCapacityId = physiologicalCapacityId;
    }

    public PhysiologicalCapacity(Integer physiologicalCapacityId, String name) {
        this.physiologicalCapacityId = physiologicalCapacityId;
        this.name = name;
    }

    public Integer getPhysiologicalCapacityId() {
        return physiologicalCapacityId;
    }

    public void setPhysiologicalCapacityId(Integer physiologicalCapacityId) {
        this.physiologicalCapacityId = physiologicalCapacityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Activity> getActivityCollection() {
        return activityCollection;
    }

    public void setActivityCollection(Collection<Activity> activityCollection) {
        this.activityCollection = activityCollection;
    }

    public Collection<Dcf> getDcfCollection() {
        return dcfCollection;
    }

    public void setDcfCollection(Collection<Dcf> dcfCollection) {
        this.dcfCollection = dcfCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (physiologicalCapacityId != null ? physiologicalCapacityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PhysiologicalCapacity)) {
            return false;
        }
        PhysiologicalCapacity other = (PhysiologicalCapacity) object;
        if ((this.physiologicalCapacityId == null && other.physiologicalCapacityId != null) || (this.physiologicalCapacityId != null && !this.physiologicalCapacityId.equals(other.physiologicalCapacityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.PhysiologicalCapacity[ physiologicalCapacityId=" + physiologicalCapacityId + " ]";
    }
    
}
