/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

/**
 *
 * @author Edwin G
 */
public class UserGroupDTO {
    
    private UserDTO user;
    private UserProfileDTO userProfile;
    
    
    public UserGroupDTO(){
        
    }
    
    public UserGroupDTO(UserDTO user, UserProfileDTO userProfile) {
       this.user = user;
       this.userProfile = userProfile;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public UserProfileDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileDTO userProfile) {
        this.userProfile = userProfile;
    }
    
    
    
    
}
