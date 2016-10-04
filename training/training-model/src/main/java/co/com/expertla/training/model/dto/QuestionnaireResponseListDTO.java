/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public class QuestionnaireResponseListDTO {
    
   private List<QuestionnaireQuestionDTO> questionnaireQuestionList;
   
    public QuestionnaireResponseListDTO() {
        questionnaireQuestionList = new ArrayList<>();
    }

    public List<QuestionnaireQuestionDTO> getQuestionnaireQuestionList() {
        return questionnaireQuestionList;
    }

    public void setQuestionnaireQuestionList(List<QuestionnaireQuestionDTO> questionnaireQuestionList) {
        this.questionnaireQuestionList = questionnaireQuestionList;
    }

 

    
}
