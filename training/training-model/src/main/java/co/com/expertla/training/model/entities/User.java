package co.com.expertla.training.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "user_training")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId"),
    @NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login = :login"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name"),
    @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByBirthDate", query = "SELECT u FROM User u WHERE u.birthDate = :birthDate"),
    @NamedQuery(name = "User.findBySex", query = "SELECT u FROM User u WHERE u.sex = :sex"),
    @NamedQuery(name = "User.findByWeight", query = "SELECT u FROM User u WHERE u.weight = :weight"),
    @NamedQuery(name = "User.findByPhone", query = "SELECT u FROM User u WHERE u.phone = :phone"),
    @NamedQuery(name = "User.findByCellphone", query = "SELECT u FROM User u WHERE u.cellphone = :cellphone"),
    @NamedQuery(name = "User.findByAddress", query = "SELECT u FROM User u WHERE u.address = :address"),
    @NamedQuery(name = "User.findByPostalCode", query = "SELECT u FROM User u WHERE u.postalCode = :postalCode"),
    @NamedQuery(name = "User.findByFacebookPage", query = "SELECT u FROM User u WHERE u.facebookPage = :facebookPage"),
    @NamedQuery(name = "User.findByIndMetricSys", query = "SELECT u FROM User u WHERE u.indMetricSys = :indMetricSys"),
    @NamedQuery(name = "User.findByCreationDate", query = "SELECT u FROM User u WHERE u.creationDate = :creationDate")})
public class User implements Serializable {

    @Column(name = "state_id")
    private Short stateId;
    @Lob
    @Column(name = "profile_photo")
    private byte[] profilePhoto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "starUserId")
    private Collection<StarTeam> startTeamCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coachUserId")
    private Collection<StarTeam> startTeamCollection1;
    @OneToMany(mappedBy = "userId")
    private Collection<UserZone> userZoneCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "user_training_user_id_seq", sequenceName = "user_training_user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_training_user_id_seq")
    @Column(name = "user_id", updatable = false)
    private Integer userId;
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Column(name = "sex")
    private String sex;
    @Column(name = "weight")
    private BigInteger weight;
    @Column(name = "phone")
    private String phone;
    @Column(name = "cellphone")
    private String cellphone;
    @Column(name = "address")
    private String address;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "facebook_page")
    private String facebookPage;
    @Column(name = "twitter_page")
    private String twitterPage;
    @Column(name = "instagram_page")
    private String instagramPage;
    @Column(name = "web_page")
    private String webPage;
    @Basic(optional = false)
    @Column(name = "ind_metric_sys")
    private String indMetricSys;
    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @OneToMany(mappedBy = "userId")
    private Collection<QuestionnaireResponse> questionnaireResponseCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<TrainingPlanUser> trainingPlanUserCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<UserProfile> userProfileCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<VideoUser> videoUserCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<DisciplineUser> disciplineUserCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<RoleUser> roleUserCollection;
    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    @ManyToOne
    private City cityId;
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    @ManyToOne
    private Country countryId;

    @OneToMany(mappedBy = "starId")
    private Collection<User> userCollection;
    @JoinColumn(name = "star_id", referencedColumnName = "user_id")
    @ManyToOne
    private User starId;
    @Column(name = "last_update")
    @Temporal(TemporalType.DATE)
    private Date lastUpdate;
    @Column(name = "user_create")
    private Integer userCreate;
    @Column(name = "user_update")
    private Integer userUpdate;
    @Column(name = "user_wordpress_id")
    private Integer userWordpressId;
    @Column(name = "ind_login_first_time")
    private Integer indLoginFirstTime;

    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public User(Integer userId, String name, String lastName, String email, Date birthDate, String address,
            String sex, BigInteger weight, String phone, String cellphone, City cityId,
            Short stateId, String login, String password, String facebookPage, String postalCode, Date creationDate) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.name = name;
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
        this.postalCode = postalCode;
        this.creationDate = creationDate;

    }

    public Country getCountryId() {
        return countryId;
    }

    public void setCountryId(Country countryId) {
        this.countryId = countryId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
    public String getIndMetricSys() {
        return indMetricSys;
    }

    public void setIndMetricSys(String indMetricSys) {
        this.indMetricSys = indMetricSys;
    }

    public User getStarId() {
        return starId;
    }

    public void setStarId(User starId) {
        this.starId = starId;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    @JsonIgnore
    public Collection<QuestionnaireResponse> getQuestionnaireResponseCollection() {
        return questionnaireResponseCollection;
    }

    public void setQuestionnaireResponseCollection(Collection<QuestionnaireResponse> questionnaireResponseCollection) {
        this.questionnaireResponseCollection = questionnaireResponseCollection;
    }
    @JsonIgnore
    public Collection<TrainingPlanUser> getTrainingPlanUserCollection() {
        return trainingPlanUserCollection;
    }

    public void setTrainingPlanUserCollection(Collection<TrainingPlanUser> trainingPlanUserCollection) {
        this.trainingPlanUserCollection = trainingPlanUserCollection;
    }
    @JsonIgnore
    public Collection<UserProfile> getUserProfileCollection() {
        return userProfileCollection;
    }

    public void setUserProfileCollection(Collection<UserProfile> userProfileCollection) {
        this.userProfileCollection = userProfileCollection;
    }
    @JsonIgnore
    public Collection<VideoUser> getVideoUserCollection() {
        return videoUserCollection;
    }

    public void setVideoUserCollection(Collection<VideoUser> videoUserCollection) {
        this.videoUserCollection = videoUserCollection;
    }
    @JsonIgnore
    public Collection<DisciplineUser> getDisciplineUserCollection() {
        return disciplineUserCollection;
    }

    public void setDisciplineUserCollection(Collection<DisciplineUser> disciplineUserCollection) {
        this.disciplineUserCollection = disciplineUserCollection;
    }
    @JsonIgnore
    public Collection<RoleUser> getRoleUserCollection() {
        return roleUserCollection;
    }

    public void setRoleUserCollection(Collection<RoleUser> roleUserCollection) {
        this.roleUserCollection = roleUserCollection;
    }

    public City getCityId() {
        return cityId;
    }

    public void setCityId(City cityId) {
        this.cityId = cityId;
    }

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }
    @JsonIgnore
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.User[ userId=" + userId + " ]";
    }

    @JsonIgnore
    public Collection<StarTeam> getStartTeamCollection() {
        return startTeamCollection;
    }

    public void setStartTeamCollection(Collection<StarTeam> startTeamCollection) {
        this.startTeamCollection = startTeamCollection;
    }
    @JsonIgnore
    public Collection<StarTeam> getStartTeamCollection1() {
        return startTeamCollection1;
    }

    public void setStartTeamCollection1(Collection<StarTeam> startTeamCollection1) {
        this.startTeamCollection1 = startTeamCollection1;
    }
    @JsonIgnore
    public Collection<UserZone> getUserZoneCollection() {
        return userZoneCollection;
    }

    public void setUserZoneCollection(Collection<UserZone> userZoneCollection) {
        this.userZoneCollection = userZoneCollection;
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

}
