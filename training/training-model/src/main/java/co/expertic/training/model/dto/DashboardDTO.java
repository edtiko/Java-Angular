package co.expertic.training.model.dto;

import co.expertic.training.model.entities.City;
import co.expertic.training.model.entities.Country;
import co.expertic.training.model.entities.Environment;
import co.expertic.training.model.entities.Injury;
import co.expertic.training.model.entities.Weather;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashboardDTO {

    private Integer userId;
    private String name;
    private String secondName;
    private String lastName;
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    private String sex;
    private Float weight;
    private Float height;
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
    private Integer age;
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
    private String environment;
    private String twitterPage;
    private String instagramPage;
    private String webPage;
    private Integer vo2Running;
    private Integer vo2Ciclismo;
    private String injury;
    private String disease;
    private String weather;
    private Integer countSupervisor;
    private Integer countStar;
    private Integer imc;


    public DashboardDTO() {
    }

    public DashboardDTO(Integer userId, String name,String secondName, String lastName, String email, Date birthDate, 
            String sex, Float weight,Float height,
            String phone, String cellphone, String address, String postalCode, byte[] profilePhoto, String facebookPage,
            String indMetricSys, City city, String country, Integer ageSport, BigInteger ppm, BigInteger power, 
            String sportsAchievements, String aboutMe, String objective, String modality, String twitterPage, String instagramPage, 
            String webPage, Integer vo2Running, Integer vo2Ciclismo, Injury injury, String disease, Environment environment, Weather weather) {
        this.userId = userId;
        this.name = name;
        this.secondName = secondName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.sex = sex;
        this.weight = weight;
        this.height = height;
        this.phone = phone;
        this.cellphone = cellphone;
        this.address = address;
        this.postalCode = postalCode;
        this.profilePhoto = profilePhoto;
        this.facebookPage = facebookPage;
        this.indMetricSys = indMetricSys;
        this.country = country;
        this.ageSport = ageSport;
        this.ppm = ppm;
        this.power = power;
        this.sportsAchievements = sportsAchievements;
        this.aboutMe = aboutMe;
        this.objective = objective;
        this.modality = modality;
        this.twitterPage = twitterPage;
        this.instagramPage = instagramPage;
        this.webPage = webPage;
        this.vo2Running = vo2Running;
        this.vo2Ciclismo = vo2Ciclismo; 
        if (city != null) {
            this.city = city.getName();
            if (city.getFederalStateId() != null) {
                this.federalState = city.getFederalStateId().getName();
            }
        }
        if(injury != null){
            this.injury = injury.getName();
        }
        if(environment != null){
            this.environment = environment.getName();
        }
         if(weather != null){
            this.weather = weather.getName();
        }
        this.disease = disease;
        
    }
    
    public DashboardDTO(Integer userId, String name, String secondName, String lastName, String email, Date birthDate, String sex, Float weight,
            String phone, String cellphone, String address, 
            String postalCode, byte[] profilePhoto, String facebookPage,
            String indMetricSys, Country objCountry, City city) {
        this.userId = userId;
        this.name = name;
        this.secondName = secondName;
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
        if (city != null) {
            this.city = city.getName();
            if (city.getFederalStateId() != null) {
                this.federalState = city.getFederalStateId().getName();
            }
        }
        
        if(objCountry != null) {
            this.country = objCountry.getName();
        }
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

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
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

    public String getTwitterPage() {
        return twitterPage;
    }

    public void setTwitterPage(String twitterPage) {
        this.twitterPage = twitterPage;
    }

    public String getInstagramPage() {
        return instagramPage;
    }

    public void setInstagramPage(String instagramPage) {
        this.instagramPage = instagramPage;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
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

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getInjury() {
        return injury;
    }

    public void setInjury(String injury) {
        this.injury = injury;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Integer getCountSupervisor() {
        return countSupervisor;
    }

    public void setCountSupervisor(Integer countSupervisor) {
        this.countSupervisor = countSupervisor;
    }

    public Integer getCountStar() {
        return countStar;
    }

    public void setCountStar(Integer countStar) {
        this.countStar = countStar;
    }

    public Integer getAge() {
        if (this.birthDate != null) {
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.setTime(birthDate);
            Integer year = cal.get(Calendar.YEAR);
            Integer month = cal.get(Calendar.MONTH);
            Integer day = cal.get(Calendar.DAY_OF_MONTH);
            LocalDate today = LocalDate.now();
            LocalDate birthday = LocalDate.of(year, month+1, day);

            Period p = Period.between(birthday, today);
            this.age = p.getYears();
        }
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getImc() {
        if(this.weight != null && this.height != null){
            this.imc = Math.round((this.weight / (this.height * this.height)*10)/10);
        }
        return imc;
    }

    public void setImc(Integer imc) {
        this.imc = imc;
    }
    
    
    public String getFullName(){
        return this.name+" "+this.secondName+" "+this.lastName;
    }

}
