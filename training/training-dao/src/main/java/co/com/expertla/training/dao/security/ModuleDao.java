package co.com.expertla.training.dao.security;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.ModuleDTO;
import co.com.expertla.training.model.entities.Module;
import java.util.List;

/**
* Module Dao <br>
* Info. Creación: <br>
* fecha 26/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface ModuleDao extends BaseDAO<Module> {
    
    
    /**
     * Obtiene todos los registros de module <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Module> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de module <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Module> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de module paginados <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @return
     * @throws Exception 
     */
    public List<ModuleDTO> findPaginate(int first, int max, String order) throws Exception;

    /**
     * Obtiene todos los registros de module por su id <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param module
     * @return
     * @throws Exception 
     */
    public List<Module> findByModule(Module module) throws Exception;   

    /**
     * Obtiene todos los registros de module por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param module
     * @return
     * @throws Exception 
     */
    public List<Module> findByFiltro(Module module) throws Exception; 

}
