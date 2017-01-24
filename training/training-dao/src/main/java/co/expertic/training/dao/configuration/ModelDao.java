package co.expertic.training.dao.configuration;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.dto.ModelDTO;
import co.expertic.training.model.entities.Model;
import java.util.List;

/**
* Model Dao <br>
* Info. Creación: <br>
* fecha Oct 19, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface ModelDao extends BaseDAO<Model> {
    
    
    /**
     * Obtiene todos los registros de model <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Model> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de model <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Model> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de model paginados <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<ModelDTO> findPaginate(int first, int max, String order, String filter) throws Exception;

    /**
     * Obtiene todos los registros de model por su id <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param model
     * @return
     * @throws Exception 
     */
    public List<Model> findByModel(Model model) throws Exception;   

    /**
     * Obtiene todos los registros de model por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param model
     * @return
     * @throws Exception 
     */
    public List<Model> findByFiltro(Model model) throws Exception; 

    /**
    * Obtiene todos los registros de model por nombre <br>
    * Info. Creación: <br>
    * fecha Oct 19, 2016 <br>
    * @author Andres Felipe Lopez Rodriguez
    * @param model
    * @return
    * @throws Exception 
    */
    public List<Model> findByName(Model model) throws Exception;  
}
