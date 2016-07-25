package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.Question;
import co.com.expertla.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * It's used to bring the neccessary information about the questionnaire question
 * Creation Info:
 * date 15/09/2015 
 * @author Angela Ramirez 
 */
public class QuestionnaireQuestionDto {
    private Integer questionnaireQuestionId;
    private int questionOrder;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date creationDate;
    private Short stateId;
    private Question questionId;
    private QuestionnaireResponseDto questionnaireResponse;
    @JsonIgnore
    private HashMap<Integer,Integer> hashOption;
    private List<QuestionOptionDto> questionOptionList;
    private Integer questionnaireCategoryId;
    
    public QuestionnaireQuestionDto () {
        this.questionOptionList = new ArrayList<>();
        this.hashOption = new HashMap<>();
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


    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }

    public QuestionnaireResponseDto getQuestionnaireResponse() {
        return questionnaireResponse;
    }

    public void setQuestionnaireResponse(QuestionnaireResponseDto questionnaireResponse) {
        this.questionnaireResponse = questionnaireResponse;
    }

    public HashMap<Integer, Integer> getHashOption() {
        return hashOption;
    }

    public void setHashOption(HashMap<Integer, Integer> hashOption) {
        this.hashOption = hashOption;
    }

    public List<QuestionOptionDto> getQuestionOptionList() {
        return questionOptionList;
    }

    public void setQuestionOptionList(List<QuestionOptionDto> questionOptionList) {
        this.questionOptionList = questionOptionList;
    }

    public Integer getQuestionnaireCategoryId() {
        return questionnaireCategoryId;
    }

    public void setQuestionnaireCategoryId(Integer questionnaireCategoryId) {
        this.questionnaireCategoryId = questionnaireCategoryId;
    }
    
 
}
