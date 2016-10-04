/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "questionnaire_user")
@NamedQueries({
    @NamedQuery(name = "QuestionnaireUser.findAll", query = "SELECT q FROM QuestionnaireUser q"),
    @NamedQuery(name = "QuestionnaireUser.findByQuestionnaireUserId", query = "SELECT q FROM QuestionnaireUser q WHERE q.questionnaireUserId = :questionnaireUserId"),
    @NamedQuery(name = "QuestionnaireUser.findByUserTrainingId", query = "SELECT q FROM QuestionnaireUser q WHERE q.userTrainingId = :userTrainingId")})
public class QuestionnaireUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "questionnaire_user_id")
    private Integer questionnaireUserId;
    @Column(name = "user_training_id")
    private Integer userTrainingId;
    @JoinColumn(name = "questionnaire_id", referencedColumnName = "questionnaire_id")
    @ManyToOne
    private Questionnaire questionnaireId;

    public QuestionnaireUser() {
    }

    public QuestionnaireUser(Integer questionnaireUserId) {
        this.questionnaireUserId = questionnaireUserId;
    }

    public Integer getQuestionnaireUserId() {
        return questionnaireUserId;
    }

    public void setQuestionnaireUserId(Integer questionnaireUserId) {
        this.questionnaireUserId = questionnaireUserId;
    }

    public Integer getUserTrainingId() {
        return userTrainingId;
    }

    public void setUserTrainingId(Integer userTrainingId) {
        this.userTrainingId = userTrainingId;
    }

    public Questionnaire getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Questionnaire questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionnaireUserId != null ? questionnaireUserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionnaireUser)) {
            return false;
        }
        QuestionnaireUser other = (QuestionnaireUser) object;
        if ((this.questionnaireUserId == null && other.questionnaireUserId != null) || (this.questionnaireUserId != null && !this.questionnaireUserId.equals(other.questionnaireUserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.QuestionnaireUser[ questionnaireUserId=" + questionnaireUserId + " ]";
    }
    
}
