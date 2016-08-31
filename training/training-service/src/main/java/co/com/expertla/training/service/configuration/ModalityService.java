package co.com.expertla.training.service.configuration;

import co.com.expertla.training.model.dto.ModalityDTO;
import java.util.List;

/**
 * Service for Modality <br>
 * Creation Date : <br>
 * date 18/07/2016 <br>
 *
 * @author Angela Ramírez
*
 */
public interface ModalityService {

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
     * Trae todos los registros de modality por discipline id<br>
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
