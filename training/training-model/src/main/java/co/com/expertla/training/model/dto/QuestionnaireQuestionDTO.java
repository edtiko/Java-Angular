package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class QuestionnaireQuestionDTO  implements Serializable{
    
    private Integer number;
    private Integer questionnaireQuestionId;
    private QuestionnaireDTO questionnaireId;
    private String questionnaire;
    private int questionOrder;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date creationDate;
    private Short stateId;
    private QuestionDTO questionId;
    @JsonIgnore
    private HashMap<Integer,Integer> hashOption;
    private Integer userId;
    private QuestionnaireCategoryDTO questionnaireCategoryId;
    private List<QuestionnaireResponseDTO> questionnaireResponseList;
    
    public QuestionnaireQuestionDTO () {
        this.hashOption = new HashMap<>();
    }
    
     public QuestionnaireQuestionDTO(Integer questionnaireQuestionId, int questionOrder, QuestionDTO questionId, List<QuestionnaireResponseDTO> questionnaireResponse,
                                     List<QuestionOptionDTO> questionOptionList, QuestionnaireCategoryDTO questionnaireCategoryId) {
        this.questionnaireQuestionId = questionnaireQuestionId;
        this.questionOrder = questionOrder;
        this.questionId = questionId;
        this.questionnaireResponseList = questionnaireResponse;
        this.questionnaireCategoryId = questionnaireCategoryId;
        
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

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }


    public QuestionDTO getQuestionId() {
        return questionId;
    }

    public void setQuestionId(QuestionDTO questionId) {
        this.questionId = questionId;
    }

    public List<QuestionnaireResponseDTO> getQuestionnaireResponseList() {
        return questionnaireResponseList;
    }

    public void setQuestionnaireResponseList(List<QuestionnaireResponseDTO> questionnaireResponse) {
        this.questionnaireResponseList = questionnaireResponse;
    }

    public HashMap<Integer, Integer> getHashOption() {
        return hashOption;
    }

    public void setHashOption(HashMap<Integer, Integer> hashOption) {
        this.hashOption = hashOption;
    }

    public QuestionnaireCategoryDTO getQuestionnaireCategoryId() {
        return questionnaireCategoryId;
    }

    public void setQuestionnaireCategoryId(QuestionnaireCategoryDTO questionnaireCategoryId) {
        this.questionnaireCategoryId = questionnaireCategoryId;
    }

    public QuestionnaireDTO getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(QuestionnaireDTO questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(String questionnaire) {
        this.questionnaire = questionnaire;
    }
    
    
 
}
