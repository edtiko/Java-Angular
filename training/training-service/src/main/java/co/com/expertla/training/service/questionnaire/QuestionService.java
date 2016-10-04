package co.com.expertla.training.service.questionnaire;

import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.Question;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionService {

    /**
     * Create question <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param question
     * @return the question created
     * @throws Exception 
     */
    public Question create(Question question) throws Exception;
    /**
     * Modify question <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param question
     * @return the question updated
     * @throws Exception 
     */
    public Question merge(Question question) throws Exception;
    /**
     * Delete question <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param question
     * @throws Exception 
     */
    public void remove(Question question) throws Exception;
    /**
     * Gets all the records from question <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws Exception 
     */
    public List<Question> findAll(SePaginator sePaginator) throws Exception;
    /**
     * Gets all the records from question by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param question
     * @return
     * @throws Exception 
     */
    public List<Question> findByQuestionId(Question question) throws Exception;
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