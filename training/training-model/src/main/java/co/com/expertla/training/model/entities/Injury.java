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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "injury")
@NamedQueries({
    @NamedQuery(name = "Injury.findAll", query = "SELECT i FROM Injury i"),
    @NamedQuery(name = "Injury.findByInjuryId", query = "SELECT i FROM Injury i WHERE i.injuryId = :injuryId"),
    @NamedQuery(name = "Injury.findByInjuryParentId", query = "SELECT i FROM Injury i WHERE i.injuryParentId = :injuryParentId"),
    @NamedQuery(name = "Injury.findByName", query = "SELECT i FROM Injury i WHERE i.name = :name")})
public class Injury implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "injury_id")
    private Integer injuryId;
    @Column(name = "injury_parent_id")
    private Integer injuryParentId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public Injury() {
    }

    public Injury(Integer injuryId) {
        this.injuryId = injuryId;
    }

    public Injury(Integer injuryId, String name) {
        this.injuryId = injuryId;
        this.name = name;
    }

    public Integer getInjuryId() {
        return injuryId;
    }

    public void setInjuryId(Integer injuryId) {
        this.injuryId = injuryId;
    }

    public Integer getInjuryParentId() {
        return injuryParentId;
    }

    public void setInjuryParentId(Integer injuryParentId) {
        this.injuryParentId = injuryParentId;
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
        hash += (injuryId != null ? injuryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Injury)) {
            return false;
        }
        Injury other = (Injury) object;
        if ((this.injuryId == null && other.injuryId != null) || (this.injuryId != null && !this.injuryId.equals(other.injuryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Injury[ injuryId=" + injuryId + " ]";
    }
    
}
