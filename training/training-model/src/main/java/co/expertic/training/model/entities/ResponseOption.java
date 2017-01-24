/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "response_option")
@NamedQueries({
    @NamedQuery(name = "ResponseOption.findAll", query = "SELECT r FROM ResponseOption r"),
    @NamedQuery(name = "ResponseOption.findByResponseOptionId", query = "SELECT r FROM ResponseOption r WHERE r.responseOptionId = :responseOptionId")})
public class ResponseOption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "response_option_response_option_id_seq", sequenceName = "response_option_response_option_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "response_option_response_option_id_seq")
    @Column(name = "response_option_id")
    private Integer responseOptionId;
    @JoinColumn(name = "question_option_id", referencedColumnName = "question_option_id")
    @ManyToOne
    private QuestionOption questionOptionId;
    @JoinColumn(name = "questionnaire_response_id", referencedColumnName = "questionnaire_response_id")
    @ManyToOne
    private QuestionnaireResponse questionnaireResponseId;

    public ResponseOption() {
    }

    public ResponseOption(Integer responseOptionId) {
        this.responseOptionId = responseOptionId;
    }

    public Integer getResponseOptionId() {
        return responseOptionId;
    }

    public void setResponseOptionId(Integer responseOptionId) {
        this.responseOptionId = responseOptionId;
    }

    public QuestionOption getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(QuestionOption questionOptionId) {
        this.questionOptionId = questionOptionId;
    }

    public QuestionnaireResponse getQuestionnaireResponseId() {
        return questionnaireResponseId;
    }

    public void setQuestionnaireResponseId(QuestionnaireResponse questionnaireResponseId) {
        this.questionnaireResponseId = questionnaireResponseId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (responseOptionId != null ? responseOptionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResponseOption)) {
            return false;
        }
        ResponseOption other = (ResponseOption) object;
        if ((this.responseOptionId == null && other.responseOptionId != null) || (this.responseOptionId != null && !this.responseOptionId.equals(other.responseOptionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.ResponseOption[ responseOptionId=" + responseOptionId + " ]";
    }
    
}
