package co.com.expertla.training.enums;

/**
 * Enum for the parameter names and descriptions <br>
 * Creation Info:  <br>
 * date 17/11/2015  <br>
 * @author Angela Ramirez
 */
public enum SeParameterEnum {
    EXAMS_LOCATION("EXAMS_LOCATION"), USER_PHOTO_LOCATION("USER_PHOTO_LOCATION"), 
    OPTION_IMG_LOCATION("OPTION_IMG_LOCATION"),URL_REGISTER_SUBSCRIBER("URL_REGISTER_SUBSCRIBER"),
    URL_LOGIN_SUBSCRIBER("URL_LOGIN_SUBSCRIBER"),COLORS("COLORS");
    private String name;
    
    private SeParameterEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
