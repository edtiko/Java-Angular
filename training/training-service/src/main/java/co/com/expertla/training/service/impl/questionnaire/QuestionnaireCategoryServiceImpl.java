package co.com.expertla.training.service.impl.questionnaire;

import co.com.expertla.training.dao.questionnaire.QuestionnaireCategoryDao;
import co.com.expertla.training.model.dto.QuestionCategoryDto;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.QuestionnaireCategory;
import co.com.expertla.training.model.entities.QuestionnaireQuestion;
import co.com.expertla.training.service.questionnaire.QuestionnaireCategoryService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireCategoryServiceImpl implements QuestionnaireCategoryService{

    @Autowired
    private QuestionnaireCategoryDao questionnaireCategoryDao;

    @Override
    public QuestionnaireCategory create(QuestionnaireCategory questionnaireCategory) throws Exception {
        return questionnaireCategoryDao.create(questionnaireCategory);
    }

    @Override
    public QuestionnaireCategory merge(QuestionnaireCategory questionnaireCategory) throws Exception {
        return questionnaireCategoryDao.merge(questionnaireCategory);
    }

    @Override
    public void remove(QuestionnaireCategory questionnaireCategory) throws Exception {
        questionnaireCategoryDao.remove(questionnaireCategory);
    }

    @Override
    public QuestionCategoryDto findAll(SePaginator sePaginator) throws Exception {
        List<QuestionnaireCategory> resultList = questionnaireCategoryDao.findAll(sePaginator);
        QuestionCategoryDto dtoObject = new QuestionCategoryDto();
        dtoObject.setCategoryAmount(resultList.size());
        dtoObject.setQuestionnaireCategoryList(resultList);
        return dtoObject;
    }

    @Override
    public List<QuestionnaireCategory> findByQuestionnaireCategoryId(QuestionnaireCategory questionnaireCategory) throws Exception {
        return questionnaireCategoryDao.findByQuestionnaireCategoryId(questionnaireCategory);
    }

    @Override
    public QuestionCategoryDto findByQuestionnaireId(QuestionnaireQuestion questionnaireQuestion) throws Exception {
        List<QuestionnaireCategory> categoryList = questionnaireCategoryDao.findByQuestionnaireId(questionnaireQuestion);
        QuestionCategoryDto dtoObject = new QuestionCategoryDto();
        if (categoryList != null && !categoryList.isEmpty()) {
            List<Integer> idList = categoryListToIntegerList(categoryList);
            List<QuestionnaireCategory> categoryChildrenList = questionnaireCategoryDao.findCategoryChildrenByQuestionnaireCategoryId(idList);

            if (questionnaireQuestion.getQuestionnaireCategoryId() != null
                    && questionnaireQuestion.getQuestionnaireCategoryId().getQuestionnaireCategoryId() != null) {
                dtoObject.setQuestionnaireCategoryList(categoryChildrenList);
            } else {
                dtoObject.setQuestionnaireCategoryList(categoryList);
            }
            
        }

        return dtoObject;
    }

    @Override
    public QuestionCategoryDto findByQuestionnaireParentCategoryId(QuestionnaireQuestion questionnaireQuestion) throws Exception {
        List<QuestionnaireCategory> categoryList = questionnaireCategoryDao.findByQuestionnaireParentCategoryId(questionnaireQuestion);
        QuestionCategoryDto dtoObject = new QuestionCategoryDto();
 
        dtoObject.setCategoryAmount(categoryList.size());
        dtoObject.setQuestionnaireCategoryList(categoryList);
        return dtoObject;
    }

    private List<Integer> categoryListToIntegerList(List<QuestionnaireCategory> list) {
        List<Integer> intList = new ArrayList<>();
        list.stream().forEach((category) -> {
            intList.add(category.getQuestionnaireCategoryId());
        });
        return intList;
    }

}
