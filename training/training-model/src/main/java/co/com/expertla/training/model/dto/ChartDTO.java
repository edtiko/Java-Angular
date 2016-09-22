package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.ActivityPerformanceMetafield;
import co.com.expertla.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 * ChartDto
 *
 * @author Angela Ramírez
 */
public class ChartDTO implements Serializable {

    @JsonSerialize(using=JsonDateSerializer.class)
    private Date executedDate;
    private Long value;
    private ActivityPerformanceMetafield ActivityPerformanceMetafieldId;

    public ChartDTO() {
    }
    
    public ChartDTO(Date executedDate, Long value, ActivityPerformanceMetafield ActivityPerformanceMetafieldId) {
        this.executedDate = executedDate;
        this.value = value;
        this.ActivityPerformanceMetafieldId = ActivityPerformanceMetafieldId;
    }

    public Date getExecutedDate() {
        return executedDate;
    }

    public void setExecutedDate(Date executedDate) {
        this.executedDate = executedDate;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public ActivityPerformanceMetafield getActivityPerformanceMetafieldId() {
        return ActivityPerformanceMetafieldId;
    }

    public void setActivityPerformanceMetafieldId(ActivityPerformanceMetafield ActivityPerformanceMetafieldId) {
        this.ActivityPerformanceMetafieldId = ActivityPerformanceMetafieldId;
    } 

}
