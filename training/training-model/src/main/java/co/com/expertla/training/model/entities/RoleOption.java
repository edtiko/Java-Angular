/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andres Lopez
 */
@Entity
@Table(name = "role_option")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RoleOption.findAll", query = "SELECT r FROM RoleOption r"),
    @NamedQuery(name = "RoleOption.findByRoleOptionId", query = "SELECT r FROM RoleOption r WHERE r.roleOptionId = :roleOptionId"),
    @NamedQuery(name = "RoleOption.findByCreationDate", query = "SELECT r FROM RoleOption r WHERE r.creationDate = :creationDate"),
    @NamedQuery(name = "RoleOption.findByLastUpdate", query = "SELECT r FROM RoleOption r WHERE r.lastUpdate = :lastUpdate")})
public class RoleOption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "role_option_id")
    private Integer roleOptionId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column(name = "last_update")
    @Temporal(TemporalType.DATE)
    private Date lastUpdate;
    @JoinColumn(name = "option_id", referencedColumnName = "option_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Option optionId;

    public RoleOption() {
    }

    public RoleOption(Integer roleOptionId) {
        this.roleOptionId = roleOptionId;
    }

    public Integer getRoleOptionId() {
        return roleOptionId;
    }

    public void setRoleOptionId(Integer roleOptionId) {
        this.roleOptionId = roleOptionId;
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

    public Option getOptionId() {
        return optionId;
    }

    public void setOptionId(Option optionId) {
        this.optionId = optionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleOptionId != null ? roleOptionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoleOption)) {
            return false;
        }
        RoleOption other = (RoleOption) object;
        if ((this.roleOptionId == null && other.roleOptionId != null) || (this.roleOptionId != null && !this.roleOptionId.equals(other.roleOptionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.RoleOption[ roleOptionId=" + roleOptionId + " ]";
    }
    
}
