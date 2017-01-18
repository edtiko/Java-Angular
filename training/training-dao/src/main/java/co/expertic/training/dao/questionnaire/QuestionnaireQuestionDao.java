package co.expertic.training.dao.questionnaire;

import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.dto.QuestionnaireQuestionFormatDTO;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.Questionnaire;
import co.expertic.training.model.entities.QuestionnaireQuestion;
import co.expertic.training.model.entities.QuestionnaireResponse;
import java.util.List;


public interface QuestionnaireQuestionDao {
    
    /**
     * Create questionnaireQuestion <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireQuestion
     * @return the questionnaireQuestion created
     * @throws DAOException 
     */
    public QuestionnaireQuestion create(QuestionnaireQuestion questionnaireQuestion) throws DAOException;
	
    /**
     * Modify questionnaireQuestion <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireQuestion
     * @return the questionnaire updated 
     * @throws DAOException 
     */
    public QuestionnaireQuestion merge(QuestionnaireQuestion questionnaireQuestion) throws DAOException;
	
    /**
     * Delete questionnaireQuestion <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireQuestion
     * @throws DAOException 
     */
    public void remove(QuestionnaireQuestion questionnaireQuestion) throws DAOException;
    
    /**
     * Gets all the records from questionnaireQuestion <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireQuestion> findAll(SePaginator sePaginator) throws DAOException;
    
    public List<QuestionnaireQuestion> findByDisciplineId(Integer disciplineId, Integer userId) throws DAOException;
	
    /**
     * Gets all the records from questionnaireQuestion by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireQuestion
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireQuestion> findByQuestionnaireQuestionId(Integer questionnaireQuestion) throws DAOException; 
    
    
    /**
     * Gets the records from questionnaireQuestion by questionnaireCategory id <br>
     * Creation Info:  <br>
     * date 26/08/2015 <br>
     * Update Info:  <br>
     * date 03/09/2015 <br>
     * @author Andres Felipe Lopez Rodriguez
     * 
     * @param questionnaireQuestion
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireQuestion> findByQuestionnaireIdAndCategoryId(QuestionnaireQuestion questionnaireQuestion) throws DAOException;
    /**
     * Gets the records from questionnaireQuestion by questionnaireId 
     * Creation Info:  <br>
     * date 15/09/2015 <br>
     * @author Angela Ramirez
     * @param questionnaire
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireQuestion> findByQuestionnaireId(Questionnaire questionnaire) throws DAOException;
    /**
     * Gets the questions by questionnaireCategoryId and questionnaireId <br>
     * Creation Info:  <br>
     * date 29/09/2015 <br>
     * @author Angela Ramirez
     * @param questionnaireResponse
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireQuestionFormatDTO> findByCategoryIdAndQuestionnaireId(QuestionnaireResponse questionnaireResponse) throws DAOException;
    

}