package co.com.expertla.training.model.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "user_profile")
@NamedQueries({
    @NamedQuery(name = "UserProfile.findAll", query = "SELECT u FROM UserProfile u"),
    @NamedQuery(name = "UserProfile.findByUserProfileId", query = "SELECT u FROM UserProfile u WHERE u.userProfileId = :userProfileId"),
    @NamedQuery(name = "UserProfile.findByIndPulsometer", query = "SELECT u FROM UserProfile u WHERE u.indPulsometer = :indPulsometer"),
    @NamedQuery(name = "UserProfile.findByIndPower", query = "SELECT u FROM UserProfile u WHERE u.indPower = :indPower"),
    @NamedQuery(name = "UserProfile.findByAgeSport", query = "SELECT u FROM UserProfile u WHERE u.ageSport = :ageSport"),
    @NamedQuery(name = "UserProfile.findByPpm", query = "SELECT u FROM UserProfile u WHERE u.ppm = :ppm"),
    @NamedQuery(name = "UserProfile.findByPower", query = "SELECT u FROM UserProfile u WHERE u.power = :power"),
    @NamedQuery(name = "UserProfile.findBySportsAchievements", query = "SELECT u FROM UserProfile u WHERE u.sportsAchievements = :sportsAchievements"),
    @NamedQuery(name = "UserProfile.findByAboutMe", query = "SELECT u FROM UserProfile u WHERE u.aboutMe = :aboutMe")})
public class UserProfile implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "user_profile_user_profile_id_seq", sequenceName = "user_profile_user_profile_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_user_profile_id_seq")
    @Column(name = "user_profile_id")
    private Integer userProfileId;
    @Column(name = "ind_pulsometer")
    private String indPulsometer;
    @Column(name = "ind_power")
    private String indPower;
    @Column(name = "age_sport")
    private Integer ageSport;
    @Column(name = "ppm")
    private BigInteger ppm;
    @Column(name = "vo2_running")
    private Integer vo2Running;
    @Column(name = "vo2_ciclismo")
    private Integer vo2Ciclismo;
    @Column(name = "power")
    private BigInteger power;
    @Column(name = "sports_achievements")
    private String sportsAchievements;
    @Column(name = "about_me")
    private String aboutMe;
    @JoinColumn(name = "objective_id", referencedColumnName = "objective_id")
    @ManyToOne
    private Objective objectiveId;
    
    @JoinColumn(name = "weather_id", referencedColumnName = "weather_id")
    @ManyToOne
    private Weather weatherId;
    
    @JoinColumn(name = "environment_id", referencedColumnName = "environment_id")
    @ManyToOne
    private Environment environmentId;
    
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
    @JoinColumn(name = "modality_id", referencedColumnName = "modality_id")
    @ManyToOne
    private Modality modalityId;
    @OneToMany(mappedBy = "userProfileId")
    private Collection<UserSport> userSportCollection;
    @OneToMany(mappedBy = "userProfileId")
    private Collection<EquipmentUserProfile> equipmentUserProfileCollection;
    @OneToMany(mappedBy = "userProfileId")
    private Collection<UserAvailability> userAvailabilityCollection;

    public UserProfile() {
    }

    public UserProfile(Integer userProfileId) {
        this.userProfileId = userProfileId;
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

    public Objective getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(Objective objectiveId) {
        this.objectiveId = objectiveId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Weather getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Weather weatherId) {
        this.weatherId = weatherId;
    }

    public Environment getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Environment environmentId) {
        this.environmentId = environmentId;
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
    
    public Collection<UserSport> getUserSportCollection() {
        return userSportCollection;
    }

    public void setUserSportCollection(Collection<UserSport> userSportCollection) {
        this.userSportCollection = userSportCollection;
    }

    public Collection<EquipmentUserProfile> getEquipmentUserProfileCollection() {
        return equipmentUserProfileCollection;
    }

    public void setEquipmentUserProfileCollection(Collection<EquipmentUserProfile> equipmentUserProfileCollection) {
        this.equipmentUserProfileCollection = equipmentUserProfileCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userProfileId != null ? userProfileId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserProfile)) {
            return false;
        }
        UserProfile other = (UserProfile) object;
        if ((this.userProfileId == null && other.userProfileId != null) || (this.userProfileId != null && !this.userProfileId.equals(other.userProfileId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.UserProfile[ userProfileId=" + userProfileId + " ]";
    }



    @XmlTransient
    public Collection<UserAvailability> getUserAvailabilityCollection() {
        return userAvailabilityCollection;
    }

    public void setUserAvailabilityCollection(Collection<UserAvailability> userAvailabilityCollection) {
        this.userAvailabilityCollection = userAvailabilityCollection;
    }

    public Modality getModalityId() {
        return modalityId;
    }

    public void setModalityId(Modality modalityId) {
        this.modalityId = modalityId;

    }
    
}
