package co.com.expertla.training.model.dto;

/**
 * Creation Info: <br>
 * date 13/10/2015 <br>
 * @author Angela Ramirez
 */
public class ResponseOptionDto {
    private Integer responseOptionId;
    private QuestionOptionDto questionOptionId;

    public Integer getResponseOptionId() {
        return responseOptionId;
    }

    public void setResponseOptionId(Integer responseOptionId) {
        this.responseOptionId = responseOptionId;
    }

    public QuestionOptionDto getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(QuestionOptionDto questionOptionId) {
        this.questionOptionId = questionOptionId;
    }
    
    
}
