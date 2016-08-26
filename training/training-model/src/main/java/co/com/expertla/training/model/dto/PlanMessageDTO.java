/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.PlanMessage;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class PlanMessageDTO {

    private Integer id;
    private String message;
    private CoachAssignedPlanDTO coachAssignedPlanId;
    private UserDTO messageUserId;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date creationDate;

    public PlanMessageDTO() {

    }

    public PlanMessageDTO(Integer id, String message, User messageUserId, Date creationDate) {
        this.id = id;
        this.message = message;
        this.messageUserId = UserDTO.mapFromUserEntity(messageUserId);
        this.creationDate = creationDate;
    }
    
     public static PlanMessageDTO mapFromPlanMessageEntity(PlanMessage e) {
        if (e != null) {
            return new PlanMessageDTO(e.getPlanMessageId(), e.getMessage(), e.getMessageUserId(), e.getCreationDate());
        }
        return null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CoachAssignedPlanDTO getCoachAssignedPlanId() {
        return coachAssignedPlanId;
    }

    public void setCoachAssignedPlanId(CoachAssignedPlanDTO coachAssignedPlanId) {
        this.coachAssignedPlanId = coachAssignedPlanId;
    }

    public UserDTO getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(UserDTO messageUserId) {
        this.messageUserId = messageUserId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
   
}