/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

import co.expertic.training.model.entities.User;
import java.io.IOException;
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
    private List<NotificationDTO> notificationList;
    
    
    
    public UserResumeDTO(User user){
        this.userId = user.getUserId();
        this.fullName = user.getName()+" "+user.getSecondName()+" "+user.getLastName();
        this.srcImage = getProfilePhotoBase64(user.getProfilePhoto());
        this.country = user.getCountryId() != null?user.getCountryId().getName():"";
        
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
    
    
    
    
}
