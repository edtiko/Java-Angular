package co.expertic.training.dao.user;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.dto.DashboardDTO;
import co.expertic.training.model.dto.UserProfileDTO;
import co.expertic.training.model.entities.UserProfile;

/**
 * Dao for User Profile <br>
 * Creation Date : <br>
 * date 15/07/2016 <br>
 * @author Angela Ramírez
 */
public interface UserProfileDao extends BaseDAO<UserProfile> {

    /**
     * Busca user profile dto por user Id <br>
     * Creation Date : <br>
     * date 14/07/2016 <br>
     *
     * @author Angela Ramírez
     * @param id
     * @return
     * @throws Exception
     */
    public UserProfileDTO findDTOByUserId(Integer id) throws Exception;
    
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
