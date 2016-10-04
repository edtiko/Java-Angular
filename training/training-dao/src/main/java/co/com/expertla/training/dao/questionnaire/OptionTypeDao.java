package co.com.expertla.training.dao.questionnaire;

import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.OptionType;
import java.util.List;


public interface OptionTypeDao {
    /**
     * Create optionType <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param optionType
     * @return the optionType created
     * @throws DAOException 
     */
    public OptionType create(OptionType optionType) throws DAOException;
	
    /**
     * Modify optionType <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param optionType
     * @return the optionType updated
     * @throws DAOException 
     */
    public OptionType merge(OptionType optionType) throws DAOException;
	
    /**
     * Delete optionType
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param optionType
     * @throws DAOException 
     */
    public void remove(OptionType optionType) throws DAOException;
    
    /**
     * Gets all the records from optionType <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws DAOException 
     */
    public List<OptionType> findAll(SePaginator sePaginator) throws DAOException;
	
    /**
     * Gets all the records from optionType by its id <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param optionType
     * @return
     * @throws DAOException 
     */
    public List<OptionType> findByOptionTypeId(OptionType optionType) throws DAOException;

}
