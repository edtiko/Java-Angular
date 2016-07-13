/**
 * 
 */
package co.com.expertla.training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.expertla.training.dao.UsuarioDao;
import co.com.expertla.training.model.Usuario;
import co.com.expertla.training.service.UsuarioService;

@Service("usuarioService")
@Transactional
public class UsuarioServiceImpl implements UsuarioService{
     
    //private static final AtomicLong counter = new AtomicLong();
     
    private static List<Usuario> users;
    
    @Autowired
    private UsuarioDao usuarioDao;
    
   /* static{
        users= populateDummyUsers();
    }*/
 
    public List<Usuario> findAllUsers() {
        return usuarioDao.findAllUsers();
    }
     
    public Usuario findById(Integer id) {
        return usuarioDao.findById(id);
    }
     
    public Usuario findByName(String name) {
        /*for(Usuario user : users){
            if(user.getUsername().equalsIgnoreCase(name)){
                return user;
            }
        }*/
        return null;
    }
    
    public Usuario findUserByUsername(String username) {
        /*for(Usuario user : users){
            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }*/
        return null;
    }
     
    public Integer saveUser(Usuario user) {
        /*user.setId(counter.incrementAndGet());
        users.add(user);*/
    	return null;
    }
 
    public int updateUser(Usuario user) {
       /* int index = users.indexOf(user);
        users.set(index, user);*/
    	return 0;
    }
 
    public Integer deleteUserById(Integer id) {
         
       /* for (Iterator<Usuario> iterator = users.iterator(); iterator.hasNext(); ) {
        	Usuario user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
            }
        }*/
    	return null;
    }
 
    public boolean isUserExist(Usuario user) {
        return findByName(user.getUsername())!=null;
    }
     
    public void deleteAllUsers(){
        users.clear();
    }
 
   /* private static List<Usuario> populateDummyUsers(){
        List<Usuario> users = new ArrayList<Usuario>();
        users.add(new Usuario(counter.incrementAndGet(),"1234","Sam", "NY", "sam@abc.com"));
        users.add(new Usuario(counter.incrementAndGet(),"1234","Tomy", "ALBAMA", "tomy@abc.com"));
        users.add(new Usuario(counter.incrementAndGet(),"1234","Kelly", "NEBRASKA", "kelly@abc.com"));
        return users;
    }*/
 
}