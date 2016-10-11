package co.com.expertla.training.enums;

/**
* Enum for the configured states in the application <br>
* Creation Date : <br>
* 25/07/2016 <br>
* @author Angela Ram�rez
**/
public enum StateEnum {
    INACTIVE(0), ACTIVE(1),PENDING(2), RETIRED(3), REJECTED(4);
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
