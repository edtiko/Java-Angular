package co.expertic.training.service.impl.configuration;

import co.expertic.training.dao.configuration.SportDao;
import co.expertic.training.model.dto.SportDTO;
import co.expertic.training.service.configuration.SportService;
import co.expertic.training.model.dto.EnvironmentDTO;
import co.expertic.training.model.dto.WeatherDTO;
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
    
    @Override
    public List<SportDTO> findSportDisciplines() throws Exception {
        return sportDao.findSportDisciplines();
    }
 
}