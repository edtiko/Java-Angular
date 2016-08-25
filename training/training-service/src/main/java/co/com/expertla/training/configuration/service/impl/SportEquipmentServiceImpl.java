package co.com.expertla.training.configuration.service.impl;

import co.com.expertla.training.configuration.dao.SportEquipmentDao;
import co.com.expertla.training.model.dto.SportEquipmentDTO;
import co.com.expertla.training.configuration.service.SportEquipmentService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* Service for Sport Equipment <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
@Service("sportEquipmentService")
@Transactional
public class SportEquipmentServiceImpl implements SportEquipmentService {
           
    @Autowired
    private SportEquipmentDao sportEquipmentDao;

    @Override
    public List<SportEquipmentDTO> findAll() throws Exception {
        return sportEquipmentDao.findAll();
    }
    
    @Override
    public List<SportEquipmentDTO> findAllRunningShoes() throws Exception {
        return sportEquipmentDao.findAllRunningShoes();
    }

    @Override
    public List<SportEquipmentDTO> findAllBikes() throws Exception {
        return sportEquipmentDao.findAllBikes();
    }

    @Override
    public List<SportEquipmentDTO> findAllPulsometers() throws Exception {
        return sportEquipmentDao.findAllPulsometers();
    }

    @Override
    public List<SportEquipmentDTO> findAllPotentiometers() throws Exception {
        return sportEquipmentDao.findAllPotentiometers();
    }
 
    @Override
    public List<SportEquipmentDTO> findBikesByBikeTypeId(Integer id) throws Exception {
        return sportEquipmentDao.findBikesByBikeTypeId(id);
    }
}