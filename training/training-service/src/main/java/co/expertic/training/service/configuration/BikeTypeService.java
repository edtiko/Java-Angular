package co.expertic.training.service.configuration;

import co.expertic.training.model.dto.BikeTypeDTO;
import co.expertic.training.model.entities.BikeType;
import java.util.List;

/**
* BikeType Service <br>
* Info. Creación: <br>
* fecha Sep 9, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface BikeTypeService {
    

    /**
     * Crea bikeType <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param bikeType
     * @return 
     * @throws Exception 
     */
    public BikeType create(BikeType bikeType) throws Exception;
    /**
     * Modifica bikeType <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param bikeType
     * @return 
     * @throws Exception 
     */
    public BikeType store(BikeType bikeType) throws Exception;
    /**
     * Elimina bikeType<br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param bikeType
     * @throws Exception 
     */
    public void remove(BikeType bikeType) throws Exception;
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
     * Obtiene todos los registros de bikeType paginados <br>
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