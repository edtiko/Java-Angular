/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

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
@Table(name = "response_type")
@NamedQueries({
    @NamedQuery(name = "ResponseType.findAll", query = "SELECT r FROM ResponseType r"),
    @NamedQuery(name = "ResponseType.findByResponseTypeId", query = "SELECT r FROM ResponseType r WHERE r.responseTypeId = :responseTypeId"),
    @NamedQuery(name = "ResponseType.findByName", query = "SELECT r FROM ResponseType r WHERE r.name = :name")})
public class ResponseType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "response_type_id")
    private Integer responseTypeId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
   

    public ResponseType() {
    }

    public ResponseType(Integer responseTypeId) {
        this.responseTypeId = responseTypeId;
    }

    public ResponseType(Integer responseTypeId, String name) {
        this.responseTypeId = responseTypeId;
        this.name = name;
    }

    public Integer getResponseTypeId() {
        return responseTypeId;
    }

    public void setResponseTypeId(Integer responseTypeId) {
        this.responseTypeId = responseTypeId;
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
        hash += (responseTypeId != null ? responseTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResponseType)) {
            return false;
        }
        ResponseType other = (ResponseType) object;
        if ((this.responseTypeId == null && other.responseTypeId != null) || (this.responseTypeId != null && !this.responseTypeId.equals(other.responseTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.ResponseType[ responseTypeId=" + responseTypeId + " ]";
    }
    
}
