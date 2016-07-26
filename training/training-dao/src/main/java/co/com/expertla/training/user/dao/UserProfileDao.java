package co.com.expertla.training.user.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.UserProfileDTO;
import co.com.expertla.training.model.entities.UserProfile;

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
}
