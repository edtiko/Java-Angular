
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.PlanAudio;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.util.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;


/**
 *
 * @author Edwin G
 */
public class PlanAudioDTO {
    
    private Integer id;
    private Integer toUserId;
    private UserDTO toUser;
    private String name;
    private Integer fromUserId;
    private UserDTO fromUser;
    private String  audioPath;
    @JsonSerialize(using=JsonDateTimeSerializer.class)
    private Date    createDate;
    private Integer sesionId;
    private Integer coachAssignedPlanId;
    private Integer coachExtAthleteId;
    
    public PlanAudioDTO(){
        
    }
    
    public PlanAudioDTO(Integer planAudioId, String name, Integer fromUserId, Integer toUserId, Date createDate){
        this.id = planAudioId;
        this.name = name;
        this.fromUserId = fromUserId;
        this.toUserId =  toUserId;
        this.createDate = createDate;
        
    }
    
      public PlanAudioDTO(Integer planAudioId, String name, User fromUserId, User toUserId, Date createDate){
        this.id = planAudioId;
        this.name = name;
        this.fromUser = UserDTO.mapFromUserEntity(fromUserId);
        this.toUser =   UserDTO.mapFromUserEntity(toUserId);
        this.createDate = createDate;
        
    }
    
    public static PlanAudioDTO mapFromPlanAudioEntityShort(PlanAudio audio) {
        if (audio != null) {
            return new PlanAudioDTO(audio.getPlanAudioId(), audio.getName(), audio.getFromUserId().getUserId(), audio.getToUserId().getUserId(), audio.getCreationDate());
        }
        return null;
    }
    
      public static PlanAudioDTO mapFromPlanAudioEntity(PlanAudio audio) {
        if (audio != null) {
            return new PlanAudioDTO(audio.getPlanAudioId(), audio.getName(), audio.getFromUserId(), audio.getToUserId(), audio.getCreationDate());
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getVideoPath() {
        return audioPath;
    }

    public void setVideoPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSesionId() {
        return sesionId;
    }

    public void setSesionId(Integer sesionId) {
        this.sesionId = sesionId;
    }

    public Integer getCoachAssignedPlanId() {
        return coachAssignedPlanId;
    }

    public void setCoachAssignedPlanId(Integer coachAssignedPlanId) {
        this.coachAssignedPlanId = coachAssignedPlanId;
    }

    public UserDTO getToUser() {
        return toUser;
    }

    public void setToUser(UserDTO toUser) {
        this.toUser = toUser;
    }

    public UserDTO getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserDTO fromUser) {
        this.fromUser = fromUser;
    }

    public Integer getCoachExtAthleteId() {
        return coachExtAthleteId;
    }

    public void setCoachExtAthleteId(Integer coachExtAthleteId) {
        this.coachExtAthleteId = coachExtAthleteId;
    }
    
      
}
