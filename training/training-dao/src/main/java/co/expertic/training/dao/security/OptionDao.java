package co.expertic.training.dao.security;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.dto.OptionDTO;
import co.expertic.training.model.entities.Option;
import java.util.List;

/**
* Option Dao <br>
* Info. Creación: <br>
* fecha 25/11/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface OptionDao extends BaseDAO<Option> {
    
    
    /**
     * Obtiene todos los registros de option <br>
     * Info. Creación: <br>
     * fecha 25/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Option> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de option <br>
     * Info. Creación: <br>
     * fecha 25/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Option> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de option paginados <br>
     * Info. Creación: <br>
     * fecha 25/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<OptionDTO> findPaginate(int first, int max, String order, String filter) throws Exception;

    /**
     * Obtiene todos los registros de option por su id <br>
     * Info. Creación: <br>
     * fecha 25/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param option
     * @return
     * @throws Exception 
     */
    public List<Option> findByOption(Option option) throws Exception;   

    /**
     * Obtiene todos los registros de option por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 25/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param option
     * @return
     * @throws Exception 
     */
    public List<Option> findByFiltro(Option option) throws Exception; 

    /**
    * Obtiene todos los registros de option por nombre <br>
    * Info. Creación: <br>
    * fecha 25/11/2016 <br>
    * @author Andres Felipe Lopez Rodriguez
    * @param option
    * @return
    * @throws Exception 
    */
    public List<Option> findByName(Option option) throws Exception;  
    
    public List<Option> findByUserId(Integer userId, Integer moduleId) throws Exception;
}