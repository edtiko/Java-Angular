package co.com.expertla.training.model.dto;

/**
* ActivityPerformanceMetafield DTO <br>
* Info. Creación: <br>
* fecha Sep 15, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public class ActivityPerformanceMetafieldDTO {

    private Integer activityPerformanceMetafieldId;
    private String name;    
    private String label;    
    private String dataType;    
    private int count;
    
    public ActivityPerformanceMetafieldDTO(Integer activityPerformanceMetafieldId, String name,String label,String dataType) {
       this.activityPerformanceMetafieldId = activityPerformanceMetafieldId;
       this.name = name;    
       this.label = label;    
       this.dataType = dataType;     
    }


    public Integer getActivityPerformanceMetafieldId() {
        return activityPerformanceMetafieldId;
    }

    public void setActivityPerformanceMetafieldId(Integer activityPerformanceMetafieldId) {
        this.activityPerformanceMetafieldId = activityPerformanceMetafieldId;
    }  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }   

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }   

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }   
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
