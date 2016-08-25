package co.com.expertla.training.enums;

/**
* Enum for the Sport Equipment Types configured<br>
* Creation Date : <br>
* 15/07/2016 <br>
* @author Angela Ramírez
**/
public enum SportEquipmentTypeEnum {
    RUNNING_SHOES(1), BIKES (2),PULSOMETER(3),POTENTIOMETER(4);
    private Integer id;
    
    private SportEquipmentTypeEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
