package co.com.expertla.training.service;

import co.com.expertla.training.model.dto.ObjetiveDTO;
import java.util.List;

/**
 * Service for Objetive <br>
 * Creation Date : <br>
 * date 18/07/2016 <br>
 *
 * @author Angela Ramírez
*
 */
public interface ObjetiveService {

    /**
     * Trae todos los registros de objetive <br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<ObjetiveDTO> findAll() throws Exception;
}
