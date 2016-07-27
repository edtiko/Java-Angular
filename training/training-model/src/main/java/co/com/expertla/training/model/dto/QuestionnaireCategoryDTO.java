package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.QuestionnaireCategory;
import co.com.expertla.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * It's used to know the categories of a questionnaire with the questions within it
 * Creation Info:
 * date 15/09/2015 
 * @author Angela Ramirez 
 */
public class QuestionnaireCategoryDTO {
    private Integer questionnaireCategoryId;
    private String name;
    private String description;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date creationDate;
    private Short stateId;
    private QuestionnaireCategory questionnaireParentId;
    private List<QuestionnaireQuestionDTO> questionnaireQuestionDtoList;
    @JsonIgnore
    private HashMap<Integer,Integer> hashQuestion;

    public QuestionnaireCategoryDTO() {
        this.questionnaireQuestionDtoList = new ArrayList<>();
        this.hashQuestion = new HashMap<>();
    }
    
    public QuestionnaireCategoryDTO(Integer questionnaireCategoryId, String name) {
        this.questionnaireCategoryId = questionnaireCategoryId;
        this.name = name;
    }
    
     public static QuestionnaireCategoryDTO mapFromQuestionnaireCategoryEntity(QuestionnaireCategory e) {
        return new QuestionnaireCategoryDTO(e.getQuestionnaireCategoryId(), e.getName());
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

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }



    public QuestionnaireCategory getQuestionnaireParentId() {
        return questionnaireParentId;
    }

    public void setQuestionnaireParentId(QuestionnaireCategory questionnaireParentId) {
        this.questionnaireParentId = questionnaireParentId;
    }

    public List<QuestionnaireQuestionDTO> getQuestionnaireQuestionDtoList() {
        return questionnaireQuestionDtoList;
    }

    public void setQuestionnaireQuestionDtoList(List<QuestionnaireQuestionDTO> questionnaireQuestionDtoList) {
        this.questionnaireQuestionDtoList = questionnaireQuestionDtoList;
    }

    public HashMap<Integer, Integer> getHashQuestion() {
        return hashQuestion;
    }

    public void setHashQuestion(HashMap<Integer, Integer> hashQuestion) {
        this.hashQuestion = hashQuestion;
    }
    
}
