package co.com.expertla.training.service.configuration;

import co.com.expertla.training.model.entities.BikeType;
import java.util.List;

/**
 * Service for BikeType <br>
 * Creation Date : <br>
 * date 19/08/2016 <br>
 *
 * @author Angela Ramírez
*
 */
public interface BikeTypeService {

    /**
     * Trae todos los registros de bike type <br>
     * Creation Date : <br>
     * date 19/08/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<BikeType> findAll() throws Exception;
}
