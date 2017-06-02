/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

import co.expertic.training.model.entities.Role;
import co.expertic.training.model.entities.User;
import co.expertic.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Edwin G
 */
public class UserResumeDTO {
    
    private Integer userId;
    private String fullName;
    private String srcImage;
    private String country;
    private String color;
    private String discipline;
    private String role;
    private String email;
    private String login;
    private List<NotificationDTO> notificationList;
    private Integer msgReceivedCount;
    private Integer mailReceivedCount;
    private Integer videoReceivedCount;
    private Integer audioReceivedCount;
    private int count;
   @JsonSerialize(using = JsonDateSerializer.class)
    private Date creationDate;
   private Integer coachExtAthleteId;
   private String state;
   private Integer planId;
    
    
    
    public UserResumeDTO(User user){
        this.userId = user.getUserId();
        this.fullName = user.getName()+" "+user.getSecondName()+" "+user.getLastName();
        this.srcImage = getProfilePhotoBase64(user.getProfilePhoto());
        this.country = user.getCountryId() != null?user.getCountryId().getName():"";
        this.email = user.getEmail();
        this.login = user.getLogin();
        
    }
    
    public UserResumeDTO(Integer id, User user) {
        this.userId = user.getUserId();
        this.fullName = user.getName() + " " + user.getSecondName() + " " + user.getLastName();
        this.srcImage = getProfilePhotoBase64(user.getProfilePhoto());
        this.country = user.getCountryId() != null ? user.getCountryId().getName() : "";
        this.email = user.getEmail();
        this.login = user.getLogin();
        this.planId = id;

    }
    
    public UserResumeDTO(Integer userId, String fullName, String state){
        this.userId = userId;
        this.fullName = fullName;
        this.state = state;
    }
    
    public UserResumeDTO(User user, Date creationDate) {
        this.userId =  user.getUserId();
        this.fullName = user.getName()+" "+user.getSecondName()+" "+user.getLastName();
        this.creationDate = creationDate;
    }
    
      public UserResumeDTO(User user, String discipline){
        this.userId = user.getUserId();
        this.fullName = user.getName()+" "+user.getSecondName()+" "+user.getLastName();
        this.srcImage = getProfilePhotoBase64(user.getProfilePhoto());
        this.country = user.getCountryId() != null?user.getCountryId().getName():"";
        this.discipline = discipline;
        
    }
      
     public UserResumeDTO(User user, Role role){
        this.userId = user.getUserId();
        this.fullName = user.getName()+" "+user.getSecondName()+" "+user.getLastName();
        this.srcImage = getProfilePhotoBase64(user.getProfilePhoto());
        this.country = user.getCountryId() != null?user.getCountryId().getName():"";
        this.role = role.getName();
        
     }

    public UserResumeDTO(Integer coachExtAthleteId, User user, String state) {
        this.coachExtAthleteId = coachExtAthleteId;
        this.userId = user.getUserId();
        this.fullName = user.getName() + " " + user.getSecondName() + " " + user.getLastName();
        this.srcImage = getProfilePhotoBase64(user.getProfilePhoto());
        this.country = user.getCountryId() != null ? user.getCountryId().getName() : "";
        this.state = state;
    }
    
    public static String getProfilePhotoBase64(byte[] profilePhoto) {
        String base64Encoded = "";

        if (profilePhoto != null) {
            try {
                byte[] encodeBase64 = Base64.encodeBase64(profilePhoto);
                base64Encoded = new String(encodeBase64, "UTF-8");
            } catch (IOException e) {
                return null;
            }
        }
        if(!"".equals(base64Encoded)){
        base64Encoded = "data:image/png;base64," + base64Encoded; 
        }else{
            base64Encoded = "static/img/profile-default.png";
        }
        
        return base64Encoded;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSrcImage() {
        return srcImage;
    }

    public void setSrcImage(String srcImage) {
        this.srcImage = srcImage;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<NotificationDTO> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<NotificationDTO> notificationList) {
        this.notificationList = notificationList;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getMsgReceivedCount() {
        return msgReceivedCount;
    }

    public void setMsgReceivedCount(Integer msgReceivedCount) {
        this.msgReceivedCount = msgReceivedCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getMailReceivedCount() {
        return mailReceivedCount;
    }

    public void setMailReceivedCount(Integer mailReceivedCount) {
        this.mailReceivedCount = mailReceivedCount;
    }

    public Integer getVideoReceivedCount() {
        return videoReceivedCount;
    }

    public void setVideoReceivedCount(Integer videoReceivedCount) {
        this.videoReceivedCount = videoReceivedCount;
    }

    public Integer getAudioReceivedCount() {
        return audioReceivedCount;
    }

    public void setAudioReceivedCount(Integer audioReceivedCount) {
        this.audioReceivedCount = audioReceivedCount;
    }

    public Integer getCoachExtAthleteId() {
        return coachExtAthleteId;
    }

    public void setCoachExtAthleteId(Integer coachExtAthleteId) {
        this.coachExtAthleteId = coachExtAthleteId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
        
    
}
