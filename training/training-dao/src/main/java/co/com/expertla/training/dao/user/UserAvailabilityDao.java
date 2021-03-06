package co.com.expertla.training.dao.user;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.UserAvailability;
import java.util.List;

/**
* Dao for User Availability <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
public interface UserAvailabilityDao extends BaseDAO<UserAvailability>{
	
    /**
     * Trae todos los registros de User Availability por user Id<br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public List<UserAvailability> findByUserId(Integer id) throws Exception;
        
}
