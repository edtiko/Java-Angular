package co.com.expertla.training.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.SportEquipmentDTO;
import co.com.expertla.training.model.entities.EquipmentUserProfile;
import java.util.List;

/**
* Dao for Equipment User Profile <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
public interface EquipmentUserProfileDao extends BaseDAO<EquipmentUserProfile>{
	
    /**
     * Trae todos los sport equipment por user Id <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public List<SportEquipmentDTO> findAllByUserId(Integer id) throws Exception;
    
    /**
     * Trae todos los sport equipment que son tipo running shoes por user Id <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public List<SportEquipmentDTO> findRunningShoesByUserId(Integer id) throws Exception;
    
    /**
     * Trae todos los sport equipment que son tipo bikes por user Id <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public List<SportEquipmentDTO> findBikesByUserId(Integer id) throws Exception;
    
    /**
     * Trae todos los sport equipment que son tipo Pulsometer por user Id<br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public List<SportEquipmentDTO> findPulsometersByUserId(Integer id) throws Exception;
    
    /**
     * Trae todos los sport equipment que son tipo potentiometer por user Id <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public List<SportEquipmentDTO> findPotentiometersByUserId(Integer id) throws Exception;
        
}
