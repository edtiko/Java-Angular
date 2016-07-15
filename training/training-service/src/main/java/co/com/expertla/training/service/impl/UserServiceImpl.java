/**
 *
 */
package co.com.expertla.training.service.impl;

import co.com.expertla.training.dao.CityDao;
import co.com.expertla.training.dao.StateDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.dao.UserDao;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.City;
import co.com.expertla.training.model.entities.State;
import co.com.expertla.training.service.UserService;
import java.util.Date;
import java.util.stream.Collectors;

@Service("usuarioService")
@Transactional
public class UserServiceImpl implements UserService {

    //private static final AtomicLong counter = new AtomicLong();

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private CityDao cityDao;
    
    @Autowired
    private StateDao stateDao;

    /* static{
        users= populateDummyUsers();
    }*/
    @Override
    public List<UserDTO> findAllUsers() {
        return userDao.findAllUsers().stream().map(UserDTO::mapFromUserEntity).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Integer id) {
        return UserDTO.mapFromUserEntity(userDao.findById(id));
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
    public UserDTO findUserByUsername(String username) {
        /*for(Usuario user : users){
            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }*/
        return null;
    }
    
    @Transactional
    @Override
    public Integer saveUser(UserDTO userDTO) {
     City city = cityDao.findById(userDTO.getCityId());
     State state = stateDao.findById(userDTO.getStateId());
     User user = new User(userDTO.getUserId(), userDTO.getName(), userDTO.getLastName(),userDTO.getEmail(), userDTO.getBirthDate(), userDTO.getAddress(), 
                           userDTO.getSex(), userDTO.getWeight(), userDTO.getPhone(), userDTO.getCellphone(), city, 
                           state, userDTO.getLogin(), userDTO.getFacebookPage(), userDTO.getPostalCode(), new Date());
 
        return userDao.saveUser(user);
    }

    @Transactional
    @Override
    public int updateUser(UserDTO userDTO) {
       User user = userDao.findById(userDTO.getUserId());
       City city = cityDao.findById(userDTO.getCityId());
       State state = stateDao.findById(userDTO.getStateId());
       user.setName(userDTO.getName());
       user.setLastName(userDTO.getLastName());
       user.setEmail(userDTO.getEmail());
       user.setBirthDate(userDTO.getBirthDate());
       user.setAddress(userDTO.getAddress());
       user.setSex(userDTO.getSex());
       user.setWeight(userDTO.getWeight());
       user.setPhone(userDTO.getPhone());
       user.setCellphone(userDTO.getCellphone());
       user.setCityId(city);
       user.setStateId(state);
       user.setPostalCode(userDTO.getPostalCode());
       user.setCreationDate(new Date());
       
       
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
        return findByName(user.getName()) != null;
    }

    @Override
    public void deleteAllUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
