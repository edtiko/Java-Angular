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
@Table(name = "equipment_user_profile")
@NamedQueries({
    @NamedQuery(name = "EquipmentUserProfile.findAll", query = "SELECT e FROM EquipmentUserProfile e"),
    @NamedQuery(name = "EquipmentUserProfile.findByEquipmentUserProfileId", query = "SELECT e FROM EquipmentUserProfile e WHERE e.equipmentUserProfileId = :equipmentUserProfileId")})
public class EquipmentUserProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "equipment_user_profile_id")
    private Integer equipmentUserProfileId;
    @JoinColumn(name = "sport_equipment_id", referencedColumnName = "sport_equipment_id")
    @ManyToOne
    private SportEquipment sportEquipmentId;
    @JoinColumn(name = "user_profile_id", referencedColumnName = "user_profile_id")
    @ManyToOne
    private UserProfile userProfileId;

    public EquipmentUserProfile() {
    }

    public EquipmentUserProfile(Integer equipmentUserProfileId) {
        this.equipmentUserProfileId = equipmentUserProfileId;
    }

    public Integer getEquipmentUserProfileId() {
        return equipmentUserProfileId;
    }

    public void setEquipmentUserProfileId(Integer equipmentUserProfileId) {
        this.equipmentUserProfileId = equipmentUserProfileId;
    }

    public SportEquipment getSportEquipmentId() {
        return sportEquipmentId;
    }

    public void setSportEquipmentId(SportEquipment sportEquipmentId) {
        this.sportEquipmentId = sportEquipmentId;
    }

    public UserProfile getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(UserProfile userProfileId) {
        this.userProfileId = userProfileId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (equipmentUserProfileId != null ? equipmentUserProfileId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EquipmentUserProfile)) {
            return false;
        }
        EquipmentUserProfile other = (EquipmentUserProfile) object;
        if ((this.equipmentUserProfileId == null && other.equipmentUserProfileId != null) || (this.equipmentUserProfileId != null && !this.equipmentUserProfileId.equals(other.equipmentUserProfileId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.EquipmentUserProfile[ equipmentUserProfileId=" + equipmentUserProfileId + " ]";
    }
    
}
