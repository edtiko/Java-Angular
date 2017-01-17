package co.expertic.training.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author Edwin G
 */
public class UserBasicMovilDTO {

    private static final Logger LOGGER = Logger.getLogger(UserBasicMovilDTO.class);
    private Integer userId;
    private String login;
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
    private String urlVideo;
    private String fullName;
    private String tipoPlan;
    private Integer communicatePlanId;

    public UserBasicMovilDTO() {
    }
    
    public String getFullName() {
        if(this.secondName != null) {
            return this.fullName = this.firstName + " " + this.secondName + " " + this.lastName;
        }
        return this.fullName = this.firstName + " " + this.lastName;        
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public Integer getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Integer disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }


}
