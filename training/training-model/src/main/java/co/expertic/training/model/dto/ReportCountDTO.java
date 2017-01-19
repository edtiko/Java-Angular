/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class ReportCountDTO {

    private Integer count;
    private Object name;
    private Double price;
    private Integer percentaje;
    private Double total;
    
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
    public ReportCountDTO(String name, Long count, BigDecimal price, Integer percentaje, BigDecimal total) {
        this.count = count.intValue();
        this.name = name;
        this.price = price.doubleValue();
        this.percentaje = percentaje;
        this.total = total.doubleValue();
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getPercentaje() {
        return percentaje;
    }

    public void setPercentaje(Integer percentaje) {
        this.percentaje = percentaje;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
    
    
}
