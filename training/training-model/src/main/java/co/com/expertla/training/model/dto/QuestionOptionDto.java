package co.com.expertla.training.model.dto;

/**
 * It's used to obtain only the name and status of the option
 * Creation Info:
 * date 23/09/2015 
 * @author Angela Ramirez 
 */
public class QuestionOptionDto {
    private Integer questionOptionId;
    private String name;
    private Short stateId;
    private String label;

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


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
}
