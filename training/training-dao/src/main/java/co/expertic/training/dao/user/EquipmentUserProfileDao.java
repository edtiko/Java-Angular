package co.expertic.training.dao.user;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.dto.SportEquipmentDTO;
import co.expertic.training.model.entities.EquipmentUserProfile;
import co.expertic.training.model.entities.SportEquipment;
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
    public List<SportEquipmentDTO> findAllDTOByUserId(Integer id) throws Exception;
    
    /**
     * Trae todos los sport equipment que son tipo running shoes por user Id <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public List<SportEquipmentDTO> findDTORunningShoesByUserId(Integer id) throws Exception;
    
    /**
     * Trae todos los sport equipment que son tipo bikes por user Id <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public List<SportEquipmentDTO> findDTOBikesByUserId(Integer id) throws Exception;
    
    /**
     * Trae todos los sport equipment que son tipo Pulsometer por user Id<br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public List<SportEquipmentDTO> findDTOPulsometersByUserId(Integer id) throws Exception;
    
    /**
     * Trae todos los sport equipment que son tipo potentiometer por user Id <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public List<SportEquipmentDTO> findDTOPotentiometersByUserId(Integer id) throws Exception;
    
    /**
     * Trae equipment user profile  por user Id <br>
     * Creation Date : <br>
     * date 21/07/2016 <br>
     * @author Angela Ramírez
     * @param userId
     * @return
     * @throws Exception 
     */
    public List<EquipmentUserProfile> findByUserId (Integer userId) throws Exception;
        
}
