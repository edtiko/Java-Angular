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
@Table(name = "sport_equipment_type")
@NamedQueries({
    @NamedQuery(name = "SportEquipmentType.findAll", query = "SELECT s FROM SportEquipmentType s"),
    @NamedQuery(name = "SportEquipmentType.findBySportEquipmentTypeId", query = "SELECT s FROM SportEquipmentType s WHERE s.sportEquipmentTypeId = :sportEquipmentTypeId"),
    @NamedQuery(name = "SportEquipmentType.findByName", query = "SELECT s FROM SportEquipmentType s WHERE s.name = :name")})
public class SportEquipmentType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sport_equipment_type_id")
    private Integer sportEquipmentTypeId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "sportEquipmentTypeId")
    private Collection<SportEquipment> sportEquipmentCollection;

    public SportEquipmentType() {
    }

    public SportEquipmentType(Integer sportEquipmentTypeId) {
        this.sportEquipmentTypeId = sportEquipmentTypeId;
    }

    public SportEquipmentType(Integer sportEquipmentTypeId, String name) {
        this.sportEquipmentTypeId = sportEquipmentTypeId;
        this.name = name;
    }

    public Integer getSportEquipmentTypeId() {
        return sportEquipmentTypeId;
    }

    public void setSportEquipmentTypeId(Integer sportEquipmentTypeId) {
        this.sportEquipmentTypeId = sportEquipmentTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<SportEquipment> getSportEquipmentCollection() {
        return sportEquipmentCollection;
    }

    public void setSportEquipmentCollection(Collection<SportEquipment> sportEquipmentCollection) {
        this.sportEquipmentCollection = sportEquipmentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sportEquipmentTypeId != null ? sportEquipmentTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SportEquipmentType)) {
            return false;
        }
        SportEquipmentType other = (SportEquipmentType) object;
        if ((this.sportEquipmentTypeId == null && other.sportEquipmentTypeId != null) || (this.sportEquipmentTypeId != null && !this.sportEquipmentTypeId.equals(other.sportEquipmentTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.SportEquipmentType[ sportEquipmentTypeId=" + sportEquipmentTypeId + " ]";
    }
    
}
