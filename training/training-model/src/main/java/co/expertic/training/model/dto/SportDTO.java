package co.expertic.training.model.dto;

import java.io.Serializable;

/**
* DTO for Sport <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
public class SportDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Integer sportId;
    private String name;
    
    public SportDTO() {
    }

    public SportDTO(Integer sportId) {
        this.sportId = sportId;
    }

    public SportDTO(Integer sportId, String name) {
        this.sportId = sportId;
        this.name = name;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
