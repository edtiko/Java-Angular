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
@Table(name = "question")
@NamedQueries({
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q"),
    @NamedQuery(name = "Question.findByQuestionId", query = "SELECT q FROM Question q WHERE q.questionId = :questionId"),
    @NamedQuery(name = "Question.findByName", query = "SELECT q FROM Question q WHERE q.name = :name"),
    @NamedQuery(name = "Question.findByDescription", query = "SELECT q FROM Question q WHERE q.description = :description"),
    @NamedQuery(name = "Question.findByUnit", query = "SELECT q FROM Question q WHERE q.unit = :unit"),
    @NamedQuery(name = "Question.findByIndAdditional", query = "SELECT q FROM Question q WHERE q.indAdditional = :indAdditional"),
    @NamedQuery(name = "Question.findByQuestionType", query = "SELECT q FROM Question q WHERE q.questionType = :questionType"),
    @NamedQuery(name = "Question.findByCreationDate", query = "SELECT q FROM Question q WHERE q.creationDate = :creationDate")})
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "question_id")
    private Integer questionId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "unit")
    private String unit;
    @Basic(optional = false)
    @Column(name = "ind_additional")
    private int indAdditional;
    @Basic(optional = false)
    @Column(name = "question_type")
    private String questionType;
    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "data_type_id", referencedColumnName = "data_type_id")
    @ManyToOne
    private DataType dataTypeId;
    @JoinColumn(name = "state_id", referencedColumnName = "state_id")
    @ManyToOne
    private State stateId;
    @OneToMany(mappedBy = "questionId")
    private Collection<QuestionnaireQuestion> questionnaireQuestionCollection;
    @OneToMany(mappedBy = "questionId")
    private Collection<QuestionOption> questionOptionCollection;

    public Question() {
    }

    public Question(Integer questionId) {
        this.questionId = questionId;
    }

    public Question(Integer questionId, String name, int indAdditional, String questionType, Date creationDate) {
        this.questionId = questionId;
        this.name = name;
        this.indAdditional = indAdditional;
        this.questionType = questionType;
        this.creationDate = creationDate;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getIndAdditional() {
        return indAdditional;
    }

    public void setIndAdditional(int indAdditional) {
        this.indAdditional = indAdditional;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public DataType getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(DataType dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public State getStateId() {
        return stateId;
    }

    public void setStateId(State stateId) {
        this.stateId = stateId;
    }

    public Collection<QuestionnaireQuestion> getQuestionnaireQuestionCollection() {
        return questionnaireQuestionCollection;
    }

    public void setQuestionnaireQuestionCollection(Collection<QuestionnaireQuestion> questionnaireQuestionCollection) {
        this.questionnaireQuestionCollection = questionnaireQuestionCollection;
    }

    public Collection<QuestionOption> getQuestionOptionCollection() {
        return questionOptionCollection;
    }

    public void setQuestionOptionCollection(Collection<QuestionOption> questionOptionCollection) {
        this.questionOptionCollection = questionOptionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionId != null ? questionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.questionId == null && other.questionId != null) || (this.questionId != null && !this.questionId.equals(other.questionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Question[ questionId=" + questionId + " ]";
    }
    
}
