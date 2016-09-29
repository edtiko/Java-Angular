package co.com.expertla.training.service.configuration;

import co.com.expertla.training.model.dto.CharacteristicDTO;
import co.com.expertla.training.model.entities.Characteristic;
import java.util.List;

/**
* Characteristic Service <br>
* Info. Creación: <br>
* fecha 8/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface CharacteristicService {
    

    /**
     * Crea characteristic <br>
     * Info. Creación: <br>
     * fecha 8/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param characteristic
     * @return 
     * @throws Exception 
     */
    public Characteristic create(Characteristic characteristic) throws Exception;
    /**
     * Modifica characteristic <br>
     * Info. Creación: <br>
     * fecha 8/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param characteristic
     * @return 
     * @throws Exception 
     */
    public Characteristic store(Characteristic characteristic) throws Exception;
    /**
     * Elimina characteristic<br>
     * Info. Creación: <br>
     * fecha 8/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param characteristic
     * @throws Exception 
     */
    public void remove(Characteristic characteristic) throws Exception;
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
     * Obtiene todos los registros de characteristic paginados <br>
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
    
    /**
     * Obtiene todos los registros activos de characteristic por membership or plan <br>
     * Info. Creación: <br>
     * fecha 28/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param isMembership
     * @param userType
     * @return
     * @throws Exception 
     */
    public List<Characteristic> findAllByTypeMembershipOrPlan(boolean isMembership, String userType) throws Exception;

    
}