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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "model_equipment")
@NamedQueries({
    @NamedQuery(name = "ModelEquipment.findAll", query = "SELECT m FROM ModelEquipment m"),
    @NamedQuery(name = "ModelEquipment.findByModelEquipmentId", query = "SELECT m FROM ModelEquipment m WHERE m.modelEquipmentId = :modelEquipmentId"),
    @NamedQuery(name = "ModelEquipment.findByName", query = "SELECT m FROM ModelEquipment m WHERE m.name = :name"),
    @NamedQuery(name = "ModelEquipment.findByStateId", query = "SELECT m FROM ModelEquipment m WHERE m.stateId = :stateId"),
    @NamedQuery(name = "ModelEquipment.findByCreationDate", query = "SELECT m FROM ModelEquipment m WHERE m.creationDate = :creationDate")})
public class ModelEquipment implements Serializable {

    @OneToMany(mappedBy = "modelEquipmentId")
    private Collection<EquipmentUserProfile> equipmentUserProfileCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "model_equipment_id")
    private Integer modelEquipmentId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "state_id")
    private Integer stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "sport_equipment_id", referencedColumnName = "sport_equipment_id")
    @ManyToOne(optional = false)
    private SportEquipment sportEquipmentId;

    public ModelEquipment() {
    }

    public ModelEquipment(Integer modelEquipmentId) {
        this.modelEquipmentId = modelEquipmentId;
    }

    public ModelEquipment(Integer modelEquipmentId, String name) {
        this.modelEquipmentId = modelEquipmentId;
        this.name = name;
    }

    public Integer getModelEquipmentId() {
        return modelEquipmentId;
    }

    public void setModelEquipmentId(Integer modelEquipmentId) {
        this.modelEquipmentId = modelEquipmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public SportEquipment getSportEquipmentId() {
        return sportEquipmentId;
    }

    public void setSportEquipmentId(SportEquipment sportEquipmentId) {
        this.sportEquipmentId = sportEquipmentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modelEquipmentId != null ? modelEquipmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModelEquipment)) {
            return false;
        }
        ModelEquipment other = (ModelEquipment) object;
        if ((this.modelEquipmentId == null && other.modelEquipmentId != null) || (this.modelEquipmentId != null && !this.modelEquipmentId.equals(other.modelEquipmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.ModelEquipment[ modelEquipmentId=" + modelEquipmentId + " ]";
    }

    public Collection<EquipmentUserProfile> getEquipmentUserProfileCollection() {
        return equipmentUserProfileCollection;
    }

    public void setEquipmentUserProfileCollection(Collection<EquipmentUserProfile> equipmentUserProfileCollection) {
        this.equipmentUserProfileCollection = equipmentUserProfileCollection;
    }
    
}
