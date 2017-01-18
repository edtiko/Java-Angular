package co.expertic.training.service.impl.user;

import co.expertic.training.model.entities.VideoUser;
import co.expertic.training.dao.user.VideoUserDao;
import co.expertic.training.service.user.VideoUserService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* VideoUser Service Impl <br>
* Creation Date: <br>
* date 24/08/2016 <br>
* @author Angela Ramirez
**/
@Service("videoUserService")
@Transactional
public class VideoUserServiceImpl implements VideoUserService {

    @Autowired
    private VideoUserDao videoUserDao;

    @Override
    public VideoUser create(VideoUser videoUser) throws Exception {
        return videoUserDao.create(videoUser);
    }

    @Override
    public VideoUser store(VideoUser videoUser) throws Exception {
       return videoUserDao.merge(videoUser);
    }

    @Override
    public void remove(VideoUser videoUser) throws Exception {
        videoUserDao.remove(videoUser, videoUser.getVideoUserId());
    }

    @Override
    public List<VideoUser> findAll() throws Exception {
        return videoUserDao.findAll();
    }
  
    @Override
    public List<VideoUser> getByUser(Integer userId) throws Exception {
        return videoUserDao.getByUser(userId);
    }
 
}