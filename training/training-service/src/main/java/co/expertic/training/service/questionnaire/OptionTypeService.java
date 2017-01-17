package co.expertic.training.service.questionnaire;

import co.expertic.training.model.dto.SePaginator;
import co.expertic.training.model.entities.OptionType;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionTypeService {
    /**
     * Create optionType <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param optionType
     * @return the optionType created
     * @throws Exception 
     */
    public OptionType create(OptionType optionType) throws Exception;
    /**
     * Modify optionType <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param optionType
     * @return the optionType updated
     * @throws Exception 
     */
    public OptionType merge(OptionType optionType) throws Exception;
    /**
     * Delete optionType <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param optionType
     * @throws Exception 
     */
    public void remove(OptionType optionType) throws Exception;
    /**
     * Gets all the records from optionType <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws Exception 
     */
    public List<OptionType> findAll(SePaginator sePaginator) throws Exception;
    /**
     * Gets all the records from optionType by its id <br>
     * Creation Info:  <br>
     * date 13/10/2015 <br>
     * @author Angela Ramirez
     * 
     * @param optionType
     * @return
     * @throws Exception 
     */
    public List<OptionType> findByOptionTypeId(OptionType optionType) throws Exception;  
}