package co.com.expertla.training.service.impl.user;

import co.com.expertla.training.constant.UrlProperties;
import co.com.expertla.training.dao.configuration.CityDao;
import co.com.expertla.training.dao.configuration.DisciplineDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.City;
import java.util.Date;
import java.util.stream.Collectors;
import co.com.expertla.training.dao.configuration.FederalStateDao;
import co.com.expertla.training.dao.security.RoleUserDao;
import co.com.expertla.training.dao.user.DisciplineUserDao;
import co.com.expertla.training.dao.user.UserDao;
import co.com.expertla.training.dao.user.UserProfileDao;
import co.com.expertla.training.enums.StateEnum;
import co.com.expertla.training.model.dto.CityDTO;
import co.com.expertla.training.model.dto.FederalStateDTO;
import co.com.expertla.training.model.entities.Country;
import co.com.expertla.training.model.entities.Discipline;
import co.com.expertla.training.model.entities.DisciplineUser;
import co.com.expertla.training.model.entities.Role;
import co.com.expertla.training.model.entities.RoleUser;
import co.com.expertla.training.model.entities.State;
import co.com.expertla.training.model.entities.UserProfile;
import co.com.expertla.training.model.entities.VideoUser;
import co.com.expertla.training.service.user.UserService;
import co.com.expertla.training.dao.user.VideoUserDao;
import co.com.expertla.training.dao.user.VisibleFieldsUserDao;
import co.com.expertla.training.model.dto.DisciplineDTO;
import co.com.expertla.training.model.entities.VisibleFieldsUser;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service("usuarioService")
@Transactional
public class UserServiceImpl implements UserService {

    public static final Short STATE_ACTIVE = 1;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private FederalStateDao federalStateDao;

    @Autowired
    private RoleUserDao roleUserDao;

    @Autowired
    private DisciplineUserDao disciplineUserDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private VideoUserDao videoUserDao;
    
    @Autowired
    private DisciplineDao disciplineDao;
    
    @Autowired
    private VisibleFieldsUserDao visibleFieldsDao;

    @Override
    public List<UserDTO> findAllUsers() {
        return userDao.findAllUsers().stream().map(UserDTO::mapFromUserEntity).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Integer id) throws Exception {
        UserDTO user = UserDTO.mapFromUserEntity(userDao.findById(id));
        
        if (user != null) {
            RoleUser roleUser = roleUserDao.findByUserId(user.getUserId());

            user.setTypeUser(roleUser != null ? roleUser.getRoleId().getRoleId().toString():"");
            user.setRoleId(roleUser != null ? roleUser.getRoleId().getRoleId():null);
            List<DisciplineDTO> disciplineUser = disciplineDao.findByUserId(user.getUserId());
            if(disciplineUser != null){
                user.setDisciplineId(disciplineUser.get(0).getDisciplineId());
                user.setDisciplineName(disciplineUser.get(0).getName());
            }
            
            return user;
        }
        
        return null;
    }

    @Override
    public UserDTO findByName(String name) {
        /*for(Usuario user : users){
            if(user.getUsername().equalsIgnoreCase(name)){
                return user;
            }
        }*/
        return null;
    }

    @Override
    public UserDTO findUserByUsername(String username) throws Exception {
        UserDTO user = UserDTO.mapFromUserEntity(userDao.findUserByUsername(username));

        if (user != null) {
            RoleUser roleUser = roleUserDao.findByUserId(user.getUserId());

            user.setTypeUser(roleUser != null ? roleUser.getRoleId().getRoleId().toString():"");
            user.setRoleId(roleUser != null ? roleUser.getRoleId().getRoleId():null);
            user.setRoleName(roleUser != null ? roleUser.getRoleId().getName():"");
            List<DisciplineDTO> disciplineUser = disciplineDao.findByUserId(user.getUserId());
            if(disciplineUser != null){
                user.setDisciplineId(disciplineUser.get(0).getDisciplineId());
                user.setDisciplineName(disciplineUser.get(0).getName());
            }
            
            return user;
        }

        return null;

    }

    @Transactional
    @Override
    public Integer saveUser(User user) throws Exception {
        return userDao.saveUser(user);
    }

    @Transactional
    @Override
    public int updateUser(UserDTO userDTO) {       
        User user = userDao.findById(userDTO.getUserId());
        City city = cityDao.findById(userDTO.getCityId());
        user.setName(userDTO.getFirstName());
        user.setSecondName(userDTO.getSecondName());
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setBirthDate(userDTO.getBirthDate());
        user.setAddress(userDTO.getAddress());
        user.setSex(userDTO.getSex());
        user.setWeight(userDTO.getWeight());
        user.setPhone(userDTO.getPhone());
        user.setCellphone(userDTO.getCellphone());
        user.setCountryId(new Country(userDTO.getCountryId()));
        user.setCityId(city);
        user.setPostalCode(userDTO.getPostalCode());
        user.setFacebookPage(userDTO.getFacebookPage());
        user.setTwitterPage(userDTO.getTwitterPage());
        user.setInstagramPage(userDTO.getInstagramPage());
        user.setWebPage(userDTO.getWebPage());
        user.setIndLoginFirstTime(userDTO.getIndLoginFirstTime());
        user.setCreationDate(new Date());
        user.setName(userDTO.getFirstName());
        user.setIndStrava(userDTO.getIndStrava());
        user.setCodeStrava(userDTO.getCodeStrava());
        user.setLastExecuteStrava(userDTO.getLastExecuteStrava());

        return userDao.updateUser(user);
    }

    @Transactional
    @Override
    public void deleteUserById(Integer id) {

        User user = userDao.findById(id);
        userDao.deleteUser(user);
    }

    @Override
    public boolean isUserExist(UserDTO user) {
        return findByName(user.getFirstName()) != null;
    }

    @Override
    public void deleteAllUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public List<CountryDTO> findAllCountries() {
//        return countryDao.findAll().stream().map(CountryDTO::mapFromCountryEntity).collect(Collectors.toList());
//    }
    @Override
    public List<FederalStateDTO> findStatesByCountry(Integer countryId) {
        return federalStateDao.findStatesByCountryId(countryId).stream().map(FederalStateDTO::mapFromStateEntity).collect(Collectors.toList());
    }

    @Override
    public List<CityDTO> findCitiesByState(Integer stateId) {
        return cityDao.findCitiesByState(stateId).stream().map(CityDTO::mapFromCityEntity).collect(Collectors.toList());
    }

    @Override
    public boolean isUser(String username, String password) {
        return userDao.isUser(username, password);
    }

    @Override
    public void saveProfilePhoto(byte[] bytes, Integer userId) {
        userDao.saveProfilePhoto(bytes, userId);
    }

    @Override
    public List<UserDTO> findAllUsersWithDiscipline() throws Exception {
        return userDao.findAllUsersWithDiscipline();
    }

    @Override
    public User createInternalUser(UserDTO dto) throws Exception {
        User user = userDao.findUserByUsername(dto.getLogin());
        if (user != null) {
            throw new Exception("Ya existe ese nombre de usuario");
        }
        user = new User();
        user.setLogin(dto.getLogin());
        user.setName(dto.getFirstName());
        user.setSecondName(dto.getSecondName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setSex(dto.getSex());
        user.setPhone(dto.getPhone());
        user.setStateId(StateEnum.ACTIVE.getId().shortValue());
        user.setCreationDate(new Date());
        user.setIndMetricSys("1");
        user.setCountryId(new Country(dto.getCountryId()));
        user.setUserCreate(dto.getUserCreate());

        DisciplineUser discipline = new DisciplineUser();
        discipline.setUserId(user);
        discipline.setDiscipline(new Discipline(dto.getDisciplineId()));

        VideoUser video = new VideoUser();

        if (dto.getUrlVideo() != null) {
            video.setStateId(new State(StateEnum.ACTIVE.getId()));
            video.setUrl(dto.getUrlVideo());
            video.setUserId(user);
            video.setCreationDate(new Date());
        }
        UserProfile profile = new UserProfile();
        profile.setUserId(user);
        profile.setAboutMe(dto.getAboutMe());

        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(user);
        roleUser.setRoleId(new Role(dto.getRoleId()));
        String jsonResponse = wordpressIntegrationUserRegistration(dto);
        System.out.println(jsonResponse);
        if (jsonResponse != null && !jsonResponse.isEmpty()) {
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(jsonResponse);
            String statusRes = jo.get("status").getAsString();

            if (statusRes.equals("fail")) {
                throw new Exception(jo.get("output").getAsJsonObject().get("errors").toString());
            }
        }
        
        user = userDao.create(user);
        disciplineUserDao.create(discipline);
        roleUserDao.create(roleUser);
        userProfileDao.create(profile);
        if (dto.getUrlVideo() != null) {
            videoUserDao.create(video);
        }
        //mostrar por defecto la foto de perfil
        if(user != null){
        VisibleFieldsUser visibleDefault = new VisibleFieldsUser();
        visibleDefault.setColumnName("profile_photo");
        visibleDefault.setTableName("user_training");
        visibleDefault.setUserId(user.getUserId());
        visibleFieldsDao.create(visibleDefault);
        }
        
        return user;
    }

    @Override
    public void editInternalUser(UserDTO dto) throws Exception {
        User user = userDao.findById(dto.getUserId());
        User userExist = userDao.findUserByUsername(dto.getLogin());
        if (userExist != null && !userExist.getLogin().equals(user.getLogin())) {
            throw new Exception("Ya existe ese nombre de usuario");
        }
        user.setLogin(dto.getLogin());
        user.setName(dto.getFirstName());
        user.setSecondName(dto.getSecondName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setSex(dto.getSex());
        user.setPhone(dto.getPhone());
        user.setStateId(dto.getStateId());
        user.setUserUpdate(dto.getUserUpdate());
        user.setLastUpdate(new Date());

        if (dto.getIndLoginFirstTime() != null) {
            user.setIndLoginFirstTime(dto.getIndLoginFirstTime());
        }
        user.setCountryId(new Country(dto.getCountryId()));

        UserProfile profile = userProfileDao.findByUserId(dto.getUserId());
        if (profile != null) {
            profile.setAboutMe(dto.getAboutMe());
            userProfileDao.merge(profile);
        }

        List<VideoUser> videoUserList = videoUserDao.getByUser(dto.getUserId());
        if (videoUserList != null && !videoUserList.isEmpty()) {
            VideoUser videoUser = videoUserList.get(0);
            videoUser.setUrl(dto.getUrlVideo());
            videoUserDao.merge(videoUser);
        }

        DisciplineUser discipline = disciplineUserDao.findByUserId(dto.getUserId());
        if (discipline != null) {
            discipline.setDiscipline(new Discipline(dto.getDisciplineId()));
            disciplineUserDao.merge(discipline);
        }

        RoleUser role = roleUserDao.findByUserId(dto.getUserId());
        if (role != null) {
            role.setRoleId(new Role(dto.getRoleId()));
            roleUserDao.merge(role);
        }

        userDao.merge(user);
    }
    
    /**
     * 
     * @param uri
     * @param params
     * @return
     * @throws IOException 
     */
    @Override
    public String sendPostWordpress(String uri, String params) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setInstanceFollowRedirects(true);
        String postData = params;
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-length", String.valueOf(postData.length()));
        con.setDoOutput(true);
        con.setDoInput(true);
        try (DataOutputStream output = new DataOutputStream(con.getOutputStream())) {
            output.writeBytes(postData);
        }
        int code = con.getResponseCode(); // 200 = HTTP_OK
        // read the response
        DataInputStream input = new DataInputStream(con.getInputStream());
        int c;
        StringBuilder resultBuf = new StringBuilder();
        while ((c = input.read()) != -1) {
            resultBuf.append((char) c);
        }
        input.close();

        return resultBuf.toString();
    }
    
    /**
     * 
     * @param dto
     * @return
     * @throws IOException 
     */
    @Override
    public String wordpressIntegrationUserRegistration(UserDTO dto) throws IOException {
        String postData = "login=" + dto.getLogin().trim() + "&email=" + dto.getEmail();
        
        if(dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            postData += "&password=" + dto.getPassword();
        }
        
        return sendPostWordpress(UrlProperties.URL_PORTAL + "create_user_internal.php", postData);        
    }

    @Override
    public List<User> findUserByRole(Integer roleId) throws Exception {
        return userDao.findUserByRole(roleId);
    }

    @Override
    public List<UserDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        return userDao.findPaginate(first, max, order, filter);
    }

    @Override
    public List<UserDTO> findUserWithDisciplineById(Integer userId) throws Exception {
        return userDao.findUserWithDisciplineById(userId);
    }
    
    @Override
    public User getStarFromAtlethe(Integer userId) throws Exception {
        List<User> userList = userDao.getStarFromAtlethe(userId);
        
        if(userList != null && !userList.isEmpty()) {
            return userList.get(0);
        }
        
        return null;
    }
}
