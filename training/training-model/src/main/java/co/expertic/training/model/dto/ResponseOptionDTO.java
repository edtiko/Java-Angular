package co.expertic.training.model.dto;

/**
 * Creation Info: <br>
 * date 13/10/2015 <br>
 * @author Angela Ramirez
 */
public class ResponseOptionDTO {
    private Integer responseOptionId;
    private QuestionOptionDTO questionOptionId;
    
     public ResponseOptionDTO(Integer responseOptionId, QuestionOptionDTO questionOptionId) {
        this.responseOptionId = responseOptionId;
        this.questionOptionId = questionOptionId;

        
    }

    public ResponseOptionDTO() {
        
    }
    

    public Integer getResponseOptionId() {
        return responseOptionId;
    }

    public void setResponseOptionId(Integer responseOptionId) {
        this.responseOptionId = responseOptionId;
    }

    public QuestionOptionDTO getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(QuestionOptionDTO questionOptionId) {
        this.questionOptionId = questionOptionId;
    }
    
    
}
