/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.enums;

/**
 *
 * @author Edwin G
 */
public enum LoadTypeEnum {

    BASE(1), BUILD(2), PEAK(3), EVENT(4), DISCHARGE(5);
    
    private Integer id;

    private LoadTypeEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
