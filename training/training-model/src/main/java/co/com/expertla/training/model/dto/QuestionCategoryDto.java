package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.QuestionnaireCategory;
import java.util.List;

/**
 * It's used to know the total question amount and list of categories in a questionnaire
 * Creation Info:
 * date 14/09/2015 
 * @author Angela Ramirez 
 */
public class QuestionCategoryDto {
    private Long totalQuestionAmount;
    private Integer categoryAmount;
    private List<QuestionnaireCategory> questionnaireCategoryList;

    public Long getTotalQuestionAmount() {
        return totalQuestionAmount;
    }

    public void setTotalQuestionAmount(Long totalQuestionAmount) {
        this.totalQuestionAmount = totalQuestionAmount;
    }

    public List<QuestionnaireCategory> getQuestionnaireCategoryList() {
        return questionnaireCategoryList;
    }

    public void setQuestionnaireCategoryList(List<QuestionnaireCategory> questionnaireCategoryList) {
        this.questionnaireCategoryList = questionnaireCategoryList;
    }

    public Integer getCategoryAmount() {
        return categoryAmount;
    }

    public void setCategoryAmount(Integer categoryAmount) {
        this.categoryAmount = categoryAmount;
    }
       
}
