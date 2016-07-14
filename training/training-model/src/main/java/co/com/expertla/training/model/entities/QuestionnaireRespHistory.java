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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "questionnaire_resp_history")
@NamedQueries({
    @NamedQuery(name = "QuestionnaireRespHistory.findAll", query = "SELECT q FROM QuestionnaireRespHistory q"),
    @NamedQuery(name = "QuestionnaireRespHistory.findByQuestionnaireRespHistoryId", query = "SELECT q FROM QuestionnaireRespHistory q WHERE q.questionnaireRespHistoryId = :questionnaireRespHistoryId"),
    @NamedQuery(name = "QuestionnaireRespHistory.findByQuestionnaireResponseId", query = "SELECT q FROM QuestionnaireRespHistory q WHERE q.questionnaireResponseId = :questionnaireResponseId"),
    @NamedQuery(name = "QuestionnaireRespHistory.findByQuestionOptionId", query = "SELECT q FROM QuestionnaireRespHistory q WHERE q.questionOptionId = :questionOptionId"),
    @NamedQuery(name = "QuestionnaireRespHistory.findByUserQuestionnaireId", query = "SELECT q FROM QuestionnaireRespHistory q WHERE q.userQuestionnaireId = :userQuestionnaireId"),
    @NamedQuery(name = "QuestionnaireRespHistory.findByCreationDate", query = "SELECT q FROM QuestionnaireRespHistory q WHERE q.creationDate = :creationDate")})
public class QuestionnaireRespHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "questionnaire_resp_history_id")
    private Integer questionnaireRespHistoryId;
    @Column(name = "questionnaire_response_id")
    private Integer questionnaireResponseId;
    @Column(name = "question_option_id")
    private Integer questionOptionId;
    @Column(name = "user_questionnaire_id")
    private Integer userQuestionnaireId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    public QuestionnaireRespHistory() {
    }

    public QuestionnaireRespHistory(Integer questionnaireRespHistoryId) {
        this.questionnaireRespHistoryId = questionnaireRespHistoryId;
    }

    public Integer getQuestionnaireRespHistoryId() {
        return questionnaireRespHistoryId;
    }

    public void setQuestionnaireRespHistoryId(Integer questionnaireRespHistoryId) {
        this.questionnaireRespHistoryId = questionnaireRespHistoryId;
    }

    public Integer getQuestionnaireResponseId() {
        return questionnaireResponseId;
    }

    public void setQuestionnaireResponseId(Integer questionnaireResponseId) {
        this.questionnaireResponseId = questionnaireResponseId;
    }

    public Integer getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(Integer questionOptionId) {
        this.questionOptionId = questionOptionId;
    }

    public Integer getUserQuestionnaireId() {
        return userQuestionnaireId;
    }

    public void setUserQuestionnaireId(Integer userQuestionnaireId) {
        this.userQuestionnaireId = userQuestionnaireId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionnaireRespHistoryId != null ? questionnaireRespHistoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionnaireRespHistory)) {
            return false;
        }
        QuestionnaireRespHistory other = (QuestionnaireRespHistory) object;
        if ((this.questionnaireRespHistoryId == null && other.questionnaireRespHistoryId != null) || (this.questionnaireRespHistoryId != null && !this.questionnaireRespHistoryId.equals(other.questionnaireRespHistoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.QuestionnaireRespHistory[ questionnaireRespHistoryId=" + questionnaireRespHistoryId + " ]";
    }
    
}
