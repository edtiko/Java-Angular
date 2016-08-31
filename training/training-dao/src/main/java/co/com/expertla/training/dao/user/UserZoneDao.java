package co.com.expertla.training.dao.user;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.UserZone;
import java.util.List;

/**
* UserZone Dao <br>
* Creation Date: <br>
* date Aug 29, 2016 <br>
* @author Angela Ramirez
**/
public interface UserZoneDao extends BaseDAO<UserZone> {
    
    
    /**
     * Obtiene todos los registros de userZone <br>
     * Creation Date: <br>
     * date Aug 29, 2016 <br>
     * @author Angela Ramirez
     * @return
     * @throws Exception 
     */
    public List<UserZone> findAll() throws Exception;

    /**
     * Obtiene todos los registros de userZone por su id <br>
     * Creation Date: <br>
     * date Aug 29, 2016 <br>
     * @author Angela Ramirez
     * @param userZone
     * @return
     * @throws Exception 
     */
    public List<UserZone> findByUserZone(UserZone userZone) throws Exception;  

    /**
     * Obtiene todos los registros de userZone por su user id <br>
     * Creation Date: <br>
     * date Aug 29, 2016 <br>
     * @author Angela Ramirez
     * @param userId
     * @return
     * @throws Exception 
     */
    public List<UserZone> findByUserId(Integer userId) throws Exception; 

}
