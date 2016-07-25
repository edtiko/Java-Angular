package co.com.expertla.training.enums;

/**
 * Enum for default configuration of a healthcare user<br>
 * Creation Info:  <br>
 * date 17/11/2015  <br>
 * @author Angela Ramirez
 */
public enum HealthCareConfig {
    ORGANIZATION("1"), PROFILE("3"), ROLE("3"),COMPANY_NAME("Neroo"),CITY_ID("1"),PROVIDER_TYPE_ID("2");
    private String name;
    
    private HealthCareConfig(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
