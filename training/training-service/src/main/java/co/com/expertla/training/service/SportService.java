package co.com.expertla.training.service;

import co.com.expertla.training.model.dto.SportDTO;
import java.util.List;

/**
 * Service for Sport <br>
 * Creation Date : <br>
 * date 15/07/2016 <br>
 * @author Angela Ramírez
*
 */
public interface SportService {

    /**
     * Trae todos los registros de sport <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<SportDTO> findAll() throws Exception;
}
