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
    private String restTime;
    private long start;
    private long end;
    private String className;
    private String title;
    private String discipline;
    
    
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
    
    
    
    
}
