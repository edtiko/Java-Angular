package co.expertic.training.service.configuration;

import co.expertic.training.model.dto.ConfigurationPlanDTO;
import co.expertic.training.model.entities.ConfigurationPlan;
import java.util.List;

/**
* ConfigurationPlan Service <br>
* Info. Creación: <br>
* fecha 24/11/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface ConfigurationPlanService {
    

    /**
     * Crea configurationPlan <br>
     * Info. Creación: <br>
     * fecha 24/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param configurationPlan
     * @return 
     * @throws Exception 
     */
    public ConfigurationPlan create(ConfigurationPlan configurationPlan) throws Exception;
    /**
     * Modifica configurationPlan <br>
     * Info. Creación: <br>
     * fecha 24/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param configurationPlan
     * @return 
     * @throws Exception 
     */
    public ConfigurationPlan store(ConfigurationPlan configurationPlan) throws Exception;
    /**
     * Elimina configurationPlan<br>
     * Info. Creación: <br>
     * fecha 24/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param configurationPlan
     * @throws Exception 
     */
    public void remove(ConfigurationPlan configurationPlan) throws Exception;
    /**
     * Obtiene todos los registros de configurationPlan <br>
     * Info. Creación: <br>
     * fecha 24/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<ConfigurationPlan> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de configurationPlan <br>
     * Info. Creación: <br>
     * fecha 24/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<ConfigurationPlan> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de configurationPlan paginados <br>
     * Info. Creación: <br>
     * fecha 24/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<ConfigurationPlanDTO> findPaginate(int first, int max, String order, String filter) throws Exception;
    
    /**
     * Obtiene todos los registros de configurationPlan paginados <br>
     * Info. Creación: <br>
     * fecha 24/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @param planId
     * @return
     * @throws Exception 
     */
    public List<ConfigurationPlanDTO> findPaginate(int first, int max, String order, String filter, Integer planId) throws Exception;
    
    /**
     * Obtiene todos los registros de configurationPlan paginados <br>
     * Info. Creación: <br>
     * fecha 24/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param configurationPlan
     * @return
     * @throws Exception 
     */
    public List<ConfigurationPlan> findByConfigurationPlan(ConfigurationPlan configurationPlan) throws Exception;   

    /**
     * Obtiene todos los registros de configurationPlan por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 24/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param configurationPlan
     * @return
     * @throws Exception 
     */
    public List<ConfigurationPlan> findByFiltro(ConfigurationPlan configurationPlan) throws Exception; 



    
}