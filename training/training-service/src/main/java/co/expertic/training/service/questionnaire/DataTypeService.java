package co.expertic.training.service.questionnaire;

import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.DataType;
import java.util.List;


public interface DataTypeService {

    /**
     * Create dataType <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param dataType
     * @return the dataType created
     * @throws Exception 
     */
    public DataType create(DataType dataType) throws Exception;
    /**
     * Modify dataType <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param dataType
     * @return the dataType updated
     * @throws Exception 
     */
    public DataType merge(DataType dataType) throws Exception;
    /**
     * Delete dataType <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param dataType
     * @throws Exception 
     */
    public void remove(DataType dataType) throws Exception;
    /**
     * Gets all the records from dataType <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws Exception 
     */
    public List<DataType> findAll(SePaginator sePaginator) throws Exception;
    /**
     * Gets all the records from dataType by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param dataType
     * @return
     * @throws Exception 
     */
    public List<DataType> findByDataTypeId(DataType dataType) throws Exception;

    /**
     * Gets all the records from dataType by name <br>
     * Creation Info:  <br>
     * date 26/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param name
     * @return
     * @throws Exception 
     */
    public List<DataType> findByName(String name) throws Exception;
}