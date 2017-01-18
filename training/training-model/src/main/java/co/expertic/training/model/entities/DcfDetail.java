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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
* DcfDetail <br>
* Creation Date : <br>
* date 21/07/2016 <br>
* @author Angela Ramírez
**/
@Entity
@Table(name = "dcf_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DcfDetail.findAll", query = "SELECT d FROM DcfDetail d"),
    @NamedQuery(name = "DcfDetail.findByDcfDetailId", query = "SELECT d FROM DcfDetail d WHERE d.dcfDetailId = :dcfDetailId"),
    @NamedQuery(name = "DcfDetail.findByPercentage", query = "SELECT d FROM DcfDetail d WHERE d.percentage = :percentage")})
public class DcfDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dcf_detail_id")
    private Integer dcfDetailId;
    @Basic(optional = false)
    @Column(name = "percentage")
    private int percentage;
    @JoinColumn(name = "dcf_id", referencedColumnName = "dcf_id")
    @ManyToOne
    private Dcf dcfId;
    @JoinColumn(name = "physiological_capacity_id", referencedColumnName = "physiological_capacity_id")
    @ManyToOne
    private PhysiologicalCapacity physiologicalCapacityId;

    public DcfDetail() {
    }

    public DcfDetail(Integer dcfDetailId) {
        this.dcfDetailId = dcfDetailId;
    }

    public DcfDetail(Integer dcfDetailId, int percentage) {
        this.dcfDetailId = dcfDetailId;
        this.percentage = percentage;
    }

    public Integer getDcfDetailId() {
        return dcfDetailId;
    }

    public void setDcfDetailId(Integer dcfDetailId) {
        this.dcfDetailId = dcfDetailId;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public Dcf getDcfId() {
        return dcfId;
    }

    public void setDcfId(Dcf dcfId) {
        this.dcfId = dcfId;
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
        hash += (dcfDetailId != null ? dcfDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DcfDetail)) {
            return false;
        }
        DcfDetail other = (DcfDetail) object;
        if ((this.dcfDetailId == null && other.dcfDetailId != null) || (this.dcfDetailId != null && !this.dcfDetailId.equals(other.dcfDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.DcfDetail[ dcfDetailId=" + dcfDetailId + " ]";
    }

}
