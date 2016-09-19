package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.dao.configuration.ActivityPerformanceMetafieldDao;
import co.com.expertla.training.model.dto.ActivityPerformanceMetafieldDTO;
import co.com.expertla.training.model.entities.ActivityPerformanceMetafield;
import co.com.expertla.training.service.configuration.ActivityPerformanceMetafieldService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* ActivityPerformanceMetafield Service Impl <br>
* Info. Creación: <br>
* fecha Sep 15, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("activityPerformanceMetafieldService")
@Transactional
public class ActivityPerformanceMetafieldServiceImpl implements ActivityPerformanceMetafieldService {

    @Autowired
    private ActivityPerformanceMetafieldDao activityPerformanceMetafieldDao;

    @Override
    public ActivityPerformanceMetafield create(ActivityPerformanceMetafield activityPerformanceMetafield) throws Exception {
        return activityPerformanceMetafieldDao.create(activityPerformanceMetafield);
    }

    @Override
    public ActivityPerformanceMetafield store(ActivityPerformanceMetafield activityPerformanceMetafield) throws Exception {
       return activityPerformanceMetafieldDao.merge(activityPerformanceMetafield);
    }

    @Override
    public void remove(ActivityPerformanceMetafield activityPerformanceMetafield) throws Exception {
        activityPerformanceMetafieldDao.remove(activityPerformanceMetafield, activityPerformanceMetafield.getActivityPerformanceMetafieldId());
    }

    @Override
    public List<ActivityPerformanceMetafield> findAll() throws Exception {
        return activityPerformanceMetafieldDao.findAll();
    }

    @Override
    public List<ActivityPerformanceMetafield> findAllActive() throws Exception {
        return activityPerformanceMetafieldDao.findAllActive();
    }

    @Override
    public List<ActivityPerformanceMetafieldDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        return activityPerformanceMetafieldDao.findPaginate(first, max, order, filter);
    }

    @Override
    public List<ActivityPerformanceMetafield> findByActivityPerformanceMetafield(ActivityPerformanceMetafield activityPerformanceMetafield) throws Exception {
        return activityPerformanceMetafieldDao.findByActivityPerformanceMetafield(activityPerformanceMetafield);
    }

    @Override
    public List<ActivityPerformanceMetafield> findByFiltro(ActivityPerformanceMetafield activityPerformanceMetafield) throws Exception {
        return activityPerformanceMetafieldDao.findByFiltro(activityPerformanceMetafield);
    }

    @Override
    public List<ActivityPerformanceMetafield> findByName(ActivityPerformanceMetafield activityPerformanceMetafield) throws Exception {
        return activityPerformanceMetafieldDao.findByName(activityPerformanceMetafield);
    }
}