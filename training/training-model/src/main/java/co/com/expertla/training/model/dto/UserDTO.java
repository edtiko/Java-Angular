package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 *
 * @author Edwin G
 */
public class UserDTO {
    private static final Logger LOGGER = Logger.getLogger(UserDTO.class);
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
    private Float weight;
    private String phone;
    private String cellphone;
    private String address;
    private String postalCode;
    private byte[] profilePhoto;
    private String facebookPage;
    private String instagramPage;
    private String twitterPage;
    private String webPage;
    private String indMetricSys;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date creationDate;
    private Integer cityId;
    private Short stateId;
    private Integer federalStateId;
    private Integer countryId;
    private Integer disciplineId;
    private String  disciplineName;
    private String typeUser;
    private Integer roleId;
    private String aboutMe;
    private String urlVideo;
    private String fullName;
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date lastUpdate;
    private Integer userCreate;
    private Integer userUpdate;
    private String userCreateName;
    private String userUpdateName;
    private int count;
    private Integer disciplineIdExt;
    private Integer userWordpressId;
    private Integer indLoginFirstTime;
    private Integer planActiveId;
    private Integer trainingPlanUserId;
    
    public UserDTO() {
    }

    //Constructor usado en findUserWithDiscipline
    public UserDTO(Integer userId, String login, String firstName, String secondName, String lastName, String email, String sex, 
            String phone, Integer disciplineId, Short stateId, Integer roleId, Integer countryId, byte[] profilePhoto, String urlVideo, String aboutMe) {
        this.userId = userId;
        this.login = login;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.disciplineId = disciplineId;
        this.stateId = stateId;
        this.roleId = roleId;
        this.countryId = countryId;
        this.profilePhoto = profilePhoto;
        this.urlVideo = urlVideo;
        this.aboutMe = aboutMe;
    }
    
    //Constructor usado en findPaginate
    public UserDTO(Integer userId, String login, String firstName, String secondName, String lastName, String email, String sex, 
            String phone, Integer disciplineId, Short stateId, Integer roleId, Integer countryId, byte[] profilePhoto, String urlVideo, String aboutMe,
            Date creationDate, Date lastUpdate, String userCreateName, String userUpdateName,Integer userCreate, Integer userUpdate) {
        this.userId = userId;
        this.login = login;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.disciplineId = disciplineId;
        this.stateId = stateId;
        this.roleId = roleId;
        this.countryId = countryId;
        this.profilePhoto = profilePhoto;
        this.urlVideo = urlVideo;
        this.aboutMe = aboutMe;
        this.userCreate = userCreate;
        this.creationDate = creationDate;
        this.userUpdateName = userUpdateName;
        this.userUpdate = userUpdate;
        this.userCreateName = userCreateName;
        this.lastUpdate = lastUpdate;
    }

    public UserDTO(Integer userId, String firstName, String secondName, String lastName, String email, Date birthDate, String address,
            String sex, Float weight, String phone, String cellphone, Integer cityId,
            Short stateId, String login, String facebookPage, String instagramPage, String twitterPage, 
            String webPage, String postalCode, Integer federalStateId, Integer countryId, byte[] profilePhoto) {
        this.userId = userId;
        this.login = login;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.sex = sex;
        this.weight = weight;
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
        this.profilePhoto = profilePhoto;

    }
    
    public UserDTO(Integer userId, String login, String firstName, String secondName, String lastName, String email, String sex, 
            String phone, Integer disciplineId, Integer disciplineIdExt, 
            Short stateId, Integer roleId, Integer countryId, byte[] profilePhoto,
            String urlVideo, String aboutMe) {
        this.userId = userId;
        this.login = login;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.disciplineId = disciplineId;
        this.stateId = stateId;
        this.roleId = roleId;
        this.countryId = countryId;
        this.profilePhoto = profilePhoto;
        this.urlVideo = urlVideo;
        this.aboutMe = aboutMe;
        this.disciplineIdExt = disciplineIdExt;
    }
    
    //constructor usado por mapFromUserEntity
    public UserDTO(Integer userId, String firstName, String secondName, String lastName, String email, Date birthDate, String address,
            String sex, Float weight, String phone, String cellphone, Integer cityId,
            Short stateId, String login, String facebookPage, String instagramPage, String twitterPage, 
            String webPage, String postalCode, Integer federalStateId, Integer countryId, byte[] profilePhoto,
            Integer indLoginFirstTime, Integer userWordpressId) {
        this.userId = userId;
        this.login = login;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.sex = sex;
        this.weight = weight;
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
        this.profilePhoto = profilePhoto;
        this.indLoginFirstTime = indLoginFirstTime;
        this.userWordpressId = userWordpressId;
    }
    
    public UserDTO(Integer userId, String firstName, String secondName, String lastName, String email, String login, byte[] profilePhoto){
        this.userId = userId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.profilePhoto = profilePhoto;
    }

    public static UserDTO mapFromUserEntity(User user) {
        if (user != null) {
            return new UserDTO(user.getUserId(), user.getName(), user.getSecondName(), user.getLastName(), user.getEmail(), user.getBirthDate(), user.getAddress(),
                    user.getSex(), user.getWeight(), user.getPhone(), user.getCellphone(), (user.getCityId() != null ? user.getCityId().getCityId() : null),
                    user.getStateId(), user.getLogin(), user.getFacebookPage(), user.getInstagramPage(), user.getTwitterPage(), user.getWebPage(), user.getPostalCode(),
                    user.getCityId() != null ? user.getCityId().getFederalStateId().getFederalStateId() : null,
                    user.getCountryId() != null ? user.getCountryId().getCountryId() : null,
                    user.getProfilePhoto(), user.getIndLoginFirstTime(), user.getUserWordpressId());
        }
        return null;
    }

    public static List<UserDTO> mapFromUsersEntities(List<User> users) {
        return users.stream().map((user) -> mapFromUserEntity(user)).collect(Collectors.toList());
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

    public String getProfilePhotoBase64() {
        String base64Encoded = "";

        if (this.profilePhoto != null) {
            try {
                byte[] encodeBase64 = Base64.encodeBase64(this.profilePhoto);
                base64Encoded = new String(encodeBase64, "UTF-8");
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                return null;
            }
        }
        if(!"".equals(base64Encoded)){
        base64Encoded = "data:image/png;base64," + base64Encoded; 
        }else{
            base64Encoded = "static/img/profile-default.png";
        }
        
        return base64Encoded;
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

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getFullName() {
        return this.firstName+" "+this.secondName+" "+this.lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;

    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(Integer userCreate) {
        this.userCreate = userCreate;
    }

    public Integer getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(Integer userUpdate) {
        this.userUpdate = userUpdate;
    }

    public String getUserCreateName() {
        return userCreateName;
    }

    public void setUserCreateName(String userCreateName) {
        this.userCreateName = userCreateName;
    }

    public String getUserUpdateName() {
        return userUpdateName;
    }

    public void setUserUpdateName(String userUpdateName) {
        this.userUpdateName = userUpdateName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

	public Integer getDisciplineIdExt() {
        return disciplineIdExt;
    }

    public void setDisciplineIdExt(Integer disciplineIdExt) {
        this.disciplineIdExt = disciplineIdExt;
    }

    public Integer getUserWordpressId() {
        return userWordpressId;
    }

    public void setUserWordpressId(Integer userWordpressId) {
        this.userWordpressId = userWordpressId;
    }

    public Integer getIndLoginFirstTime() {
        return indLoginFirstTime;
    }

    public void setIndLoginFirstTime(Integer indLoginFirstTime) {
        this.indLoginFirstTime = indLoginFirstTime;
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
    
    
    
}
