package co.expertic.training.model.dto;

import co.expertic.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * It's used to know the content of a questionnaire with its list of categories
 * Creation Info:
 * date 15/09/2015 
 * @author Angela Ramirez 
 */
public class QuestionnaireDTO {
    private Integer questionnaireId;
    private String name;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date creationDate;
    private Short stateId;
    private List<QuestionnaireCategoryDTO> questionnaireCategoryDtoList;
    @JsonIgnore
    private HashMap<Integer, Integer> hashCategory;
    private Integer categoryAmount;
    private Integer questionAmount;
    private Integer responseAmount;
    
    public QuestionnaireDTO() {
        this.questionnaireCategoryDtoList = new ArrayList<>();
        this.hashCategory = new HashMap<>();
    }

    public Integer getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Integer questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<QuestionnaireCategoryDTO> getQuestionnaireCategoryDtoList() {
        return questionnaireCategoryDtoList;
    }

    public void setQuestionnaireCategoryDtoList(List<QuestionnaireCategoryDTO> questionnaireCategoryDtoList) {
        this.questionnaireCategoryDtoList = questionnaireCategoryDtoList;
    }

    public HashMap<Integer, Integer> getHashCategory() {
        return hashCategory;
    }

    public void setHashCategory(HashMap<Integer, Integer> hashCategory) {
        this.hashCategory = hashCategory;
    }

    public Integer getCategoryAmount() {
        return categoryAmount;
    }

    public void setCategoryAmount(Integer categoryAmount) {
        this.categoryAmount = categoryAmount;
    }

    public Integer getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(Integer questionAmount) {
        this.questionAmount = questionAmount;
    }

    public Integer getResponseAmount() {
        return responseAmount;
    }

    public void setResponseAmount(Integer responseAmount) {
        this.responseAmount = responseAmount;
    }
    
}
