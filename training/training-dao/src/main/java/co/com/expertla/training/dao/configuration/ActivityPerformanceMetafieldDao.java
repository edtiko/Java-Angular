package co.com.expertla.training.dao.configuration;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.ActivityPerformanceMetafieldDTO;
import co.com.expertla.training.model.entities.ActivityPerformanceMetafield;
import java.util.List;

/**
* ActivityPerformanceMetafield Dao <br>
* Info. Creación: <br>
* fecha Sep 15, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface ActivityPerformanceMetafieldDao extends BaseDAO<ActivityPerformanceMetafield> {
    
    
    /**
     * Obtiene todos los registros de activityPerformanceMetafield <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<ActivityPerformanceMetafield> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de activityPerformanceMetafield <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<ActivityPerformanceMetafield> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de activityPerformanceMetafield paginados <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<ActivityPerformanceMetafieldDTO> findPaginate(int first, int max, String order, String filter) throws Exception;

    /**
     * Obtiene todos los registros de activityPerformanceMetafield por su id <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param activityPerformanceMetafield
     * @return
     * @throws Exception 
     */
    public List<ActivityPerformanceMetafield> findByActivityPerformanceMetafield(ActivityPerformanceMetafield activityPerformanceMetafield) throws Exception;   

    /**
     * Obtiene todos los registros de activityPerformanceMetafield por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param activityPerformanceMetafield
     * @return
     * @throws Exception 
     */
    public List<ActivityPerformanceMetafield> findByFiltro(ActivityPerformanceMetafield activityPerformanceMetafield) throws Exception; 

    /**
    * Obtiene todos los registros de activityPerformanceMetafield por nombre <br>
    * Info. Creación: <br>
    * fecha Sep 15, 2016 <br>
    * @author Andres Felipe Lopez Rodriguez
    * @param activityPerformanceMetafield
    * @return
    * @throws Exception 
    */
    public List<ActivityPerformanceMetafield> findByName(ActivityPerformanceMetafield activityPerformanceMetafield) throws Exception;  
}
