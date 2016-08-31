package co.com.expertla.training.model.entities;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
* UserZone <br>
* Creation Date : <br>
* date 29/08/2016 <br>
* @author Angela Ramírez
**/
@Entity
@Table(name = "user_zone")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserZone.findAll", query = "SELECT u FROM UserZone u"),
    @NamedQuery(name = "UserZone.findByUserZoneId", query = "SELECT u FROM UserZone u WHERE u.userZoneId = :userZoneId"),
    @NamedQuery(name = "UserZone.findByZoneTwo", query = "SELECT u FROM UserZone u WHERE u.zoneTwo = :zoneTwo"),
    @NamedQuery(name = "UserZone.findByZoneThree", query = "SELECT u FROM UserZone u WHERE u.zoneThree = :zoneThree"),
    @NamedQuery(name = "UserZone.findByZoneFour", query = "SELECT u FROM UserZone u WHERE u.zoneFour = :zoneFour"),
    @NamedQuery(name = "UserZone.findByZoneFive", query = "SELECT u FROM UserZone u WHERE u.zoneFive = :zoneFive"),
    @NamedQuery(name = "UserZone.findByZoneSix", query = "SELECT u FROM UserZone u WHERE u.zoneSix = :zoneSix")})
public class UserZone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "user_zone_seq", sequenceName = "user_zone_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_zone_seq")
    @Basic(optional = false)
    @Column(name = "user_zone_id")
    private Integer userZoneId;
    @Column(name = "zone_two")
    private String zoneTwo;
    @Column(name = "zone_three")
    private String zoneThree;
    @Column(name = "zone_four")
    private String zoneFour;
    @Column(name = "zone_five")
    private String zoneFive;
    @Column(name = "zone_six")
    private String zoneSix;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
    @Column(name = "zone_type")
    private String zoneType;

    public UserZone() {
    }

    public UserZone(Integer userZoneId) {
        this.userZoneId = userZoneId;
    }

    public Integer getUserZoneId() {
        return userZoneId;
    }

    public void setUserZoneId(Integer userZoneId) {
        this.userZoneId = userZoneId;
    }

    public String getZoneTwo() {
        return zoneTwo;
    }

    public void setZoneTwo(String zoneTwo) {
        this.zoneTwo = zoneTwo;
    }

    public String getZoneThree() {
        return zoneThree;
    }

    public void setZoneThree(String zoneThree) {
        this.zoneThree = zoneThree;
    }

    public String getZoneFour() {
        return zoneFour;
    }

    public void setZoneFour(String zoneFour) {
        this.zoneFour = zoneFour;
    }

    public String getZoneFive() {
        return zoneFive;
    }

    public void setZoneFive(String zoneFive) {
        this.zoneFive = zoneFive;
    }

    public String getZoneSix() {
        return zoneSix;
    }

    public void setZoneSix(String zoneSix) {
        this.zoneSix = zoneSix;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userZoneId != null ? userZoneId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserZone)) {
            return false;
        }
        UserZone other = (UserZone) object;
        if ((this.userZoneId == null && other.userZoneId != null) || (this.userZoneId != null && !this.userZoneId.equals(other.userZoneId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.UserZone[ userZoneId=" + userZoneId + " ]";
    }

    public String getZoneType() {
        return zoneType;
    }

    public void setZoneType(String zoneType) {
        this.zoneType = zoneType;
    }
    
}
