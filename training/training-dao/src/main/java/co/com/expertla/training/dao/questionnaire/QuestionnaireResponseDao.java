package co.com.expertla.training.dao.questionnaire;

import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.QuestionnaireCategory;
import co.com.expertla.training.model.entities.QuestionnaireQuestion;
import co.com.expertla.training.model.entities.QuestionnaireResponse;
import java.util.List;


public interface QuestionnaireResponseDao {
    
    /**
     * Create questionnaireResponse <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponse
     * @return the questionnaireResponse created
     * @throws DAOException 
     */
    public QuestionnaireResponse create(QuestionnaireResponse questionnaireResponse) throws DAOException;
	
                
    public List<QuestionnaireResponse> create(List<QuestionnaireResponse> questionnaireResponse) throws DAOException;
    /**
     * Modify questionnaireResponse <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponse
     * @return the questionnaireResponse updated
     * @throws DAOException 
     */
    public QuestionnaireResponse merge(QuestionnaireResponse questionnaireResponse) throws DAOException;
	
    /**
     * Delete questionnaireResponse <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponse
     * @throws DAOException 
     */
    public void remove(QuestionnaireResponse questionnaireResponse) throws DAOException;
    
    /**
     * Gets all the records from questionnaireResponse <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireResponse> findAll(SePaginator sePaginator) throws DAOException;
	
    /**
     * Gets all the records from questionnaireResponse by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponse
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireResponse> findByQuestionnaireResponseId(Integer questionnaireResponseId) throws DAOException; 
    
    /** 
     * Gets all the records from questionnaireResponse by questionnaireCategoryId <br>
     * Creation Info: <br>
     * date 26/08/2015 <br>
     * @author Andres Felipe Lopez Rodriguez
     *
     * @param questionnaireCategory
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireResponse> findByQuestionnaireCategoryId(Integer questionnaireCategory) throws DAOException; 
    
    /** 
     * Gets all the records from questionnaireResponse by questionnaireCategoryId <br>
     * Creation Info: <br>
     * date 26/08/2015 <br>
     * @author Andres Felipe Lopez Rodriguez
     *
     * @param questionnaireCategory
     * @param questionnaireQuestion
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireResponse> findByCategoryIdAndQuestionId(Integer questionnaireCategory, Integer questionnaireQuestion) throws DAOException; 
    
    /** 
     * Gets all the records from questionnaireResponse by questionnaireCategoryId and userId <br>
     * Creation Info: <br>
     * date 26/08/2015 <br>
     * @author Andres Felipe Lopez Rodriguez
     *
     * @param questionnaireResponse
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireResponse> findByUserIdAndCategoryId(QuestionnaireResponse questionnaireResponse) throws DAOException;
    /**
     * Gets the responses by questionnaireQuestionId and userId <br>
     * Creation Info: <br>
     * date 30/09/2015 <br>
     * @author Angela Ramirez
     * @param questionnaireQuestionId
     * @param seUserId
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireResponse> findByUserIdAndQuestionnaireQuestionId(List<Integer> questionnaireQuestionId, Integer seUserId) throws DAOException;

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