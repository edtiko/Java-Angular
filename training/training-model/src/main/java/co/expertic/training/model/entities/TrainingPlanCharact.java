/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andres Lopez
 */
@Entity
@Table(name = "training_plan_charact")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrainingPlanCharact.findAll", query = "SELECT t FROM TrainingPlanCharact t"),
    @NamedQuery(name = "TrainingPlanCharact.findByTrainingPlanCharactId", query = "SELECT t FROM TrainingPlanCharact t WHERE t.trainingPlanCharactId = :trainingPlanCharactId"),
    @NamedQuery(name = "TrainingPlanCharact.findByValue", query = "SELECT t FROM TrainingPlanCharact t WHERE t.value = :value"),
    @NamedQuery(name = "TrainingPlanCharact.findByCreationDate", query = "SELECT t FROM TrainingPlanCharact t WHERE t.creationDate = :creationDate"),
    @NamedQuery(name = "TrainingPlanCharact.findByLastUpdate", query = "SELECT t FROM TrainingPlanCharact t WHERE t.lastUpdate = :lastUpdate"),
    @NamedQuery(name = "TrainingPlanCharact.findByUserCreate", query = "SELECT t FROM TrainingPlanCharact t WHERE t.userCreate = :userCreate"),
    @NamedQuery(name = "TrainingPlanCharact.findByUserUpdate", query = "SELECT t FROM TrainingPlanCharact t WHERE t.userUpdate = :userUpdate"),
    @NamedQuery(name = "TrainingPlanCharact.findByStateId", query = "SELECT t FROM TrainingPlanCharact t WHERE t.stateId = :stateId")})
public class TrainingPlanCharact implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "training_plan_charact_training_plan_charact_id_seq", sequenceName = "training_plan_charact_training_plan_charact_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_plan_charact_training_plan_charact_id_seq")
    @Basic(optional = false)
    @Column(name = "training_plan_charact_id")
    private Integer trainingPlanCharactId;
    @Basic(optional = false)
    @Column(name = "value")
    private String value;
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
    @JoinColumn(name = "characteristic_id", referencedColumnName = "characteristic_id")
    @ManyToOne(optional = false)
    private Characteristic characteristicId;
    @JoinColumn(name = "training_plan_id", referencedColumnName = "training_plan_id")
    @ManyToOne(optional = false)
    private TrainingPlan trainingPlanId;
    @JoinColumn(name = "membership_id", referencedColumnName = "membership_id")
    @ManyToOne(optional = false)
    private Membership membershipId;
    @Column(name = "user_type")
    private String userType;
    

    public TrainingPlanCharact() {
    }

    public TrainingPlanCharact(Integer trainingPlanCharactId) {
        this.trainingPlanCharactId = trainingPlanCharactId;
    }

    public TrainingPlanCharact(Integer trainingPlanCharactId, String value) {
        this.trainingPlanCharactId = trainingPlanCharactId;
        this.value = value;
    }

    public Membership getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Membership membershipId) {
        this.membershipId = membershipId;
    }

    public Integer getTrainingPlanCharactId() {
        return trainingPlanCharactId;
    }

    public void setTrainingPlanCharactId(Integer trainingPlanCharactId) {
        this.trainingPlanCharactId = trainingPlanCharactId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Characteristic getCharacteristicId() {
        return characteristicId;
    }

    public void setCharacteristicId(Characteristic characteristicId) {
        this.characteristicId = characteristicId;
    }

    public TrainingPlan getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(TrainingPlan trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trainingPlanCharactId != null ? trainingPlanCharactId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrainingPlanCharact)) {
            return false;
        }
        TrainingPlanCharact other = (TrainingPlanCharact) object;
        if ((this.trainingPlanCharactId == null && other.trainingPlanCharactId != null) || (this.trainingPlanCharactId != null && !this.trainingPlanCharactId.equals(other.trainingPlanCharactId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.TrainingPlanCharact[ trainingPlanCharactId=" + trainingPlanCharactId + " ]";
    }
    
}
