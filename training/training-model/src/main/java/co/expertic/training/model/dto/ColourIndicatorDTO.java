package co.expertic.training.model.dto;

import co.expertic.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
* ColourIndicator Controller <br>
* Info. Creación: <br>
* fecha Sep 14, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public class ColourIndicatorDTO {

    private Integer colourIndicatorId;
    private String name;    
    private String colour;    
    private Integer hoursSpent;    
    private Integer colourOrder;    
    private Short stateId;    
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date creationDate;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date lastUpdate;
    private Integer userCreate;
    private Integer userUpdate;
    private String userCreateName;
    private String userUpdateName;
    private int count;
    
    public ColourIndicatorDTO(Integer colourIndicatorId, String name, String colour, Integer hoursSpent, Integer colourOrder,Short stateId,
            Date creationDate,Date lastUpdate, String userCreateName, String userUpdateName,Integer userCreate, Integer userUpdate) {
       this.colourIndicatorId = colourIndicatorId;
       this.name = name;    
       this.colour = colour;    
       this.hoursSpent = hoursSpent;    
       this.colourOrder = colourOrder;    
       this.stateId = stateId;    
       this.creationDate = creationDate;
       this.lastUpdate = lastUpdate;
       this.userCreate = userCreate;
       this.userCreateName = userCreateName;
       this.userUpdate = userUpdate;
       this.userUpdateName = userUpdateName;     
    }


    public Integer getColourIndicatorId() {
        return colourIndicatorId;
    }

    public void setColourIndicatorId(Integer colourIndicatorId) {
        this.colourIndicatorId = colourIndicatorId;
    }  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }   
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }   
    public Integer getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(Integer hoursSpent) {
        this.hoursSpent = hoursSpent;
    }    

    public Integer getColourOrder() {
        return colourOrder;
    }

    public void setColourOrder(Integer colourOrder) {
        this.colourOrder = colourOrder;
    }
  
    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
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
}
