/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.User;
import java.util.Date;

/**
 *
 * @author Andres Felipe Lopez
 */
public class UserAssignedSupervisorDTO {
    
    private Integer id;
    private Integer supervisorId;
    private UserDTO coachUserId;
    private UserDTO starUserId;
    private Integer starTeamId;
    private String colourCoach;
    private String colourStar;
    private Date creationDate;
    
     public UserAssignedSupervisorDTO(){
         
     }
    
    public UserAssignedSupervisorDTO(Integer id, User coachUserId, User starUserId,
            Integer startTeamId, Integer supervisorId){
        this.id = id;
        this.coachUserId = UserDTO.mapFromUserEntity(coachUserId);
        this.starUserId = UserDTO.mapFromUserEntity(starUserId);
        this.starTeamId = startTeamId;
        this.supervisorId = supervisorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStarTeamId() {
        return starTeamId;
    }

    public void setStarTeamId(Integer startTeamId) {
        this.starTeamId = startTeamId;
    }

    public UserDTO getCoachUserId() {
        return coachUserId;
    }

    public void setCoachUserId(UserDTO coachUserId) {
        this.coachUserId = coachUserId;
    }

    public UserDTO getStarUserId() {
        return starUserId;
    }

    public void setStarUserId(UserDTO starUserId) {
        this.starUserId = starUserId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getColourCoach() {
        return colourCoach;
    }

    public void setColourCoach(String colourCoach) {
        this.colourCoach = colourCoach;
    }

    public String getColourStar() {
        return colourStar;
    }

    public void setColourStar(String colourStar) {
        this.colourStar = colourStar;
    }

    public Integer getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Integer supervisorId) {
        this.supervisorId = supervisorId;
    }
    
    
}
