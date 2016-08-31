package co.com.expertla.training.service.configuration;

import co.com.expertla.training.model.dto.ObjectiveDTO;
import java.util.List;

/**
 * Service for Objective <br>
 * Creation Date : <br>
 * date 18/07/2016 <br>
 * @author Angela Ramírez
 */
public interface ObjectiveService {

    /**
     * Trae todos los registros de objective <br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<ObjectiveDTO> findAll() throws Exception;
    
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
}
