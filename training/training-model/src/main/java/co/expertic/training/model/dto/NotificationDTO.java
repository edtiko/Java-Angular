/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

import co.expertic.training.model.util.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class NotificationDTO {
    
    private Integer id;
    private Integer fromUserId;
    private String fromUser;
    private Integer fromUserRole;
    private String srcImage;
    private String userName;
    @JsonSerialize(using=JsonDateTimeSerializer.class)
    private Date creationDate;
    private String module;
    private Integer roleId;
    private Long count;
    private Integer planId;
    private Integer coachUserId;
    
    
    public NotificationDTO(){
        
    }
    
    public NotificationDTO(Integer fromUserRole, Integer id, Integer fromUserId, String module, Integer roleId, Date creationDate, Integer planId) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.fromUserRole = fromUserRole;
        this.creationDate = creationDate;
        this.module = module;
        this.roleId = roleId;
        this.planId = planId;

    }
    
     public NotificationDTO(Long count, String module){
        this.module = module;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getSrcImage() {
        return srcImage;
    }

    public void setSrcImage(String srcImage) {
        this.srcImage = srcImage;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public Integer getFromUserRole() {
        return fromUserRole;
    }

    public void setFromUserRole(Integer fromUserRole) {
        this.fromUserRole = fromUserRole;
    }

    public Integer getCoachUserId() {
        return coachUserId;
    }

    public void setCoachUserId(Integer coachUserId) {
        this.coachUserId = coachUserId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
        
}
