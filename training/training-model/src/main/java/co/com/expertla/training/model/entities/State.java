/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "state")
@NamedQueries({
    @NamedQuery(name = "State.findAll", query = "SELECT s FROM State s"),
    @NamedQuery(name = "State.findByStateId", query = "SELECT s FROM State s WHERE s.stateId = :stateId"),
    @NamedQuery(name = "State.findByName", query = "SELECT s FROM State s WHERE s.name = :name"),
    @NamedQuery(name = "State.findByDescription", query = "SELECT s FROM State s WHERE s.description = :description")})
public class State implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "state_id")
    private Integer stateId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "stateId", fetch = FetchType.LAZY)
    private Collection<Option> optionCollection;
    @OneToMany(mappedBy = "stateId", fetch = FetchType.LAZY)
    private Collection<Module> moduleCollection;
    @OneToMany(mappedBy = "stateId", fetch = FetchType.LAZY)
    private Collection<Role> roleCollection;


    public State() {
    }

    public State(Integer stateId) {
        this.stateId = stateId;
    }

    public State(Integer stateId, String name) {
        this.stateId = stateId;
        this.name = name;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
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
    @JsonIgnore
    public Collection<Option> getOptionCollection() {
        return optionCollection;
    }

    public void setOptionCollection(Collection<Option> optionCollection) {
        this.optionCollection = optionCollection;
    }
    @JsonIgnore
    public Collection<Module> getModuleCollection() {
        return moduleCollection;
    }

    public void setModuleCollection(Collection<Module> moduleCollection) {
        this.moduleCollection = moduleCollection;
    }
    @JsonIgnore
    public Collection<Role> getRoleCollection() {
        return roleCollection;
    }

    public void setRoleCollection(Collection<Role> roleCollection) {
        this.roleCollection = roleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stateId != null ? stateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof State)) {
            return false;
        }
        State other = (State) object;
        if ((this.stateId == null && other.stateId != null) || (this.stateId != null && !this.stateId.equals(other.stateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.State[ stateId=" + stateId + " ]";
    }
    
}
