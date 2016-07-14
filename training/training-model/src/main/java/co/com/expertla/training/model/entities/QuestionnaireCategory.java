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
@Table(name = "questionnaire_category")
@NamedQueries({
    @NamedQuery(name = "QuestionnaireCategory.findAll", query = "SELECT q FROM QuestionnaireCategory q"),
    @NamedQuery(name = "QuestionnaireCategory.findByQuestionnaireCategoryId", query = "SELECT q FROM QuestionnaireCategory q WHERE q.questionnaireCategoryId = :questionnaireCategoryId"),
    @NamedQuery(name = "QuestionnaireCategory.findByName", query = "SELECT q FROM QuestionnaireCategory q WHERE q.name = :name"),
    @NamedQuery(name = "QuestionnaireCategory.findByDescription", query = "SELECT q FROM QuestionnaireCategory q WHERE q.description = :description"),
    @NamedQuery(name = "QuestionnaireCategory.findByCreationDate", query = "SELECT q FROM QuestionnaireCategory q WHERE q.creationDate = :creationDate")})
public class QuestionnaireCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "questionnaire_category_id")
    private Integer questionnaireCategoryId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @OneToMany(mappedBy = "questionnaireCategoryId")
    private Collection<QuestionnaireQuestion> questionnaireQuestionCollection;
    @OneToMany(mappedBy = "parentQuestionnaireCategoryI")
    private Collection<QuestionnaireCategory> questionnaireCategoryCollection;
    @JoinColumn(name = "parent_questionnaire_category_i", referencedColumnName = "questionnaire_category_id")
    @ManyToOne
    private QuestionnaireCategory parentQuestionnaireCategoryI;
    @JoinColumn(name = "state_id", referencedColumnName = "state_id")
    @ManyToOne
    private State stateId;

    public QuestionnaireCategory() {
    }

    public QuestionnaireCategory(Integer questionnaireCategoryId) {
        this.questionnaireCategoryId = questionnaireCategoryId;
    }

    public QuestionnaireCategory(Integer questionnaireCategoryId, String name, String description) {
        this.questionnaireCategoryId = questionnaireCategoryId;
        this.name = name;
        this.description = description;
    }

    public Integer getQuestionnaireCategoryId() {
        return questionnaireCategoryId;
    }

    public void setQuestionnaireCategoryId(Integer questionnaireCategoryId) {
        this.questionnaireCategoryId = questionnaireCategoryId;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Collection<QuestionnaireQuestion> getQuestionnaireQuestionCollection() {
        return questionnaireQuestionCollection;
    }

    public void setQuestionnaireQuestionCollection(Collection<QuestionnaireQuestion> questionnaireQuestionCollection) {
        this.questionnaireQuestionCollection = questionnaireQuestionCollection;
    }

    public Collection<QuestionnaireCategory> getQuestionnaireCategoryCollection() {
        return questionnaireCategoryCollection;
    }

    public void setQuestionnaireCategoryCollection(Collection<QuestionnaireCategory> questionnaireCategoryCollection) {
        this.questionnaireCategoryCollection = questionnaireCategoryCollection;
    }

    public QuestionnaireCategory getParentQuestionnaireCategoryI() {
        return parentQuestionnaireCategoryI;
    }

    public void setParentQuestionnaireCategoryI(QuestionnaireCategory parentQuestionnaireCategoryI) {
        this.parentQuestionnaireCategoryI = parentQuestionnaireCategoryI;
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
        hash += (questionnaireCategoryId != null ? questionnaireCategoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionnaireCategory)) {
            return false;
        }
        QuestionnaireCategory other = (QuestionnaireCategory) object;
        if ((this.questionnaireCategoryId == null && other.questionnaireCategoryId != null) || (this.questionnaireCategoryId != null && !this.questionnaireCategoryId.equals(other.questionnaireCategoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.QuestionnaireCategory[ questionnaireCategoryId=" + questionnaireCategoryId + " ]";
    }
    
}
