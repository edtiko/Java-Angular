package co.com.expertla.training.service.questionnaire;

import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.QuestionOption;
import java.util.List;


public interface QuestionOptionService {

    /**
     * Create questionOption <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionOption
     * @return the questionOption created
     * @throws Exception 
     */
    public QuestionOption create(QuestionOption questionOption) throws Exception;
    /**
     * Modify questionOption <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionOption
     * @return the questionOption updated
     * @throws Exception 
     */
    public QuestionOption merge(QuestionOption questionOption) throws Exception;
    /**
     * Delete questionOption <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionOption
     * @throws Exception 
     */
    public void remove(QuestionOption questionOption) throws Exception;
    /**
     * Gets all the records from questionOption <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws Exception 
     */
    public List<QuestionOption> findAll(SePaginator sePaginator) throws Exception;
    /**
     * Gets all the records from questionOption by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionOption
     * @return
     * @throws Exception 
     */
    public List<QuestionOption> findByQuestionOptionId(QuestionOption questionOption) throws Exception;
    /**
     * Gets all the records from questionOption by name <br>
     * Creation Info:  <br>
     * date 26/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param name
     * @return
     * @throws Exception 
     */
    public List<QuestionOption> findByName(String name) throws Exception; 	
}