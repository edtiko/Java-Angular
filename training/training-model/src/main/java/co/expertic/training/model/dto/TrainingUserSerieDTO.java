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
    private String description;

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
    
    

}
