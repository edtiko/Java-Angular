package co.expertic.training.service.user;

import co.expertic.training.model.dto.UserAvailabilityDTO;
import co.expertic.training.model.entities.UserAvailability;
import java.util.List;

/**
 * Service for UserAvailability <br>
 * Creation Date : <br>
 * date 18/07/2016 <br>
 *
 * @author Angela Ramírez
*
 */
public interface UserAvailabilityService {

    /**
     * Trae todos los registros de UserAvailability por user Id <br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return dto
     */
    public List<UserAvailabilityDTO> findDtoByUserId(Integer id) throws Exception;
    
    /**
     * Trae todos los registros de UserAvailability por user Id <br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public UserAvailability findByUserId(Integer id) throws Exception;
    
    /**
     * Crea UserAvailability
     * @param userAvailability
     * @return
     * @throws Exception 
     */
    public UserAvailability create(UserAvailability userAvailability) throws Exception;
    
    /**
     * Modifica UserAvailability<br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @param userAvailability
     * @throws Exception
     * @return
     */
    public UserAvailability merge(UserAvailability userAvailability) throws Exception;
}
