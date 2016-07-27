package co.com.expertla.training.service.questionnaire;

import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.QuestionnaireRespHistory;
import java.util.List;


public interface QuestionnaireResponseHistoryService {

    /**
     * Create questionnaireResponseHistory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponseHistory
     * @return the questionnaireResponseHistory
     * @throws Exception 
     */
    public QuestionnaireRespHistory create(QuestionnaireRespHistory questionnaireResponseHistory) throws Exception;
    /**
     * Modify questionnaireResponseHistory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponseHistory
     * @return the questionnaireResponseHistory
     * @throws Exception 
     */
    public QuestionnaireRespHistory merge(QuestionnaireRespHistory questionnaireResponseHistory) throws Exception;
    /**
     * Delete questionnaireResponseHistory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponseHistory
     * @throws Exception 
     */
    public void remove(QuestionnaireRespHistory questionnaireResponseHistory) throws Exception;
    /**
     * Gets all the records from questionnaireResponseHistory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws Exception 
     */
    public List<QuestionnaireRespHistory> findAll(SePaginator sePaginator) throws Exception;
    /**
     * Gets all the records from questionnaireResponseHistory by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireResponseHistory
     * @return
     * @throws Exception 
     */
    public List<QuestionnaireRespHistory> findByQuestionnaireResponseHistoryId(QuestionnaireRespHistory questionnaireResponseHistory) throws Exception; 	
}