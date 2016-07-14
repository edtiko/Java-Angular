/**
 * 
 */
package co.com.expertla.training.service;

import java.util.List;

import co.com.expertla.training.model.entities.User;

/**
 * @author Edwin G
 *
 */
public interface UserService {

	User findById(Integer id);

	User findByName(String name);
	
	User findUserByUsername(String username);

	Integer saveUser(User user);

	int updateUser(User user);

	Integer deleteUserById(Integer id);

	List<User> findAllUsers();

	void deleteAllUsers();

	public boolean isUserExist(User user);
}
