package co.com.expertla.training.service.user;

import co.com.expertla.training.model.dto.CityDTO;
import co.com.expertla.training.model.dto.CountryDTO;
import co.com.expertla.training.model.dto.FederalStateDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.User;
import java.util.List;

/**
 * @author Edwin G
 *
 */
public interface UserService {

    UserDTO findById(Integer id);

    UserDTO findByName(String name);

    UserDTO findUserByUsername(String username);

    Integer saveUser(User user) throws Exception;

    int updateUser(UserDTO user);

    void deleteUserById(Integer id);

    List<UserDTO> findAllUsers();

    void deleteAllUsers();

    public boolean isUserExist(UserDTO user);

//    public List<CountryDTO> findAllCountries();

    public List<FederalStateDTO> findStatesByCountry(Integer countryId);

    public List<CityDTO> findCitiesByState(Integer stateId);
    
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
     * Crea un usuario interno <br>
     * Creation Date : <br>
     * date 18/08/2016 <br>
     * @author Angela Ramírez
     * @param user
     * @throws Exception
     */
    public void createInternalUser(UserDTO user) throws Exception;
    
    /**
     * Edita un usuario y su disciplina <br>
     * Creation Date : <br>
     * date 18/08/2016 <br>
     * @author Angela Ramírez
     * @param user
     * @throws Exception
     */
    public void editInternalUser(UserDTO user) throws Exception;
}
