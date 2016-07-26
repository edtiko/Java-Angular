package co.com.expertla.training.service.questionnaire;

import co.com.expertla.training.model.dto.QuestionnaireDTO;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.Questionnaire;
import java.util.List;


public interface QuestionnaireService {

    /**
     * Create questionnaire <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaire
     * @return the questionnaire created
     * @throws Exception 
     */
    public Questionnaire create(Questionnaire questionnaire) throws Exception;
    /**
     * Modify questionnaire <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaire
     * @return the questionnaire updated
     * @throws Exception 
     */
    public Questionnaire merge(Questionnaire questionnaire) throws Exception;
    /**
     * Delete questionnaire <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaire
     * @throws Exception 
     */
    public void remove(Questionnaire questionnaire) throws Exception;
    /**
     * Gets all the records from questionnaire <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws Exception 
     */
    public List<Questionnaire> findAll(SePaginator sePaginator) throws Exception;
    /**
     * Gets all the records from questionnaire by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaire
     * @return
     * @throws Exception 
     */
    public List<Questionnaire> findByQuestionnaireId(Questionnaire questionnaire) throws Exception; 

    
    /**
     * Get the amount of question and response by questionnaire <br>
     * Creation Info:  <br>
     * date 02/09/2015 <br>
     * @author Andres Felipe Lopez Rodriguez
     * 
     * @param sePaginator
     * @return
     * @throws Exception 
     */
    public List<Questionnaire> findAmountQuestionAndResponseByQuestionnaire(SePaginator sePaginator) throws Exception;
    /**
     * Get questionnaire dto by questionnaire id <br>
     * Creation Info:  <br>
     * date 15/09/2015 <br>
     * @author Angela Ramirez
     * @param questionnaire
     * @return
     * @throws Exception 
     */
    public List<QuestionnaireDTO> findQuestionnaireDtoByQuestionnaireId(Questionnaire questionnaire) throws Exception;
}