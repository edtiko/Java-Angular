package co.expertic.training.service.impl.user;

import co.expertic.training.constant.UrlProperties;
import co.expertic.training.dao.configuration.CityDao;
import co.expertic.training.dao.configuration.ConfigurationPlanDao;
import co.expertic.training.dao.configuration.CountryDao;
import co.expertic.training.dao.configuration.DisciplineDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.expertic.training.model.entities.User;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.City;
import java.util.Date;
import java.util.stream.Collectors;
import co.expertic.training.dao.configuration.FederalStateDao;
import co.expertic.training.dao.plan.CoachAssignedPlanDao;
import co.expertic.training.dao.plan.CoachExtAthleteDao;
import co.expertic.training.dao.plan.MailCommunicationDao;
import co.expertic.training.dao.plan.PlanAudioDao;
import co.expertic.training.dao.plan.PlanMessageDao;
import co.expertic.training.dao.plan.PlanVideoDao;
import co.expertic.training.dao.security.RoleUserDao;
import co.expertic.training.dao.user.DisciplineUserDao;
import co.expertic.training.dao.user.UserDao;
import co.expertic.training.dao.user.UserProfileDao;
import co.expertic.training.enums.StateEnum;
import co.expertic.training.model.dto.CityDTO;
import co.expertic.training.model.dto.FederalStateDTO;
import co.expertic.training.model.entities.Country;
import co.expertic.training.model.entities.Discipline;
import co.expertic.training.model.entities.DisciplineUser;
import co.expertic.training.model.entities.Role;
import co.expertic.training.model.entities.RoleUser;
import co.expertic.training.model.entities.State;
import co.expertic.training.model.entities.UserProfile;
import co.expertic.training.model.entities.VideoUser;
import co.expertic.training.service.user.UserService;
import co.expertic.training.dao.user.VideoUserDao;
import co.expertic.training.dao.user.VisibleFieldsUserDao;
import co.expertic.training.enums.RoleEnum;
import co.expertic.training.model.dto.AccountDTO;
import co.expertic.training.model.dto.CoachAssignedPlanDTO;
import co.expertic.training.model.dto.CoachExtAthleteDTO;
import co.expertic.training.model.dto.CommunicationDTO;
import co.expertic.training.model.dto.DisciplineDTO;
import co.expertic.training.model.dto.NotificationDTO;
import co.expertic.training.model.dto.UserMovilDTO;
import co.expertic.training.model.entities.ConfigurationPlan;
import co.expertic.training.model.entities.FederalState;
import co.expertic.training.model.entities.VisibleFieldsUser;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URLEncoder;

@Service("usuarioService")
@Transactional
public class UserServiceImpl implements UserService {

    public static final Short STATE_ACTIVE = 1;
    private static final String PLAN_TYPE_IN = "IN";
    private static final String PLAN_TYPE_EXT = "EXT";

    @Autowired
    private UserDao userDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private CountryDao countryDao;

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

    @Autowired
    PlanMessageDao planMessageDao;

    @Autowired
    PlanAudioDao planAudioDao;

    @Autowired
    PlanVideoDao PlanVideoDao;

    @Autowired
    MailCommunicationDao mailCommunicationDao;

    @Autowired
    CoachAssignedPlanDao coachDao;

    @Autowired
    CoachExtAthleteDao coachExtDao;

    @Autowired
    ConfigurationPlanDao ConfigurationPlanDao;

    @Override
    public List<UserDTO> findAllUsers() {
        return userDao.findAllUsers().stream().map(UserDTO::mapFromUserEntity).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Integer id) throws Exception {
        UserDTO user = UserDTO.mapFromUserEntity(userDao.findById(id));

        if (user != null) {
            RoleUser roleUser = roleUserDao.findByUserId(user.getUserId());

            user.setTypeUser(roleUser != null ? roleUser.getRoleId().getRoleId().toString() : "");
            user.setRoleId(roleUser != null ? roleUser.getRoleId().getRoleId() : null);
            List<DisciplineDTO> disciplineUser = disciplineDao.findByUserId(user.getUserId());
            if (disciplineUser != null) {
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

            user.setTypeUser(roleUser != null ? roleUser.getRoleId().getRoleId().toString() : "");
            user.setRoleId(roleUser != null ? roleUser.getRoleId().getRoleId() : null);
            user.setRoleName(roleUser != null ? roleUser.getRoleId().getName() : "");
            List<DisciplineDTO> disciplineUser = disciplineDao.findByUserId(user.getUserId());
            if (disciplineUser != null) {
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
        return userDao.saveUser(user).getUserId();
    }

    @Transactional
    @Override
    public UserMovilDTO saveUserMovil(User user) throws Exception {
        return UserMovilDTO.mapFromUserEntity(userDao.saveUser(user));
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
        if (user != null) {
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

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
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

        if (userList != null && !userList.isEmpty()) {
            return userList.get(0);
        }

        return null;
    }

    @Override
    public CommunicationDTO getCommunicationUser(String planType, Integer communicatePlanId, Integer userId, Integer toUserId, Integer roleSelected) throws Exception {

        CommunicationDTO communication = new CommunicationDTO();
        if (planType.equals(PLAN_TYPE_IN)) {
            ConfigurationPlan configuration = ConfigurationPlanDao.findByAthleteUserId(userId, roleSelected);
            communication.setAvailableMsg(planMessageDao.getCountMessagesByPlan(communicatePlanId, userId, roleSelected));
            communication.setReceivedMsg(planMessageDao.getCountMessagesReceived(communicatePlanId, userId, toUserId, roleSelected));
            communication.setEmergencyMsg(planMessageDao.getCountMessageEmergencyIn(communicatePlanId, userId, roleSelected));
            communication.setPlanMsg(configuration.getMessageCount());

            communication.setAvailableAudio(planAudioDao.getCountAudioByPlan(communicatePlanId, userId, roleSelected));
            communication.setReceivedAudio(planAudioDao.getCountAudiosReceived(communicatePlanId, userId, roleSelected));
            communication.setEmergencyAudio(planAudioDao.getCountAudioEmergencyByPlan(communicatePlanId, userId, roleSelected));
            communication.setPlanAudio(configuration.getAudioCount());
            communication.setAudioDuration(configuration.getAudioDuration());

            communication.setAvailableMail(mailCommunicationDao.getCountMailsByPlan(communicatePlanId, userId, roleSelected));
            communication.setReceivedMail(mailCommunicationDao.getCountMailsReceived(communicatePlanId, userId, toUserId, roleSelected));
            communication.setEmergencyMail(mailCommunicationDao.getMailsEmergencyByPlan(communicatePlanId, userId, roleSelected));
            communication.setPlanMail(configuration.getEmailCount());

            communication.setAvailableVideo(PlanVideoDao.getCountVideoByPlan(communicatePlanId, userId, roleSelected));
            communication.setReceivedVideo(PlanVideoDao.getCountVideosReceived(communicatePlanId, userId, toUserId , roleSelected));
            communication.setEmergencyVideo(PlanVideoDao.getCountVideoEmergencyIn(communicatePlanId, toUserId, roleSelected));
            communication.setPlanVideo(configuration.getVideoCount());
            communication.setVideoDuration(configuration.getVideoDuration());

        } else if (planType.equals(PLAN_TYPE_EXT)) {

            CoachExtAthleteDTO assigned = coachExtDao.findByAthleteUserId(userId);
            //communication.setAvailableMail(PlanVideoService.getCountVideoByPlan(communicatePlanId, userId, roleSelected));

        }

        return communication;
    }

    @Override
    public Boolean notificationRoleCommunication(String planType, Integer communicatePlanId, Integer userId, Integer athleteUserId, Integer roleSelected) throws Exception {
        Boolean communication = false;
        if (planType.equals(PLAN_TYPE_IN)) {

            if (planMessageDao.getCountMessagesReceived(communicatePlanId, athleteUserId, userId, roleSelected) > 0) {
                return true;
            }

            if (planAudioDao.getCountAudiosReceived(communicatePlanId, athleteUserId, roleSelected) > 0) {
                return true;
            }

            if (mailCommunicationDao.getCountMailsReceived(communicatePlanId, athleteUserId, userId, roleSelected) > 0) {
                return true;
            }

            if (PlanVideoDao.getCountVideosReceived(communicatePlanId, athleteUserId, userId, roleSelected) > 0) {
                return true;
            }

        }

        return communication;
    }

    @Override
    public Boolean notificationInternal(Integer userSessionId) throws Exception {
        return (userDao.getCountNotification(userSessionId) > 0);
    }

    @Override
    public City getCityById(Integer cityId) throws Exception {
        return cityDao.findById(cityId);
    }

    @Override
    public Country getCountryById(Integer countryId) throws Exception {
        return countryDao.findByCountry(new Country(countryId)).get(0);
    }

    @Override
    public FederalState getFederalStateById(Integer federalStateId) throws Exception {
        return federalStateDao.findById(federalStateId);
    }

    @Override
    public List<Integer> getUserAges() throws Exception {
        return userDao.getUserAges();
    }

    @Override
    public List<NotificationDTO> getUserNotification(Integer userSessionId) throws Exception {
        return userDao.getUserNotification(userSessionId);
    }

    @Override
    public String getSubscriptions(Integer userId) throws Exception {
        User user = userDao.findById(userId);
        String postData = "user_id=" + user.getUserWordpressId() + "&action=get_subscriptions";
        return sendPostWordpress(UrlProperties.URL_PORTAL + "training-controller.php", postData);
    }

    @Override
    public String cancelSubscription(Integer subscriptionId, String actualStatus) throws Exception {
        String postData = "subscription_id=" + subscriptionId + "&actual_status=" + actualStatus + "&action=cancel_subscription";
        return sendPostWordpress(UrlProperties.URL_PORTAL + "training-controller.php", postData);
    }

    @Override
    public String getInfoAddressUser(Integer userId) throws Exception {
        User user = userDao.findById(userId);
        String postData = "user_id=" + user.getUserWordpressId() + "&action=info_user_address";
        return sendPostWordpress(UrlProperties.URL_PORTAL + "training-controller.php", postData);
    }

    @Override
    public String editAddressUser(String addressUserJson) throws Exception {

        String postData = "json=" + URLEncoder.encode(addressUserJson, "UTF-8") + "&action=edit_user_address";
        return sendPostWordpress(UrlProperties.URL_PORTAL + "training-controller.php", postData);
    }

    @Override
    public String editAccountUser(AccountDTO account) throws Exception {
        User user = userDao.findById(account.getUserId());

        if (!user.getPassword().equals(account.getPassword())) {
            throw new Exception(", La contraseña actual es incorrecta");
        }

        user.setName(account.getFirstName());
        user.setSecondName(account.getSecondName());
        user.setLastName(account.getLastName());
        if (!"".equals(account.getNewPassword())) {
            user.setPassword(account.getNewPassword());
        }
        userDao.merge(user);
        String postData = "user_id=" + user.getUserWordpressId() + "&first_name=" + account.getFirstName()
                + "&second_name=" + account.getSecondName() + "&last_name=" + account.getLastName()
                + "&password=" + account.getNewPassword() + "&action=edit_account_user";
        return sendPostWordpress(UrlProperties.URL_PORTAL + "training-controller.php", postData);
    }

    @Override
    public CommunicationDTO getCommunicationStarAthlete(CoachAssignedPlanDTO plan) throws Exception {
        CommunicationDTO communication = new CommunicationDTO();
        Integer roleSelected = RoleEnum.ESTRELLA.getId();
        Integer athleteUserId = plan.getAthleteUserId().getUserId();
        Integer starUserId = plan.getStarUserId().getUserId();
        Integer coachUserId = plan.getCoachUserId().getUserId();

        ConfigurationPlan configuration = ConfigurationPlanDao.findByAthleteUserId(athleteUserId, roleSelected);
        communication.setAvailableMsg(planMessageDao.getCountMessagesByPlan(plan.getId(), starUserId, roleSelected));
        communication.setReceivedMsg(planMessageDao.getCountMessagesReceived(plan.getId(), starUserId, coachUserId, roleSelected));
        communication.setEmergencyMsg(planMessageDao.getCountMessageEmergencyIn(plan.getId(), starUserId, roleSelected));
        communication.setPlanMsg(configuration.getMessageCount());

        communication.setAvailableAudio(planAudioDao.getCountAudioByPlan(plan.getId(), starUserId, roleSelected));
        communication.setReceivedAudio(planAudioDao.getCountAudiosReceived(plan.getId(), starUserId, roleSelected));
        communication.setEmergencyAudio(planAudioDao.getCountAudioEmergencyByPlan(plan.getId(), starUserId, roleSelected));
        communication.setPlanAudio(configuration.getAudioCount());
        communication.setAudioDuration(configuration.getAudioDuration());

        communication.setAvailableMail(mailCommunicationDao.getCountMailsByPlan(plan.getId(), starUserId, roleSelected));
        communication.setReceivedMail(mailCommunicationDao.getCountMailsReceived(plan.getId(), starUserId, coachUserId, roleSelected));
        communication.setEmergencyMail(mailCommunicationDao.getMailsEmergencyByPlan(plan.getId(), starUserId, roleSelected));
        communication.setPlanMail(configuration.getEmailCount());

        communication.setAvailableVideo(PlanVideoDao.getCountVideoByPlan(plan.getId(), starUserId, roleSelected));
        communication.setReceivedVideo(PlanVideoDao.getCountVideosReceived(plan.getId(), starUserId, coachUserId, roleSelected));
        communication.setEmergencyVideo(PlanVideoDao.getCountVideoEmergencyIn(plan.getId(), starUserId, roleSelected));
        communication.setPlanVideo(configuration.getVideoCount());
        communication.setVideoDuration(configuration.getVideoDuration());

        return communication;
    }
}
