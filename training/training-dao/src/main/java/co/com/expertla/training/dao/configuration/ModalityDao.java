package co.com.expertla.training.dao.configuration;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.ModalityDTO;
import co.com.expertla.training.model.entities.Modality;
import java.util.List;

/**
* Modality Dao <br>
* Info. Creación: <br>
* fecha Sep 5, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface ModalityDao extends BaseDAO<Modality> {
    
    /**
     * Obtiene todos los registros activos de modality <br>
     * Info. Creación: <br>
     * fecha Sep 5, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Modality> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de modality paginados <br>
     * Info. Creación: <br>
     * fecha Sep 5, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<ModalityDTO> findPaginate(int first, int max, String order, String filter) throws Exception;

    /**
     * Obtiene todos los registros de modality por su id <br>
     * Info. Creación: <br>
     * fecha Sep 5, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param modality
     * @return
     * @throws Exception 
     */
    public List<Modality> findByModality(Modality modality) throws Exception;   

    /**
     * Obtiene todos los registros de modality por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha Sep 5, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param modality
     * @return
     * @throws Exception 
     */
    public List<Modality> findByFiltro(Modality modality) throws Exception; 

    /**
     * Trae todos los registros de modality <br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<ModalityDTO> findAll() throws Exception;
    
    /**
     * Trae todos los registros de Modality  por discipline id<br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public List<ModalityDTO> findByDisciplineId(Integer id) throws Exception;
    
    /**
     * Trae todos los registros de modality por discipline id and objective Id<br>
     * Creation Date : <br>
     * date 30/08/2016 <br>
     * @author Angela Ramírez
     * @param objectiveId
     * @throws Exception
     * @return
     */
    public List<ModalityDTO> findByObjectiveId(Integer objectiveId) throws Exception;

}
