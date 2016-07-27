package co.com.expertla.training.enums;

/**
 * Enum for default configuration of a subscriber<br>
 * Creation Info:  <br>
 * date 31/08/2015  <br>
 * @author Angela Ramirez
 */
public enum SubscriberConfig {
    ORGANIZATION("2"), PROFILE("2"), ROLE("2");
    private String name;
    
    private SubscriberConfig(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
