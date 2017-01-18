package co.expertic.training.model.dto;

import co.expertic.training.model.entities.Activity;
import co.expertic.training.model.entities.ActivityPerformanceMetafield;
import co.expertic.training.model.entities.User;
import co.expertic.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
* UserActivityPerformance DTO <br>
* Info. Creación: <br>
* fecha Sep 15, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public class UserActivityPerformanceDTO {

    private Integer userActivityPerformanceId;
    private User userId;    
    private Activity activityId;    
    private String value;    
    private ActivityPerformanceMetafield activityPerformanceMetafieldId;    
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date executedDate;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date creationDate;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date lastUpdate;
    private Integer userCreate;
    private Integer userUpdate;
    private String userCreateName;
    private String userUpdateName;
    private String photoUrl;
    private String measure;
    private String textLastExecution;
    private int count;

    public UserActivityPerformanceDTO() {
    }
    
    public UserActivityPerformanceDTO(Integer userActivityPerformanceId,User userId,Activity activityId, String value,
ActivityPerformanceMetafield activityPerformanceMetafieldId, Date executedDate, Date creationDate,Date lastUpdate,
        String userCreateName, String userUpdateName,Integer userCreate, Integer userUpdate) {
       this.userActivityPerformanceId = userActivityPerformanceId;
       this.userId = userId;    
       this.activityId = activityId;    
       this.value = value;    
       this.activityPerformanceMetafieldId = activityPerformanceMetafieldId;
       this.executedDate = executedDate;
       this.creationDate = creationDate;
       this.lastUpdate = lastUpdate;
       this.userCreate = userCreate;
       this.userCreateName = userCreateName;
       this.userUpdate = userUpdate;
       this.userUpdateName = userUpdateName;     
    }

    public UserActivityPerformanceDTO(Integer userActivityPerformanceId, User userId, Activity activityId, String value, ActivityPerformanceMetafield activityPerformanceMetafieldId, Date executedDate, Date creationDate) {
        this.userActivityPerformanceId = userActivityPerformanceId;
        this.userId = userId;
        this.activityId = activityId;
        this.value = value;
        this.activityPerformanceMetafieldId = activityPerformanceMetafieldId;
        this.executedDate = executedDate;
        this.creationDate = creationDate;
    }
    
    public UserActivityPerformanceDTO(Integer userActivityPerformanceId, User userId, Activity activityId, Long value, ActivityPerformanceMetafield activityPerformanceMetafieldId, Date executedDate, Date creationDate) {
        this.userActivityPerformanceId = userActivityPerformanceId;
        this.userId = userId;
        this.activityId = activityId;
        this.value = value.toString();
        this.activityPerformanceMetafieldId = activityPerformanceMetafieldId;
        this.executedDate = executedDate;
        this.creationDate = creationDate;
    }


    public Integer getUserActivityPerformanceId() {
        return userActivityPerformanceId;
    }

    public void setUserActivityPerformanceId(Integer userActivityPerformanceId) {
        this.userActivityPerformanceId = userActivityPerformanceId;
    }  

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }    

    public Activity getActivityId() {
        return activityId;
    }

    public void setActivityId(Activity activityId) {
        this.activityId = activityId;
    }    

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }   

    public ActivityPerformanceMetafield getActivityPerformanceMetafieldId() {
        return activityPerformanceMetafieldId;
    }

    public void setActivityPerformanceMetafieldId(ActivityPerformanceMetafield activityPerformanceMetafieldId) {
        this.activityPerformanceMetafieldId = activityPerformanceMetafieldId;
    }    


    public Date getExecutedDate() {
        return executedDate;
    }

    public void setExecutedDate(Date executedDate) {
        this.executedDate = executedDate;
    }    

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(Integer userCreate) {
        this.userCreate = userCreate;
    }

    public Integer getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(Integer userUpdate) {
        this.userUpdate = userUpdate;
    }

    public String getUserCreateName() {
        return userCreateName;
    }

    public void setUserCreateName(String userCreateName) {
        this.userCreateName = userCreateName;
    }

    public String getUserUpdateName() {
        return userUpdateName;
    }

    public void setUserUpdateName(String userUpdateName) {
        this.userUpdateName = userUpdateName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getTextLastExecution() {
        return textLastExecution;
    }

    public void setTextLastExecution(String textLastExecution) {
        this.textLastExecution = textLastExecution;
    }
}
