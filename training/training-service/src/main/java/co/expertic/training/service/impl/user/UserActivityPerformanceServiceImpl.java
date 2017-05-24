package co.expertic.training.service.impl.user;

import co.expertic.training.dao.plan.TrainingUserSerieDao;
import co.expertic.training.dao.user.UserActivityPerformanceDao;
import co.expertic.training.model.dto.ChartDTO;
import co.expertic.training.model.dto.ProgressReportDTO;
import co.expertic.training.model.dto.UserActivityPerformanceDTO;
import co.expertic.training.model.dto.WeeklyGoalsDTO;
import co.expertic.training.model.entities.UserActivityPerformance;
import co.expertic.training.service.user.UserActivityPerformanceService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UserActivityPerformance Service Impl <br>
* Info. Creación: <br>
* fecha Sep 15, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("userActivityPerformanceService")
@Transactional
public class UserActivityPerformanceServiceImpl implements UserActivityPerformanceService {

    @Autowired
    private UserActivityPerformanceDao userActivityPerformanceDao;
    
    @Autowired
    private TrainingUserSerieDao trainingUserSerieDao;

    @Override
    public UserActivityPerformance create(UserActivityPerformance userActivityPerformance) throws Exception {
        return userActivityPerformanceDao.create(userActivityPerformance);
    }

    @Override
    public UserActivityPerformance store(UserActivityPerformance userActivityPerformance) throws Exception {
       return userActivityPerformanceDao.merge(userActivityPerformance);
    }

    @Override
    public void remove(UserActivityPerformance userActivityPerformance) throws Exception {
        userActivityPerformanceDao.remove(userActivityPerformance, userActivityPerformance.getUserActivityPerformanceId());
    }

    @Override
    public List<UserActivityPerformance> findAll() throws Exception {
        return userActivityPerformanceDao.findAll();
    }

    @Override
    public List<UserActivityPerformance> findAllActive() throws Exception {
        return userActivityPerformanceDao.findAllActive();
    }

    @Override
    public List<UserActivityPerformanceDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        return userActivityPerformanceDao.findPaginate(first, max, order, filter);
    }

    @Override
    public List<UserActivityPerformance> findByUserActivityPerformance(UserActivityPerformance userActivityPerformance) throws Exception {
        return userActivityPerformanceDao.findByUserActivityPerformance(userActivityPerformance);
    }

    @Override
    public List<UserActivityPerformance> findByFiltro(UserActivityPerformance userActivityPerformance) throws Exception {
        return userActivityPerformanceDao.findByFiltro(userActivityPerformance);
    }
    
    @Override
    public List<UserActivityPerformanceDTO> findByDateRangeAndUserId( Date fromDate, Date toDate, Integer userId) throws Exception {
        return userActivityPerformanceDao.findByDateRangeAndUserId(fromDate, toDate, userId);
    }

    @Override
    public List<ChartDTO> findByDateRangeAndUserIdAndMetaField( Date fromDate, Date toDate, Integer userId, Integer metafieldId) throws Exception {
        return userActivityPerformanceDao.findByDateRangeAndUserIdAndMetaField(fromDate, toDate, userId, metafieldId);
    }
    
    @Override
    public List<ChartDTO> findByDateRangeAndUserIdAndMetaField( Date fromDate, Date toDate, Integer userId, Integer metafieldId, Integer days, boolean weekly) throws Exception {
        return userActivityPerformanceDao.findByDateRangeAndUserIdAndMetaField(fromDate, toDate, userId, metafieldId, days, weekly);
    }
    
    @Override
    public List<ChartDTO> findActivitiesByDateRangeAndUserId( Date fromDate, Date toDate, Integer userId) throws Exception {
        return userActivityPerformanceDao.findActivitiesByDateRangeAndUserId(fromDate, toDate, userId);
    }
    
    @Override
    public List<UserActivityPerformanceDTO> findConsolidationByDateRangeAndUserId(Date fromDate, Date toDate, Integer userId) throws Exception {
        return userActivityPerformanceDao.findConsolidationByDateRangeAndUserId(fromDate, toDate, userId);
    }

    @Override
    public List<ProgressReportDTO> getProgressReport(Integer date, Integer activity, Integer userId)throws Exception  {
        return userActivityPerformanceDao.getProgressReport(date, activity, userId);
    }

    @Override
    public WeeklyGoalsDTO getWeeklyGoals(Integer userId) throws Exception {
        WeeklyGoalsDTO result = new WeeklyGoalsDTO();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        Calendar calendarTo = Calendar.getInstance(Locale.getDefault());
        calendarTo.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendarTo.set(Calendar.MINUTE, 0);
        calendarTo.set(Calendar.SECOND, 0);
        calendarTo.set(Calendar.HOUR_OF_DAY, 0);
        Date fromDate = calendar.getTime();
        Date toDate = calendarTo.getTime();
        
        result.setNumActivities(userActivityPerformanceDao.getNumActivities(userId, fromDate, toDate));
        result.setNumSessions(trainingUserSerieDao.getPlanWorkoutByUser(userId, fromDate, toDate).size());
        result.setSpeedAverage(userActivityPerformanceDao.getSpeedAverage(userId, fromDate, toDate));
        result.setDistance(userActivityPerformanceDao.getDistance(userId, fromDate, toDate));
        result.setNumCalories(userActivityPerformanceDao.getCalories(userId, fromDate, toDate));

        return result;
    }
}