package co.expertic.training.model.dto;

import co.expertic.training.model.entities.User;
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
    private UserDTO atleteUserId;
    private UserDTO userId;
    private Integer starTeamId;
    private String colourCoach;
    private String colourStar;
    private String colourAtlete;
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

    public UserAssignedSupervisorDTO(Integer id, Integer supervisorId, User coachUserId,
            User atleteUserId
            ) {
        this.id = id;
        this.supervisorId = supervisorId;
        this.atleteUserId = UserDTO.mapFromUserEntity(atleteUserId);
        this.coachUserId = UserDTO.mapFromUserEntity(coachUserId);
    }
    
    public UserAssignedSupervisorDTO(Integer id, Integer supervisorId, User userId
            ) {
        this.id = id;
        this.supervisorId = supervisorId;
        this.userId = UserDTO.mapFromUserEntity(userId);
    }

    public UserDTO getUserId() {
        return userId;
    }

    public void setUserId(UserDTO userId) {
        this.userId = userId;
    }

    public UserDTO getAtleteUserId() {
        return atleteUserId;
    }

    public void setAtleteUserId(UserDTO atleteUserId) {
        this.atleteUserId = atleteUserId;
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

    public String getColourAtlete() {
        return colourAtlete;
    }

    public void setColourAtlete(String colourAtlete) {
        this.colourAtlete = colourAtlete;
    }
    
    
}
