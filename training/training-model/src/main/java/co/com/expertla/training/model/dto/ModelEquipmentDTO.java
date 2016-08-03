/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import java.io.Serializable;

/**
 *
 * @author Edwin G
 */
public class ModelEquipmentDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private Integer modelEquipmentId;
    private Integer sportEquipmentId;
    private String name;
    
    public ModelEquipmentDTO(){
        
    }
    
    public ModelEquipmentDTO(Integer modelEquipmentId, Integer sportEquipmentId, String name){
        this.modelEquipmentId = modelEquipmentId;
        this.sportEquipmentId = sportEquipmentId;
        this.name = name;
    }

    public Integer getModelEquipmentId() {
        return modelEquipmentId;
    }

    public void setModelEquipmentId(Integer modelEquipmentId) {
        this.modelEquipmentId = modelEquipmentId;
    }

    public Integer getSportEquipmentId() {
        return sportEquipmentId;
    }

    public void setSportEquipmentId(Integer sportEquipmentId) {
        this.sportEquipmentId = sportEquipmentId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    
}
