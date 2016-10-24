/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

/**
 *
 * @author Andres Lopez
 */
public class PlanWorkoutDTO {
    private Integer userId;
    private Integer activityId;
    private Integer manualActivityId;
    private String activityDate;
    private Integer trainingPlanWorkoutId;
    private long from;
    private long to;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public Integer getTrainingPlanWorkoutId() {
        return trainingPlanWorkoutId;
    }

    public void setTrainingPlanWorkoutId(Integer trainingPlanWorkoutId) {
        this.trainingPlanWorkoutId = trainingPlanWorkoutId;
    }

    public Integer getManualActivityId() {
        return manualActivityId;
    }

    public void setManualActivityId(Integer manualActivityId) {
        this.manualActivityId = manualActivityId;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }
    
    
}
