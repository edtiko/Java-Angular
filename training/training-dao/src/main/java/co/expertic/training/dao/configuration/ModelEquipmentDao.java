/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.configuration;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.dto.ModelEquipmentDTO;
import co.expertic.training.model.entities.ModelEquipment;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface ModelEquipmentDao extends BaseDAO<ModelEquipment>{
    
    List<ModelEquipmentDTO> findBySportEquipmentId(Integer sportEquipmentId) throws Exception;
    
}
