package co.com.expertla.training.dao.security;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.RoleUser;
import java.util.List;

/**
* RoleUser Dao <br>
* Info. Creación: <br>
* fecha 10/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface RoleUserDao extends BaseDAO<RoleUser> {
    
    
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

}
