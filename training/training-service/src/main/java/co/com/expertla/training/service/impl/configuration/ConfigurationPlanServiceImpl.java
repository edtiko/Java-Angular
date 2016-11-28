package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.dao.configuration.ConfigurationPlanDao;
import co.com.expertla.training.model.dto.ConfigurationPlanDTO;
import co.com.expertla.training.model.entities.ConfigurationPlan;
import co.com.expertla.training.service.configuration.ConfigurationPlanService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* ConfigurationPlan Service Impl <br>
* Info. Creación: <br>
* fecha 24/11/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("configurationPlanService")
@Transactional
public class ConfigurationPlanServiceImpl implements ConfigurationPlanService {

    @Autowired
    private ConfigurationPlanDao configurationPlanDao;

    @Override
    public ConfigurationPlan create(ConfigurationPlan configurationPlan) throws Exception {
        return configurationPlanDao.create(configurationPlan);
    }

    @Override
    public ConfigurationPlan store(ConfigurationPlan configurationPlan) throws Exception {
       return configurationPlanDao.merge(configurationPlan);
    }

    @Override
    public void remove(ConfigurationPlan configurationPlan) throws Exception {
        configurationPlanDao.remove(configurationPlan, configurationPlan.getConfigurationPlanId());
    }

    @Override
    public List<ConfigurationPlan> findAll() throws Exception {
        return configurationPlanDao.findAll();
    }

    @Override
    public List<ConfigurationPlan> findAllActive() throws Exception {
        return configurationPlanDao.findAllActive();
    }

    @Override
    public List<ConfigurationPlanDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        return configurationPlanDao.findPaginate(first, max, order, filter);
    }
    
    @Override
    public List<ConfigurationPlanDTO> findPaginate(int first, int max, String order, String filter, Integer planId) throws Exception {
        return configurationPlanDao.findPaginate(first, max, order, filter, planId);
    }

    @Override
    public List<ConfigurationPlan> findByConfigurationPlan(ConfigurationPlan configurationPlan) throws Exception {
        return configurationPlanDao.findByConfigurationPlan(configurationPlan);
    }

    @Override
    public List<ConfigurationPlan> findByFiltro(ConfigurationPlan configurationPlan) throws Exception {
        return configurationPlanDao.findByFiltro(configurationPlan);
    }

}