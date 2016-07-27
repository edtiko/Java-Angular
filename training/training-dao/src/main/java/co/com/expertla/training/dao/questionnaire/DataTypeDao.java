package co.com.expertla.training.dao.questionnaire;

import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.DataType;
import java.util.List;


public interface DataTypeDao {
    
    /**
     * Create dataType <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param dataType
     * @return the dataType created
     * @throws DAOException 
     */
    public DataType create(DataType dataType) throws DAOException;
	
    /**
     * Modify dataType <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param dataType
     * @return the dataType updated
     * @throws DAOException 
     */
    public DataType merge(DataType dataType) throws DAOException;
	
    /**
     * Delete dataType
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param dataType
     * @throws DAOException 
     */
    public void remove(DataType dataType) throws DAOException;
    
    /**
     * Gets all the records from dataType <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws DAOException 
     */
    public List<DataType> findAll(SePaginator sePaginator) throws DAOException;
	
    /**
     * Gets all the records from dataType by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param dataType
     * @return
     * @throws DAOException 
     */
    public List<DataType> findByDataTypeId(DataType dataType) throws DAOException;
    
    /**
     * Gets all the records from dataType <br>
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