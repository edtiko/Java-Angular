/**
 * 
 */
package co.com.expertla.training.web.controller;

import co.com.expertla.training.model.dto.UserDTO;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.service.UserService;

  
@RestController
public class UserController {
  
    @Autowired
    UserService userService;  //Service which will do all data retrieval/manipulation work
  
     
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
    public ResponseEntity<User> deleteAllUsers() {
        System.out.println("Deleting All Users");
  
        userService.deleteAllUsers();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  
}