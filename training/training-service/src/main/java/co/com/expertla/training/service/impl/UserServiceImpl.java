/**
 * 
 */
package co.com.expertla.training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.user.dao.UserDao;
import co.com.expertla.training.service.UserService;

@Service("usuarioService")
@Transactional
public class UserServiceImpl implements UserService{
     
    //private static final AtomicLong counter = new AtomicLong();
     
    private static List<User> users;
    
    @Autowired
    private UserDao userDao;
    
   /* static{
        users= populateDummyUsers();
    }*/
   @Override
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }
     @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }
     @Override
    public User findByName(String name) {
        /*for(Usuario user : users){
            if(user.getUsername().equalsIgnoreCase(name)){
                return user;
            }
        }*/
        return null;
    }
    @Override
    public User findUserByUsername(String username) {
        /*for(Usuario user : users){
            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }*/
        return null;
    }
     @Override
    public Integer saveUser(User user) {
        /*user.setId(counter.incrementAndGet());
        users.add(user);*/
    	return null;
    }
 
    @Override
    public int updateUser(User user) {
       /* int index = users.indexOf(user);
        users.set(index, user);*/
    	return 0;
    }
 
    @Override
    public Integer deleteUserById(Integer id) {
         
       /* for (Iterator<Usuario> iterator = users.iterator(); iterator.hasNext(); ) {
        	Usuario user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
            }
        }*/
    	return null;
    }
 
    @Override
    public boolean isUserExist(User user) {
        return findByName(user.getName())!=null;
    }

    @Override
    public void deleteAllUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     

 
}