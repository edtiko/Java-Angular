/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.configuration;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.ModelEquipmentDTO;
import co.com.expertla.training.model.entities.ModelEquipment;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface ModelEquipmentDao extends BaseDAO<ModelEquipment>{
    
    List<ModelEquipmentDTO> findBySportEquipmentId(Integer sportEquipmentId) throws Exception;
    
}
