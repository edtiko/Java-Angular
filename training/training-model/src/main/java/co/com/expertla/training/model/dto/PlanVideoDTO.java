/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

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
    private UserDTO toUserId;
    private String name;
    private UserDTO fromUserId;
    private String  videoPath;
    @JsonSerialize(using=JsonDateTimeSerializer.class)
    private Date    createDate;
    
    public PlanVideoDTO(){
        
    }
    
    public PlanVideoDTO(Integer planVideoId, String name, User fromUserId, User toUserId, Date createDate){
        this.id = planVideoId;
        this.name = name;
        this.fromUserId = UserDTO.mapFromUserEntity(fromUserId);
        this.toUserId =  UserDTO.mapFromUserEntity(toUserId);
        this.createDate = createDate;
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTO getToUserId() {
        return toUserId;
    }

    public void setToUserId(UserDTO toUserId) {
        this.toUserId = toUserId;
    }

    public UserDTO getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(UserDTO fromUserId) {
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
    
    
    
    
}
