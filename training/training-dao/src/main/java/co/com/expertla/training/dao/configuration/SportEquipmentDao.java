package co.com.expertla.training.dao.configuration;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.MarketingDTO;
import co.com.expertla.training.model.dto.SportEquipmentDTO;
import co.com.expertla.training.model.entities.SportEquipment;
import java.util.List;

/**
* Dao for Sport Equipment <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
public interface SportEquipmentDao extends BaseDAO<SportEquipment>{
	
    /**
     * Trae todos los registros de sport  equipment <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<SportEquipmentDTO> findAll() throws Exception;
    
    /**
     * Trae todos los registros de sport equipment que son tipo running shoes <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<SportEquipmentDTO> findAllRunningShoes() throws Exception;
    
    /**
     * Trae todos los registros de sport equipment que son tipo bicicleta <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<SportEquipmentDTO> findAllBikes() throws Exception;
    
    /**
     * Trae todos los registros de sport equipment que son tipo Pulsometer <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<SportEquipmentDTO> findAllPulsometers() throws Exception;
    
    /**
     * Trae todos los registros de sport equipment que son tipo potentiometer <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<SportEquipmentDTO> findAllPotentiometers() throws Exception;
     
    /**
     * Trae todos los registros de sport equipment que son tipo bicicleta por bike Type Id <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public List<SportEquipmentDTO> findBikesByBikeTypeId(Integer id) throws Exception;

    public List<MarketingDTO> findMarketingPaginate(int page, int limit, String order, Object filterDto) throws Exception;

}
