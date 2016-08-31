package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.dao.configuration.ActivityDao;
import co.com.expertla.training.service.configuration.ActivityService;
import co.com.expertla.training.model.entities.Activity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Activity Service Impl <br>
* Info. Creación: <br>
* fecha 5/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("activityService")
@Transactional
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao activityDao;

    @Override
    public Activity create(Activity activity) throws Exception {
        return activityDao.create(activity);
    }

    @Override
    public Activity store(Activity activity) throws Exception {
       return activityDao.merge(activity);
    }

    @Override
    public void remove(Activity activity) throws Exception {
        activityDao.remove(activity, activity.getActivityId());
    }

    @Override
    public List<Activity> findAll() throws Exception {
        return activityDao.findAll();
    }

    @Override
    public List<Activity> findAllActive() throws Exception {
        return activityDao.findAllActive();
    }

    @Override
    public List<Activity> findByActivity(Activity activity) throws Exception {
        return activityDao.findByActivity(activity);
    }

    @Override
    public List<Activity> findByFiltro(Activity activity) throws Exception {
        return activityDao.findByFiltro(activity);
    }

    @Override
    public List<Activity> findByUserDiscipline(Integer usuarioId) throws Exception {
        return activityDao.findByUserDiscipline(usuarioId);
    }

}