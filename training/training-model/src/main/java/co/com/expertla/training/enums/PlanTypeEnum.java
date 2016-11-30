/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.enums;

/**
 *
 * @author Edwin G
 */
public enum PlanTypeEnum {
    
    PLAN(1), PLATFORM(2);
    private Integer id;

    private PlanTypeEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
