package co.expertic.training.web.enums;

/**
 * Enum for the status response of the rest services <br>
 * Creation Info:  <br>
 * date 08/08/2015  <br>
 * @author Andres Felipe Lopez
 */
public enum StatusResponse {
    SUCCESS("success"), FAIL("fail");
    private String name;
    
    private StatusResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
