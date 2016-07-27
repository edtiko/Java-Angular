/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.Question;

/**
 *
 * @author Edwin G
 */
public class QuestionDTO {
    
    private Integer questionId;
    private String name;
    private String description;
    private String questionType;
    private Integer dataTypeId;
    
    public QuestionDTO() {
    }

   public QuestionDTO(Integer questionId, String name, String description, String questionType, Integer dataTypeId) {
        this.questionId = questionId;
        this.description = description;
        this.name = name;
        this.questionType = questionType;
        this.dataTypeId = dataTypeId;

        
    }


    public static QuestionDTO mapFromQuestionEntity(Question question) {
        return new QuestionDTO(question.getQuestionId(), question.getName(), question.getDescription(), question.getQuestionType(), question.getDataTypeId().getDataTypeId());
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

    public Integer getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
    }
    
    
    
}
