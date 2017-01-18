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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "environment")
@NamedQueries({
    @NamedQuery(name = "Environment.findAll", query = "SELECT e FROM Environment e"),
    @NamedQuery(name = "Environment.findByEnvironmentId", query = "SELECT e FROM Environment e WHERE e.environmentId = :environmentId"),
    @NamedQuery(name = "Environment.findByName", query = "SELECT e FROM Environment e WHERE e.name = :name"),
    @NamedQuery(name = "Environment.findByStateId", query = "SELECT e FROM Environment e WHERE e.stateId = :stateId"),
    @NamedQuery(name = "Environment.findByCreationDate", query = "SELECT e FROM Environment e WHERE e.creationDate = :creationDate")})
public class Environment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "environment_id")
    private Integer environmentId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "state_id")
    private Integer stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    public Environment() {
    }

    public Environment(Integer environmentId) {
        this.environmentId = environmentId;
    }

    public Environment(Integer environmentId, String name) {
        this.environmentId = environmentId;
        this.name = name;
    }

    public Integer getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Integer environmentId) {
        this.environmentId = environmentId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (environmentId != null ? environmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Environment)) {
            return false;
        }
        Environment other = (Environment) object;
        if ((this.environmentId == null && other.environmentId != null) || (this.environmentId != null && !this.environmentId.equals(other.environmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Environment[ environmentId=" + environmentId + " ]";
    }
    
}
