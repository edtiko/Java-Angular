/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "option_type")
@NamedQueries({
    @NamedQuery(name = "OptionType.findAll", query = "SELECT o FROM OptionType o"),
    @NamedQuery(name = "OptionType.findByOptionTypeId", query = "SELECT o FROM OptionType o WHERE o.optionTypeId = :optionTypeId"),
    @NamedQuery(name = "OptionType.findByName", query = "SELECT o FROM OptionType o WHERE o.name = :name")})
public class OptionType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "option_type_id")
    private Integer optionTypeId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "optionTypeId")
    private Collection<QuestionOption> questionOptionCollection;

    public OptionType() {
    }

    public OptionType(Integer optionTypeId) {
        this.optionTypeId = optionTypeId;
    }

    public OptionType(Integer optionTypeId, String name) {
        this.optionTypeId = optionTypeId;
        this.name = name;
    }

    public Integer getOptionTypeId() {
        return optionTypeId;
    }

    public void setOptionTypeId(Integer optionTypeId) {
        this.optionTypeId = optionTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<QuestionOption> getQuestionOptionCollection() {
        return questionOptionCollection;
    }

    public void setQuestionOptionCollection(Collection<QuestionOption> questionOptionCollection) {
        this.questionOptionCollection = questionOptionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (optionTypeId != null ? optionTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OptionType)) {
            return false;
        }
        OptionType other = (OptionType) object;
        if ((this.optionTypeId == null && other.optionTypeId != null) || (this.optionTypeId != null && !this.optionTypeId.equals(other.optionTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.OptionType[ optionTypeId=" + optionTypeId + " ]";
    }
    
}
