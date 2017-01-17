package co.expertic.training.service.questionnaire;

import co.expertic.training.model.dto.QuestionnaireQuestionDTO;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.Questionnaire;
import co.expertic.training.model.entities.QuestionnaireQuestion;
import co.expertic.training.model.entities.QuestionnaireResponse;
import java.util.List;


public interface QuestionnaireQuestionService {

    /**
     * Create questionnaireQuestion <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireQuestion
     * @return the questionnaireQuestion created
     * @throws Exception 
     */
    public void create(List<QuestionnaireQuestionDTO> questionnaireQuestion) throws Exception;
    /**
     * Modify questionnaireQuestion <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireQuestion
     * @return the questoinnaireQuestion updated
     * @throws Exception 
     */
    public QuestionnaireQuestion merge(QuestionnaireQuestion questionnaireQuestion) throws Exception;
    /**
     * Delete questionnaireQuestion <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireQuestion
     * @throws Exception 
     */
    public void remove(QuestionnaireQuestion questionnaireQuestion) throws Exception;
    /**
     * Gets all the records from questionnaireQuestion <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws Exception 
     */
    public List<QuestionnaireQuestion> findAll(SePaginator sePaginator) throws Exception;
    
   public List<QuestionnaireQuestionDTO> findByDisciplineId(Integer disciplineId, Integer userId) throws Exception;
    /**
     * Gets all the records from questionnaireQuestion by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireQuestion
     * @return
     * @throws Exception 
     */
    public List<QuestionnaireQuestion> findByQuestionnaireQuestionId(Integer questionnaireQuestion) throws Exception; 	
    /**
     * Gets the records from questionnaireQuestion by questionnaireId and Category id <br>
     * Creation Info:  <br>
     * date 25/08/2015 <br>
     * Update Info:  <br>
     * date 03/09/2015 <br>
     * @author Andres Felipe Lopez Rodriguez
     * 
     * @param questionnaireQuestion
     * @return
     * @throws Exception 
     */
    public List<QuestionnaireQuestion> findByQuestionnaireIdAndCategoryId(QuestionnaireQuestion questionnaireQuestion) throws Exception; 	
    /**
     * Gets the records from questionnaireQuestion by questionnaireId 
     * Creation Info:  <br>
     * date 15/09/2015 <br>
     * @author Angela Ramirez
     * @param questionnaire
     * @return
     * @throws Exception 
     */
    public List<QuestionnaireQuestion> findByQuestionnaireId(Questionnaire questionnaire) throws Exception; 	
    /**
     * Gets the questions by questionnaireCategoryId and questionnaireId <br>
     * Creation Info:  <br>
     * date 29/09/2015 <br>
     * @author Angela Ramirez 
     * @param questionnaireResponse
     * @return
     * @throws Exception 
     */
    public List<QuestionnaireQuestionDTO> findByCategoryIdAndQuestionnaireIdAndUserId(QuestionnaireResponse questionnaireResponse) throws Exception;
}