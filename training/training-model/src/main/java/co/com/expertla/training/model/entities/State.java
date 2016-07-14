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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "state")
@NamedQueries({
    @NamedQuery(name = "State.findAll", query = "SELECT s FROM State s"),
    @NamedQuery(name = "State.findByStateId", query = "SELECT s FROM State s WHERE s.stateId = :stateId"),
    @NamedQuery(name = "State.findByName", query = "SELECT s FROM State s WHERE s.name = :name"),
    @NamedQuery(name = "State.findByDescription", query = "SELECT s FROM State s WHERE s.description = :description")})
public class State implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "state_id")
    private Integer stateId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "stateId")
    private Collection<Questionnaire> questionnaireCollection;
    @OneToMany(mappedBy = "stateId")
    private Collection<TrainingPlanUser> trainingPlanUserCollection;
    @OneToMany(mappedBy = "stateId")
    private Collection<Membership> membershipCollection;
    @OneToMany(mappedBy = "stateId")
    private Collection<VideoUser> videoUserCollection;
    @OneToMany(mappedBy = "stateId")
    private Collection<Question> questionCollection;
    @OneToMany(mappedBy = "stateId")
    private Collection<QuestionnaireQuestion> questionnaireQuestionCollection;
    @OneToMany(mappedBy = "stateId")
    private Collection<QuestionOption> questionOptionCollection;
    @OneToMany(mappedBy = "stateId")
    private Collection<QuestionnaireCategory> questionnaireCategoryCollection;
    @OneToMany(mappedBy = "stateId")
    private Collection<User> userCollection;

    public State() {
    }

    public State(Integer stateId) {
        this.stateId = stateId;
    }

    public State(Integer stateId, String name) {
        this.stateId = stateId;
        this.name = name;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
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

    public Collection<Questionnaire> getQuestionnaireCollection() {
        return questionnaireCollection;
    }

    public void setQuestionnaireCollection(Collection<Questionnaire> questionnaireCollection) {
        this.questionnaireCollection = questionnaireCollection;
    }

    public Collection<TrainingPlanUser> getTrainingPlanUserCollection() {
        return trainingPlanUserCollection;
    }

    public void setTrainingPlanUserCollection(Collection<TrainingPlanUser> trainingPlanUserCollection) {
        this.trainingPlanUserCollection = trainingPlanUserCollection;
    }

    public Collection<Membership> getMembershipCollection() {
        return membershipCollection;
    }

    public void setMembershipCollection(Collection<Membership> membershipCollection) {
        this.membershipCollection = membershipCollection;
    }

    public Collection<VideoUser> getVideoUserCollection() {
        return videoUserCollection;
    }

    public void setVideoUserCollection(Collection<VideoUser> videoUserCollection) {
        this.videoUserCollection = videoUserCollection;
    }

    public Collection<Question> getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(Collection<Question> questionCollection) {
        this.questionCollection = questionCollection;
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

    public Collection<QuestionnaireCategory> getQuestionnaireCategoryCollection() {
        return questionnaireCategoryCollection;
    }

    public void setQuestionnaireCategoryCollection(Collection<QuestionnaireCategory> questionnaireCategoryCollection) {
        this.questionnaireCategoryCollection = questionnaireCategoryCollection;
    }

    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stateId != null ? stateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof State)) {
            return false;
        }
        State other = (State) object;
        if ((this.stateId == null && other.stateId != null) || (this.stateId != null && !this.stateId.equals(other.stateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.State[ stateId=" + stateId + " ]";
    }
    
}
