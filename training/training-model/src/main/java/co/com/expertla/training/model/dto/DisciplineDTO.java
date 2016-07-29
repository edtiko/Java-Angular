package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.Discipline;
import java.io.Serializable;

/**
* DTO for Discipline <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
public class DisciplineDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Integer disciplineId;
    private String name;
    private String description;

    public DisciplineDTO() {
    }

    public DisciplineDTO(Integer disciplineId, String name, String description) {
        this.disciplineId = disciplineId;
        this.name = name;
        this.description = description;
    }
    
     public static DisciplineDTO mapFromDisciplineEntity(Discipline discipline) {
        return new DisciplineDTO(discipline.getDisciplineId(), discipline.getName(), discipline.getDescription());
    }
    
    public DisciplineDTO(Integer disciplineId) {
        this.disciplineId = disciplineId;
    }

    public Integer getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Integer disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
