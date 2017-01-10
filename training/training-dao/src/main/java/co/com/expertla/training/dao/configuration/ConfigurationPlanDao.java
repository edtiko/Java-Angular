package co.com.expertla.training.dao.configuration;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.ConfigurationPlanDTO;
import co.com.expertla.training.model.entities.ConfigurationPlan;
import java.util.List;

/**
* ConfigurationPlan Dao <br>
* Info. Creación: <br>
* fecha 24/11/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface ConfigurationPlanDao extends BaseDAO<ConfigurationPlan> {
    
    
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
     * Obtiene todos los registros de configurationPlan por su id <br>
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
    
     /**
     * Obtiene la configuración del plan para un atleta según el rol seleccionado <br>
     * Info. Creación: <br>
     * fecha 10/01/2017 <br>
     * @author Edwin Gómez
     * @param userId
     * @param roleSelected
     * @return 
     * @throws co.com.expertla.base.jpa.DAOException 
     */
     public ConfigurationPlan findByAthleteUserId(Integer userId, Integer roleSelected) throws DAOException;

}
