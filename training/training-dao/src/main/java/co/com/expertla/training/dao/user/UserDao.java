package co.com.expertla.training.dao.user;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.UserDTO;
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

    /**
     * Trae todos los registros de user training con su disciplina <br>
     * Creation Date : <br>
     * date 18/08/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return dto
     */
    public List<UserDTO> findAllUsersWithDiscipline() throws Exception;
    
    /**
     * Obtiene todos los registros de user paginados <br>
     * Creation Date : <br>
     * date 31/08/2016 <br>
     * @author Angela Ramirez
     * @param first
     * @param max
     * @param order
     * @return
     * @throws Exception 
     */
    public List<UserDTO> findPaginate(int first, int max, String order) throws Exception;
}
