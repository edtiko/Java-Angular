package co.com.expertla.training.dao.questionnaire;

import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.QuestionnaireFormatDTO;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.Questionnaire;
import java.util.List;


public interface QuestionnaireDao {
    
    /**
     * Create questionnaire <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaire
     * @return the qusetionnaire created
     * @throws DAOException 
     */
    public Questionnaire create(Questionnaire questionnaire) throws DAOException;
	
    /**
     * Modify questionnaire <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaire
     * @return the questionnaire updated
     * @throws DAOException 
     */
    public Questionnaire merge(Questionnaire questionnaire) throws DAOException;
	
    /**
     * Delete questionnaire <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaire
     * @throws DAOException 
     */
    public void remove(Questionnaire questionnaire) throws DAOException;
    
    /**
     * Gets all the records from questionnaire <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws DAOException 
     */
    public List<Questionnaire> findAll(SePaginator sePaginator) throws DAOException;
	
    /**
     * Gets all the records from questionnaire by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaire
     * @return
     * @throws DAOException 
     */
    public List<Questionnaire> findByQuestionnaireId(Questionnaire questionnaire) throws DAOException; 
    

    /**
     * Get the amount of question and response by questionnaire <br>
     * Creation Info:  <br>
     * date 02/09/2015 <br>
     * @author Andres Felipe Lopez Rodriguez
     * 
     * @param sePaginator
     * @return
     * @throws DAOException 
     */
    public List<Questionnaire> findAmountQuestionAndResponseByQuestionnaire(SePaginator sePaginator) throws DAOException;
    /**
     * Get all the information to build questionnaireDto by questionnaire id <br>
     * Creation Info:  <br>
     * date 15/09/2015 <br>
     * @author Angela Ramirez
     * @param questionnaire
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireFormatDTO> findQuestionnaireDtoByQuestionnaireId(Questionnaire questionnaire) throws DAOException;

}