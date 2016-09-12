package co.com.expertla.training.model.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @Column(name = "state_id")
    private Short stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column(name = "last_update")
    @Temporal(TemporalType.DATE)
    private Date lastUpdate;
    @Column(name = "user_create")
    private Integer userCreate;
    @Column(name = "user_update")
    private Integer userUpdate;

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

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

}
