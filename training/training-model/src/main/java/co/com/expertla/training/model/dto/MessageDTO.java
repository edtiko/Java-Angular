/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.util.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class MessageDTO {

    private Integer id;
    private String message;
    private Integer messageUserId;
    private String messageUserName;
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date creationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(Integer messageUserId) {
        this.messageUserId = messageUserId;
    }

    public String getMessageUserName() {
        return messageUserName;
    }

    public void setMessageUserName(String messageUserName) {
        this.messageUserName = messageUserName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    
    
}
