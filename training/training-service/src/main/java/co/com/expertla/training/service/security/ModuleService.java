package co.com.expertla.training.service.security;

import co.com.expertla.training.model.dto.ModuleDTO;
import co.com.expertla.training.model.entities.Module;
import java.util.List;

/**
* Module Service <br>
* Info. Creación: <br>
* fecha 26/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface ModuleService {
    

    /**
     * Crea module <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param module
     * @return 
     * @throws Exception 
     */
    public Module create(Module module) throws Exception;
    /**
     * Modifica module <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param module
     * @throws Exception 
     */
    public Module store(Module module) throws Exception;
    /**
     * Elimina module<br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param module
     * @return 
     * @throws Exception 
     */
    public void remove(Module module) throws Exception;
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
     * Obtiene todos los registros de module paginados <br>
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
    
    /**
     * Obtiene todos los registros activos por el id del usuario <br>
     * Info. Creación: <br>
     * fecha 14/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return
     * @throws Exception 
     */
    public List<Module> findByUserId(Integer userId) throws Exception;



    
}