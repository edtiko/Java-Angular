/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

import co.expertic.training.model.entities.TrainingUserSerie;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class TrainingUserSerieDTO {

    private Integer id;
    private Integer numSeries;
    private Double serieTime;
    private Integer numZone;
    private Integer restTime;
    private String description;
    private Integer warmUpTime;
    private Integer pullDownTime;
    private Double timeSerie;
    private String ppmPaceDescription;
    private String restSerieDescription;

    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "CET")
    private Date workDate;

    public TrainingUserSerieDTO() {

    }

    public TrainingUserSerieDTO(TrainingUserSerie serie) {
        this.id = serie.getTrainingUserSerieId();
        this.numSeries = serie.getNumSeries();
        this.numZone = serie.getNumZona();
        this.serieTime = serie.getSerieTime();
        this.workDate = serie.getWorkDate();
        this.restTime = serie.getRestTime();
        this.timeSerie = serie.getSerieTime();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumSeries() {
        return numSeries;
    }

    public void setNumSeries(Integer numSeries) {
        this.numSeries = numSeries;
    }

    public Double getSerieTime() {
        return serieTime;
    }

    public void setSerieTime(Double serieTime) {
        this.serieTime = serieTime;
    }

    public Integer getNumZone() {
        return numZone;
    }

    public void setNumZone(Integer numZone) {
        this.numZone = numZone;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRestTime() {
        return restTime;
    }

    public void setRestTime(Integer restTime) {
        this.restTime = restTime;
    }

    public Integer getWarmUpTime() {
        return warmUpTime;
    }

    public void setWarmUpTime(Integer warmUpTime) {
        this.warmUpTime = warmUpTime;
    }

    public Integer getPullDownTime() {
        return pullDownTime;
    }

    public void setPullDownTime(Integer pullDownTime) {
        this.pullDownTime = pullDownTime;
    }
    
    
    public Double getTimeSerie() {
        return timeSerie;
    }

    public void setTimeSerie(Double timeSerie) {
        this.timeSerie = timeSerie;
    }

    public String getPpmPaceDescription() {
        return ppmPaceDescription;
    }

    public void setPpmPaceDescription(String ppmPaceDescription) {
        this.ppmPaceDescription = ppmPaceDescription;
    }

    public String getRestSerieDescription() {
        return restSerieDescription;
    }

    public void setRestSerieDescription(String restSerieDescription) {
        this.restSerieDescription = restSerieDescription;
    }
    
    
}
