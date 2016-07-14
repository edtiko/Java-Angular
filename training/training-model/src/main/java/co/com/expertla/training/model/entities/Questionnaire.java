/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
    @JoinColumn(name = "state_id", referencedColumnName = "state_id")
    @ManyToOne
    private State stateId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
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

    public State getStateId() {
        return stateId;
    }

    public void setStateId(State stateId) {
        this.stateId = stateId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
    
}
