package co.com.expertla.training.model.dto;

import java.io.Serializable;

/**
* DTO for Sport Equipment <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
public class SportEquipmentDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer sportEquipmentId;
    private String name;
    private String brand;

    public SportEquipmentDTO() {
    }

    public SportEquipmentDTO(Integer sportEquipmentId, String name, String brand) {
        this.sportEquipmentId = sportEquipmentId;
        this.name = name;
        this.brand = brand;
    }

    public SportEquipmentDTO(Integer sportEquipmentId) {
        this.sportEquipmentId = sportEquipmentId;
    }

    public SportEquipmentDTO(Integer sportEquipmentId, String name) {
        this.sportEquipmentId = sportEquipmentId;
        this.name = name;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
