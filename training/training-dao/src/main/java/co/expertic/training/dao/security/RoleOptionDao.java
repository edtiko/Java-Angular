package co.expertic.training.dao.security;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.entities.RoleOption;
import java.util.List;

/**
* RoleOption Dao <br>
* Info. Creación: <br>
* fecha 15/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface RoleOptionDao extends BaseDAO<RoleOption> {
    
    
    /**
     * Obtiene todos los registros de roleOption <br>
     * Info. Creación: <br>
     * fecha 15/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<RoleOption> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de roleOption <br>
     * Info. Creación: <br>
     * fecha 15/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<RoleOption> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de roleOption por su id <br>
     * Info. Creación: <br>
     * fecha 15/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param roleOption
     * @return
     * @throws Exception 
     */
    public List<RoleOption> findByRoleOption(RoleOption roleOption) throws Exception;   

    /**
     * Obtiene todos los registros de roleOption por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 15/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param roleOption
     * @return
     * @throws Exception 
     */
    public List<RoleOption> findByFiltro(RoleOption roleOption) throws Exception; 

}
