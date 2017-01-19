package co.expertic.training.service.security;

import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.RoleUser;
import java.util.List;

/**
* RoleUser Service <br>
* Info. Creación: <br>
* fecha 10/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface RoleUserService {
    

    /**
     * Crea roleUser <br>
     * Info. Creación: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param roleUser
     * @return 
     * @throws Exception 
     */
    public RoleUser create(RoleUser roleUser) throws Exception;
    /**
     * Modifica roleUser <br>
     * Info. Creación: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param roleUser
     * @return 
     * @throws Exception 
     */
    public RoleUser store(RoleUser roleUser) throws Exception;
    /**
     * Elimina roleUser<br>
     * Info. Creación: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param roleUser 
     * @throws Exception 
     */
    public void remove(RoleUser roleUser) throws Exception;
    /**
     * Obtiene todos los registros de roleUser <br>
     * Info. Creación: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<RoleUser> findAll() throws Exception;

    /**
     * Obtiene todos los registros activos de roleUser <br>
     * Info. Creación: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<RoleUser> findAllActive() throws Exception;
    /**
     * Obtiene todos los registros de roleUser por su id <br>
     * Info. Creación: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param roleUser
     * @return
     * @throws Exception 
     */
    public List<RoleUser> findByRoleUser(RoleUser roleUser) throws Exception;   

    /**
     * Obtiene todos los registros de roleUser por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param roleUser
     * @return
     * @throws Exception 
     */
    public List<RoleUser> findByFiltro(RoleUser roleUser) throws Exception; 


    /**
     * Obtiene todos los registros de roleUser por user Id <br>
     * Creation Date <br>
     * date 19/08/2016 <br>
     * @author Angela Ramirez O
     * @param id
     * @return
     * @throws Exception 
     */
    public RoleUser findByUserId(Integer id) throws Exception; 

    public List<UserDTO> getUsersByRole(Integer roleId)throws Exception; 

    
}