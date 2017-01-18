package co.expertic.training.enums;

/**
 * Enum for the data type ids <br>
 * Creation Info:  <br>
 * date 17/11/2015  <br>
 * @author Angela Ramirez
 */
public enum DataTypeEnum {
    TEXTBOX("1"), CHECKBOX("2"), MULTIPLE_OPTION("3"),MULTIPLE_ANSWER("4"),SLIDER("5"),INPUT("6");
    private String name;
    
    private DataTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
