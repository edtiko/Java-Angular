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
    private UserDTO receivingUser;
    private UserDTO sendingUser;
    private String colour;
    private long hoursSpent; 
    private Double hours;
    private String readableTime;
    private Integer coachAssignedPlanId;
    private Integer coachExtAthleteId;
    private Integer roleSelected;

    public MailCommunicationDTO(Integer mailCommunicationId, String subject, String message, Integer stateId, Date creationDate,
            Boolean read, User receivingUser, User sendingUser) {
        this.mailCommunicationId = mailCommunicationId;
        this.subject = subject;
        this.message = message;
        this.stateId = stateId;
        this.creationDate = creationDate;
        this.read = read;
        this.receivingUser = UserDTO.mapFromUserEntity(receivingUser);
        this.sendingUser = UserDTO.mapFromUserEntity(sendingUser);
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

    public UserDTO getReceivingUser() {
        return receivingUser;
    }

    public void setReceivingUser(UserDTO receivingUser) {
        this.receivingUser = receivingUser;
    }

    public UserDTO getSendingUser() {
        return sendingUser;
    }

    public void setSendingUser(UserDTO sendingUser) {
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

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public String getReadableTime() {
        return readableTime;
    }

    public void setReadableTime(String readableTime) {
        this.readableTime = readableTime;
    }

    public Integer getCoachAssignedPlanId() {
        return coachAssignedPlanId;
    }

    public void setCoachAssignedPlanId(Integer coachAssignedPlanId) {
        this.coachAssignedPlanId = coachAssignedPlanId;
    }

    public Integer getCoachExtAthleteId() {
        return coachExtAthleteId;
    }

    public void setCoachExtAthleteId(Integer coachExtAthleteId) {
        this.coachExtAthleteId = coachExtAthleteId;
    }

    public Integer getRoleSelected() {
        return roleSelected;
    }

    public void setRoleSelected(Integer roleSelected) {
        this.roleSelected = roleSelected;
    }


}
