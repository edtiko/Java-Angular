package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.QuestionnaireResponse;
import co.com.expertla.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class QuestionnaireQuestionDTO {
    
    private Integer number;
    private Integer questionnaireQuestionId;
    private QuestionnaireDTO questionnaireId;
    private int questionOrder;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date creationDate;
    private Short stateId;
    private QuestionDTO questionId;
    private QuestionnaireResponseDTO questionnaireResponse;
    @JsonIgnore
    private HashMap<Integer,Integer> hashOption;
    
    private QuestionnaireCategoryDTO questionnaireCategoryId;
    private Collection<QuestionnaireResponse> questionnaireResponseList;
    
    public QuestionnaireQuestionDTO () {
        this.hashOption = new HashMap<>();
    }
    
     public QuestionnaireQuestionDTO(Integer questionnaireQuestionId, int questionOrder, QuestionDTO questionId, QuestionnaireResponseDTO questionnaireResponse,
                                     List<QuestionOptionDTO> questionOptionList, QuestionnaireCategoryDTO questionnaireCategoryId) {
        this.questionnaireQuestionId = questionnaireQuestionId;
        this.questionOrder = questionOrder;
        this.questionId = questionId;
        this.questionnaireResponse = questionnaireResponse;
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

    public QuestionnaireResponseDTO getQuestionnaireResponse() {
        return questionnaireResponse;
    }

    public void setQuestionnaireResponse(QuestionnaireResponseDTO questionnaireResponse) {
        this.questionnaireResponse = questionnaireResponse;
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

    public Collection<QuestionnaireResponse> getQuestionnaireResponseList() {
        return questionnaireResponseList;
    }

    public void setQuestionnaireResponseList(Collection<QuestionnaireResponse> questionnaireResponseList) {
        this.questionnaireResponseList = questionnaireResponseList;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    
    
 
}
