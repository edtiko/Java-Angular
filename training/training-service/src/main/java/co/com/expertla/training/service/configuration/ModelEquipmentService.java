/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.configuration;

import co.com.expertla.training.model.dto.ModelEquipmentDTO;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface ModelEquipmentService {
    
    List<ModelEquipmentDTO> findBySportEquipmentId(Integer sportEquipmentId) throws Exception;
    
}
