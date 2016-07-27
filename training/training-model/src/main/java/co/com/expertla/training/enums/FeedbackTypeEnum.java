package co.com.expertla.training.enums;

/**
 * Enum for the feedback type ids <br>
 * Creation Info:  <br>
 * date 19/11/2015  <br>
 * @author Angela Ramirez
 */
public enum FeedbackTypeEnum {
    GENERAL("1"), REPORT("2"), EXAM_RESULT("3"),CONCLUSION("4"),USER("5");
    private String name;
    
    private FeedbackTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
