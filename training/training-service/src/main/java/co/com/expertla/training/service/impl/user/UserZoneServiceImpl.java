package co.com.expertla.training.service.impl.user;

import co.com.expertla.training.model.entities.UserZone;
import co.com.expertla.training.service.user.UserZoneService;
import co.com.expertla.training.dao.user.UserZoneDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UserZone Service Impl <br>
* Creation Date: <br>
* date Aug 29, 2016 <br>
* @author Angela Ramirez
**/
@Service("userZoneService")
@Transactional
public class UserZoneServiceImpl implements UserZoneService {

    @Autowired
    private UserZoneDao userZoneDao;

    @Override
    public UserZone create(UserZone userZone) throws Exception {
        return userZoneDao.create(userZone);
    }

    @Override
    public UserZone store(UserZone userZone) throws Exception {
       return userZoneDao.merge(userZone);
    }

    @Override
    public void remove(UserZone userZone) throws Exception {
        userZoneDao.remove(userZone, userZone.getUserZoneId());
    }

    @Override
    public List<UserZone> findAll() throws Exception {
        return userZoneDao.findAll();
    }

    @Override
    public List<UserZone> findByUserZone(UserZone userZone) throws Exception {
        return userZoneDao.findByUserZone(userZone);
    }

}