package co.com.expertla.training.service.impl;

import co.com.expertla.training.dao.UserProfileDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.expertla.training.model.entities.UserProfile;
import co.com.expertla.training.service.UserProfileService;

@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService {
          
    
    @Autowired
    private UserProfileDao userProfileDao;
    
   /* static{
        users= populateDummyUsers();
    }*/
 
    public UserProfile findById(Integer id) {
        return userProfileDao.findById(id);
    }
 
   /* private static List<Usuario> populateDummyUsers(){
        List<Usuario> users = new ArrayList<Usuario>();
        users.add(new Usuario(counter.incrementAndGet(),"1234","Sam", "NY", "sam@abc.com"));
        users.add(new Usuario(counter.incrementAndGet(),"1234","Tomy", "ALBAMA", "tomy@abc.com"));
        users.add(new Usuario(counter.incrementAndGet(),"1234","Kelly", "NEBRASKA", "kelly@abc.com"));
        return users;
    }*/
 
}