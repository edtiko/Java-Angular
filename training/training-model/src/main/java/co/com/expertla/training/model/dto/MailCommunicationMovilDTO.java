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
* @author Andres Felipe Lopez
**/
public class MailCommunicationMovilDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer mailCommunicationId;
    private String subject;
    private String message;
    private Integer stateId;
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date creationDate;
    private Boolean read;
    private Integer receivingUser;
    private Integer sendingUser;
    private String receivingUserFullName;
    private String receivingUserPhoto;
    private String sendingUserFullName;
    private String sendingUserPhoto;

    public MailCommunicationMovilDTO() {
    }

    public MailCommunicationMovilDTO(Integer mailCommunicationId) {
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

    public Integer getReceivingUser() {
        return receivingUser;
    }

    public void setReceivingUser(Integer receivingUser) {
        this.receivingUser = receivingUser;
    }

    public Integer getSendingUser() {
        return sendingUser;
    }

    public void setSendingUser(Integer sendingUser) {
        this.sendingUser = sendingUser;
    }

    public String getReceivingUserFullName() {
        return receivingUserFullName;
    }

    public void setReceivingUserFullName(String receivingUserFullName) {
        this.receivingUserFullName = receivingUserFullName;
    }

    public String getReceivingUserPhoto() {
        return receivingUserPhoto;
    }

    public void setReceivingUserPhoto(String receivingUserPhoto) {
        this.receivingUserPhoto = receivingUserPhoto;
    }

    public String getSendingUserFullName() {
        return sendingUserFullName;
    }

    public void setSendingUserFullName(String sendingUserFullName) {
        this.sendingUserFullName = sendingUserFullName;
    }

    public String getSendingUserPhoto() {
        return sendingUserPhoto;
    }

    public void setSendingUserPhoto(String sendingUserPhoto) {
        this.sendingUserPhoto = sendingUserPhoto;
    }


}
