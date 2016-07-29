/**
 * 
 */
package co.com.expertla.training.web.user.controller;

import co.com.expertla.training.model.dto.CityDTO;
import co.com.expertla.training.model.dto.CountryDTO;
import co.com.expertla.training.model.dto.FederalStateDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.util.ResponseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import co.com.expertla.training.service.UserService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
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

  
@RestController
public class UserController {
  
    
    private static final Logger LOGGER = Logger.getLogger(UserController.class);
    
    @Autowired
    UserService userService;  //Service which will do all data retrieval/manipulation work
  
    	/**
	 * Upload single file using Spring Controller
     * @param file
     * @param userId
     * @return 
	 */
    @RequestMapping(value = "/uploadFile/{userId}", method = RequestMethod.POST)
    public @ResponseBody
    String uploadFileHandler(@RequestParam("file") MultipartFile file, @PathVariable("userId") Integer userId) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                userService.saveProfilePhoto(bytes, userId);
                return "You successfully uploaded file=" ;
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                return "You failed to upload  => " + e.getMessage();
            }
        } else {
            return "You failed to upload  because the file was empty.";
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
        if(users.isEmpty()){
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
    public Response createUser(@RequestBody User user) {
            ResponseService responseService = new ResponseService();
        try {            
            if (userService.findUserByUsername(user.getLogin()) != null) {
                responseService.setOutput("El usuario " + user.getLogin() + " ya existe");
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            
            user.setCreationDate(new Date());
            userService.saveUser(user);
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
    
    @RequestMapping(value = "user/autenticate/{login}", method = RequestMethod.GET)
    public Response autenticateUser(@PathVariable("login") String login, HttpSession session, HttpServletResponse response) {
            ResponseService responseService = new ResponseService();
        try {      
            UserDTO userDto = userService.findUserByUsername(login);
            if (userDto == null) {
                responseService.setOutput("El usuario " + login + " no existe");
                response.sendRedirect("http://localhost/wordpress/wp-login.php?action=login&err_int=q");
                return null;
            }
            
            session.setAttribute("user" , userDto);
            response.sendRedirect("http://localhost:8085/training/");
            return null;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear usuario");
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
          
        if (currentUser==null) {
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
    public ResponseEntity<List<CountryDTO>> listAllCountries() {
        List<CountryDTO> countries = userService.findAllCountries();
        if(countries.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }
    
          //-------------------Retrieve States--------------------------------------------------------
    @RequestMapping(value = "/states/{countryId}", method = RequestMethod.GET)
    public ResponseEntity<List<FederalStateDTO>> getStatesByCountry(@PathVariable("countryId") Integer countryId) {
        List<FederalStateDTO> states = userService.findStatesByCountry(countryId);
        if(states.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(states, HttpStatus.OK);
    }
    
            //-------------------Retrieve Cities--------------------------------------------------------
      
    @RequestMapping(value = "/cities/{stateId}", method = RequestMethod.GET)
    public ResponseEntity<List<CityDTO>> listAllCountries(@PathVariable("stateId") Integer stateId) {
        List<CityDTO> cities = userService.findCitiesByState(stateId);
        if(cities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
    
     @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }
  
}