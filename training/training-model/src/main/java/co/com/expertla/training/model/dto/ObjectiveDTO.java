package co.com.expertla.training.model.dto;

import java.io.Serializable;

public class ObjectiveDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer objectiveId;
    private String name;
    private int level;

    public ObjectiveDTO() {
    }

    public ObjectiveDTO(Integer objectiveId) {
        this.objectiveId = objectiveId;
    }

    public ObjectiveDTO(Integer objectiveId, String name, int level) {
        this.objectiveId = objectiveId;
        this.name = name;
        this.level = level;
    }

    public Integer getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Integer objectiveId) {
        this.objectiveId = objectiveId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
}
