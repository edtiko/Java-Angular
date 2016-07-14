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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "question_option")
@NamedQueries({
    @NamedQuery(name = "QuestionOption.findAll", query = "SELECT q FROM QuestionOption q"),
    @NamedQuery(name = "QuestionOption.findByQuestionOptionId", query = "SELECT q FROM QuestionOption q WHERE q.questionOptionId = :questionOptionId"),
    @NamedQuery(name = "QuestionOption.findByName", query = "SELECT q FROM QuestionOption q WHERE q.name = :name"),
    @NamedQuery(name = "QuestionOption.findByDescription", query = "SELECT q FROM QuestionOption q WHERE q.description = :description")})
public class QuestionOption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "question_option_id")
    private Integer questionOptionId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "questionOptionId")
    private Collection<QuestionnaireResponse> questionnaireResponseCollection;
    @OneToMany(mappedBy = "questionOptionId")
    private Collection<ResponseOption> responseOptionCollection;
    @JoinColumn(name = "option_type_id", referencedColumnName = "option_type_id")
    @ManyToOne(optional = false)
    private OptionType optionTypeId;
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    @ManyToOne
    private Question questionId;
    @OneToMany(mappedBy = "parentQuestionOptionId")
    private Collection<QuestionOption> questionOptionCollection;
    @JoinColumn(name = "parent_question_option_id", referencedColumnName = "question_option_id")
    @ManyToOne
    private QuestionOption parentQuestionOptionId;
    @JoinColumn(name = "state_id", referencedColumnName = "state_id")
    @ManyToOne
    private State stateId;

    public QuestionOption() {
    }

    public QuestionOption(Integer questionOptionId) {
        this.questionOptionId = questionOptionId;
    }

    public QuestionOption(Integer questionOptionId, String name) {
        this.questionOptionId = questionOptionId;
        this.name = name;
    }

    public Integer getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(Integer questionOptionId) {
        this.questionOptionId = questionOptionId;
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

    public Collection<QuestionnaireResponse> getQuestionnaireResponseCollection() {
        return questionnaireResponseCollection;
    }

    public void setQuestionnaireResponseCollection(Collection<QuestionnaireResponse> questionnaireResponseCollection) {
        this.questionnaireResponseCollection = questionnaireResponseCollection;
    }

    public Collection<ResponseOption> getResponseOptionCollection() {
        return responseOptionCollection;
    }

    public void setResponseOptionCollection(Collection<ResponseOption> responseOptionCollection) {
        this.responseOptionCollection = responseOptionCollection;
    }

    public OptionType getOptionTypeId() {
        return optionTypeId;
    }

    public void setOptionTypeId(OptionType optionTypeId) {
        this.optionTypeId = optionTypeId;
    }

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }

    public Collection<QuestionOption> getQuestionOptionCollection() {
        return questionOptionCollection;
    }

    public void setQuestionOptionCollection(Collection<QuestionOption> questionOptionCollection) {
        this.questionOptionCollection = questionOptionCollection;
    }

    public QuestionOption getParentQuestionOptionId() {
        return parentQuestionOptionId;
    }

    public void setParentQuestionOptionId(QuestionOption parentQuestionOptionId) {
        this.parentQuestionOptionId = parentQuestionOptionId;
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
        hash += (questionOptionId != null ? questionOptionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionOption)) {
            return false;
        }
        QuestionOption other = (QuestionOption) object;
        if ((this.questionOptionId == null && other.questionOptionId != null) || (this.questionOptionId != null && !this.questionOptionId.equals(other.questionOptionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.QuestionOption[ questionOptionId=" + questionOptionId + " ]";
    }
    
}
