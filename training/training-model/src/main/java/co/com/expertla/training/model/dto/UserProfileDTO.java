package co.com.expertla.training.model.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * UserProfileDTO for object UserProfile
 * @author Angela Ramírez
 */
public class UserProfileDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Integer userProfileId;
    private String indPulsometer;
    private String indPower;
    private Integer ageSport;
    private BigInteger ppm;
    private BigInteger power;
    private String sportsAchievements;
    private String aboutMe;
    private Integer userId;
    private String indMetricSys;
    private Integer discipline;
    private Integer sport;
    private Integer shoes;
    private Integer bikes;
    private Integer modelBike;
    private Integer potentiometer;
    private String  otherPotentiometer;
    private Integer modelPotentiometer;
    private String  otherModelPotentiometer;
    private Integer pulsometer;
    private String  otherPulsometer;
    private Integer modelPulsometer;
    private String  otherModelPulsometer;
    private Integer objective;
    private List<UserAvailabilityDTO> availability;
    private Integer modality;
    private Integer vo2Running;
    private Integer vo2Ciclismo;
    private Integer environmentId;
    private Integer weatherId;

    public UserProfileDTO() {
    }
    
    public UserProfileDTO(Integer userProfileId, String indPulsometer, String indPower, Integer ageSport, BigInteger ppm,
            BigInteger power, String sportsAchievements, String aboutMe, Integer userId, String indMetricSys,  
            Integer objective, Integer modality, Integer vo2Running, Integer vo2Ciclismo, Integer environmentId, Integer weatherId) {
        this.userProfileId = userProfileId;
        this.indPulsometer = indPulsometer;
        this.indPower = indPower;
        this.ageSport = ageSport;
        this.ppm = ppm;
        this.power = power;
        this.sportsAchievements = sportsAchievements;
        this.aboutMe = aboutMe;
        this.userId = userId;
        this.indMetricSys = indMetricSys;
        this.objective = objective;
        this.modality = modality;
        this.vo2Running = vo2Running;
        this.vo2Ciclismo = vo2Ciclismo;
        this.environmentId = environmentId;
        this.weatherId = weatherId;
    }

    public Integer getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Integer userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getIndPulsometer() {
        return indPulsometer;
    }

    public void setIndPulsometer(String indPulsometer) {
        this.indPulsometer = indPulsometer;
    }

    public String getIndPower() {
        return indPower;
    }

    public void setIndPower(String indPower) {
        this.indPower = indPower;
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

    public String getSportsAchievements() {
        return sportsAchievements;
    }

    public void setSportsAchievements(String sportsAchievements) {
        this.sportsAchievements = sportsAchievements;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIndMetricSys() {
        return indMetricSys;
    }

    public void setIndMetricSys(String indMetricSys) {
        this.indMetricSys = indMetricSys;
    }

    public Integer getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Integer discipline) {
        this.discipline = discipline;
    }

    public Integer getSport() {
        return sport;
    }

    public void setSport(Integer sport) {
        this.sport = sport;
    }

    public Integer getShoes() {
        return shoes;
    }

    public void setShoes(Integer shoes) {
        this.shoes = shoes;
    }

    public Integer getBikes() {
        return bikes;
    }

    public void setBikes(Integer bikes) {
        this.bikes = bikes;
    }

    public Integer getPotentiometer() {
        return potentiometer;
    }

    public void setPotentiometer(Integer potentiometer) {
        this.potentiometer = potentiometer;
    }

    public Integer getPulsometer() {
        return pulsometer;
    }

    public void setPulsometer(Integer pulsometer) {
        this.pulsometer = pulsometer;
    }

    public Integer getObjective() {
        return objective;
    }

    public void setObjective(Integer objective) {
        this.objective = objective;
    }

    public List<UserAvailabilityDTO> getAvailability() {
        return availability;
    }

    public void setAvailability(List<UserAvailabilityDTO> availability) {
        this.availability = availability;
    }

    public Integer getModality() {
        return modality;
    }

    public void setModality(Integer modality) {
        this.modality = modality;
    }

    public Integer getModelPotentiometer() {
        return modelPotentiometer;
    }

    public void setModelPotentiometer(Integer modelPotentiometer) {
        this.modelPotentiometer = modelPotentiometer;
    }

    public Integer getModelPulsometer() {
        return modelPulsometer;
    }

    public void setModelPulsometer(Integer modelPulsometer) {
        this.modelPulsometer = modelPulsometer;
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

    public String getOtherPotentiometer() {
        return otherPotentiometer;
    }

    public void setOtherPotentiometer(String otherPotentiometer) {
        this.otherPotentiometer = otherPotentiometer;
    }

    public String getOtherModelPotentiometer() {
        return otherModelPotentiometer;
    }

    public void setOtherModelPotentiometer(String otherModelPotentiometer) {
        this.otherModelPotentiometer = otherModelPotentiometer;
    }

    public String getOtherPulsometer() {
        return otherPulsometer;
    }

    public void setOtherPulsometer(String otherPulsometer) {
        this.otherPulsometer = otherPulsometer;
    }

    public String getOtherModelPulsometer() {
        return otherModelPulsometer;
    }

    public void setOtherModelPulsometer(String otherModelPulsometer) {
        this.otherModelPulsometer = otherModelPulsometer;
    }

    public Integer getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Integer environmentId) {
        this.environmentId = environmentId;
    }

    public Integer getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public Integer getModelBike() {
        return modelBike;
    }

    public void setModelBike(Integer modelBike) {
        this.modelBike = modelBike;
    }
    
    
    
}
