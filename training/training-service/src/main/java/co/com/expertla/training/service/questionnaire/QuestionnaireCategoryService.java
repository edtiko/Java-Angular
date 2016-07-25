package co.com.expertla.training.service.questionnaire;

import co.com.expertla.training.model.dto.QuestionCategoryDto;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.QuestionnaireCategory;
import co.com.expertla.training.model.entities.QuestionnaireQuestion;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireCategoryService {

    /**
     * Create questionnaireCategory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireCategory
     * @return the questionnaireCategory
     * @throws Exception 
     */
    public QuestionnaireCategory create(QuestionnaireCategory questionnaireCategory) throws Exception;
    /**
     * Modify questionnaireCategory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireCategory
     * @return the questionnaireCategory
     * @throws Exception 
     */
    public QuestionnaireCategory merge(QuestionnaireCategory questionnaireCategory) throws Exception;
    /**
     * Delete questionnaireCategory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireCategory
     * @throws Exception 
     */
    public void remove(QuestionnaireCategory questionnaireCategory) throws Exception;
    /**
     * Gets all the records from questionnaireCategory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * Info Update:  <br>
     * date 24/09/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws Exception 
     */
    public QuestionCategoryDto findAll(SePaginator sePaginator) throws Exception;
    /**
     * Gets all the records from questionnaireCategory by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireCategory
     * @return
     * @throws Exception 
     */
    public List<QuestionnaireCategory> findByQuestionnaireCategoryId(QuestionnaireCategory questionnaireCategory) throws Exception; 	
    
    /**
     * Gets all the parent records from questionnaireCategory by questionnaire id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * Info Update:  <br>
     * date 14/09/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireQuestion
     * @return
     * @throws Exception 
     */
    public QuestionCategoryDto findByQuestionnaireId(QuestionnaireQuestion questionnaireQuestion) throws Exception;
    /**
     * Gets all the categories belonging to the category parent id <br>
     * Creation Info:  <br>
     * date 25/09/2015 <br>
     * @author Angela Ramirez
     * @param questionnaireQuestion
     * @return
     * @throws Exception 
     */
    public QuestionCategoryDto findByQuestionnaireParentCategoryId(QuestionnaireQuestion questionnaireQuestion) throws Exception;
  
}