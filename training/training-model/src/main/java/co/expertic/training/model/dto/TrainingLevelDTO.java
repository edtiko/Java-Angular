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
    private Integer maxWeekPlan;
    private Integer modalityId;
    private String  modality;
    private Integer trainingLevelTypeId;
    private Integer disciplineId;

    public TrainingLevelDTO() {

    }

    public TrainingLevelDTO(TrainingLevel t) {
        this.id = t.getTrainingLevelId();
        this.name = t.getTrainingLeveTypelId().getName();
        this.minSesion = t.getMinSesion();
        this.maxSesion = t.getMaxSesion();
        this.minHourWeek = t.getMinHourWeek();
        this.maxHourWeek = t.getMaxHourWeek();
        this.minWeekPlan = t.getMinWeekPlan();
        this.maxWeekPlan = t.getMaxWeekPlan();
        this.modality = t.getModalityId().getName();
        this.modalityId = t.getModalityId().getModalityId();
        this.trainingLevelTypeId = t.getTrainingLeveTypelId().getTrainingLevelTypeId();
        this.disciplineId = t.getModalityId().getDisciplineId().getDisciplineId();
        
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

    public Integer getMaxWeekPlan() {
        return maxWeekPlan;
    }

    public void setMaxWeekPlan(Integer maxWeekPlan) {
        this.maxWeekPlan = maxWeekPlan;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public Integer getTrainingLevelTypeId() {
        return trainingLevelTypeId;
    }

    public void setTrainingLevelTypeId(Integer trainingLevelTypeId) {
        this.trainingLevelTypeId = trainingLevelTypeId;
    }

    public Integer getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Integer disciplineId) {
        this.disciplineId = disciplineId;
    }



}
