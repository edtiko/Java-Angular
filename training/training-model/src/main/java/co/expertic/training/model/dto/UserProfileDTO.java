package co.expertic.training.model.dto;

import co.expertic.training.model.entities.Injury;
import co.expertic.training.model.entities.Modality;
import co.expertic.training.model.entities.TrainingLevel;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
    private String  disciplineName;
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
    private String otherBike;
    private String otherModelBike;
    private Integer bikeType;
    
    private BigInteger ftp0;
    private BigInteger ftp129;
    private BigInteger ftp114;
    private BigInteger ftp106;
    private BigInteger ftp100;
    private BigInteger ftp97;
    private BigInteger ftp90;
    
    private BigInteger ppm0;
    private BigInteger ppm81;
    private BigInteger ppm82;
    private BigInteger ppm89;
    private BigInteger ppm90;
    private BigInteger ppm93;
    private BigInteger ppm94;
    private BigInteger ppm99;
    private BigInteger ppm100;
    private BigInteger ppm102;
    private BigInteger ppm103;
    private BigInteger ppm106;
    private Float weight;
    private Float height;
    private Integer injuryId;
    private String disease;
    private Integer availableTime;
    private Double testDistance;
    private Double testDistanceN;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    public UserProfileDTO() {
    }
    
    public UserProfileDTO(Integer userProfileId, String indPulsometer, String indPower, Integer ageSport, BigInteger ppm,
            BigInteger power, String sportsAchievements, String aboutMe, Integer userId, String indMetricSys, TrainingLevel objective,
            Modality modality, Integer vo2Running, Integer vo2Ciclismo, Integer environmentId, Integer weatherId,
            Float weight, Float height, Injury injury, String disease, Integer availableTime, Date competenceDate, Double testDistance, Double testDistanceN) {
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
        if(modality != null){
        this.modality = modality.getModalityId();
        }
        if(objective != null) {
            this.objective = objective.getTrainingLevelId();
        }
        this.vo2Running = vo2Running;
        this.vo2Ciclismo = vo2Ciclismo;
        this.environmentId = environmentId;
        this.weatherId = weatherId;
        this.weight = weight;
        this.height = height;
        this.availableTime = availableTime;
        this.endDate = competenceDate;
        if(injury != null){
        this.injuryId = injury.getInjuryId();
        }
        this.disease = disease;
        this.testDistance = testDistance;
        this.testDistanceN = testDistanceN;
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

    public String getOtherBike() {
        return otherBike;
    }

    public void setOtherBike(String otherBike) {
        this.otherBike = otherBike;
    }

    public String getOtherModelBike() {
        return otherModelBike;
    }

    public void setOtherModelBike(String otherModelBike) {
        this.otherModelBike = otherModelBike;
    }

    public Integer getBikeType() {
        return bikeType;
    }

    public void setBikeType(Integer bikeType) {
        this.bikeType = bikeType;
    }

    public BigInteger getFtp0() {
        return ftp0;
    }

    public void setFtp0(BigInteger ftp0) {
        this.ftp0 = ftp0;
    }

    public BigInteger getFtp129() {
        return ftp129;
    }

    public void setFtp129(BigInteger ftp129) {
        this.ftp129 = ftp129;
    }

    public BigInteger getFtp114() {
        return ftp114;
    }

    public void setFtp114(BigInteger ftp114) {
        this.ftp114 = ftp114;
    }

    public BigInteger getFtp100() {
        return ftp100;
    }

    public void setFtp100(BigInteger ftp100) {
        this.ftp100 = ftp100;
    }

    public BigInteger getFtp97() {
        return ftp97;
    }

    public void setFtp97(BigInteger ftp97) {
        this.ftp97 = ftp97;
    }

    public BigInteger getFtp90() {
        return ftp90;
    }

    public void setFtp90(BigInteger ftp90) {
        this.ftp90 = ftp90;
    }

    public BigInteger getFtp106() {
        return ftp106;
    }

    public void setFtp106(BigInteger ftp106) {
        this.ftp106 = ftp106;
    }

    public BigInteger getPpm81() {
        return ppm81;
    }

    public void setPpm81(BigInteger ppm81) {
        this.ppm81 = ppm81;
    }

    public BigInteger getPpm89() {
        return ppm89;
    }

    public void setPpm89(BigInteger ppm89) {
        this.ppm89 = ppm89;
    }

    public BigInteger getPpm90() {
        return ppm90;
    }

    public void setPpm90(BigInteger ppm90) {
        this.ppm90 = ppm90;
    }

    public BigInteger getPpm93() {
        return ppm93;
    }

    public void setPpm93(BigInteger ppm93) {
        this.ppm93 = ppm93;
    }

    public BigInteger getPpm94() {
        return ppm94;
    }

    public void setPpm94(BigInteger ppm94) {
        this.ppm94 = ppm94;
    }

    public BigInteger getPpm99() {
        return ppm99;
    }

    public void setPpm99(BigInteger ppm99) {
        this.ppm99 = ppm99;
    }

    public BigInteger getPpm100() {
        return ppm100;
    }

    public void setPpm100(BigInteger ppm100) {
        this.ppm100 = ppm100;
    }

    public BigInteger getPpm102() {
        return ppm102;
    }

    public void setPpm102(BigInteger ppm102) {
        this.ppm102 = ppm102;
    }

    public BigInteger getPpm103() {
        return ppm103;
    }

    public void setPpm103(BigInteger ppm103) {
        this.ppm103 = ppm103;
    }

    public BigInteger getPpm106() {
        return ppm106;
    }

    public void setPpm106(BigInteger ppm106) {
        this.ppm106 = ppm106;
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

    public Integer getInjuryId() {
        return injuryId;
    }

    public void setInjuryId(Integer injuryId) {
        this.injuryId = injuryId;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Integer getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(Integer availableTime) {
        this.availableTime = availableTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigInteger getPpm82() {
        return ppm82;
    }

    public void setPpm82(BigInteger ppm82) {
        this.ppm82 = ppm82;
    }

    public BigInteger getPpm0() {
        return ppm0;
    }

    public void setPpm0(BigInteger ppm0) {
        this.ppm0 = ppm0;
    }

    public Double getTestDistance() {
        return testDistance;
    }

    public void setTestDistance(Double testDistance) {
        this.testDistance = testDistance;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public Double getTestDistanceN() {
        return testDistanceN;
    }

    public void setTestDistanceN(Double testDistanceN) {
        this.testDistanceN = testDistanceN;
    }
    
    
        
}
