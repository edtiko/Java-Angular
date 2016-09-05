package co.com.expertla.training.web.controller.user;

import co.com.expertla.training.service.configuration.CountryService;
import co.com.expertla.training.enums.StateEnum;
import co.com.expertla.training.model.dto.CityDTO;
import co.com.expertla.training.model.dto.FederalStateDTO;
import co.com.expertla.training.model.dto.OpenTokDTO;
import co.com.expertla.training.model.dto.OptionDTO;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.Country;
import co.com.expertla.training.model.entities.Discipline;
import co.com.expertla.training.model.entities.DisciplineUser;
import co.com.expertla.training.model.entities.Role;
import co.com.expertla.training.model.entities.RoleUser;
import co.com.expertla.training.model.entities.TrainingPlan;
import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.plan.TrainingPlanUserService;
import co.com.expertla.training.service.security.RoleUserService;
import co.com.expertla.training.service.user.DisciplineUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import co.com.expertla.training.service.user.UserService;
import co.com.expertla.training.web.controller.security.OptionController;
import co.com.expertla.training.web.enums.StatusResponse;
import com.opentok.OpenTok;
import com.opentok.exception.OpenTokException;
import java.io.IOException;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

@RestController
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class);
    public static final String ROOT = "c:/upload-video/";
    private static final String apiKey = "45634832";
    private static final String apiSecret = "547b77a30287725ef942607913540d1eef48a161";
    private static OpenTok opentok;

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
        } catch (IOException e) {
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
        System.out.println("Fetching User with id " + userId);
        UserDTO user = userService.findById(userId);
        if (user == null) {
            System.out.println("User with id " + userId + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //-------------------Create a User--------------------------------------------------------
    @RequestMapping(value = "user/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response createUser(@RequestBody UserDTO userDTO) {
        ResponseService responseService = new ResponseService();
        try {
            User user = new User();
            user.setLogin(userDTO.getLogin());
            user.setName(userDTO.getFirstName());
            user.setSecondName(userDTO.getSecondName());
            user.setPassword(userDTO.getPassword());
            user.setEmail(userDTO.getEmail());
            user.setIndMetricSys(userDTO.getIndMetricSys());
            user.setPhone(userDTO.getPhone());
            user.setLastName(userDTO.getLastName());
            user.setSex(userDTO.getSex());
            user.setStateId(StateEnum.ACTIVE.getId().shortValue());
            
            if(userDTO.getCountryId() != null) {
                user.setCountryId(new Country(userDTO.getCountryId()));
            }
            
            if (userService.findUserByUsername(user.getLogin()) != null) {
                responseService.setOutput("El usuario " + user.getLogin() + " ya existe");
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
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
            

            TrainingPlanUser trainingPlanUser = new TrainingPlanUser();
            trainingPlanUser.setStateId(StateEnum.ACTIVE.getId());
            trainingPlanUser.setUserId(user);
            trainingPlanUser.setTrainingPlanId(new TrainingPlan(1));//Plan basico por defecto
            trainingPlanUserService.create(trainingPlanUser);

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

    @RequestMapping(value = "user/authenticate/{login}", method = RequestMethod.GET)
    public Response autenticateUser(@PathVariable("login") String login, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        ResponseService responseService = new ResponseService();
        try {
            UserDTO userDto = userService.findUserByUsername(login);
            if (userDto == null) {
                responseService.setOutput("El usuario " + login + " no existe");
                response.sendRedirect("http://181.143.227.220:8081/cpt/my-account/customer-logout/");
                return null;
            }
            
            session.setAttribute("user", userDto);
            Locale locale = new Locale("es", "CO");
            Locale.setDefault(locale);
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
        System.out.println("Updating User " + userId);

        UserDTO currentUser = userService.findById(userId);

        if (currentUser == null) {
            System.out.println("User with id " + userId + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.updateUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //------------------- Delete a User --------------------------------------------------------
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("userId") Integer userId) {
        System.out.println("Fetching & Deleting User with id " + userId);

        UserDTO user = userService.findById(userId);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + userId + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

    @RequestMapping(value = "/video/upload", method = RequestMethod.POST)
    public @ResponseBody
    Response uploadVideo(@RequestParam("fileToUpload") MultipartFile file, @RequestParam String filename) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        if (!file.isEmpty()) {
            try {
                //byte[] bytes = file.getBytes();
                Files.copy(file.getInputStream(), Paths.get(ROOT, filename));
                strResponse.append("video cargado correctamente.");
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
            strResponse.append("Video cargado esta vacio.");
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        }
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
     * @author Angela Ramirez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/user/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<UserDTO> userList = userService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(), paginateDto.getOrder());
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
}
