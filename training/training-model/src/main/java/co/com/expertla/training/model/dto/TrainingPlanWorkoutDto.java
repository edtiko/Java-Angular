/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
* Dto para consulta de planes de entrenamiento por usuario <br>
* Info. Creación: <br>
* fecha 15/07/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
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
    private Integer objetiveId;
    private String objetive;
    private int level;
    private Integer userId;

    public TrainingPlanWorkoutDto() {
    }

    public TrainingPlanWorkoutDto(Integer trainingPlanWorkoutId, Date workoutDate,
            Integer activityId, String activity, String activityDescription, Integer modalityId, String modality,
            Integer objetiveId, String objetive, Integer disciplineId, String discipline, Integer level, Integer userId) {
        this.id = trainingPlanWorkoutId;
        this.workoutDate = workoutDate;
        this.activityId = activityId;
        this.title = activity;
        this.activityDescription = activityDescription;
        this.modalityId = modalityId;
        this.modality = modality;
        this.disciplineId = disciplineId;
        this.discipline = discipline;
        this.objetiveId = objetiveId;
        this.objetive = objetive;
        this.level = level;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getObjetiveId() {
        return objetiveId;
    }

    public void setObjetiveId(Integer objetiveId) {
        this.objetiveId = objetiveId;
    }

    public String getObjetive() {
        return objetive;
    }

    public void setObjetive(String objetive) {
        this.objetive = objetive;
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
    
    
    
}
