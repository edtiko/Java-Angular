/**
 *
 */
package co.com.expertla.training.user.service;

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

    public List<CountryDTO> findAllCountries();

    public List<FederalStateDTO> findStatesByCountry(Integer countryId);

    public List<CityDTO> findCitiesByState(Integer stateId);
    
    public boolean isUser(String username, String password);

    public void saveProfilePhoto(byte[] bytes, Integer userId);

}
