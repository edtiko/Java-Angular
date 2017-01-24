/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "questionnaire")
@NamedQueries({
    @NamedQuery(name = "Questionnaire.findAll", query = "SELECT q FROM Questionnaire q"),
    @NamedQuery(name = "Questionnaire.findByQuestionnaireId", query = "SELECT q FROM Questionnaire q WHERE q.questionnaireId = :questionnaireId"),
    @NamedQuery(name = "Questionnaire.findByName", query = "SELECT q FROM Questionnaire q WHERE q.name = :name"),
    @NamedQuery(name = "Questionnaire.findByCreationDate", query = "SELECT q FROM Questionnaire q WHERE q.creationDate = :creationDate")})
public class Questionnaire implements Serializable {

 

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "questionnaire_id")
    private Integer questionnaireId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column(name = "state_id")
    private Short stateId;
    @JoinColumn(name = "discipline_id", referencedColumnName = "discipline_id")
    @ManyToOne
    private Discipline disciplineId;
    @OneToMany(mappedBy = "questionnaireId")
    private List<QuestionnaireUser> questionnaireUserList;
    @OneToMany(mappedBy = "questionnaireId")
    private Collection<QuestionnaireQuestion> questionnaireQuestionCollection;

    public Questionnaire() {
    }

    public Questionnaire(Integer questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Questionnaire(Integer questionnaireId, String name, Date creationDate) {
        this.questionnaireId = questionnaireId;
        this.name = name;
        this.creationDate = creationDate;
    }

    public Integer getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Integer questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    public Collection<QuestionnaireQuestion> getQuestionnaireQuestionCollection() {
        return questionnaireQuestionCollection;
    }

    public void setQuestionnaireQuestionCollection(Collection<QuestionnaireQuestion> questionnaireQuestionCollection) {
        this.questionnaireQuestionCollection = questionnaireQuestionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionnaireId != null ? questionnaireId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Questionnaire)) {
            return false;
        }
        Questionnaire other = (Questionnaire) object;
        if ((this.questionnaireId == null && other.questionnaireId != null) || (this.questionnaireId != null && !this.questionnaireId.equals(other.questionnaireId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Questionnaire[ questionnaireId=" + questionnaireId + " ]";
    }


    public List<QuestionnaireUser> getQuestionnaireUserList() {
        return questionnaireUserList;
    }

    public void setQuestionnaireUserList(List<QuestionnaireUser> questionnaireUserList) {
        this.questionnaireUserList = questionnaireUserList;
    }


    public Discipline getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Discipline disciplineId) {
        this.disciplineId = disciplineId;
    }
    
}
