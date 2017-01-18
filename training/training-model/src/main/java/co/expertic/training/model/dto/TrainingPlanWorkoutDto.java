package co.expertic.training.model.dto;

import co.expertic.training.model.entities.Activity;
import co.expertic.training.model.entities.ManualActivity;
import co.expertic.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 * Dto para consulta de planes de entrenamiento por usuario <br>
 * Info. Creación: <br>
 * fecha 15/07/2016 <br>
 *
 * @author Andres Felipe Lopez Rodriguez
 *
 */
public class TrainingPlanWorkoutDto {

    private Integer id;
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "CET")
    private Date workoutDate;
    private long start;
    private long end;
    private String url;
    private String className;
    private Integer activityId;
    private String title;
    private String activityDescription;
    private Integer modalityId;
    private String modality;
    private Integer disciplineId;
    private String discipline;
    private Integer objectiveId;
    private String objective;
    private int level;
    private Integer userId;
    private String sportIcon;
    private Integer sportId;
    private boolean manualActivity = false;
    private Integer percentageWeather;
    private Boolean isDrag;
    private Double executedTime;
    private Double executedDistance;
    private Short indStrava;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date lastUpdateStrava;

    public TrainingPlanWorkoutDto() {
    }

    public TrainingPlanWorkoutDto(Integer trainingPlanWorkoutId, Date workoutDate,
            Activity activityId, ManualActivity manualActivityId,
            Integer userId) {
        this.id = trainingPlanWorkoutId;
        this.workoutDate = workoutDate;
        if (activityId != null) {
            this.activityId = activityId.getActivityId();
            this.title = activityId.getName();
            this.activityDescription = activityId.getDescription();
            this.modalityId = activityId.getModalityId().getModalityId();
            this.modality = activityId.getModalityId().getName();
            this.disciplineId = activityId.getModalityId().getDisciplineId().getDisciplineId();
            this.discipline = activityId.getModalityId().getDisciplineId().getName();
            this.objectiveId = activityId.getObjectiveId().getObjectiveId();
            this.objective = activityId.getObjectiveId().getName();
            this.level = activityId.getObjectiveId().getLevel();
            if (activityId.getSportId() != null) {
                this.sportIcon = activityId.getSportId().getIcon();
            }
        } else if (manualActivityId != null) {
            this.manualActivity = true;
            this.activityId = manualActivityId.getManualActivityId();
            this.title = manualActivityId.getName();
            this.activityDescription = manualActivityId.getDescription();
            this.sportIcon = manualActivityId.getSportId().getIcon();
            this.sportId = manualActivityId.getSportId().getSportId();
        }

        this.userId = userId;
    }

    public TrainingPlanWorkoutDto(Integer trainingPlanWorkoutId, Date workoutDate,
            Activity activityId, ManualActivity manualActivityId,
            Integer userId, Integer percentageWeather, Boolean isDrag, Double executedTime, 
            Double executedDistance,  Short indStrava, Date lastUpdateStrava) {
        this.id = trainingPlanWorkoutId;
        this.workoutDate = workoutDate;
        this.percentageWeather = percentageWeather;
        this.isDrag = isDrag;
        if (activityId != null) {
            this.activityId = activityId.getActivityId();
            this.title = activityId.getName();
            this.activityDescription = activityId.getDescription();
            this.modalityId = activityId.getModalityId().getModalityId();
            this.modality = activityId.getModalityId().getName();
            this.disciplineId = activityId.getModalityId().getDisciplineId().getDisciplineId();
            this.discipline = activityId.getModalityId().getDisciplineId().getName();
            this.objectiveId = activityId.getObjectiveId().getObjectiveId();
            this.objective = activityId.getObjectiveId().getName();
            this.level = activityId.getObjectiveId().getLevel();
            if (activityId.getSportId() != null) {
                this.sportId = activityId.getSportId().getSportId();
                this.sportIcon = activityId.getSportId().getIcon();
            }
        } else if (manualActivityId != null) {
            this.manualActivity = true;
            this.activityId = manualActivityId.getManualActivityId();
            this.title = manualActivityId.getName();
            this.activityDescription = manualActivityId.getDescription();
            this.sportIcon = manualActivityId.getSportId().getIcon();
            this.sportId = manualActivityId.getSportId().getSportId();
        }

        this.userId = userId;
        this.executedTime = executedTime;
        this.executedDistance = executedDistance;
        this.indStrava = indStrava;
        this.lastUpdateStrava = lastUpdateStrava;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPercentageWeather() {
        return percentageWeather;
    }

    public void setPercentageWeather(Integer percentageWeather) {
        this.percentageWeather = percentageWeather;
    }

    public String getSportIcon() {
        return sportIcon;
    }

    public void setSportIcon(String sportIcon) {
        this.sportIcon = sportIcon;
    }

    public Date getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(Date workoutDate) {
        this.workoutDate = workoutDate;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getModalityId() {
        return modalityId;
    }

    public void setModalityId(Integer modalityId) {
        this.modalityId = modalityId;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public Integer getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Integer disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public Integer getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Integer objectiveId) {
        this.objectiveId = objectiveId;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public boolean isManualActivity() {
        return manualActivity;
    }

    public void setManualActivity(boolean manualActivity) {
        this.manualActivity = manualActivity;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public Boolean isDrag() {
        return isDrag;
    }

    public void isDrag(Boolean isDrag) {
        this.isDrag = isDrag;
    }

    public Double getExecutedTime() {
        return executedTime;
    }

    public void setExecutedTime(Double executedTime) {
        this.executedTime = executedTime;
    }

    public Double getExecutedDistance() {
        return executedDistance;
    }

    public void setExecutedDistance(Double executedDistance) {
        this.executedDistance = executedDistance;
    }

    public Short getIndStrava() {
        return indStrava;
    }

    public void setIndStrava(Short indStrava) {
        this.indStrava = indStrava;
    }
    

}
