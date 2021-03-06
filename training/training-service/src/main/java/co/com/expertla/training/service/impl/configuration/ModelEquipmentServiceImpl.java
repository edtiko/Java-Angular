/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.model.dto.ModelEquipmentDTO;
import co.com.expertla.training.service.configuration.ModelEquipmentService;
import co.com.expertla.training.dao.configuration.ModelEquipmentDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin G
 */
@Service
@Transactional
public class ModelEquipmentServiceImpl implements ModelEquipmentService{
    
    @Autowired
    ModelEquipmentDao modelEquipmentDao;

    @Override
    public List<ModelEquipmentDTO> findBySportEquipmentId(Integer sportEquipmentId)throws Exception {
        return modelEquipmentDao.findBySportEquipmentId(sportEquipmentId);
    }
    
}
