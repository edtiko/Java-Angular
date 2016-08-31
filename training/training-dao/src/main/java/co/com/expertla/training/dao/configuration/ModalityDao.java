package co.com.expertla.training.dao.configuration;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.ModalityDTO;
import co.com.expertla.training.model.entities.Modality;
import java.util.List;

/**
* Dao for Modality <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
public interface ModalityDao extends BaseDAO<Modality>{
	
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
