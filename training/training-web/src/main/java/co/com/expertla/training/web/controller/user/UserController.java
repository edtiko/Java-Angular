package co.com.expertla.training.web.controller.user;

import co.com.expertla.base.util.DateUtil;
import co.com.expertla.training.constant.UrlProperties;
import co.com.expertla.training.enums.RoleEnum;
import co.com.expertla.training.service.configuration.CountryService;
import co.com.expertla.training.enums.StateEnum;
import co.com.expertla.training.model.dto.CityDTO;
import co.com.expertla.training.model.dto.CoachAssignedPlanDTO;
import co.com.expertla.training.model.dto.CoachExtAthleteDTO;
import co.com.expertla.training.model.dto.CommunicationDTO;
import co.com.expertla.training.model.dto.FederalStateDTO;
import co.com.expertla.training.model.dto.OpenTokDTO;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.dto.UserBasicMovilDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.dto.UserMovilDTO;
import co.com.expertla.training.model.entities.CoachAssignedPlan;
import co.com.expertla.training.model.entities.Country;
import co.com.expertla.training.model.entities.Discipline;
import co.com.expertla.training.model.entities.DisciplineUser;
import co.com.expertla.training.model.entities.Role;
import co.com.expertla.training.model.entities.RoleUser;
import co.com.expertla.training.model.entities.StarTeam;
import co.com.expertla.training.model.entities.TrainingPlan;
import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.entities.UserTrainingOrder;
import co.com.expertla.training.model.entities.VisibleFieldsUser;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.configuration.StartTeamService;
import co.com.expertla.training.service.plan.CoachAssignedPlanService;
import co.com.expertla.training.service.plan.CoachExtAthleteService;
import co.com.expertla.training.service.plan.MailCommunicationService;
import co.com.expertla.training.service.plan.PlanAudioService;
import co.com.expertla.training.service.plan.PlanMessageService;
import co.com.expertla.training.service.plan.PlanVideoService;
import co.com.expertla.training.service.plan.TrainingPlanUserService;
import co.com.expertla.training.service.plan.UserTrainingOrderService;
import co.com.expertla.training.service.security.RoleUserService;
import co.com.expertla.training.service.user.DisciplineUserService;
import co.com.expertla.training.service.user.StravaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import co.com.expertla.training.service.user.UserService;
import co.com.expertla.training.service.user.VisibleFieldsUserService;
import co.com.expertla.training.web.controller.security.OptionController;
import co.com.expertla.training.web.enums.StatusResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.opentok.OpenTok;
import com.opentok.exception.OpenTokException;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Locale;
import java.util.Objects;

@RestController
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class);

    private static final String apiKey = "45634832";
    private static final String apiSecret = "547b77a30287725ef942607913540d1eef48a161";
    private static OpenTok opentok;

    private static final String PLAN_TYPE_IN = "IN";
    private static final String PLAN_TYPE_EXT = "EXT";

    @Autowired
    UserService userService;  //Service which will do all data retrieval/manipulation work

    @Autowired
    DisciplineUserService disciplineUserService;

    @Autowired
    RoleUserService roleUserService;

    @Autowired
    TrainingPlanUserService trainingPlanUserService;

    @Autowired
    CountryService countryService;

    @Autowired
    UserTrainingOrderService userTrainingOrderService;

    @Autowired
    CoachAssignedPlanService coachAssignedPlanService;

    @Autowired
    CoachExtAthleteService coachExtAthleteService;

    @Autowired
    StartTeamService startTeamService;

    @Autowired
    StravaService stravaService;

    @Autowired
    PlanMessageService planMessageService;

    @Autowired
    PlanAudioService planAudioService;

    @Autowired
    PlanVideoService PlanVideoService;

    @Autowired
    MailCommunicationService mailCommunicationService;

    @Autowired
    CoachAssignedPlanService coachService;

    @Autowired
    CoachExtAthleteService coachExtService;
    
    @Autowired
    VisibleFieldsUserService visibleFieldsUserService;

    /**
     * Upload single file using Spring Controller
     *
     * @param file
     * @param userId
     * @return
     */
    @RequestMapping(value = "/uploadFile/{userId}", method = RequestMethod.POST)
    public @ResponseBody
    Response uploadFileHandler(@RequestParam("file") MultipartFile file, @PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                userService.saveProfilePhoto(bytes, userId);
                strResponse.append("Imagen cargada correctamente.");
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                responseService.setOutput(strResponse);
                return Response.status(Response.Status.OK).entity(responseService).build();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                responseService.setDetail(e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
            }
        } else {
            strResponse.append("Imagen cargada esta vacia.");
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/getImageProfile/{userId}", method = RequestMethod.GET)
    public ResponseEntity<String> getImageProfile(@PathVariable("userId") Integer userId,
            HttpServletResponse response) {
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            UserDTO usuario = userService.findById(userId);
            byte[] binaryData = usuario != null ? usuario.getProfilePhoto() : null;
            if (binaryData != null) {
                byte[] encodeBase64 = Base64.encodeBase64(binaryData);
                String base64Encoded = new String(encodeBase64, "UTF-8");
                return new ResponseEntity<>(base64Encoded, headers, HttpStatus.OK);
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    //-------------------Retrieve All Users--------------------------------------------------------
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> listAllUsers() {
        List<UserDTO> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //-------------------Retrieve Single User--------------------------------------------------------
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Integer userId) {
        try {
            System.out.println("Fetching User with id " + userId);
            UserDTO user = userService.findById(userId);
            if (user == null) {
                System.out.println("User with id " + userId + " not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //-------------------Create a User--------------------------------------------------------
    @RequestMapping(value = "user/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response createUser(@RequestBody UserDTO userDTO) {
        ResponseService responseService = new ResponseService();
        try {
            if (userService.findUserByUsername(userDTO.getLogin()) != null) {
                responseService.setOutput("El usuario " + userDTO.getLogin() + " ya existe");
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            responseService = createUserPlan(userDTO);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear usuario");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        }
    }

    /**
     *
     * @param userDTO
     * @return
     * @throws Exception
     */
    private ResponseService createUserPlan(UserDTO userDTO) throws Exception {
        ResponseService responseService = new ResponseService();
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setName(userDTO.getFirstName());
        user.setSecondName(userDTO.getSecondName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setIndMetricSys(userDTO.getIndMetricSys());

        if (userDTO.getIndMetricSys() == null || userDTO.getIndMetricSys().isEmpty()) {
            user.setIndMetricSys("1");
        }

        user.setPhone(userDTO.getPhone());
        user.setLastName(userDTO.getLastName());
        user.setSex(userDTO.getSex());
        user.setStateId(StateEnum.ACTIVE.getId().shortValue());
        user.setIndLoginFirstTime(userDTO.getIndLoginFirstTime());
        user.setUserWordpressId(userDTO.getUserWordpressId());

        if (userDTO.getCountryId() != null) {
            user.setCountryId(new Country(userDTO.getCountryId()));
        }

        user.setCreationDate(new Date());
        Integer userId = userService.saveUser(user);
        DisciplineUser disciplineUser = new DisciplineUser();
        disciplineUser.setUserId(new User(userId));
        disciplineUser.setDiscipline(new Discipline(userDTO.getDisciplineId()));
        disciplineUserService.create(disciplineUser);

        if (userDTO.getTypeUser() != null) {
            Role role = new Role();
            if (userDTO.getTypeUser().equals("atleta")) {
                role.setRoleId(1);
            } else if (userDTO.getTypeUser().equals("coach")) {
                role.setRoleId(2);
            } else {
                role.setRoleId(3);
            }

            RoleUser roleUser = new RoleUser();
            roleUser.setRoleId(role);
            roleUser.setUserId(user);
            roleUserService.create(roleUser);
        }
         //mostrar por defecto la foto de perfil
        if (user != null) {
            VisibleFieldsUser visibleDefault = new VisibleFieldsUser();
            visibleDefault.setColumnName("profile_photo");
            visibleDefault.setTableName("user_training");
            visibleDefault.setUserId(userId);
            visibleFieldsUserService.create(visibleDefault);
        }

        TrainingPlanUser trainingPlanUser = new TrainingPlanUser();
        trainingPlanUser.setStateId(StateEnum.ACTIVE.getId());
        trainingPlanUser.setUserId(user);
        trainingPlanUser.setTrainingPlanId(new TrainingPlan(0));//Plan basico por defecto
        trainingPlanUserService.create(trainingPlanUser);

        responseService.setStatus(StatusResponse.SUCCESS.getName());
        return responseService;
    }

    @RequestMapping(value = "user/authenticate/{login}", method = RequestMethod.GET)
    public Response autenticateUser(@PathVariable("login") String login, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        ResponseService responseService = new ResponseService();
        session.removeAttribute("user");
        try {
            UserDTO userDto = userService.findUserByUsername(login);
            if (userDto == null) {
                responseService.setOutput("El usuario " + login + " no existe");
                response.sendRedirect(UrlProperties.URL_PORTAL + "mi-cuenta/");
                return null;
            }
            UserDTO userSession = new UserDTO();
            userSession.setUserId(userDto.getUserId());
            userSession.setFirstName(userDto.getFirstName());
            userSession.setLastName(userDto.getLastName());
            userSession.setSecondName(userDto.getSecondName());
            userSession.setTypeUser(userDto.getTypeUser());
            userSession.setFullName(userDto.getFullName());
            userSession.setIndLoginFirstTime(userDto.getIndLoginFirstTime());
            userSession.setDisciplineId(userDto.getDisciplineId());
            userSession.setDisciplineName(userDto.getDisciplineName());
            userSession.setRoleName(userDto.getRoleName());
            userSession.setIndStrava(userDto.getIndStrava());
            userSession.setProfilePhoto(userDto.getProfilePhoto());
            if (userDto.getUserWordpressId() != null) {
                createOrderFromAuthetication(userDto);
            }

            List<TrainingPlanUser> trainingPlanUserlist = trainingPlanUserService.getTrainingPlanUserByUser(new User(userDto.getUserId()));
            if (trainingPlanUserlist != null && !trainingPlanUserlist.isEmpty()) {
                userSession.setPlanActiveId(trainingPlanUserlist.get(0).getTrainingPlanId().getTrainingPlanId());
                userSession.setTrainingPlanUserId(trainingPlanUserlist.get(0).getTrainingPlanUserId());
            }

            session.setAttribute("user", userSession);
            Locale locale = new Locale("es", "CO");
            Locale.setDefault(locale);

            //Importa los datos de strava si el usuario los tiene autorizados
            if (userDto.getIndStrava() != null
                    && userDto.getIndStrava().equals("1") && userDto.getLastExecuteStrava() != null
                    && DateUtil.compareMoreDate(new Date(), userDto.getLastExecuteStrava())) {
                Runnable task2 = () -> {
                    try {
                        stravaService.importStravaByUser(userDto.getUserId());
                    } catch (Exception ex) {
                        java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };
                new Thread(task2).start();
            }

            if (userDto.getIndLoginFirstTime() != null && userDto.getIndLoginFirstTime() == 1 && Objects.equals(userDto.getRoleId(), RoleEnum.ATLETA.getId())) {
                response.sendRedirect(request.getRequestURL() + "/../../../#/data-person");
                return null;
            }
            response.sendRedirect(request.getRequestURL() + "/../../../#/dashboard");
            return null;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error interno");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        }
    }

    @RequestMapping(value = "user/getUserSession", method = RequestMethod.GET)
    public Response getUserSession(HttpSession session, HttpServletResponse response) {
        ResponseService responseService = new ResponseService();
        try {
            responseService.setOutput(session.getAttribute("user"));
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error interno");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        }
    }

    //------------------- Update a User --------------------------------------------------------
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") Integer userId, @RequestBody UserDTO user) {
        try {
            System.out.println("Updating User " + userId);

            UserDTO currentUser = userService.findById(userId);

            if (currentUser == null) {
                System.out.println("User with id " + userId + " not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            userService.updateUser(user);

            String postData = "id=" + user.getUserWordpressId() + "&discipline_id=" + user.getDisciplineId()
                    + "&country_id=" + user.getCountryId();
            String url = UrlProperties.URL_PORTAL + "update_user.php";
            String jsonResponse = userService.sendPostWordpress(url, postData);
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject) jsonParser.parse(jsonResponse);
                String statusRes = jo.get("status").getAsString();

                if (statusRes.equals("fail")) {
                    java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, jo.get("output").getAsString());
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //------------------- Delete a User --------------------------------------------------------
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("userId") Integer userId) {
        try {
            System.out.println("Fetching & Deleting User with id " + userId);

            UserDTO user = userService.findById(userId);
            if (user == null) {
                System.out.println("Unable to delete. User with id " + userId + " not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            userService.deleteUserById(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //------------------- Delete All Users --------------------------------------------------------
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<UserDTO> deleteAllUsers() {
        System.out.println("Deleting All Users");

        userService.deleteAllUsers();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //-------------------Retrieve All Countries--------------------------------------------------------
    @RequestMapping(value = "/countries/", method = RequestMethod.GET)
    public ResponseEntity<List<Country>> listAllCountries() {
        try {
            List<Country> countries = countryService.findAll();
            if (countries.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
            }
            return new ResponseEntity<>(countries, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //-------------------Retrieve States--------------------------------------------------------
    @RequestMapping(value = "/states/{countryId}", method = RequestMethod.GET)
    public ResponseEntity<List<FederalStateDTO>> getStatesByCountry(@PathVariable("countryId") Integer countryId) {
        List<FederalStateDTO> states = userService.findStatesByCountry(countryId);
        if (states.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(states, HttpStatus.OK);
    }

    //-------------------Retrieve Cities--------------------------------------------------------
    @RequestMapping(value = "/cities/{stateId}", method = RequestMethod.GET)
    public ResponseEntity<List<CityDTO>> listAllCountries(@PathVariable("stateId") Integer stateId) {
        List<CityDTO> cities = userService.findCitiesByState(stateId);
        if (cities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/session/opentok", method = RequestMethod.GET)
    public @ResponseBody
    Response getSessionOpenTok(HttpSession session, HttpServletResponse response) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            opentok = new OpenTok(Integer.parseInt(apiKey), apiSecret);
            String sessionId = opentok.createSession().getSessionId();
            String token = opentok.generateToken(sessionId);
            OpenTokDTO openTok = new OpenTokDTO(apiKey, sessionId, token);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(openTok);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (NumberFormatException | OpenTokException e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

    @RequestMapping(value = "user/get/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response findUsersWithDiscipline() {
        ResponseService responseService = new ResponseService();
        try {
            List<UserDTO> list = userService.findAllUsersWithDiscipline();
            responseService.setOutput(list);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear usuario");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        }
    }

    @RequestMapping(value = "user/getDiscipline/by/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response findUsersWithDiscipline(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        try {
            List<UserDTO> list = userService.findUserWithDisciplineById(userId);

            if (list != null || !list.isEmpty()) {
                responseService.setOutput(list.get(0));
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            responseService.setOutput(null);
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear usuario");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        }
    }

    @RequestMapping(value = "user/create/internal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response createInternalUser(@RequestBody UserDTO userDTO) {
        ResponseService responseService = new ResponseService();
        try {
            User user = userService.createInternalUser(userDTO);
            responseService.setOutput(user);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear usuario");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        }
    }

    @RequestMapping(value = "user/merge/internal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response mergeInternalUser(@RequestBody UserDTO userDTO) {
        ResponseService responseService = new ResponseService();
        try {
            userService.editInternalUser(userDTO);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear usuario");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        }
    }

    /**
     * Consulta user paginado <br>
     * Creation Date: <br>
     * date 31/08/2016 <br>
     *
     * @author Angela Ramirez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/user/paginated/old", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {
            paginateDto.setPage((paginateDto.getPage() - 1) * paginateDto.getLimit());
            List<UserDTO> userList = userService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(), paginateDto.getOrder(), null);
            responseService.setOutput(userList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(OptionController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "user/authenticate/movil", method = RequestMethod.POST)
    public ResponseEntity<ResponseService> autenticateUserMovil(@RequestBody UserDTO user, HttpServletRequest request) {
        ResponseService responseService = new ResponseService();
        try {
            UserDTO userDto = userService.findUserByUsername(user.getLogin());
            if (userDto == null) {
                responseService.setOutput("El usuario " + user.getLogin() + " o la contraseña son invalidos");
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            String postData = "login=" + user.getLogin().trim() + "&password=" + user.getPassword();
            String url = UrlProperties.URL_PORTAL + "authenticate_user.php";
            String jsonResponse = userService.sendPostWordpress(url, postData);
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject) jsonParser.parse(jsonResponse);
                String statusRes = jo.get("status").getAsString();

                if (statusRes.equals("fail")) {
                    java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, jo.get("output").getAsString());
                    responseService.setOutput("Usuario o contraseña invalidos");
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }
            }

            UserMovilDTO userSession = new UserMovilDTO();
            userSession.setUserId(userDto.getUserId());
            userSession.setLogin(userDto.getLogin());
            userSession.setFirstName(userDto.getFirstName());
            userSession.setLastName(userDto.getLastName());
            userSession.setSecondName(userDto.getSecondName());
            userSession.setTypeUser(userDto.getTypeUser());
            userSession.setFullName(userDto.getFullName());
            userSession.setIndLoginFirstTime(userDto.getIndLoginFirstTime());
            userSession.setDisciplineId(userDto.getDisciplineId());
            userSession.setDisciplineName(userDto.getDisciplineName());
            userSession.setEmail(userDto.getEmail());
            userSession.setRoleId(userDto.getRoleId());

            if (userDto.getUserWordpressId() != null) {
                createOrderFromAuthetication(userDto);
            }

            if (userDto.getRoleId().equals(RoleEnum.ATLETA.getId())) {
                CoachAssignedPlanDTO coachAssignedPlanDTO = coachAssignedPlanService.findByAthleteUserId(userDto.getUserId(), RoleEnum.COACH_INTERNO.getId());
                CoachExtAthleteDTO coachExtAthleteDTO = coachExtAthleteService.findByAthleteUserId(userDto.getUserId());

                if (coachAssignedPlanDTO != null) {
                    userSession.setPlanType(PLAN_TYPE_IN);
                    userSession.setCommunicationPlanId(coachAssignedPlanDTO.getId());
                    UserDTO coachUserDTO = coachAssignedPlanDTO.getCoachUserId();
                    UserBasicMovilDTO userCoach = new UserBasicMovilDTO();
                    userCoach.setUserId(coachUserDTO.getUserId());
                    userCoach.setLogin(coachUserDTO.getLogin());
                    userCoach.setFirstName(coachUserDTO.getFirstName());
                    userCoach.setLastName(coachUserDTO.getLastName());
                    userCoach.setSecondName(coachUserDTO.getSecondName());
                    userCoach.setTypeUser(coachUserDTO.getTypeUser());
                    userCoach.setFullName(coachUserDTO.getFullName());
                    userCoach.setDisciplineId(coachUserDTO.getDisciplineId());
                    userCoach.setDisciplineName(coachUserDTO.getDisciplineName());
                    userCoach.setRoleId(coachUserDTO.getRoleId());
                    userCoach.setEmail(coachUserDTO.getEmail());
                    userCoach.setSex(coachUserDTO.getSex());
                    userCoach.setBirthDate(coachUserDTO.getBirthDate());

                    UserDTO starUserDTO = coachAssignedPlanDTO.getStarUserId();
                    UserBasicMovilDTO userStar = new UserBasicMovilDTO();
                    userStar.setUserId(starUserDTO.getUserId());
                    userStar.setLogin(starUserDTO.getLogin());
                    userStar.setFirstName(starUserDTO.getFirstName());
                    userStar.setLastName(starUserDTO.getLastName());
                    userStar.setSecondName(starUserDTO.getSecondName());
                    userStar.setTypeUser(starUserDTO.getTypeUser());
                    userStar.setFullName(starUserDTO.getFullName());
                    userStar.setDisciplineId(starUserDTO.getDisciplineId());
                    userStar.setDisciplineName(starUserDTO.getDisciplineName());
                    userStar.setRoleId(starUserDTO.getRoleId());
                    userStar.setEmail(starUserDTO.getEmail());
                    userStar.setSex(starUserDTO.getSex());
                    userStar.setBirthDate(starUserDTO.getBirthDate());
                    userSession.setStarUser(userStar);
                    userSession.setCoachUser(userCoach);
                    //userSession.setCoachAssignedPlanId(coachAssignedPlanDTO.getId());
                } else if (coachExtAthleteDTO != null) {
                    userSession.setPlanType(PLAN_TYPE_EXT);
                    userSession.setCommunicationPlanId(coachExtAthleteDTO.getId());

                }
            }

            List<TrainingPlanUser> trainingPlanUserlist = trainingPlanUserService.getTrainingPlanUserByUser(new User(userDto.getUserId()));
            if (trainingPlanUserlist != null && !trainingPlanUserlist.isEmpty()) {
                userSession.setPlanActiveId(trainingPlanUserlist.get(0).getTrainingPlanId().getTrainingPlanId());
                userSession.setTrainingPlanUserId(trainingPlanUserlist.get(0).getTrainingPlanUserId());
            }
            //Importa los datos de strava si el usuario los tiene autorizados
            if (userDto.getIndStrava() != null && userDto.getIndStrava().equals("1") && userDto.getLastExecuteStrava() != null
                    && DateUtil.compareMoreDate(new Date(), userDto.getLastExecuteStrava())) {
                Runnable task2 = () -> {
                    try {
                        stravaService.importStravaByUser(userDto.getUserId());
                    } catch (Exception ex) {
                        java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };
                new Thread(task2).start();
            }

            responseService.setOutput(userSession);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error interno");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/download/photo/{userId}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity downloadPhotoByUser(@PathVariable("userId") Integer userId, HttpServletRequest request) {
        try {
            UserDTO userDto = userService.findById(userId);
            HttpHeaders responseHeaders = new HttpHeaders();

            if (userDto != null) {
                responseHeaders.add("content-disposition", "inline; filename=user" + userId + ".jpg");

                if (userDto.getProfilePhoto() != null) {
                    return new ResponseEntity(userDto.getProfilePhoto(), responseHeaders, HttpStatus.OK);
                }
                String uri = request.getRequestURL().substring(0, request.getRequestURL().indexOf("user/download"));
                uri += "static/img/profile-default.png";
                URL url = new URL(uri);
                InputStream in = new BufferedInputStream(url.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1 != (n = in.read(buf))) {
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();
                byte[] response = out.toByteArray();
                return new ResponseEntity(response, responseHeaders, HttpStatus.OK);
            }

            return new ResponseEntity("El usuario no existe", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(OptionController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "user/register/movil", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> registerUserMovil(@RequestBody UserDTO userDTO) {
        ResponseService responseService = new ResponseService();
        try {
            if (userService.findUserByUsername(userDTO.getLogin()) != null) {
                responseService.setOutput("El usuario " + userDTO.getLogin() + " ya existe");
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            String jsonResponse = userService.wordpressIntegrationUserRegistration(userDTO);

            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject) jsonParser.parse(jsonResponse);
                String statusRes = jo.get("status").getAsString();

                if (statusRes.equals("fail")) {
                    responseService.setOutput(jo.get("output").getAsJsonObject().get("errors").toString());
                    responseService.setDetail(null);
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }

                responseService = createUserPlan(userDTO);

                if (responseService.getOutput() == null) {
                    responseService.setOutput("Usuario registrado exitosamente");
                }

                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }

            responseService.setOutput("Error al crear usuario");
            responseService.setDetail(null);
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear usuario");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/update/personal/data", method = RequestMethod.POST)
    public ResponseEntity<ResponseService> updateUserPersonal(@RequestBody UserDTO user) {
        ResponseService responseService = new ResponseService();
        try {
            UserDTO currentUser = userService.findById(user.getUserId());

            if (currentUser == null) {
                responseService.setOutput("El usuario no existe");
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }

            userService.updateUser(user);
            responseService.setOutput("Usuario editado exitosamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar datos");
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(ex.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     *
     * @param userDto
     * @throws Exception
     */
    private void createOrderFromAuthetication(UserDTO userDto) throws Exception {
        UserTrainingOrder objUserTrainingOrder = new UserTrainingOrder();
        objUserTrainingOrder.setUserId(userDto.getUserWordpressId());
        objUserTrainingOrder.setStatus("pending");
        List<UserTrainingOrder> userTrainingOrderList = userTrainingOrderService.findByFiltro(objUserTrainingOrder);

        for (UserTrainingOrder userTrainingOrder : userTrainingOrderList) {
            String jsonResponse = userTrainingOrderService.getPlanIdByOrder(userTrainingOrder);
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                JsonParser jsonParser = new JsonParser();
                JsonObject jo = (JsonObject) jsonParser.parse(jsonResponse);
                String statusRes = jo.get("status").getAsString();

                if (statusRes.equals("success")) {

                    if (jo.get("planId") != null && !jo.get("planId").isJsonNull()
                            && !jo.get("planId").getAsString().trim().isEmpty()) {
                        Integer trainingPlanId = jo.get("planId").getAsInt();

                        List<TrainingPlanUser> trainingPlanUserlist = trainingPlanUserService.getTrainingPlanUserByUser(new User(userDto.getUserId()));

                        for (TrainingPlanUser trainingPlanUser : trainingPlanUserlist) {
                            trainingPlanUser.setStateId(StateEnum.INACTIVE.getId());
                            trainingPlanUserService.store(trainingPlanUser);
                        }

                        TrainingPlanUser trainingPlanUser = new TrainingPlanUser();
                        User userId = new User();
                        userId.setUserId(userDto.getUserId());
                        trainingPlanUser.setUserId(userId);
                        trainingPlanUser.setStateId(StateEnum.ACTIVE.getId());
                        trainingPlanUser.setTrainingPlanId(new TrainingPlan(trainingPlanId));
                        trainingPlanUserService.create(trainingPlanUser);

                        if (jo.get("starTeamId") != null
                                && !jo.get("starTeamId").getAsString().trim().isEmpty()) {
                            userDto.setIndLoginFirstTime(1);
                            userService.updateUser(userDto);
                            Integer starTeamId = jo.get("starTeamId").getAsInt();
                            CoachAssignedPlan coachAssignedPlan = new CoachAssignedPlan();
                            coachAssignedPlan.setCreationDate(new Date());
                            coachAssignedPlan.setStarTeamId(new StarTeam(starTeamId));
                            coachAssignedPlan.setStateId(StateEnum.ACTIVE.getId().shortValue());
                            coachAssignedPlan.setTrainingPlanUserId(trainingPlanUser);
                            coachAssignedPlanService.create(coachAssignedPlan);
                            List<StarTeam> starTeamList = startTeamService.findByStartTeam(new StarTeam(starTeamId));

                            if (starTeamList != null && !starTeamList.isEmpty()) {
                                StarTeam starTeam = starTeamList.get(0);
                                Integer starUserId = starTeam.getStarUserId().getUserId();
                                DisciplineUser disciplineUserStar = disciplineUserService.findByUserId(starUserId);
                                DisciplineUser disciplineUser = disciplineUserService.findByUserId(userDto.getUserId());

                                if (disciplineUser != null && disciplineUserStar != null) {
                                    if (!disciplineUser.getDisciplineUserId().equals(disciplineUserStar.getDisciplineUserId())) {
                                        disciplineUser.setDiscipline(disciplineUserStar.getDiscipline());
                                        disciplineUserService.store(disciplineUser);
                                    }
                                }

                            }
                        }else if(jo.get("membershipId") != null && !jo.get("membershipId").getAsString().trim().isEmpty()){
                               userDto.setIndLoginFirstTime(1);
                            userService.updateUser(userDto);
                            Integer starTeamId = 21; //Supervisor por defecto //TODO
                            CoachAssignedPlan coachAssignedPlan = new CoachAssignedPlan();
                            coachAssignedPlan.setCreationDate(new Date());
                            coachAssignedPlan.setStarTeamId(new StarTeam(starTeamId));
                            coachAssignedPlan.setStateId(StateEnum.ACTIVE.getId().shortValue());
                            coachAssignedPlan.setTrainingPlanUserId(trainingPlanUser);
                            coachAssignedPlanService.create(coachAssignedPlan);
                            List<StarTeam> starTeamList = startTeamService.findByStartTeam(new StarTeam(starTeamId));

                            if (starTeamList != null && !starTeamList.isEmpty()) {
                                StarTeam starTeam = starTeamList.get(0);
                                Integer coachUserId = starTeam.getCoachUserId().getUserId();
                                DisciplineUser disciplineUserCoach = disciplineUserService.findByUserId(coachUserId);
                                DisciplineUser disciplineUser = disciplineUserService.findByUserId(userDto.getUserId());

                                if (disciplineUser != null && disciplineUserCoach != null) {
                                    if (!disciplineUser.getDisciplineUserId().equals(disciplineUserCoach.getDisciplineUserId())) {
                                        disciplineUser.setDiscipline(disciplineUserCoach.getDiscipline());
                                        disciplineUserService.store(disciplineUser);
                                    }
                                }

                            }
                        }

                        userTrainingOrder.setStatus("integrated");
                        userTrainingOrderService.store(userTrainingOrder);
                    } else {
                        userTrainingOrder.setStatus("error");
                        userTrainingOrderService.store(userTrainingOrder);
                    }
                }
            }
        }
    }

    @RequestMapping(value = "user/get/coaches", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getCoaches() {
        ResponseService responseService = new ResponseService();
        try {
            List<UserDTO> list = UserDTO.mapFromUsersEntities(userService.findUserByRole(RoleEnum.COACH_INTERNO.getId()));

            if (list != null || !list.isEmpty()) {
                responseService.setOutput(list);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            responseService.setOutput(null);
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al obtener coaches");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        }
    }

    @RequestMapping(value = "user/get/stars", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getStars() {
        ResponseService responseService = new ResponseService();
        try {
            List<UserDTO> list = UserDTO.mapFromUsersEntities(userService.findUserByRole(RoleEnum.ESTRELLA.getId()));

            if (list != null || !list.isEmpty()) {
                responseService.setOutput(list);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            responseService.setOutput(null);
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al obtener estrellas");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        }
    }

    @RequestMapping(value = "user/get/supervisors", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getSupervisors() {
        ResponseService responseService = new ResponseService();
        try {
            List<UserDTO> list = UserDTO.mapFromUsersEntities(userService.findUserByRole(RoleEnum.SUPERVISOR.getId()));

            if (list != null || !list.isEmpty()) {
                responseService.setOutput(list);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }

            responseService.setOutput(null);
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al obtener supervisores");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/user/update/strava/autorize/{userId}/{stravaAutorize}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseService> updateUserStrava(@PathVariable("userId") Integer userId, @PathVariable("stravaAutorize") String stravaAutorize) {
        ResponseService responseService = new ResponseService();
        try {
            UserDTO currentUser = userService.findById(userId);

            if (currentUser == null) {
                responseService.setOutput("El usuario no existe");
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity(responseService, HttpStatus.OK);
            }
            currentUser.setIndStrava(stravaAutorize);
            responseService.setOutput("El usuario modificado exitosamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            userService.updateUser(currentUser);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar usuario");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "get/count/communication/{communicatePlanId}/{userId}/{toUserId}/{planType}/{roleSelected}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getCountCommunication(@PathVariable("communicatePlanId") Integer communicatePlanId, @PathVariable("userId") Integer userId,
            @PathVariable("toUserId") Integer toUserId, @PathVariable("planType") String planType, @PathVariable("roleSelected") Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        CommunicationDTO communication = new CommunicationDTO();

        try {
            communication = userService.getCommunicationUser(planType, communicatePlanId, userId, toUserId, roleSelected);

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(communication);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }
    
    @RequestMapping(value = "get/notification/{communicatePlanId}/{athleteUserId}/{userId}/{planType}/{roleSelected}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> notificationRoleCommunication(@PathVariable("communicatePlanId") Integer communicatePlanId, @PathVariable("athleteUserId") Integer athleteUserId,
            @PathVariable("userId") Integer userId, @PathVariable("planType") String planType, @PathVariable("roleSelected") Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        Boolean res = false;

        try {
            res = userService.notificationRoleCommunication(planType, communicatePlanId, userId, athleteUserId, roleSelected);

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(res);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }
}
