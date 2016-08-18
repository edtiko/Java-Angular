package co.com.expertla.training.service.user;

import co.com.expertla.training.model.dto.DashboardDTO;
import co.com.expertla.training.model.dto.UserProfileDTO;
import co.com.expertla.training.model.entities.UserProfile;

/**
 * Service for User Profile <br>
 * Creation Date : <br>
 * date 14/07/2016 <br>
 * @author Angela Ramírez
 */
public interface UserProfileService {

    /**
     * Busca user profile dto por user Id <br>
     * Creation Date : <br>
     * date 14/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @param id
     * @return
     */
    public UserProfileDTO findDTOByUserId(Integer id) throws Exception;
    
    /**
     * Crea la información user profile <br>
     * Creation Date : <br>
     * date 14/07/2016 <br>
     * @author Angela Ramírez
     * @param userProfileDTO
     * @throws Exception
     */
    public void create(UserProfileDTO userProfileDTO) throws Exception;
    
    /**
     * Modifica la información user profile <br>
     * Creation Date : <br>
     * date 14/07/2016 <br>
     * @author Angela Ramírez
     * @param userProfileDTO
     * @throws Exception
     * @return
     */
    public UserProfileDTO merge(UserProfileDTO userProfileDTO) throws Exception;
    
    /**
     * Busca user profile por user Id <br>
     * Creation Date : <br>
     * date 14/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @param id
     * @return
     */
    public UserProfile findByUserId(Integer id) throws Exception;
    
    
    /**
     * Busca Dashboard dto por user Id <br>
     * Creation Date : <br>
     * date 10/08/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @param id
     * @return
     */
    public DashboardDTO findDashboardDTOByUserId(Integer id) throws Exception;
}
