package co.expertic.training.dao.questionnaire;

import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.QuestionnaireResponse;
import co.expertic.training.model.entities.ResponseOption;
import java.util.List;


public interface ResponseOptionDao {
    /**
     * Create responseOption <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseOption
     * @return the responseOption created
     * @throws DAOException 
     */
    public ResponseOption create(ResponseOption responseOption) throws DAOException;
    
    /**
     * Create responseOption list <br>
     * Creation Info:  <br>
     * date 1/12/2015 <br>
     * @author Angela Ramirez
     * @param responseOption
     * @return
     * @throws DAOException 
     */
    public List<ResponseOption> create(List<ResponseOption> responseOption) throws DAOException;
	
    /**
     * Modify responseOption <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseOption
     * @return the responseOption updated
     * @throws DAOException 
     */
    public ResponseOption merge(ResponseOption responseOption) throws DAOException;
	
    /**
     * Delete responseOption <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseOption
     * @throws DAOException 
     */
    public void remove(ResponseOption responseOption) throws DAOException;
    
    /**
     * Gets all the records from responseOption <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws DAOException 
     */
    public List<ResponseOption> findAll(SePaginator sePaginator) throws DAOException;
	
    /**
     * Gets all the records from responseOption by its id <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseOption
     * @return
     * @throws DAOException 
     */
    public List<ResponseOption> findByResponseOptionId(ResponseOption responseOption) throws DAOException;
    /**
     * Gets all the records from responseOption by questionnaireResponse id <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez 
     * @param questionnaireResponseIds
     * @return
     * @throws DAOException 
     */
    public List<ResponseOption> findByQuestionnaireResponseIds(List<Integer> questionnaireResponseIds) throws DAOException;
    
    public List<ResponseOption> findByQuestionnaireResponseId(QuestionnaireResponse questionnaireResponseId) throws DAOException;
}
