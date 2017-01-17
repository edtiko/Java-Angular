package co.expertic.training.model.dto;

import co.expertic.training.model.entities.QuestionOption;

/**
 * It's used to obtain only the name and status of the option
 * Creation Info:
 * date 23/09/2015 
 * @author Angela Ramirez 
 */
public class QuestionOptionDTO {
    private Integer questionOptionId;
    private String name;
    private Short stateId;
    private QuestionOptionDTO  parentQuestionOptionId;

    
    public QuestionOptionDTO() {
    }

   public QuestionOptionDTO(Integer questionOptionId, String name, Short stateId) {
        this.questionOptionId = questionOptionId;
        this.name = name;
        this.stateId = stateId;
        
    }


    public static QuestionOptionDTO mapFromQuestionOptionEntity(QuestionOption questionOption) {
        return new QuestionOptionDTO(questionOption.getQuestionOptionId(), questionOption.getName(), questionOption.getStateId());
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

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }
    
}
