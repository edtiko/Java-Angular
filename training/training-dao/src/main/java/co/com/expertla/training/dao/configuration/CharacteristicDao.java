package co.com.expertla.training.dao.configuration;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.CharacteristicDTO;
import co.com.expertla.training.model.entities.Characteristic;
import java.util.List;

/**
* Characteristic Dao <br>
* Info. Creación: <br>
* fecha 8/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface CharacteristicDao extends BaseDAO<Characteristic> {
    
    
    /**
     * Obtiene todos los registros de characteristic <br>
     * Info. Creación: <br>
     * fecha 8/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Characteristic> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de characteristic <br>
     * Info. Creación: <br>
     * fecha 8/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Characteristic> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de characteristic paginados <br>
     * Info. Creación: <br>
     * fecha 8/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<CharacteristicDTO> findPaginate(int first, int max, String order, String filter) throws Exception;

    /**
     * Obtiene todos los registros de characteristic por su id <br>
     * Info. Creación: <br>
     * fecha 8/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param characteristic
     * @return
     * @throws Exception 
     */
    public List<Characteristic> findByCharacteristic(Characteristic characteristic) throws Exception;   

    /**
     * Obtiene todos los registros de characteristic por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 8/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param characteristic
     * @return
     * @throws Exception 
     */
    public List<Characteristic> findByFiltro(Characteristic characteristic) throws Exception; 

    /**
    * Obtiene todos los registros de characteristic por nombre <br>
    * Info. Creación: <br>
    * fecha 8/09/2016 <br>
    * @author Andres Felipe Lopez Rodriguez
    * @param characteristic
    * @return
    * @throws Exception 
    */
    public List<Characteristic> findByName(Characteristic characteristic) throws Exception;  
}
