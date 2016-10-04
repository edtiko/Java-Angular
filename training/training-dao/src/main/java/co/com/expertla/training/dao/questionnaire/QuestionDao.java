package co.com.expertla.training.dao.questionnaire;

import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.Question;
import java.util.List;


public interface QuestionDao {
    
    /**
     * Create question <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param question
     * @return the question created
     * @throws DAOException 
     */
    public Question create(Question question) throws DAOException;
	
    /**
     * Modify question <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param question
     * @return the question updated
     * @throws DAOException 
     */
    public Question merge(Question question) throws DAOException;
	
    /**
     * Delete question <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param question
     * @throws DAOException 
     */
    public void remove(Question question) throws DAOException;
    
    /**
     * Gets all the records from question <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws DAOException 
     */
    public List<Question> findAll(SePaginator sePaginator) throws DAOException;
	
    /**
     * Gets all the records from question by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param question
     * @return
     * @throws DAOException 
     */
    public List<Question> findByQuestionId(Question question) throws DAOException; 
    
    /**
     * Gets all the records from question by name <br>
     * Creation Info:  <br>
     * date 26/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param name
     * @return
     * @throws Exception 
     */
    public List<Question> findByName(String name) throws Exception;

}