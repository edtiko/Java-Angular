package co.com.expertla.training.service;

import co.com.expertla.training.model.entities.UserProfile;

/**
 * @author Angela Ramirez
 *
 */
public interface UserProfileService {
        
	public UserProfile findById(Integer id);
}
