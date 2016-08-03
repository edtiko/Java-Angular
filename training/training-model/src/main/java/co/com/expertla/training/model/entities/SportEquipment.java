/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "sport_equipment")
@NamedQueries({
    @NamedQuery(name = "SportEquipment.findAll", query = "SELECT s FROM SportEquipment s"),
    @NamedQuery(name = "SportEquipment.findBySportEquipmentId", query = "SELECT s FROM SportEquipment s WHERE s.sportEquipmentId = :sportEquipmentId"),
    @NamedQuery(name = "SportEquipment.findByName", query = "SELECT s FROM SportEquipment s WHERE s.name = :name")})
public class SportEquipment implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sportEquipmentId")
    private Collection<ModelEquipment> modelEquipmentCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sport_equipment_id")
    private Integer sportEquipmentId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
    @ManyToOne
    private Brand brandId;
    @JoinColumn(name = "sport_equipment_type_id", referencedColumnName = "sport_equipment_type_id")
    @ManyToOne
    private SportEquipmentType sportEquipmentTypeId;
    @OneToMany(mappedBy = "sportEquipmentId")
    private Collection<EquipmentUserProfile> equipmentUserProfileCollection;

    public SportEquipment() {
    }

    public SportEquipment(Integer sportEquipmentId) {
        this.sportEquipmentId = sportEquipmentId;
    }

    public SportEquipment(Integer sportEquipmentId, String name) {
        this.sportEquipmentId = sportEquipmentId;
        this.name = name;
    }

    public Integer getSportEquipmentId() {
        return sportEquipmentId;
    }

    public void setSportEquipmentId(Integer sportEquipmentId) {
        this.sportEquipmentId = sportEquipmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrandId() {
        return brandId;
    }

    public void setBrandId(Brand brandId) {
        this.brandId = brandId;
    }

    public SportEquipmentType getSportEquipmentTypeId() {
        return sportEquipmentTypeId;
    }

    public void setSportEquipmentTypeId(SportEquipmentType sportEquipmentTypeId) {
        this.sportEquipmentTypeId = sportEquipmentTypeId;
    }

    public Collection<EquipmentUserProfile> getEquipmentUserProfileCollection() {
        return equipmentUserProfileCollection;
    }

    public void setEquipmentUserProfileCollection(Collection<EquipmentUserProfile> equipmentUserProfileCollection) {
        this.equipmentUserProfileCollection = equipmentUserProfileCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sportEquipmentId != null ? sportEquipmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SportEquipment)) {
            return false;
        }
        SportEquipment other = (SportEquipment) object;
        if ((this.sportEquipmentId == null && other.sportEquipmentId != null) || (this.sportEquipmentId != null && !this.sportEquipmentId.equals(other.sportEquipmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.SportEquipment[ sportEquipmentId=" + sportEquipmentId + " ]";
    }

    public Collection<ModelEquipment> getModelEquipmentCollection() {
        return modelEquipmentCollection;
    }

    public void setModelEquipmentCollection(Collection<ModelEquipment> modelEquipmentCollection) {
        this.modelEquipmentCollection = modelEquipmentCollection;
    }
    
}
