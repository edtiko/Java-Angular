package co.expertic.training.dao.questionnaire;

import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.ResponseType;
import java.util.List;


public interface ResponseTypeDao  {
    /**
     * Create responseType <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseType
     * @return the responseType created
     * @throws DAOException 
     */
    public ResponseType create(ResponseType responseType) throws DAOException;
	
    /**
     * Modify responseType <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseType
     * @return the responseType updated
     * @throws DAOException 
     */
    public ResponseType merge(ResponseType responseType) throws DAOException;
	
    /**
     * Delete responseType
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseType
     * @throws DAOException 
     */
    public void remove(ResponseType responseType) throws DAOException;
    
    /**
     * Gets all the records from responseType <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws DAOException 
     */
    public List<ResponseType> findAll(SePaginator sePaginator) throws DAOException;
	
    /**
     * Gets all the records from responseType by its id <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseType
     * @return
     * @throws DAOException 
     */
    public List<ResponseType> findByResponseTypeId(ResponseType responseType) throws DAOException;
}
