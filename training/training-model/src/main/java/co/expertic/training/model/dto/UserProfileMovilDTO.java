/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

import co.expertic.training.model.entities.Environment;
import co.expertic.training.model.entities.Modality;
import co.expertic.training.model.entities.TrainingLevel;
import co.expertic.training.model.entities.User;
import co.expertic.training.model.entities.UserProfile;
import co.expertic.training.model.entities.Weather;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public class UserProfileMovilDTO {

    private Integer userProfileId;
    private Integer ageSport;
    private BigInteger ppm;
    private BigInteger power;
    private Integer userId;
    private Integer objectiveId;
    private String  objectiveName;
    private List<UserAvailabilityDTO> availability;
    private Integer modalityId;
    private String  modalityName;
    private Integer vo2Running;
    private Integer vo2Ciclismo;
    private Integer environmentId;
    private String environmentName;
    private Integer weatherId;
    private String weatherName;
    private Float weight;
    private Float height;

    public UserProfileMovilDTO() {
    }

    private UserProfileMovilDTO(Integer userProfileId, Integer ageSport, BigInteger ppm, BigInteger power, User userId, TrainingLevel objectiveId, Modality modalityId, Integer vo2Running, Integer vo2Ciclismo, Environment environmentId, Weather weatherId) {
        this.userProfileId = userProfileId;
        this.ageSport = ageSport;
        this.ppm = ppm;
        this.power = power;
        this.userId = userId.getUserId();
        if(objectiveId != null && objectiveId.getTrainingLevelTypeId() != null){
        this.objectiveId = objectiveId.getTrainingLevelId();
        this.objectiveName = objectiveId.getTrainingLevelTypeId().getName();
        }
        if(modalityId != null){
        this.modalityId = modalityId.getModalityId();
        this.modalityName = modalityId.getName();
        }
        this.vo2Running = vo2Running;
        this.vo2Ciclismo = vo2Ciclismo;
        if(environmentId != null){
        this.environmentId = environmentId.getEnvironmentId();
        this.environmentName = environmentId.getName();
        }
        if(weatherId != null){
        this.weatherId = weatherId.getWeatherId();
        this.weatherName = weatherId.getName();
        }
        this.height = userId.getHeight();
        this.weight = userId.getWeight();
                
    }

    public static UserProfileMovilDTO mapFromUserEntity(UserProfile up) {
        if (up != null) {
            return new UserProfileMovilDTO(up.getUserProfileId(), up.getAgeSport(), up.getPpm(), up.getPower(), 
                                           up.getUserId(), up.getObjectiveId(), up.getModalityId(), up.getVo2Running(), 
                                           up.getVo2Ciclismo(), up.getEnvironmentId(), up.getWeatherId());
        }
        return null;
    }

    
    public Integer getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Integer userProfileId) {
        this.userProfileId = userProfileId;
    }

    public Integer getAgeSport() {
        return ageSport;
    }

    public void setAgeSport(Integer ageSport) {
        this.ageSport = ageSport;
    }

    public BigInteger getPpm() {
        return ppm;
    }

    public void setPpm(BigInteger ppm) {
        this.ppm = ppm;
    }

    public BigInteger getPower() {
        return power;
    }

    public void setPower(BigInteger power) {
        this.power = power;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getObjectiveName() {
        return objectiveName;
    }

    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    public List<UserAvailabilityDTO> getAvailability() {
        return availability;
    }

    public void setAvailability(List<UserAvailabilityDTO> availability) {
        this.availability = availability;
    }


    public String getModalityName() {
        return modalityName;
    }

    public void setModalityName(String modalityName) {
        this.modalityName = modalityName;
    }

    public Integer getVo2Running() {
        return vo2Running;
    }

    public void setVo2Running(Integer vo2Running) {
        this.vo2Running = vo2Running;
    }

    public Integer getVo2Ciclismo() {
        return vo2Ciclismo;
    }

    public void setVo2Ciclismo(Integer vo2Ciclismo) {
        this.vo2Ciclismo = vo2Ciclismo;
    }

    public Integer getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Integer environmentId) {
        this.environmentId = environmentId;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public Integer getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public String getWeatherName() {
        return weatherName;
    }

    public void setWeatherName(String weatherName) {
        this.weatherName = weatherName;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Integer getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Integer objectiveId) {
        this.objectiveId = objectiveId;
    }

    public Integer getModalityId() {
        return modalityId;
    }

    public void setModalityId(Integer modalityId) {
        this.modalityId = modalityId;
    }
    
    
}
