package co.expertic.training.model.dto;

import co.expertic.training.model.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

/**
 *
 * @author Edwin G
 */
public class UserMovilDTO {

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
    private Integer disciplineId;
    private String disciplineName;
    private String typeUser;
    private Integer roleId;
    private String fullName;
    private Integer planActiveId;
    private Integer trainingPlanUserId;
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
        this.email = email;
        this.sex = sex;
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

}
