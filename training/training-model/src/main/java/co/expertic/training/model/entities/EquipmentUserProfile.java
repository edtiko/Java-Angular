package co.expertic.training.model.entities;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
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
    @SequenceGenerator(name = "equipment_user_profile_equipment_user_profile_id_seq", sequenceName = "equipment_user_profile_equipment_user_profile_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipment_user_profile_equipment_user_profile_id_seq")
    @Column(name = "equipment_user_profile_id")
    private Integer equipmentUserProfileId;
    @JoinColumn(name = "model_equipment_id", referencedColumnName = "model_equipment_id")
    @ManyToOne
    private ModelEquipment modelEquipmentId;
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

    public ModelEquipment getModelEquipmentId() {
        return modelEquipmentId;
    }

    public void setModelEquipmentId(ModelEquipment modelEquipmentId) {
        this.modelEquipmentId = modelEquipmentId;
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
        return "co.com.expertla.training.constant.EquipmentUserProfile[ equipmentUserProfileId=" + equipmentUserProfileId + " ]";
    }
    
}
