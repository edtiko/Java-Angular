package co.expertic.training.model.dto;

import co.expertic.training.model.entities.User;
import co.expertic.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author Edwin G
 */
public class UserMovilDTO {

    private static final Logger LOGGER = Logger.getLogger(UserMovilDTO.class);
    private Integer userId;
    private String login;
    private String password;
    private String firstName;
    private String secondName;
    private String lastName;
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    private String sex;
    private String phone;
    private String cellphone;
    private String address;
    private String postalCode;
    private String facebookPage;
    private String instagramPage;
    private String twitterPage;
    private String webPage;
    private String indMetricSys;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date creationDate;
    private Integer cityId;
    private String cityName;
    private Short stateId;
    private Integer federalStateId;
    private String federalStateName;
    private Integer countryId;
    private String countryName;
    private Integer disciplineId;
    private String disciplineName;
    private String typeUser;
    private Integer roleId;
    private String fullName;
    private Integer planActiveId;
    private Integer trainingPlanUserId;
    private UserBasicMovilDTO starUser;
    private UserBasicMovilDTO coachUser;
    @JsonIgnore
    private UserProfileMovilDTO userProfile;
    private String planType;
    private Integer communicationPlanId;

    public UserMovilDTO() {
    }

    //constructor usado por mapFromUserEntity
    public UserMovilDTO(Integer userId, String firstName, String secondName, String lastName, String email, Date birthDate, String address,
            String sex, String phone, String cellphone, Integer cityId,
            Short stateId, String login, String facebookPage, String instagramPage, String twitterPage,
            String webPage, String postalCode, Integer federalStateId, Integer countryId) {

        this.userId = userId;
        this.login = login;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.cellphone = cellphone;
        this.cityId = cityId;
        this.stateId = stateId;
        this.facebookPage = facebookPage;
        this.instagramPage = instagramPage;
        this.twitterPage = twitterPage;
        this.webPage = webPage;
        this.postalCode = postalCode;
        this.federalStateId = federalStateId;
        this.countryId = countryId;
    }

    public static UserMovilDTO mapFromUserEntity(User user) {
        if (user != null) {
            return new UserMovilDTO(user.getUserId(), user.getName(), user.getSecondName(), user.getLastName(), user.getEmail(), user.getBirthDate(), user.getAddress(),
                    user.getSex(), user.getPhone(), user.getCellphone(), (user.getCityId() != null ? user.getCityId().getCityId() : null),
                    user.getStateId(), user.getLogin(), user.getFacebookPage(), user.getInstagramPage(), user.getTwitterPage(), user.getWebPage(), user.getPostalCode(),
                    user.getCityId() != null ? user.getCityId().getFederalStateId().getFederalStateId() : null,
                    user.getCountryId() != null ? user.getCountryId().getCountryId() : null);
        }
        return null;
    }

    public UserBasicMovilDTO getStarUser() {
        return starUser;
    }

    public void setStarUser(UserBasicMovilDTO starUser) {
        this.starUser = starUser;
    }

    public UserBasicMovilDTO getCoachUser() {
        return coachUser;
    }

    public void setCoachUser(UserBasicMovilDTO coachUser) {
        this.coachUser = coachUser;
    }

    public Integer getPlanActiveId() {
        return planActiveId;
    }

    public void setPlanActiveId(Integer planActiveId) {
        this.planActiveId = planActiveId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public Integer getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Integer disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
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

    public String getFacebookPage() {
        return facebookPage;
    }

    public void setFacebookPage(String facebookPage) {
        this.facebookPage = facebookPage;
    }

    public String getInstagramPage() {
        return instagramPage;
    }

    public void setInstagramPage(String instagramPage) {
        this.instagramPage = instagramPage;
    }

    public String getTwitterPage() {
        return twitterPage;
    }

    public void setTwitterPage(String twitterPage) {
        this.twitterPage = twitterPage;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getIndMetricSys() {
        return indMetricSys;
    }

    public void setIndMetricSys(String indMetricSys) {
        this.indMetricSys = indMetricSys;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    public Integer getFederalStateId() {
        return federalStateId;
    }

    public void setFederalStateId(Integer federalStateId) {
        this.federalStateId = federalStateId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getFullName() {
        if (this.secondName != null) {
            return this.fullName = this.firstName + " " + this.secondName + " " + this.lastName;
        }
        return this.fullName = this.firstName + " " + this.lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;

    }

    public Integer getTrainingPlanUserId() {
        return trainingPlanUserId;
    }

    public void setTrainingPlanUserId(Integer trainingPlanUserId) {
        this.trainingPlanUserId = trainingPlanUserId;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public Integer getCommunicationPlanId() {
        return communicationPlanId;
    }

    public void setCommunicationPlanId(Integer communicationPlanId) {
        this.communicationPlanId = communicationPlanId;
    }

    public UserProfileMovilDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileMovilDTO userProfile) {
        this.userProfile = userProfile;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getFederalStateName() {
        return federalStateName;
    }

    public void setFederalStateName(String federalStateName) {
        this.federalStateName = federalStateName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
