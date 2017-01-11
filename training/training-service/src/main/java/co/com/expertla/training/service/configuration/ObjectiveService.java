package co.com.expertla.training.service.configuration;

import co.com.expertla.training.model.dto.ObjectiveDTO;
import co.com.expertla.training.model.entities.Objective;
import java.util.List;

/**
* Objective Service <br>
* Info. Creación: <br>
* fecha 30/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface ObjectiveService {
    

    /**
     * Crea objective <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param objective
     * @return 
     * @throws Exception 
     */
    public Objective create(Objective objective) throws Exception;
    /**
     * Modifica objective <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param objective
     * @return 
     * @throws Exception 
     */
    public Objective store(Objective objective) throws Exception;
    /**
     * Elimina objective<br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param objective 
     * @throws Exception 
     */
    public void remove(Objective objective) throws Exception;
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
     * Obtiene todos los registros de objective paginados <br>
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
     * Trae todos los registros de objective por disciplineId<br>
     * Creation Date : <br>
     * date 26/08/2016 <br>
     * @author Angela Ramírez
     * @param disciplineId
     * @throws Exception
     * @return
     */
    public List<ObjectiveDTO> findByDiscipline(Integer disciplineId) throws Exception;
    
    public Integer findNextObjective(Integer trainingUserPlanId) throws Exception;
    
    public Integer findCurrentObjective(Integer trainingUserPlanId) throws Exception;

    
}
