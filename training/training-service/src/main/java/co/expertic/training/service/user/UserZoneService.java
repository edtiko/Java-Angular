package co.expertic.training.service.user;

import co.expertic.training.model.entities.UserZone;
import java.util.List;

/**
* UserZone Service <br>
* Creation Date: <br>
* date Aug 29, 2016 <br>
* @author Angela Ramirez
**/
public interface UserZoneService {
    

    /**
     * Crea userZone <br>
     * Creation Date: <br>
     * date Aug 29, 2016 <br>
     * @author Angela Ramirez
     * @param userZone
     * @return 
     * @throws Exception 
     */
    public UserZone create(UserZone userZone) throws Exception;
    
    /**
     * Modifica userZone <br>
     * Creation Date: <br>
     * date Aug 29, 2016 <br>
     * @author Angela Ramirez
     * @param userZone
     * @return 
     * @throws Exception 
     */
    public UserZone store(UserZone userZone) throws Exception;
    
    /**
     * Elimina userZone<br>
     * Creation Date: <br>
     * date Aug 29, 2016 <br>
     * @author Angela Ramirez
     * @param userZone 
     * @throws Exception 
     */
    public void remove(UserZone userZone) throws Exception;
    
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