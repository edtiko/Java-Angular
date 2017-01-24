package co.expertic.training.enums;

/**
 * Enum for a record status <br>
 * Creation Info:  <br>
 * date 08/08/2015  <br>
 * @author Andres Felipe Lopez
 */
public enum Status {
    ACTIVE("1"), INACTIVE("2"), DELETE("3");
    private String id;
    
    private Status(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    
}
