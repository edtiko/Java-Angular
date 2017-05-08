package co.expertic.training.enums;

/**
* Enum for the configured states in the application <br>
* Creation Date : <br>
* 25/07/2016 <br>
* @author Angela Ramírez
**/
public enum StateEnum {
    INACTIVE(0), ACTIVE(1),PENDING(2), RETIRED(3), REJECTED(4), APPROVED(5), RESPONDIDO(6);
    private Integer id;
    
    private StateEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
