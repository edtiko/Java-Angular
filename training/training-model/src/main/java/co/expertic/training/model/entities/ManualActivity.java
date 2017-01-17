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

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "manual_activity")
@NamedQueries({
    @NamedQuery(name = "ManualActivity.findAll", query = "SELECT m FROM ManualActivity m"),
    @NamedQuery(name = "ManualActivity.findByManualActivityId", query = "SELECT m FROM ManualActivity m WHERE m.manualActivityId = :manualActivityId"),
    @NamedQuery(name = "ManualActivity.findByName", query = "SELECT m FROM ManualActivity m WHERE m.name = :name"),
    @NamedQuery(name = "ManualActivity.findByDescription", query = "SELECT m FROM ManualActivity m WHERE m.description = :description"),
    @NamedQuery(name = "ManualActivity.findByStateId", query = "SELECT m FROM ManualActivity m WHERE m.stateId = :stateId"),
    @NamedQuery(name = "ManualActivity.findByCreationDate", query = "SELECT m FROM ManualActivity m WHERE m.creationDate = :creationDate")})
public class ManualActivity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "manual_activity_manual_activity_id_seq", sequenceName = "manual_activity_manual_activity_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manual_activity_manual_activity_id_seq")
    @Column(name = "manual_activity_id")
    private Integer manualActivityId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "sport_id", referencedColumnName = "sport_id")
    @ManyToOne(optional = false)
    private Sport sportId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
    @Column(name = "state_id")
    private Integer stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    public ManualActivity() {
    }

    public ManualActivity(Integer manualActivityId) {
        this.manualActivityId = manualActivityId;
    }

    public ManualActivity(Integer manualActivityId, String name) {
        this.manualActivityId = manualActivityId;
        this.name = name;
    }

    public Integer getManualActivityId() {
        return manualActivityId;
    }

    public void setManualActivityId(Integer manualActivityId) {
        this.manualActivityId = manualActivityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (manualActivityId != null ? manualActivityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ManualActivity)) {
            return false;
        }
        ManualActivity other = (ManualActivity) object;
        if ((this.manualActivityId == null && other.manualActivityId != null) || (this.manualActivityId != null && !this.manualActivityId.equals(other.manualActivityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.ManualActivity[ manualActivityId=" + manualActivityId + " ]";
    }

    public Sport getSportId() {
        return sportId;
    }

    public void setSportId(Sport sportId) {
        this.sportId = sportId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
    
}
