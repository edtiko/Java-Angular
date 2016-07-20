package co.com.expertla.training.model.dto;

import java.io.Serializable;

public class ObjetiveDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer objetiveId;
    private String name;
    private int level;
    private int minSessions;
    private int maxSessions;

    public ObjetiveDTO() {
    }

    public ObjetiveDTO(Integer objetiveId) {
        this.objetiveId = objetiveId;
    }

    public ObjetiveDTO(Integer objetiveId, String name, int level, int minSessions, int maxSessions) {
        this.objetiveId = objetiveId;
        this.name = name;
        this.level = level;
        this.minSessions = minSessions;
        this.maxSessions = maxSessions;
    }

    public Integer getObjetiveId() {
        return objetiveId;
    }

    public void setObjetiveId(Integer objetiveId) {
        this.objetiveId = objetiveId;
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

    public int getMinSessions() {
        return minSessions;
    }

    public void setMinSessions(int minSessions) {
        this.minSessions = minSessions;
    }

    public int getMaxSessions() {
        return maxSessions;
    }

    public void setMaxSessions(int maxSessions) {
        this.maxSessions = maxSessions;
    }
    
}
