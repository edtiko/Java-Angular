/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

import co.expertic.training.model.entities.TrainingLevel;

/**
 *
 * @author Edwin G
 */
public class TrainingLevelDTO {

    private Integer id;
    private String name;
    private Integer minSesion;
    private Integer maxSesion;
    private Integer minHourWeek;
    private Integer maxHourWeek;
    private Integer minWeekPlan;
    private Integer maxWeelPlan;
    private Integer modalityId;

    public TrainingLevelDTO() {

    }

    public TrainingLevelDTO(TrainingLevel t) {
        this.id = t.getTrainingLevelId();
        this.name = t.getDescription();
        this.minSesion = t.getMinSesion();
        this.maxSesion = t.getMaxSesion();
        this.minHourWeek = t.getMinHourWeek();
        this.maxHourWeek = t.getMaxHourWeek();
        this.minWeekPlan = t.getMinWeekPlan();
        this.maxWeelPlan = t.getMaxWeekPlan();
    }

    public TrainingLevelDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getModalityId() {
        return modalityId;
    }

    public void setModalityId(Integer modalityId) {
        this.modalityId = modalityId;
    }

    public Integer getMinSesion() {
        return minSesion;
    }

    public void setMinSesion(Integer minSesion) {
        this.minSesion = minSesion;
    }

    public Integer getMaxSesion() {
        return maxSesion;
    }

    public void setMaxSesion(Integer maxSesion) {
        this.maxSesion = maxSesion;
    }

    public Integer getMinHourWeek() {
        return minHourWeek;
    }

    public void setMinHourWeek(Integer minHourWeek) {
        this.minHourWeek = minHourWeek;
    }

    public Integer getMaxHourWeek() {
        return maxHourWeek;
    }

    public void setMaxHourWeek(Integer maxHourWeek) {
        this.maxHourWeek = maxHourWeek;
    }

    public Integer getMinWeekPlan() {
        return minWeekPlan;
    }

    public void setMinWeekPlan(Integer minWeekPlan) {
        this.minWeekPlan = minWeekPlan;
    }

    public Integer getMaxWeelPlan() {
        return maxWeelPlan;
    }

    public void setMaxWeelPlan(Integer maxWeelPlan) {
        this.maxWeelPlan = maxWeelPlan;
    }

}
