/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

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
@Table(name = "discipline")
@NamedQueries({
    @NamedQuery(name = "Discipline.findAll", query = "SELECT d FROM Discipline d"),
    @NamedQuery(name = "Discipline.findByDisciplineId", query = "SELECT d FROM Discipline d WHERE d.disciplineId = :disciplineId"),
    @NamedQuery(name = "Discipline.findByName", query = "SELECT d FROM Discipline d WHERE d.name = :name"),
    @NamedQuery(name = "Discipline.findByDescription", query = "SELECT d FROM Discipline d WHERE d.description = :description")})
public class Discipline implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "discipline_id")
    private Integer disciplineId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "disciplineId")
    private Collection<Modality> modalityCollection;
    @OneToMany(mappedBy = "discipline")
    private Collection<DisciplineUser> disciplineUserCollection;

    public Discipline() {
    }

    public Discipline(Integer disciplineId) {
        this.disciplineId = disciplineId;
    }

    public Integer getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Integer disciplineId) {
        this.disciplineId = disciplineId;
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

    public Collection<Modality> getModalityCollection() {
        return modalityCollection;
    }

    public void setModalityCollection(Collection<Modality> modalityCollection) {
        this.modalityCollection = modalityCollection;
    }

    public Collection<DisciplineUser> getDisciplineUserCollection() {
        return disciplineUserCollection;
    }

    public void setDisciplineUserCollection(Collection<DisciplineUser> disciplineUserCollection) {
        this.disciplineUserCollection = disciplineUserCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (disciplineId != null ? disciplineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Discipline)) {
            return false;
        }
        Discipline other = (Discipline) object;
        if ((this.disciplineId == null && other.disciplineId != null) || (this.disciplineId != null && !this.disciplineId.equals(other.disciplineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Discipline[ disciplineId=" + disciplineId + " ]";
    }
    
}
