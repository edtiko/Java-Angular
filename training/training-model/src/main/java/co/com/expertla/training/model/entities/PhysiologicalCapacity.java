package co.com.expertla.training.model.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
* PhysiologicalCapacity <br>
* Creation Date : <br>
* date 21/07/2016 <br>
* @author Angela Ramírez
**/
@Entity
@Table(name = "physiological_capacity")
@XmlRootElement
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
    @Column(name = "code")
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
