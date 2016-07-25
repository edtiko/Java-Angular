package co.com.expertla.training.service.questionnaire;

import co.com.expertla.training.model.dto.QuestionnaireResponseDto;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.QuestionnaireCategory;
import co.com.expertla.training.model.entities.QuestionnaireQuestion;
import co.com.expertla.training.model.entities.QuestionnaireResponse;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireResponseService {

    /**
     * Create questionnaireResponse <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * @param questionnaireResponseDto
     * @return the questionnaireResponse created
     * @throws Exception 
     */
    public List<QuestionnaireResponse> create(List<QuestionnaireResponseDto> questionnaireResponseDto) throws Exception;
    /**
     * Modify questionnaireResponse <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponse
     * @return the questionnaireResponse updated
     * @throws Exception 
     */
    public QuestionnaireResponse merge(QuestionnaireResponse questionnaireResponse) throws Exception;
    /**
     * Delete questionnaireResponse <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponse
     * @throws Exception 
     */
    public void remove(QuestionnaireResponse questionnaireResponse) throws Exception;
    /**
     * Gets all the records from questionnaireResponse <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws Exception 
     */
    public List<QuestionnaireResponse> findAll(SePaginator sePaginator) throws Exception;
    /**
     * Gets all the records from questionnaireResponse by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponse
     * @return
     * @throws Exception 
     */
    public List<QuestionnaireResponse> findByQuestionnaireResponseId(Integer questionnaireResponse) throws Exception; 	
    
    /**
     * Gets all the records from questionnaireResponse by questionnaireCategoryId <br>
     * Creation Info:  <br>
     * date 25/08/2015 <br>
     * @author Andres Felipe Lopez Rodriguez
     * 
     * @param questionnaireCategory
     * @return
     * @throws Exception 
     */
    public List<QuestionnaireResponse> findByQuestionnaireCategoryId(Integer questionnaireCategory) throws Exception; 
    
    /**
     * Gets all the records from questionnaireResponse by questionnaireCategoryId and questionnaireQuestionId <br>
     * Creation Info:  <br>
     * date 25/08/2015 <br>
     * @author Andres Felipe Lopez Rodriguez
     * 
     * @param questionnaireCategory
     * @param questionnaireQuestion
     * @return
     * @throws Exception 
     */
    public List<QuestionnaireResponse> findByCategoryIdAndQuestionId(Integer questionnaireCategory, Integer questionnaireQuestion) throws Exception; 
    
    /**
     * Gets all the records from questionnaireResponse by questionnaireCategoryId and userId <br>
     * Creation Info:  <br>
     * date 25/08/2015 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param questionnaireResponse
     * @return
     * @throws Exception 
     */
    public List<QuestionnaireResponse> findByUserIdAndCategoryId(QuestionnaireResponse questionnaireResponse) throws Exception; 
    
    /**
     * Creates the history of the questionnaire response by questionnaireQuestion and userId <br>
     * Creation Info:  <br>
     * date 25/11/2015 <br>
     * @author Angela Ramirez
     * @param questionnaireQuestionIds
     * @param seUserId
     * @throws Exception 
     */
    public void createQuestionnaireResponseHistory(String questionnaireQuestionIds, Integer seUserId) throws Exception;
    
    /**
     * Creates the history of the response option by questionnaireQuestion and userId <br>
     * Creation Info:  <br>
     * date 25/11/2015 <br>
     * @author Angela Ramirez
     * @param questionnaireQuestionIds
     * @param seUserId
     * @throws Exception 
     */
    public void createResponseOptionHistory(String questionnaireQuestionIds, Integer seUserId) throws Exception;
    
    /**
     * Deletes the questionnaire response by questionnaireQuestion and userId <br>
     * Creation Info:  <br>
     * date 25/11/2015 <br>
     * @author Angela Ramirez
     * @param questionnaireQuestionIds
     * @param seUserId
     * @throws Exception 
     */
    public void deleteQuestionnaireResponse(String questionnaireQuestionIds, Integer seUserId) throws Exception;
    
    /**
     * Deletes the response option by questionnaireQuestion and userId <br>
     * Creation Info:  <br>
     * date 25/11/2015 <br>
     * @author Angela Ramirez
     * @param questionnaireQuestionIds
     * @param seUserId
     * @throws Exception 
     */
    public void deleteResponseOption(String questionnaireQuestionIds, Integer seUserId) throws Exception;
}