package co.com.expertla.training.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.UserProfile;

/**
 * @author Angela Ramirez
 *
 */
public interface UserProfileDao extends BaseDAO<UserProfile>{
	
	public UserProfile findById(Integer id);
        
}
