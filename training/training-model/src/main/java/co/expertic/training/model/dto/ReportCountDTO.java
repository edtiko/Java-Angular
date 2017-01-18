/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class ReportCountDTO {

    private Integer count;
    private Object name;
    
    public ReportCountDTO(){
        
    }
    
     public ReportCountDTO(String name, Long count){
        this.count = count.intValue();
        this.name = name;
    }
    public ReportCountDTO(Date name, Long count) {
        this.count = count.intValue();
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
