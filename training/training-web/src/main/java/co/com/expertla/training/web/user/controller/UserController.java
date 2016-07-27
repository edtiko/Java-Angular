/**
 * 
 */
package co.com.expertla.training.web.user.controller;

import co.com.expertla.training.model.dto.CityDTO;
import co.com.expertla.training.model.dto.CountryDTO;
import co.com.expertla.training.model.dto.FederalStateDTO;
import co.com.expertla.training.model.dto.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import co.com.expertla.training.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.util.UriComponentsBuilder;

  
@RestController
public class UserController {
  
    
    private static final Logger LOGGER = Logger.getLogger(UserController.class);
    
    @Autowired
    UserService userService;  //Service which will do all data retrieval/manipulation work
  
    
   /* @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public UserDTO getUserInfo(Principal principal) {
        System.out.println("Entro user info");
        UserDTO user = userService.findUserByUsername(principal.getName());

        return user != null ? user : null;
    }*/
   //@RequestMapping(value = "/login/", method = RequestMethod.POST)
    /*public @ResponseBody String authenticate(@PathVariable("login") String login, @PathVariable("password") String password) {
        

        
        boolean user = userService.isUser(login, password);
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
	String res  = "false";	
        
        if(user){
            res = "true";
        }
      return prettyGson.toJson(res);
    }*/
    
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
      
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody UserDTO user,    UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getName());
  
        if (userService.isUserExist(user)) {
            System.out.println("A User with name " + user.getName() + " already exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
  
        userService.saveUser(user);
  
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getUserId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
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