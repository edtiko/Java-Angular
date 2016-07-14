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
@Table(name = "questionnaire_question")
@NamedQueries({
    @NamedQuery(name = "QuestionnaireQuestion.findAll", query = "SELECT q FROM QuestionnaireQuestion q"),
    @NamedQuery(name = "QuestionnaireQuestion.findByQuestionnaireQuestionId", query = "SELECT q FROM QuestionnaireQuestion q WHERE q.questionnaireQuestionId = :questionnaireQuestionId"),
    @NamedQuery(name = "QuestionnaireQuestion.findByQuestionOrder", query = "SELECT q FROM QuestionnaireQuestion q WHERE q.questionOrder = :questionOrder"),
    @NamedQuery(name = "QuestionnaireQuestion.findByCreationDate", query = "SELECT q FROM QuestionnaireQuestion q WHERE q.creationDate = :creationDate")})
public class QuestionnaireQuestion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "questionnaire_question_id")
    private Integer questionnaireQuestionId;
    @Basic(optional = false)
    @Column(name = "question_order")
    private int questionOrder;
    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @OneToMany(mappedBy = "questionnaireQuestionId")
    private Collection<QuestionnaireResponse> questionnaireResponseCollection;
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    @ManyToOne
    private Question questionId;
    @JoinColumn(name = "questionnaire_id", referencedColumnName = "questionnaire_id")
    @ManyToOne
    private Questionnaire questionnaireId;
    @JoinColumn(name = "questionnaire_category_id", referencedColumnName = "questionnaire_category_id")
    @ManyToOne
    private QuestionnaireCategory questionnaireCategoryId;
    @JoinColumn(name = "state_id", referencedColumnName = "state_id")
    @ManyToOne
    private State stateId;

    public QuestionnaireQuestion() {
    }

    public QuestionnaireQuestion(Integer questionnaireQuestionId) {
        this.questionnaireQuestionId = questionnaireQuestionId;
    }

    public QuestionnaireQuestion(Integer questionnaireQuestionId, int questionOrder, Date creationDate) {
        this.questionnaireQuestionId = questionnaireQuestionId;
        this.questionOrder = questionOrder;
        this.creationDate = creationDate;
    }

    public Integer getQuestionnaireQuestionId() {
        return questionnaireQuestionId;
    }

    public void setQuestionnaireQuestionId(Integer questionnaireQuestionId) {
        this.questionnaireQuestionId = questionnaireQuestionId;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Collection<QuestionnaireResponse> getQuestionnaireResponseCollection() {
        return questionnaireResponseCollection;
    }

    public void setQuestionnaireResponseCollection(Collection<QuestionnaireResponse> questionnaireResponseCollection) {
        this.questionnaireResponseCollection = questionnaireResponseCollection;
    }

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }

    public Questionnaire getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Questionnaire questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public QuestionnaireCategory getQuestionnaireCategoryId() {
        return questionnaireCategoryId;
    }

    public void setQuestionnaireCategoryId(QuestionnaireCategory questionnaireCategoryId) {
        this.questionnaireCategoryId = questionnaireCategoryId;
    }

    public State getStateId() {
        return stateId;
    }

    public void setStateId(State stateId) {
        this.stateId = stateId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionnaireQuestionId != null ? questionnaireQuestionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionnaireQuestion)) {
            return false;
        }
        QuestionnaireQuestion other = (QuestionnaireQuestion) object;
        if ((this.questionnaireQuestionId == null && other.questionnaireQuestionId != null) || (this.questionnaireQuestionId != null && !this.questionnaireQuestionId.equals(other.questionnaireQuestionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.QuestionnaireQuestion[ questionnaireQuestionId=" + questionnaireQuestionId + " ]";
    }
    
}
