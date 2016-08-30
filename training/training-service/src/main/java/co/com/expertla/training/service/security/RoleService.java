package co.com.expertla.training.service.security;

import co.com.expertla.training.model.dto.RoleDTO;
import co.com.expertla.training.model.entities.Role;
import java.util.List;

/**
* Role Service <br>
* Info. Creación: <br>
* fecha 30/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface RoleService {
    

    /**
     * Crea role <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param role
     * @return 
     * @throws Exception 
     */
    public Role create(Role role) throws Exception;
    /**
     * Modifica role <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param role
     * @throws Exception 
     */
    public Role store(Role role) throws Exception;
    /**
     * Elimina role<br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param role
     * @return 
     * @throws Exception 
     */
    public void remove(Role role) throws Exception;
    /**
     * Obtiene todos los registros de role <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Role> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de role <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Role> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de role paginados <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @return
     * @throws Exception 
     */
    public List<RoleDTO> findPaginate(int first, int max, String order) throws Exception;
    
    /**
     * Obtiene todos los registros de role paginados <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param role
     * @return
     * @throws Exception 
     */
    public List<Role> findByRole(Role role) throws Exception;   

    /**
     * Obtiene todos los registros de role por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param role
     * @return
     * @throws Exception 
     */
    public List<Role> findByFiltro(Role role) throws Exception; 



    
}