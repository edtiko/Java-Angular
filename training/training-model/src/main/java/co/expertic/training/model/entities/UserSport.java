package co.expertic.training.model.entities;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "user_sport")
@NamedQueries({
    @NamedQuery(name = "UserSport.findAll", query = "SELECT u FROM UserSport u"),
    @NamedQuery(name = "UserSport.findByUserSportId", query = "SELECT u FROM UserSport u WHERE u.userSportId = :userSportId")})
public class UserSport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)@SequenceGenerator(name = "user_sport_seq", sequenceName = "user_sport_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sport_seq")
    @Column(name = "user_sport_id")
    private Integer userSportId;
    @JoinColumn(name = "sport_id", referencedColumnName = "sport_id")
    @ManyToOne
    private Sport sportId;
    @JoinColumn(name = "user_profile_id", referencedColumnName = "user_profile_id")
    @ManyToOne
    private UserProfile userProfileId;

    public UserSport() {
    }

    public UserSport(Integer userSportId) {
        this.userSportId = userSportId;
    }

    public Integer getUserSportId() {
        return userSportId;
    }

    public void setUserSportId(Integer userSportId) {
        this.userSportId = userSportId;
    }

    public Sport getSportId() {
        return sportId;
    }

    public void setSportId(Sport sportId) {
        this.sportId = sportId;
    }

    public UserProfile getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(UserProfile userProfileId) {
        this.userProfileId = userProfileId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userSportId != null ? userSportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserSport)) {
            return false;
        }
        UserSport other = (UserSport) object;
        if ((this.userSportId == null && other.userSportId != null) || (this.userSportId != null && !this.userSportId.equals(other.userSportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.UserSport[ userSportId=" + userSportId + " ]";
    }
    
}
