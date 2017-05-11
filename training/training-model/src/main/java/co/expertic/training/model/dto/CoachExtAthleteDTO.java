/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

import static co.expertic.training.model.dto.UserResumeDTO.getProfilePhotoBase64;
import co.expertic.training.model.entities.ConfigurationPlan;
import co.expertic.training.model.entities.State;
import co.expertic.training.model.entities.User;
import co.expertic.training.model.util.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Edwin G
 */
public class CoachExtAthleteDTO {

    private Integer id;
    private UserDTO athleteUserId;
    private UserDTO coachUserId;
    private Integer trainingPlanUserId;
    private TrainingPlanDTO trainingPlanId;
    private String state;
    private Integer stateId;
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date creationDate;
    private boolean external;
    private int count;
    private Integer msgReceivedCount;
    private Integer mailReceivedCount;
    private Integer videoReceivedCount;
    private Integer audioReceivedCount;
    private String color;
    private String srcImage;
    private CommunicationDTO coachCommunication;

    public CoachExtAthleteDTO() {

    }

    public CoachExtAthleteDTO(Integer id, User athleteUserId, User coachUserId, ConfigurationPlan cplan, Date creationDate, State stateId) {
        this.id = id;
        this.athleteUserId = UserDTO.mapFromUserEntity(athleteUserId);
        this.coachUserId = UserDTO.mapFromUserEntity(coachUserId);
        this.creationDate = creationDate;
        //this.trainingPlanUserId = trainingPlanUserId;
        this.stateId = stateId.getStateId();
        this.state = stateId.getName();
        this.external = true;
        this.trainingPlanId = TrainingPlanDTO.mapFromTrainingPlanEntity(cplan);
    }

    public CoachExtAthleteDTO(Integer id, User athleteUserId, User coachUserId, Date creationDate, State stateId) {
        this.id = id;
        this.athleteUserId = UserDTO.mapFromUserEntity(athleteUserId);
        this.coachUserId = UserDTO.mapFromUserEntity(coachUserId);
        this.creationDate = creationDate;
        this.stateId = stateId.getStateId();
        this.state = stateId.getName();
        this.external = true;
        this.srcImage = getProfilePhotoBase64(athleteUserId.getProfilePhoto());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTO getAthleteUserId() {
        return athleteUserId;
    }

    public void setAthleteUserId(UserDTO athleteUser) {
        this.athleteUserId = athleteUser;
    }

    public Integer getTrainingPlanUserId() {
        return trainingPlanUserId;
    }

    public void setTrainingPlanUserId(Integer trainingPlanUserId) {
        this.trainingPlanUserId = trainingPlanUserId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public TrainingPlanDTO getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(TrainingPlanDTO trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public UserDTO getCoachUserId() {
        return coachUserId;
    }

    public void setCoachUserId(UserDTO coachUserId) {
        this.coachUserId = coachUserId;
    }

    public boolean isExternal() {
        return external;
    }

    public void setExternal(boolean external) {
        this.external = external;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getMsgReceivedCount() {
        return msgReceivedCount;
    }

    public void setMsgReceivedCount(Integer msgReceivedCount) {
        this.msgReceivedCount = msgReceivedCount;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSrcImage() {
        return srcImage;
    }

    public void setSrcImage(String srcImage) {
        this.srcImage = srcImage;
    }

    public CommunicationDTO getCoachCommunication() {
        return coachCommunication;
    }

    public void setCoachCommunication(CommunicationDTO coachCommunication) {
        this.coachCommunication = coachCommunication;
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
        if (!"".equals(base64Encoded)) {
            base64Encoded = "data:image/png;base64," + base64Encoded;
        } else {
            base64Encoded = "static/img/profile-default.png";
        }

        return base64Encoded;
    }

}
