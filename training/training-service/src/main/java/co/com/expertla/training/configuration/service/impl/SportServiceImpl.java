package co.com.expertla.training.configuration.service.impl;

import co.com.expertla.training.configuration.dao.SportDao;
import co.com.expertla.training.model.dto.SportDTO;
import co.com.expertla.training.configuration.service.SportService;
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
 
}