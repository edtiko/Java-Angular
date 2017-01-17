/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "data_type")
@NamedQueries({
    @NamedQuery(name = "DataType.findAll", query = "SELECT d FROM DataType d"),
    @NamedQuery(name = "DataType.findByDataTypeId", query = "SELECT d FROM DataType d WHERE d.dataTypeId = :dataTypeId"),
    @NamedQuery(name = "DataType.findByName", query = "SELECT d FROM DataType d WHERE d.name = :name")})
public class DataType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "data_type_id")
    private Integer dataTypeId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "dataTypeId")
    private Collection<Question> questionCollection;

    public DataType() {
    }

    public DataType(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public DataType(Integer dataTypeId, String name) {
        this.dataTypeId = dataTypeId;
        this.name = name;
    }

    public Integer getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Question> getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(Collection<Question> questionCollection) {
        this.questionCollection = questionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataTypeId != null ? dataTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataType)) {
            return false;
        }
        DataType other = (DataType) object;
        if ((this.dataTypeId == null && other.dataTypeId != null) || (this.dataTypeId != null && !this.dataTypeId.equals(other.dataTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.DataType[ dataTypeId=" + dataTypeId + " ]";
    }
    
}
