package co.com.expertla.training.service.configuration;

import co.com.expertla.training.model.dto.DisciplineDTO;
import java.util.List;

/**
 * Service for Sport Discipline <br>
 * Creation Date : <br>
 * date 15/07/2016 <br>
 *
 * @author Angela Ramírez
*
 */
public interface DisciplineService {

    /**
     * Trae todos los registros de discipline <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<DisciplineDTO> findAll() throws Exception;
    
    public DisciplineDTO findByUserId(Integer userId) throws Exception;
}
