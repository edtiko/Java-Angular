/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.configuration;

import co.expertic.training.model.dto.ModelEquipmentDTO;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface ModelEquipmentService {
    
    List<ModelEquipmentDTO> findBySportEquipmentId(Integer sportEquipmentId) throws Exception;
    
}
