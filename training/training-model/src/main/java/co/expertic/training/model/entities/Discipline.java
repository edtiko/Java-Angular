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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

    @OneToMany(mappedBy = "disciplineId")
    private List<Questionnaire> questionnaireList;

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "discipline_seq", sequenceName = "discipline_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discipline_seq")
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
    @OneToMany(mappedBy = "disciplineId")
    private Collection<DisciplineSport> disciplineSportCollection;
    @Column(name = "state_id")
    private Short stateId;
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
    @OneToMany(mappedBy = "disciplineId")
    private Collection<Objective> objectiveCollection;
    @Column(name = "discipline_id_ext")
    private Integer disciplineIdExt;

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
    @JsonIgnore
    public Collection<Modality> getModalityCollection() {
        return modalityCollection;
    }

    public void setModalityCollection(Collection<Modality> modalityCollection) {
        this.modalityCollection = modalityCollection;
    }
    @JsonIgnore
    public Collection<DisciplineSport> getDisciplineSportCollection() {
        return disciplineSportCollection;
    }

    public void setDisciplineSportCollection(Collection<DisciplineSport> disciplineSportCollection) {
        this.disciplineSportCollection = disciplineSportCollection;
    }
    
    @JsonIgnore
    public Collection<DisciplineUser> getDisciplineUserCollection() {
        return disciplineUserCollection;
    }

    public void setDisciplineUserCollection(Collection<DisciplineUser> disciplineUserCollection) {
        this.disciplineUserCollection = disciplineUserCollection;
    }
    @JsonIgnore
    public Collection<Objective> getObjectiveCollection() {
        return objectiveCollection;
    }

    public void setObjectiveCollection(Collection<Objective> objectiveCollection) {
        this.objectiveCollection = objectiveCollection;
    }

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
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

    public Integer getDisciplineIdExt() {
        return disciplineIdExt;
    }

    public void setDisciplineIdExt(Integer disciplineIdExt) {
        this.disciplineIdExt = disciplineIdExt;
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
        return !((this.disciplineId == null && other.disciplineId != null) || (this.disciplineId != null && !this.disciplineId.equals(other.disciplineId)));
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Discipline[ disciplineId=" + disciplineId + " ]";
    }
    @JsonIgnore
    public List<Questionnaire> getQuestionnaireList() {
        return questionnaireList;
    }

    public void setQuestionnaireList(List<Questionnaire> questionnaireList) {
        this.questionnaireList = questionnaireList;
    }
    
}
