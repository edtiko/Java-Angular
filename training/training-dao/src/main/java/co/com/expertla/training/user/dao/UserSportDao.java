package co.com.expertla.training.user.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.UserSport;

/**
* Dao for UserSport <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
public interface UserSportDao extends BaseDAO<UserSport>{
	
    /**
     * Trae todos los registros de user sport by user Id <br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public UserSport findByUserId(Integer id) throws Exception;
        
}
