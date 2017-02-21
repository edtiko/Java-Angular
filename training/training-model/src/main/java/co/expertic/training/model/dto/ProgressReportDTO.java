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
public class ProgressReportDTO {

    private Integer id;
    private String label;
    private Double value;
    private Long activityCount;
    
    
    public ProgressReportDTO(){
        
    }
    
    public ProgressReportDTO(Integer id, Double value, String label, Long activityCount) {
        this.id = id;
        this.label = label;
        this.value = value;
        this.activityCount = activityCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(Long activityCount) {
        this.activityCount = activityCount;
    }
    
    

}
