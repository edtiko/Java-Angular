package co.com.expertla.training.model.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
* BikeType <br>
* Creation Date : <br>
* date 19/08/2016 <br>
* @author Angela Ramírez
**/
@Entity
@Table(name = "bike_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikeType.findAll", query = "SELECT b FROM BikeType b"),
    @NamedQuery(name = "BikeType.findByBikeTypeId", query = "SELECT b FROM BikeType b WHERE b.bikeTypeId = :bikeTypeId"),
    @NamedQuery(name = "BikeType.findByName", query = "SELECT b FROM BikeType b WHERE b.name = :name")})
public class BikeType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bike_type_id")
    private Integer bikeTypeId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public BikeType() {
    }

    public BikeType(Integer bikeTypeId) {
        this.bikeTypeId = bikeTypeId;
    }

    public BikeType(Integer bikeTypeId, String name) {
        this.bikeTypeId = bikeTypeId;
        this.name = name;
    }

    public Integer getBikeTypeId() {
        return bikeTypeId;
    }

    public void setBikeTypeId(Integer bikeTypeId) {
        this.bikeTypeId = bikeTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bikeTypeId != null ? bikeTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikeType)) {
            return false;
        }
        BikeType other = (BikeType) object;
        if ((this.bikeTypeId == null && other.bikeTypeId != null) || (this.bikeTypeId != null && !this.bikeTypeId.equals(other.bikeTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.BikeType[ bikeTypeId=" + bikeTypeId + " ]";
    }

}
