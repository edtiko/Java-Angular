package co.expertic.training.service.impl.questionnaire;

import co.expertic.training.dao.questionnaire.QuestionnaireCategoryDao;
import co.expertic.training.model.dto.QuestionCategoryDTO;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.QuestionnaireCategory;
import co.expertic.training.model.entities.QuestionnaireQuestion;
import co.expertic.training.service.questionnaire.QuestionnaireCategoryService;
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
    public QuestionCategoryDTO findAll(SePaginator sePaginator) throws Exception {
        List<QuestionnaireCategory> resultList = questionnaireCategoryDao.findAll(sePaginator);
        QuestionCategoryDTO dtoObject = new QuestionCategoryDTO();
        dtoObject.setCategoryAmount(resultList.size());
        dtoObject.setQuestionnaireCategoryList(resultList);
        return dtoObject;
    }

    @Override
    public List<QuestionnaireCategory> findByQuestionnaireCategoryId(QuestionnaireCategory questionnaireCategory) throws Exception {
        return questionnaireCategoryDao.findByQuestionnaireCategoryId(questionnaireCategory);
    }

    @Override
    public QuestionCategoryDTO findByQuestionnaireId(QuestionnaireQuestion questionnaireQuestion) throws Exception {
        List<QuestionnaireCategory> categoryList = questionnaireCategoryDao.findByQuestionnaireId(questionnaireQuestion);
        QuestionCategoryDTO dtoObject = new QuestionCategoryDTO();
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
    public QuestionCategoryDTO findByQuestionnaireParentCategoryId(QuestionnaireQuestion questionnaireQuestion) throws Exception {
        List<QuestionnaireCategory> categoryList = questionnaireCategoryDao.findByQuestionnaireParentCategoryId(questionnaireQuestion);
        QuestionCategoryDTO dtoObject = new QuestionCategoryDTO();
 
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
