package co.expertic.training.model.dto;

import java.io.Serializable;

/**
* UserAvailability <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
public class UserAvailabilityDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String day;
    private Boolean checked;

    public UserAvailabilityDTO() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

}
