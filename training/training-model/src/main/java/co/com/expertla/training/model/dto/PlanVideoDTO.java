/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.PlanVideo;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.util.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class PlanVideoDTO {
    
    private Integer id;
    private Integer toUserId;
    private UserDTO toUser;
    private String name;
    private Integer fromUserId;
    private UserDTO fromUser;
    private String  videoPath;
    @JsonSerialize(using=JsonDateTimeSerializer.class)
    private Date    createDate;
    private Integer sesionId;
    private Integer coachAssignedPlanId;
    private Integer coachExtAthleteId;
    
    public PlanVideoDTO(){
        
    }
    
    public PlanVideoDTO(Integer planVideoId, String name, Integer fromUserId, Integer toUserId, Date createDate){
        this.id = planVideoId;
        this.name = name;
        this.fromUserId = fromUserId;
        this.toUserId =  toUserId;
        this.createDate = createDate;
        
    }
    
      public PlanVideoDTO(Integer planVideoId, String name, User fromUserId, User toUserId, Date createDate){
        this.id = planVideoId;
        this.name = name;
        this.fromUser = UserDTO.mapFromUserEntity(fromUserId);
        this.toUser =   UserDTO.mapFromUserEntity(toUserId);
        this.createDate = createDate;
        
    }
    
    public static PlanVideoDTO mapFromPlanVideoEntityShort(PlanVideo video) {
        if (video != null) {
            return new PlanVideoDTO(video.getPlanVideoId(), video.getName(), video.getFromUserId().getUserId(), video.getToUserId().getUserId(), video.getCreationDate());
        }
        return null;
    }
    
      public static PlanVideoDTO mapFromPlanVideoEntity(PlanVideo video) {
        if (video != null) {
            return new PlanVideoDTO(video.getPlanVideoId(), video.getName(), video.getFromUserId(), video.getToUserId(), video.getCreationDate());
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
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
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
