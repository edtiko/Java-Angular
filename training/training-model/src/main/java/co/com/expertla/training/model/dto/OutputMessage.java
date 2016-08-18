/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class OutputMessage extends PlanMessageDTO {

    private Date time;
    
    public OutputMessage(PlanMessageDTO original, Date time) {
        super(original.getId(), original.getMessage());
        this.time = time;
    }
    
    public Date getTime() {
        return time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }
}
