package co.com.expertla.training.dao.security;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.RoleDTO;
import co.com.expertla.training.model.entities.Role;
import java.util.List;

/**
* Role Dao <br>
* Info. Creación: <br>
* fecha 28/11/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface RoleDao extends BaseDAO<Role> {
    
    
    /**
     * Obtiene todos los registros de role <br>
     * Info. Creación: <br>
     * fecha 28/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Role> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de role <br>
     * Info. Creación: <br>
     * fecha 28/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Role> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de role paginados <br>
     * Info. Creación: <br>
     * fecha 28/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<RoleDTO> findPaginate(int first, int max, String order, String filter) throws Exception;

    /**
     * Obtiene todos los registros de role por su id <br>
     * Info. Creación: <br>
     * fecha 28/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param role
     * @return
     * @throws Exception 
     */
    public List<Role> findByRole(Role role) throws Exception;   

    /**
     * Obtiene todos los registros de role por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 28/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param role
     * @return
     * @throws Exception 
     */
    public List<Role> findByFiltro(Role role) throws Exception; 

    /**
    * Obtiene todos los registros de role por nombre <br>
    * Info. Creación: <br>
    * fecha 28/11/2016 <br>
    * @author Andres Felipe Lopez Rodriguez
    * @param role
    * @return
    * @throws Exception 
    */
    public List<Role> findByName(Role role) throws Exception;  
}
