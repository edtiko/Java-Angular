package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.dao.configuration.BikeTypeDao;
import co.com.expertla.training.service.configuration.BikeTypeService;
import co.com.expertla.training.model.entities.BikeType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Service for BikeType <br>
* Creation Date : <br>
* date 19/08/2016 <br>
* @author Angela Ramírez
**/
@Service("bikeTypeService")
@Transactional
public class BikeTypeServiceImpl implements BikeTypeService {
           
    @Autowired
    private BikeTypeDao bikeTypeDao;

    @Override
    public List<BikeType> findAll() throws Exception {
        return bikeTypeDao.findAll();
    }
 
}