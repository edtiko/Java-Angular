package co.expertic.training.service.user;

import co.expertic.training.model.dto.CityDTO;
import co.expertic.training.model.dto.CommunicationDTO;
import co.expertic.training.model.dto.FederalStateDTO;
import co.expertic.training.model.dto.NotificationDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.dto.UserMovilDTO;
import co.expertic.training.model.entities.City;
import co.expertic.training.model.entities.Country;
import co.expertic.training.model.entities.FederalState;
import co.expertic.training.model.entities.User;
import java.io.IOException;
import java.util.List;

/**
 * @author Edwin G
 *
 */
public interface UserService {

    UserDTO findById(Integer id) throws Exception;

    UserDTO findByName(String name);

    UserDTO findUserByUsername(String username)throws Exception;

    Integer saveUser(User user) throws Exception;
    
    UserMovilDTO saveUserMovil(User user) throws Exception;

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
     * @author Angela Ramirez
     * @throws Exception
     * @return dto
     */
    public List<UserDTO> findAllUsersWithDiscipline() throws Exception;
    
    /**
     * Crea un usuario interno <br>
     * Creation Date : <br>
     * date 18/08/2016 <br>
     * @author Angela Ramirez
     * @param user
     * @return 
     * @throws Exception
     */
    public User createInternalUser(UserDTO user) throws Exception;
    
    /**
     * Edita un usuario y su disciplina <br>
     * Creation Date : <br>
     * date 18/08/2016 <br>
     * @author Angela Ramirez
     * @param user
     * @throws Exception
     */
    public void editInternalUser(UserDTO user) throws Exception;
    
    /**
     * Obtiene todos los registros de user por role <br>
     * Info. Creación: <br>
     * fecha 01/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param roleId
     * @return
     * @throws Exception 
     */
    public List<User> findUserByRole(Integer roleId) throws Exception;
	/**
     * Obtiene todos los registros de user paginados <br>
     * Creation Date : <br>
     * date 31/08/2016 <br>
     * @author Angela Ramirez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<UserDTO> findPaginate(int first, int max, String order, String filter) throws Exception;

	/**
     * Trae todos los registros de user training con su disciplina por id <br>
     * Creation Date : <br>
     * date 05/09/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @throws Exception
     * @return dto
     */
    public List<UserDTO> findUserWithDisciplineById(Integer userId) throws Exception;
    
    /**
     * 
     * @param uri
     * @param params
     * @return
     * @throws IOException 
     */
    public String sendPostWordpress(String uri, String params) throws IOException;
    
    public String wordpressIntegrationUserRegistration(UserDTO dto) throws IOException;
    
    /**
     * Consulta la estrella de un atleta <br>
     * Info. Creación: <br>
     * fecha Oct 28, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return 
     * @throws Exception 
     */
    public User getStarFromAtlethe(Integer userId)throws  Exception;

    public CommunicationDTO getCommunicationUser(String planType, Integer communicatePlanId, Integer userId, Integer toUserId, Integer roleSelected)throws  Exception;

    public Boolean notificationRoleCommunication(String planType, Integer communicatePlanId, Integer userId, Integer toUserId, Integer roleSelected)throws  Exception;

    public Boolean notificationInternal(Integer userSessionId) throws  Exception;
    
    public City getCityById(Integer cityId) throws Exception;
    
    public Country getCountryById(Integer countryId) throws Exception; 
    
    public FederalState getFederalStateById(Integer federalStateId) throws Exception; 

    public List<Integer> getUserAges()throws Exception; 

    public List<NotificationDTO> getUserNotification(Integer userSessionId) throws Exception; 

    public String getSubscriptions(Integer userId) throws Exception; 
   
}
