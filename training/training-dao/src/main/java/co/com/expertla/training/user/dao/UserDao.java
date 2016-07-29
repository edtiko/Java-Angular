/**
 * 
 */
package co.com.expertla.training.user.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.User;
import java.util.List;


/**
 * @author Edwin G
 *
 */
public interface UserDao extends BaseDAO<User>{
	
	User findById(Integer id);
       User findUserByUsername(String userName);
        
        List<User> findAllUsers();

    public Integer saveUser(User user) throws Exception;
    
   public Integer updateUser(User user);

    public void deleteUser(User user);

    public boolean isUser(String username, String password);

    public void saveProfilePhoto(byte[] bytes, Integer userId);

    
}
