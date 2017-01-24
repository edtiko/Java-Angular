/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "questionnaire_response")
@NamedQueries({
    @NamedQuery(name = "QuestionnaireResponse.findAll", query = "SELECT q FROM QuestionnaireResponse q"),
    @NamedQuery(name = "QuestionnaireResponse.findByQuestionnaireResponseId", query = "SELECT q FROM QuestionnaireResponse q WHERE q.questionnaireResponseId = :questionnaireResponseId"),
    @NamedQuery(name = "QuestionnaireResponse.findByResponse", query = "SELECT q FROM QuestionnaireResponse q WHERE q.response = :response"),
    @NamedQuery(name = "QuestionnaireResponse.findByCreationDate", query = "SELECT q FROM QuestionnaireResponse q WHERE q.creationDate = :creationDate")})
public class QuestionnaireResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "questionnaire_response_questionnaire_response_id_seq", sequenceName = "questionnaire_response_questionnaire_response_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionnaire_response_questionnaire_response_id_seq")
    @Column(name = "questionnaire_response_id")
    private Integer questionnaireResponseId;
    @Column(name = "response")
    private String response;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "question_option_id", referencedColumnName = "question_option_id")
    @ManyToOne
    private QuestionOption questionOptionId;
    @JoinColumn(name = "questionnaire_question_id", referencedColumnName = "questionnaire_question_id")
    @ManyToOne
    private QuestionnaireQuestion questionnaireQuestionId;
    @Column(name = "response_type_id")
    private Short responseTypeId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
    @OneToMany(mappedBy = "questionnaireResponseId")
    private Collection<ResponseOption> responseOptionCollection;

    public QuestionnaireResponse() {
    }

    public QuestionnaireResponse(Integer questionnaireResponseId) {
        this.questionnaireResponseId = questionnaireResponseId;
    }

    public Integer getQuestionnaireResponseId() {
        return questionnaireResponseId;
    }

    public void setQuestionnaireResponseId(Integer questionnaireResponseId) {
        this.questionnaireResponseId = questionnaireResponseId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public QuestionOption getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(QuestionOption questionOptionId) {
        this.questionOptionId = questionOptionId;
    }

    public QuestionnaireQuestion getQuestionnaireQuestionId() {
        return questionnaireQuestionId;
    }

    public void setQuestionnaireQuestionId(QuestionnaireQuestion questionnaireQuestionId) {
        this.questionnaireQuestionId = questionnaireQuestionId;
    }

    public Short getResponseTypeId() {
        return responseTypeId;
    }

    public void setResponseTypeId(Short responseTypeId) {
        this.responseTypeId = responseTypeId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Collection<ResponseOption> getResponseOptionCollection() {
        return responseOptionCollection;
    }

    public void setResponseOptionCollection(Collection<ResponseOption> responseOptionCollection) {
        this.responseOptionCollection = responseOptionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionnaireResponseId != null ? questionnaireResponseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionnaireResponse)) {
            return false;
        }
        QuestionnaireResponse other = (QuestionnaireResponse) object;
        if ((this.questionnaireResponseId == null && other.questionnaireResponseId != null) || (this.questionnaireResponseId != null && !this.questionnaireResponseId.equals(other.questionnaireResponseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.QuestionnaireResponse[ questionnaireResponseId=" + questionnaireResponseId + " ]";
    }
    
}
