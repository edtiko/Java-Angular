/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Andres Lopez
 */
@Entity
@Table(name = "characteristic")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Characteristic.findAll", query = "SELECT c FROM Characteristic c"),
    @NamedQuery(name = "Characteristic.findByCharacteristicId", query = "SELECT c FROM Characteristic c WHERE c.characteristicId = :characteristicId"),
    @NamedQuery(name = "Characteristic.findByName", query = "SELECT c FROM Characteristic c WHERE c.name = :name"),
    @NamedQuery(name = "Characteristic.findByValueType", query = "SELECT c FROM Characteristic c WHERE c.valueType = :valueType"),
    @NamedQuery(name = "Characteristic.findByCreationDate", query = "SELECT c FROM Characteristic c WHERE c.creationDate = :creationDate"),
    @NamedQuery(name = "Characteristic.findByLastUpdate", query = "SELECT c FROM Characteristic c WHERE c.lastUpdate = :lastUpdate"),
    @NamedQuery(name = "Characteristic.findByUserCreate", query = "SELECT c FROM Characteristic c WHERE c.userCreate = :userCreate"),
    @NamedQuery(name = "Characteristic.findByUserUpdate", query = "SELECT c FROM Characteristic c WHERE c.userUpdate = :userUpdate"),
    @NamedQuery(name = "Characteristic.findByStateId", query = "SELECT c FROM Characteristic c WHERE c.stateId = :stateId")})
public class Characteristic implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "characteristic_characteristic_id_seq", sequenceName = "characteristic_characteristic_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "characteristic_characteristic_id_seq")
    @Column(name = "characteristic_id")
    private Integer characteristicId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "value_type")
    private String valueType;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Column(name = "user_create")
    private Integer userCreate;
    @Column(name = "user_update")
    private Integer userUpdate;
    @Column(name = "state_id")
    private Short stateId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "characteristicId")
    private Collection<TrainingPlanCharact> trainingPlanCharactCollection;

    public Characteristic() {
    }

    public Characteristic(Integer characteristicId) {
        this.characteristicId = characteristicId;
    }

    public Characteristic(Integer characteristicId, String name, String valueType) {
        this.characteristicId = characteristicId;
        this.name = name;
        this.valueType = valueType;
    }

    public Integer getCharacteristicId() {
        return characteristicId;
    }

    public void setCharacteristicId(Integer characteristicId) {
        this.characteristicId = characteristicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
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

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<TrainingPlanCharact> getTrainingPlanCharactCollection() {
        return trainingPlanCharactCollection;
    }

    public void setTrainingPlanCharactCollection(Collection<TrainingPlanCharact> trainingPlanCharactCollection) {
        this.trainingPlanCharactCollection = trainingPlanCharactCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (characteristicId != null ? characteristicId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Characteristic)) {
            return false;
        }
        Characteristic other = (Characteristic) object;
        if ((this.characteristicId == null && other.characteristicId != null) || (this.characteristicId != null && !this.characteristicId.equals(other.characteristicId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Characteristic[ characteristicId=" + characteristicId + " ]";
    }
    
}
