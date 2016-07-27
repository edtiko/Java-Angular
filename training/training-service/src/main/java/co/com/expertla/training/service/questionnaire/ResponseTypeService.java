package co.com.expertla.training.service.questionnaire;

import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.ResponseType;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseTypeService {
    /**
     * Create responseType <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseType
     * @return the responseType created
     * @throws Exception 
     */
    public ResponseType create(ResponseType responseType) throws Exception;
    /**
     * Modify responseType <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseType
     * @return the responseType updated
     * @throws Exception 
     */
    public ResponseType merge(ResponseType responseType) throws Exception;
    /**
     * Delete responseType <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseType
     * @throws Exception 
     */
    public void remove(ResponseType responseType) throws Exception;
    /**
     * Gets all the records from responseType <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws Exception 
     */
    public List<ResponseType> findAll(SePaginator sePaginator) throws Exception;
    /**
     * Gets all the records from responseType by its id <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param responseType
     * @return
     * @throws Exception 
     */
    public List<ResponseType> findByResponseTypeId(ResponseType responseType) throws Exception;   
}