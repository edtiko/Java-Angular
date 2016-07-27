package co.com.expertla.training.enums;

/**
 * Enum for a record status <br>
 * Creation Info:  <br>
 * date 08/08/2015  <br>
 * @author Andres Felipe Lopez
 */
public enum Status {
    ACTIVE("1"), INACTIVE("2"), DELETE("3");
    private String name;
    
    private Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
