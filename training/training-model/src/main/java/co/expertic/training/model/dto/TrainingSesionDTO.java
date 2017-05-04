/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

import java.util.List;

/**
 *
 * @author Edwin G
 */
public class TrainingSesionDTO {
    
    
    private List<SerieGenerada> series;
    private String description;
    private String sportIcon;
    private Integer sesion;
    private Integer week;
    private Integer restTime;
    private Integer pullDown;
    private Integer warmUp;
    private Integer zone;
    private long start;
    private long end;
    private String className;
    private String title;
    private String discipline;
    private Double timeSerie;
    
    
    public TrainingSesionDTO(){
        
    }

    public List<SerieGenerada> getSeries() {
        return series;
    }

    public void setSeries(List<SerieGenerada> series) {
        this.series = series;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSportIcon() {
        return sportIcon;
    }

    public void setSportIcon(String sportIcon) {
        this.sportIcon = sportIcon;
    }

    public Integer getSesion() {
        return sesion;
    }

    public void setSesion(Integer sesion) {
        this.sesion = sesion;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
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

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public Integer getRestTime() {
        return restTime;
    }

    public void setRestTime(Integer restTime) {
        this.restTime = restTime;
    }

    public Integer getPullDown() {
        return pullDown;
    }

    public void setPullDown(Integer pullDown) {
        this.pullDown = pullDown;
    }

    public Integer getWarmUp() {
        return warmUp;
    }

    public void setWarmUp(Integer warmUp) {
        this.warmUp = warmUp;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }

    public Double getTimeSerie() {
        return timeSerie;
    }

    public void setTimeSerie(Double timeSerie) {
        this.timeSerie = timeSerie;
    }
    
    
}
