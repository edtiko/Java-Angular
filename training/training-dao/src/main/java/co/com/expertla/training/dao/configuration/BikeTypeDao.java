package co.com.expertla.training.dao.configuration;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.BikeTypeDTO;
import co.com.expertla.training.model.entities.BikeType;
import java.util.List;

/**
* BikeType Dao <br>
* Info. Creación: <br>
* fecha Sep 9, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface BikeTypeDao extends BaseDAO<BikeType> {
    
    
    /**
     * Obtiene todos los registros de bikeType <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<BikeType> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de bikeType <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<BikeType> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de bikeType paginados <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<BikeTypeDTO> findPaginate(int first, int max, String order, String filter) throws Exception;

    /**
     * Obtiene todos los registros de bikeType por su id <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param bikeType
     * @return
     * @throws Exception 
     */
    public List<BikeType> findByBikeType(BikeType bikeType) throws Exception;   

    /**
     * Obtiene todos los registros de bikeType por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param bikeType
     * @return
     * @throws Exception 
     */
    public List<BikeType> findByFiltro(BikeType bikeType) throws Exception; 

    /**
    * Obtiene todos los registros de bikeType por nombre <br>
    * Info. Creación: <br>
    * fecha Sep 9, 2016 <br>
    * @author Andres Felipe Lopez Rodriguez
    * @param bikeType
    * @return
    * @throws Exception 
    */
    public List<BikeType> findByName(BikeType bikeType) throws Exception;  
}
