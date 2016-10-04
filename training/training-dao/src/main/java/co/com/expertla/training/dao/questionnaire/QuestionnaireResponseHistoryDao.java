package co.com.expertla.training.dao.questionnaire;

import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.QuestionnaireRespHistory;
import java.util.List;


public interface QuestionnaireResponseHistoryDao {
    
    /**
     * Create questionnaireResponseHistory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponseHistory
     * @return the questionnaireResponseHistory created
     * @throws DAOException 
     */
    public QuestionnaireRespHistory create(QuestionnaireRespHistory questionnaireResponseHistory) throws DAOException;
	
    /**
     * Modify questionnaireResponseHistory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponseHistory
     * @return the questionnaireResponseHistory updated
     * @throws DAOException 
     */
    public QuestionnaireRespHistory merge(QuestionnaireRespHistory questionnaireResponseHistory) throws DAOException;
	
    /**
     * Delete questionnaireResponseHistory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponseHistory
     * @throws DAOException 
     */
    public void remove(QuestionnaireRespHistory questionnaireResponseHistory) throws DAOException;
    
    /**
     * Gets all the records from questionnaireResponseHistory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireRespHistory> findAll(SePaginator sePaginator) throws DAOException;
	
    /**
     * Gets all the records from questionnaireResponseHistory by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponseHistory
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireRespHistory> findByQuestionnaireResponseHistoryId(QuestionnaireRespHistory questionnaireResponseHistory) throws DAOException; 

}