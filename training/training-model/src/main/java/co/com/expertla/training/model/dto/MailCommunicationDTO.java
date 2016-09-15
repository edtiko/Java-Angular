package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.*;
import co.com.expertla.training.model.util.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
* MailCommunication DTO <br>
* Creation Date : <br>
* date 12/09/2016 <br>
* @author Angela Ramírez
**/
public class MailCommunicationDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer mailCommunicationId;
    private String subject;
    private String message;
    private Integer stateId;
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date creationDate;
    private Boolean read;
    private User receivingUser;
    private User sendingUser;
    private String colour;
    private long hoursSpent;      

    public MailCommunicationDTO(Integer mailCommunicationId, String subject, String message, Integer stateId, Date creationDate, 
            Boolean read, User receivingUser, User sendingUser) {
        this.mailCommunicationId = mailCommunicationId;
        this.subject = subject;
        this.message = message;
        this.stateId = stateId;
        this.creationDate = creationDate;
        this.read = read;
        this.receivingUser = receivingUser;
        this.sendingUser = sendingUser;
    } 
    
    public MailCommunicationDTO() {
    }

    public MailCommunicationDTO(Integer mailCommunicationId) {
        this.mailCommunicationId = mailCommunicationId;
    }

    public Integer getMailCommunicationId() {
        return mailCommunicationId;
    }

    public void setMailCommunicationId(Integer mailCommunicationId) {
        this.mailCommunicationId = mailCommunicationId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public User getReceivingUser() {
        return receivingUser;
    }

    public void setReceivingUser(User receivingUser) {
        this.receivingUser = receivingUser;
    }

    public User getSendingUser() {
        return sendingUser;
    }

    public void setSendingUser(User sendingUser) {
        this.sendingUser = sendingUser;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public long getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(long hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

}
