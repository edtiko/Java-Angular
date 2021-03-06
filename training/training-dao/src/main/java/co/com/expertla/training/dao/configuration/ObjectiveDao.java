package co.com.expertla.training.dao.configuration;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.ObjectiveDTO;
import co.com.expertla.training.model.entities.Objective;
import java.util.List;

/**
* Objective Dao <br>
* Info. Creación: <br>
* fecha 30/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface ObjectiveDao extends BaseDAO<Objective> {
    
    
    /**
     * Obtiene todos los registros de objective <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<ObjectiveDTO> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de objective <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Objective> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de objective paginados <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @return
     * @throws Exception 
     */
    public List<ObjectiveDTO> findPaginate(int first, int max, String order) throws Exception;

    /**
     * Obtiene todos los registros de objective por su id <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param objective
     * @return
     * @throws Exception 
     */
    public List<Objective> findByObjective(Objective objective) throws Exception;   

    /**
     * Obtiene todos los registros de objective por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param objective
     * @return
     * @throws Exception 
     */
    public List<Objective> findByFiltro(Objective objective) throws Exception; 

    /**
     * Trae todos los registros de objective por disciplineId <br>
     * Creation Date : <br>
     * date 26/08/2016 <br>
     * @author Angela Ramírez
     * @param disciplineId
     * @throws Exception
     * @return
     */
    public List<ObjectiveDTO> findByDiscipline(Integer disciplineId) throws Exception;
    
}
