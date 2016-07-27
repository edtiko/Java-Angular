/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "dcf")
@NamedQueries({
    @NamedQuery(name = "Dcf.findAll", query = "SELECT d FROM Dcf d"),
    @NamedQuery(name = "Dcf.findByDcfId", query = "SELECT d FROM Dcf d WHERE d.dcfId = :dcfId"),
    @NamedQuery(name = "Dcf.findByPercentage", query = "SELECT d FROM Dcf d WHERE d.percentage = :percentage")})
public class Dcf implements Serializable {

    @JoinColumn(name = "objective_id", referencedColumnName = "objective_id")
    @ManyToOne
    private Objective objectiveId;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dcf_id")
    private Integer dcfId;
    @Basic(optional = false)
    @Column(name = "percentage")
    private int percentage;
    @JoinColumn(name = "objetive_id", referencedColumnName = "objetive_id")
    @ManyToOne
    private Objetive objetiveId;
    @JoinColumn(name = "physiological_capacity_id", referencedColumnName = "physiological_capacity_id")
    @ManyToOne
    private PhysiologicalCapacity physiologicalCapacityId;

    public Dcf() {
    }

    public Dcf(Integer dcfId) {
        this.dcfId = dcfId;
    }

    public Dcf(Integer dcfId, int percentage) {
        this.dcfId = dcfId;
        this.percentage = percentage;
    }

    public Integer getDcfId() {
        return dcfId;
    }

    public void setDcfId(Integer dcfId) {
        this.dcfId = dcfId;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public Objetive getObjetiveId() {
        return objetiveId;
    }

    public void setObjetiveId(Objetive objetiveId) {
        this.objetiveId = objetiveId;
    }

    public PhysiologicalCapacity getPhysiologicalCapacityId() {
        return physiologicalCapacityId;
    }

    public void setPhysiologicalCapacityId(PhysiologicalCapacity physiologicalCapacityId) {
        this.physiologicalCapacityId = physiologicalCapacityId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dcfId != null ? dcfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dcf)) {
            return false;
        }
        Dcf other = (Dcf) object;
        if ((this.dcfId == null && other.dcfId != null) || (this.dcfId != null && !this.dcfId.equals(other.dcfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Dcf[ dcfId=" + dcfId + " ]";
    }

    public Objective getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Objective objectiveId) {
        this.objectiveId = objectiveId;
    }
    
}
