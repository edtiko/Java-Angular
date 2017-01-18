package co.expertic.training.service.security;

import co.expertic.training.model.entities.RoleOption;
import java.util.List;

/**
* RoleOption Service <br>
* Info. Creación: <br>
* fecha 15/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface RoleOptionService {
    

    /**
     * Crea roleOption <br>
     * Info. Creación: <br>
     * fecha 15/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param roleOption
     * @return 
     * @throws Exception 
     */
    public RoleOption create(RoleOption roleOption) throws Exception;
    /**
     * Modifica roleOption <br>
     * Info. Creación: <br>
     * fecha 15/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param roleOption
     * @return 
     * @throws Exception 
     */
    public RoleOption store(RoleOption roleOption) throws Exception;
    /**
     * Elimina roleOption<br>
     * Info. Creación: <br>
     * fecha 15/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param roleOption
     * @throws Exception 
     */
    public void remove(RoleOption roleOption) throws Exception;
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
     * Obtiene todos los registros de roleOption paginados <br>
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