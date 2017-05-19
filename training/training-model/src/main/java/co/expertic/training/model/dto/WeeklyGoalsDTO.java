/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

/**
 *
 * @author Edwin G
 */
public class WeeklyGoalsDTO {
    
    private Integer numActivities;
    private Integer numSessions;
    private Integer distance;
    private Integer speedAverage;
    private Integer numCalories;

    public Integer getNumActivities() {
        return numActivities;
    }

    public void setNumActivities(Integer numActivities) {
        this.numActivities = numActivities;
    }

    public Integer getNumSessions() {
        return numSessions;
    }

    public void setNumSessions(Integer numSessions) {
        this.numSessions = numSessions;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getSpeedAverage() {
        return speedAverage;
    }

    public void setSpeedAverage(Integer speedAverage) {
        this.speedAverage = speedAverage;
    }

    public Integer getNumCalories() {
        return numCalories;
    }

    public void setNumCalories(Integer numCalories) {
        this.numCalories = numCalories;
    }
    
    
    
    
    
}
