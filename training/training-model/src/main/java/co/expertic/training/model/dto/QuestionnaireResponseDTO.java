package co.expertic.training.model.dto;

import co.expertic.training.model.entities.QuestionnaireResponse;
import java.util.ArrayList;
import java.util.List;


public class QuestionnaireResponseDTO {
    
    private List<ResponseOptionDTO> responseOptionList;
    private Integer questionnaireResponseId;
    private String response;
    private Integer questionOptionId;
    private Short responseTypeId;
    private Integer userId;
    private Boolean checked;
    private Integer questionnaireQuestionId;
    
    public QuestionnaireResponseDTO() {
        responseOptionList = new ArrayList<>();
    }
    
      public QuestionnaireResponseDTO(Integer questionnaireResponseId, String response, Integer questionOptionId, 
              Short responseTypeId, Integer userId, Integer questionnaireQuestionId) {
        this.questionnaireResponseId = questionnaireResponseId;
        this.response = response;
        this.questionOptionId = questionOptionId;
        this.responseTypeId = responseTypeId;
        this.userId = userId;
        this.checked = true;
        this.questionnaireQuestionId = questionnaireQuestionId;

        
    }


    public static QuestionnaireResponseDTO mapFromQuestionnaireResponseEntity(QuestionnaireResponse question) {
        return new QuestionnaireResponseDTO(question.getQuestionnaireResponseId(), question.getResponse(), 
                question.getQuestionOptionId()!=null?question.getQuestionOptionId().getQuestionOptionId():null,
                question.getResponseTypeId(), question.getUserId()!=null?question.getUserId().getUserId():null,
                question.getQuestionnaireQuestionId().getQuestionnaireQuestionId()
        );
    }
    
    public List<ResponseOptionDTO> getResponseOptionList() {
        return responseOptionList;
    }

    public void setResponseOptionList(List<ResponseOptionDTO> responseOptionList) {
        this.responseOptionList = responseOptionList;
    }

    public Integer getQuestionnaireResponseId() {
        return questionnaireResponseId;
    }

    public void setQuestionnaireResponseId(Integer questionnaireResponseId) {
        this.questionnaireResponseId = questionnaireResponseId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(Integer questionOptionId) {
        this.questionOptionId = questionOptionId;
    }

    public Short getResponseTypeId() {
        return responseTypeId;
    }

    public void setResponseTypeId(Short responseTypeId) {
        this.responseTypeId = responseTypeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getQuestionnaireQuestionId() {
        return questionnaireQuestionId;
    }

    public void setQuestionnaireQuestionId(Integer questionnaireQuestionId) {
        this.questionnaireQuestionId = questionnaireQuestionId;
    }
    
    
    
}
