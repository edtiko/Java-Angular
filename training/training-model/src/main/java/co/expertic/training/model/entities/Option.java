/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Andres Lopez
 */
@Entity
@Table(name = "option")
@XmlRootElement
public class Option implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "option_id")
    private Integer optionId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "url")
    private String url;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column(name = "last_update")
    @Temporal(TemporalType.DATE)
    private Date lastUpdate;
    @Column(name = "user_create")
    private Integer userCreate;
    @Column(name = "user_update")
    private Integer userUpdate;
    @OneToMany(mappedBy = "optionId", fetch = FetchType.LAZY)
    private Collection<RoleOption> roleOptionCollection;
    @JoinColumn(name = "module_id", referencedColumnName = "module_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Module moduleId;
    @OneToMany(mappedBy = "masterOptionId", fetch = FetchType.LAZY)
    private Collection<Option> optionCollection;
    @JoinColumn(name = "master_option_id", referencedColumnName = "option_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Option masterOptionId;
    @Column(name = "state_id")
    private Short stateId;

    public Option() {
    }

    public Option(Integer optionId) {
        this.optionId = optionId;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @XmlTransient
    @JsonIgnore
    public Collection<RoleOption> getRoleOptionCollection() {
        return roleOptionCollection;
    }

    public void setRoleOptionCollection(Collection<RoleOption> roleOptionCollection) {
        this.roleOptionCollection = roleOptionCollection;
    }

    public Module getModuleId() {
        return moduleId;
    }

    public void setModuleId(Module moduleId) {
        this.moduleId = moduleId;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Option> getOptionCollection() {
        return optionCollection;
    }

    public void setOptionCollection(Collection<Option> optionCollection) {
        this.optionCollection = optionCollection;
    }

    public Option getMasterOptionId() {
        return masterOptionId;
    }

    public void setMasterOptionId(Option masterOptionId) {
        this.masterOptionId = masterOptionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (optionId != null ? optionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Option)) {
            return false;
        }
        Option other = (Option) object;
        if ((this.optionId == null && other.optionId != null) || (this.optionId != null && !this.optionId.equals(other.optionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Option[ optionId=" + optionId + " ]";
    }
    
}
