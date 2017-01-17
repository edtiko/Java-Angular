package co.expertic.training.dao.questionnaire;

import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.QuestionOption;
import java.util.List;



public interface QuestionOptionDao {
    
    /**
     * Create questionOption <br>
     * Creation Info: <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionOption
     * @return the questionOption created
     * @throws DAOException 
     */
    public QuestionOption create(QuestionOption questionOption) throws DAOException;
	
    /**
     * Modify questionOption <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionOption
     * @return the questionOption updated
     * @throws DAOException 
     */
    public QuestionOption merge(QuestionOption questionOption) throws DAOException;
	
    /**
     * Delete questionOption <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionOption
     * @throws DAOException 
     */
    public void remove(QuestionOption questionOption) throws DAOException;
    
    /**
     * Gets all the records from questionOption <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws DAOException 
     */
    public List<QuestionOption> findAll(SePaginator sePaginator) throws DAOException;
	
    /**
     * Gets all the records from questionOption by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionOptionId
     * @return
     * @throws DAOException 
     */
    public List<QuestionOption> findByQuestionOptionId(Integer questionOptionId) throws DAOException;
    
    public List<QuestionOption> findByQuestionId(Integer questionId) throws DAOException;
    
    /**
     * Gets all the records from questionOption by name <br>
     * Creation Info:<br> 
     * date 26/08/2015  <br>
     * @author Angela Ramirez
     * 
     * @param name
     * @return
     * @throws Exception 
     */
    public List<QuestionOption> findByName(String name) throws Exception; 

}