package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.configuration.dao.SportDao;
import co.com.expertla.training.model.dto.SportDTO;
import co.com.expertla.training.service.configuration.SportService;
import co.com.expertla.training.model.dto.EnvironmentDTO;
import co.com.expertla.training.model.dto.WeatherDTO;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* Service for Sport <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
@Service("sportService")
@Transactional
public class SportServiceImpl implements SportService {
           
    @Autowired
    private SportDao sportDao;

    @Override
    public List<SportDTO> findAll() throws Exception {
        return sportDao.findAll();
    }
    
    @Override
    public List<EnvironmentDTO> findEntornos() throws Exception {
        return sportDao.findEntornos();
    }
    
     @Override
    public List<WeatherDTO> findClimas() throws Exception {
        return sportDao.findClimas();
    }
 
}