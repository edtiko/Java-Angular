package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.dao.configuration.ActivityDao;

import co.com.expertla.training.dao.configuration.ManualActivityDao;
import co.com.expertla.training.dao.plan.TrainingPlanWorkoutDao;
import co.com.expertla.training.enums.StateEnum;
import co.com.expertla.training.model.dto.ActivityCalendarDTO;
import co.com.expertla.training.model.entities.ManualActivity;
import co.com.expertla.training.model.entities.User;
import java.util.Calendar;

import co.com.expertla.training.model.dto.ActivityDTO;
import co.com.expertla.training.model.dto.ActivityMovilDTO;
import co.com.expertla.training.model.entities.Activity;
import co.com.expertla.training.model.entities.Sport;
import co.com.expertla.training.service.configuration.ActivityService;

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
    
     @Autowired
    private ManualActivityDao manualActivityDao;
     
     @Autowired
     private TrainingPlanWorkoutDao trainingPlanWorkoutDao;

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
    public List<ActivityDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        return activityDao.findPaginate(first, max, order, filter);
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
    public List<ActivityCalendarDTO> findByUserDiscipline(Integer usuarioId) throws Exception {
        return activityDao.findByUserDiscipline(usuarioId);
    }
    
    @Override
    public List<ActivityMovilDTO> findActivityDefaultByUserDiscipline(Integer usuarioId) throws Exception {
        return activityDao.findActivityDefaultByUserDiscipline(usuarioId);
    }
  
    @Override
     public Integer createManualActivity(ActivityCalendarDTO activity) throws Exception {
         ManualActivity manualActivity = new ManualActivity();
         manualActivity.setSportId(new Sport(activity.getSportId()));
         manualActivity.setName(activity.getName());
         manualActivity.setDescription(activity.getDescription());
         manualActivity.setUserId(new User(activity.getUserId()));
         manualActivity.setCreationDate(Calendar.getInstance().getTime());
         manualActivity.setStateId(StateEnum.ACTIVE.getId());
         return manualActivityDao.create(manualActivity).getManualActivityId();
    }
     
    @Override
     public List<ActivityCalendarDTO> findManualActivitiesByUserId(Integer userId) throws Exception {
        return manualActivityDao.findByUserId(userId);
    }

    @Override
    public void deleteManualActivity(Integer manualActivityId) throws Exception {
         manualActivityDao.remove(new ManualActivity(), manualActivityId);
         trainingPlanWorkoutDao.deleteByManualActivityId(manualActivityId);
    }

    @Override
    public ActivityCalendarDTO findByManualActivityId(Integer manualActivityId) throws Exception {
        return manualActivityDao.findByManualActivityId(manualActivityId);
    }

    @Override
    public Integer updateManualActivity(ActivityCalendarDTO activity) throws Exception {
        ManualActivity manualActivity = new ManualActivity();
        manualActivity.setManualActivityId(activity.getId());
        manualActivity.setName(activity.getName());
        manualActivity.setDescription(activity.getDescription());
        manualActivity.setSportId(new Sport(activity.getSportId()));
        manualActivity.setUserId(new User(activity.getUserId()));
        manualActivity.setCreationDate(Calendar.getInstance().getTime());
        return  manualActivityDao.merge(manualActivity).getManualActivityId();
    }

    @Override
    public List<Activity> findActivityReplaceByActivity(Integer activityId) throws Exception {
        return activityDao.findActivityReplaceByActivity(activityId);
    }

    @Override
    public boolean existManualActivity(ActivityCalendarDTO activity) throws Exception {
       return manualActivityDao.existManualActivity(activity);
    }
    
    public List<ActivityMovilDTO> findActivityReplaceByActivityMovil(Integer activityId) throws Exception {
        return activityDao.findActivityReplaceByActivityMovil(activityId);
    }
    
    public List<ActivityMovilDTO> findManualActivitiesMovilByUserId(Integer activityId) throws Exception {
        return activityDao.findManualActivitiesMovilByUserId(activityId);
    }

}