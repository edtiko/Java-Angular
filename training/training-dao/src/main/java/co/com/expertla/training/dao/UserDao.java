/**
 * 
 */
package co.com.expertla.training.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.User;
import java.util.List;


/**
 * @author Edwin G
 *
 */
public interface UserDao extends BaseDAO<User>{
	
	User findById(Integer id);
        
        List<User> findAllUsers();

}
