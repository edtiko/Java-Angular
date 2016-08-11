package co.com.expertla.training.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigInteger;
import java.util.Date;

public class DashboardDTO {

    private Integer userId;
    private String name;
    private String lastName;
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    private String sex;
    private BigInteger weight;
    private String phone;
    private String cellphone;
    private String address;
    private String postalCode;
    private byte[] profilePhoto;
    private String facebookPage;
    private String indMetricSys;
    private String city;
    private String federalState;
    private String country;
    private String discipline;
    private Integer ageSport;
    private BigInteger ppm;
    private BigInteger power;
    private String sportsAchievements;
    private String aboutMe;
    private String sport;
    private String shoes;
    private String bikes;
    private String potentiometer;
    private String modelPotentiometer;
    private String pulsometer;
    private String modelPulsometer;
    private String objective;
    private String availability;
    private String modality;


    public DashboardDTO() {
    }

    public DashboardDTO(Integer userId, String name, String lastName, String email, Date birthDate, String sex, BigInteger weight,
            String phone, String cellphone, String address, String postalCode, byte[] profilePhoto, String facebookPage,
            String indMetricSys, String city, String federalState, String country, Integer ageSport, BigInteger ppm, BigInteger power, 
            String sportsAchievements, String aboutMe, String objective, String modality) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.sex = sex;
        this.weight = weight;
        this.phone = phone;
        this.cellphone = cellphone;
        this.address = address;
        this.postalCode = postalCode;
        this.profilePhoto = profilePhoto;
        this.facebookPage = facebookPage;
        this.indMetricSys = indMetricSys;
        this.city = city;
        this.federalState = federalState;
        this.country = country;
        this.ageSport = ageSport;
        this.ppm = ppm;
        this.power = power;
        this.sportsAchievements = sportsAchievements;
        this.aboutMe = aboutMe;
        this.objective = objective;
        this.modality = modality;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public BigInteger getWeight() {
        return weight;
    }

    public void setWeight(BigInteger weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getFacebookPage() {
        return facebookPage;
    }

    public void setFacebookPage(String facebookPage) {
        this.facebookPage = facebookPage;
    }

    public String getIndMetricSys() {
        return indMetricSys;
    }

    public void setIndMetricSys(String indMetricSys) {
        this.indMetricSys = indMetricSys;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFederalState() {
        return federalState;
    }

    public void setFederalState(String federalState) {
        this.federalState = federalState;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
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

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getShoes() {
        return shoes;
    }

    public void setShoes(String shoes) {
        this.shoes = shoes;
    }

    public String getBikes() {
        return bikes;
    }

    public void setBikes(String bikes) {
        this.bikes = bikes;
    }

    public String getPotentiometer() {
        return potentiometer;
    }

    public void setPotentiometer(String potentiometer) {
        this.potentiometer = potentiometer;
    }

    public String getModelPotentiometer() {
        return modelPotentiometer;
    }

    public void setModelPotentiometer(String modelPotentiometer) {
        this.modelPotentiometer = modelPotentiometer;
    }

    public String getPulsometer() {
        return pulsometer;
    }

    public void setPulsometer(String pulsometer) {
        this.pulsometer = pulsometer;
    }

    public String getModelPulsometer() {
        return modelPulsometer;
    }

    public void setModelPulsometer(String modelPulsometer) {
        this.modelPulsometer = modelPulsometer;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    
}
