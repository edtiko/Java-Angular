package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.PlanMessage;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.util.JsonDateTimeSerializer;
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
    private CoachExtAthleteDTO coachExtAthleteId;
    private UserDTO messageUserId;
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date creationDate;
    private Integer countMessagesCoach;
    private Integer countMessagesAthlete;
    private UserDTO receivingUserId;
    private String readableTime;
    private Double hours;

    public PlanMessageDTO() {

    }

    public PlanMessageDTO(Integer id, String message, User messageUserId, Date creationDate) {
        this.id = id;
        this.message = message;
        this.messageUserId = UserDTO.mapFromUserEntity(messageUserId);
        this.creationDate = creationDate;
    }
    
    public PlanMessageDTO(Integer id, String message, User messageUserId, Date creationDate,User receivingUserId) {
        this.id = id;
        this.message = message;
        this.messageUserId = UserDTO.mapFromUserEntity(messageUserId);
        this.creationDate = creationDate;
        this.receivingUserId = UserDTO.mapFromUserEntity(receivingUserId);
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

    public Integer getCountMessagesCoach() {
        return countMessagesCoach;
    }

    public void setCountMessagesCoach(Integer countMessagesCoach) {
        this.countMessagesCoach = countMessagesCoach;
    }

    public Integer getCountMessagesAthlete() {
        return countMessagesAthlete;
    }

    public void setCountMessagesAthlete(Integer countMessagesAthlete) {
        this.countMessagesAthlete = countMessagesAthlete;
    }

    public UserDTO getReceivingUserId() {
        return receivingUserId;
    }

    public void setReceivingUserId(UserDTO receivingUserId) {
        this.receivingUserId = receivingUserId;
    }
    public CoachExtAthleteDTO getCoachExtAthleteId() {
        return coachExtAthleteId;
    }

    public void setCoachExtAthleteId(CoachExtAthleteDTO coachExtAthleteId) {
        this.coachExtAthleteId = coachExtAthleteId;
    }

    public String getReadableTime() {
        return readableTime;
    }

    public void setReadableTime(String readableTime) {
        this.readableTime = readableTime;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;

    }
    
}
