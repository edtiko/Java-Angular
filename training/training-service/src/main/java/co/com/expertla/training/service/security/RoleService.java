package co.com.expertla.training.service.security;

import co.com.expertla.training.model.dto.RoleDTO;
import co.com.expertla.training.model.entities.Role;
import java.util.List;

/**
* Role Service <br>
* Creation Date: <br>
* date 19/08/2016 <br>
* @author Angela Ramirez O
**/
public interface RoleService {
    

    /**
     * Crea role <br>
     * Creation Date: <br>
     * date 19/08/2016 <br>
     * @author Angela Ramirez O
     * @param role
     * @return 
     * @throws Exception 
     */
    public Role create(Role role) throws Exception;
    /**
     * Modifica role <br>
     * Creation Date: <br>
     * date 19/08/2016 <br>
     * @author Angela Ramirez O
     * @param role
     * @return 
     * @throws Exception 
     */
    public Role store(Role role) throws Exception;
    /**
     * Elimina role<br>
     * Creation Date: <br>
     * date 19/08/2016 <br>
     * @author Angela Ramirez O
     * @param role 
     * @throws Exception 
     */
    public void remove(Role role) throws Exception;
    /**
     * Obtiene todos los registros de role en roleDTO<br>
     * Creation Date: <br>
     * date 19/08/2016 <br>
     * @author Angela Ramirez O
     * @return
     * @throws Exception 
     */
    public List<RoleDTO> findAll() throws Exception;

}