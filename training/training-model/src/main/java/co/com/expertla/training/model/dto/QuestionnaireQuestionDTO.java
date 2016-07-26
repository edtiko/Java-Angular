package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class QuestionnaireQuestionDTO {
    private Integer questionnaireQuestionId;
    private int questionOrder;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date creationDate;
    private Short stateId;
    private QuestionDTO questionId;
    private QuestionnaireResponseDTO questionnaireResponse;
    @JsonIgnore
    private HashMap<Integer,Integer> hashOption;
    private List<QuestionOptionDTO> questionOptionList;
    private Integer questionnaireCategoryId;
    
    public QuestionnaireQuestionDTO () {
        this.questionOptionList = new ArrayList<>();
        this.hashOption = new HashMap<>();
    }
    
     public QuestionnaireQuestionDTO(Integer questionnaireQuestionId, int questionOrder, QuestionDTO questionId, QuestionnaireResponseDTO questionnaireResponse,
                                     List<QuestionOptionDTO> questionOptionList, Integer questionnaireCategoryId) {
        this.questionnaireQuestionId = questionnaireQuestionId;
        this.questionOrder = questionOrder;
        this.questionId = questionId;
        this.questionnaireResponse = questionnaireResponse;
        this.questionOptionList = questionOptionList;
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

    public List<QuestionOptionDTO> getQuestionOptionList() {
        return questionOptionList;
    }

    public void setQuestionOptionList(List<QuestionOptionDTO> questionOptionList) {
        this.questionOptionList = questionOptionList;
    }

    public Integer getQuestionnaireCategoryId() {
        return questionnaireCategoryId;
    }

    public void setQuestionnaireCategoryId(Integer questionnaireCategoryId) {
        this.questionnaireCategoryId = questionnaireCategoryId;
    }
    
 
}
