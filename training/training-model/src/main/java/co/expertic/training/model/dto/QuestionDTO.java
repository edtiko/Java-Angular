/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

import co.expertic.training.model.entities.Question;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public class QuestionDTO {
    
    private Integer questionId;
    private String name;
    private String description;
    private String questionType;
    private String dataType;
    private List<QuestionOptionDTO> questionOptionList;
    
    public QuestionDTO() {
    }

   public QuestionDTO(Integer questionId, String name, String description, String questionType, String dataType) {
        this.questionId = questionId;
        this.description = description;
        this.name = name;
        this.questionType = questionType;
        this.dataType = dataType;

        
    }


    public static QuestionDTO mapFromQuestionEntity(Question question) {
        return new QuestionDTO(question.getQuestionId(), question.getName(), question.getDescription(), question.getQuestionType(), question.getDataTypeId().getName());
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
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

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
    public List<QuestionOptionDTO> getQuestionOptionList() {
        return questionOptionList;
    }

    public void setQuestionOptionList(List<QuestionOptionDTO> questionOptionList) {
        this.questionOptionList = questionOptionList;
    }
    
    
    
}
