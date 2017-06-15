package co.expertic.training.model.dto;

import co.expertic.training.model.entities.PlanMessage;
import co.expertic.training.model.entities.User;
import co.expertic.training.model.util.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import java.util.List;

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
    private Boolean toStar;
    private Integer roleSelected;
    private boolean mobile;
    private List<Integer> starMessages;
    private Boolean read;

    public PlanMessageDTO() {

    }

    public PlanMessageDTO(Integer id, String message, User messageUserId, Date creationDate) {
        this.id = id;
        this.message = message;
        this.messageUserId = UserDTO.mapFromUserShortEntity(messageUserId);
        this.creationDate = creationDate;
    }
    
    public PlanMessageDTO(Integer id, String message, User messageUserId, Date creationDate,User receivingUserId, Boolean read) {
        this.id = id;
        this.message = message;
        this.messageUserId = UserDTO.mapFromUserShortEntity(messageUserId);
        this.creationDate = creationDate;
        this.receivingUserId = UserDTO.mapFromUserShortEntity(receivingUserId);
        this.read = read;
    }
    
     public static PlanMessageDTO mapFromPlanMessageEntity(PlanMessage e) {
        if (e != null) {
            return new PlanMessageDTO(e.getPlanMessageId(), e.getMessage(), e.getMessageUserId(), e.getCreationDate(), e.getReceivingUserId(), e.getReaded());
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

    public Boolean getToStar() {
        return toStar;
    }

    public void setToStar(Boolean toStar) {
        this.toStar = toStar;
    }

    public Integer getRoleSelected() {
        return roleSelected;
    }

    public void setRoleSelected(Integer roleSelected) {
        this.roleSelected = roleSelected;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public List<Integer> getStarMessages() {
        return starMessages;
    }

    public void setStarMessages(List<Integer> starMessages) {
        this.starMessages = starMessages;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }


        
}
