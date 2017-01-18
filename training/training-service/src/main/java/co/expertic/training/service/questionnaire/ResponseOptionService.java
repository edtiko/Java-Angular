package co.expertic.training.service.questionnaire;

import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.QuestionnaireResponse;
import co.expertic.training.model.entities.ResponseOption;
import java.util.List;


public interface ResponseOptionService {
    /**
     * Create responseOption <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseOption
     * @return the responseOption created
     * @throws Exception 
     */
    public ResponseOption create(ResponseOption responseOption) throws Exception;
    /**
     * Modify responseOption <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseOption
     * @return the responseOption updated
     * @throws Exception 
     */
    public ResponseOption merge(ResponseOption responseOption) throws Exception;
    /**
     * Delete responseOption <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseOption
     * @throws Exception 
     */
    public void remove(ResponseOption responseOption) throws Exception;
    /**
     * Gets all the records from responseOption <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws Exception 
     */
    public List<ResponseOption> findAll(SePaginator sePaginator) throws Exception;
    /**
     * Gets all the records from responseOption by its id <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseOption
     * @return
     * @throws Exception 
     */
    public List<ResponseOption> findByResponseOptionId(ResponseOption responseOption) throws Exception; 
    
    public ResponseOption findByQuestionnaireResponseId(QuestionnaireResponse questionnaireResponseId) throws Exception;
}