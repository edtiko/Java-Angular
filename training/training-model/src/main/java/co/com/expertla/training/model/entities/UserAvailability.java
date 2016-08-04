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
* UserAvailability <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
@Entity
@Table(name = "user_availability")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAvailability.findAll", query = "SELECT u FROM UserAvailability u"),
    @NamedQuery(name = "UserAvailability.findByUserAvailabilityId", query = "SELECT u FROM UserAvailability u WHERE u.userAvailabilityId = :userAvailabilityId"),
    @NamedQuery(name = "UserAvailability.findByMonday", query = "SELECT u FROM UserAvailability u WHERE u.monday = :monday"),
    @NamedQuery(name = "UserAvailability.findByTuesday", query = "SELECT u FROM UserAvailability u WHERE u.tuesday = :tuesday"),
    @NamedQuery(name = "UserAvailability.findByWednesday", query = "SELECT u FROM UserAvailability u WHERE u.wednesday = :wednesday"),
    @NamedQuery(name = "UserAvailability.findByThursday", query = "SELECT u FROM UserAvailability u WHERE u.thursday = :thursday"),
    @NamedQuery(name = "UserAvailability.findByFriday", query = "SELECT u FROM UserAvailability u WHERE u.friday = :friday"),
    @NamedQuery(name = "UserAvailability.findBySaturday", query = "SELECT u FROM UserAvailability u WHERE u.saturday = :saturday"),
    @NamedQuery(name = "UserAvailability.findBySunday", query = "SELECT u FROM UserAvailability u WHERE u.sunday = :sunday")})
public class UserAvailability implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "user_availability_user_availability_id_seq", sequenceName = "user_availability_user_availability_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_availability_user_availability_id_seq")
    @Column(name = "user_availability_id")
    private Integer userAvailabilityId;
    @Column(name = "monday")
    private Boolean monday;
    @Column(name = "tuesday")
    private Boolean tuesday;
    @Column(name = "wednesday")
    private Boolean wednesday;
    @Column(name = "thursday")
    private Boolean thursday;
    @Column(name = "friday")
    private Boolean friday;
    @Column(name = "saturday")
    private Boolean saturday;
    @Column(name = "sunday")
    private Boolean sunday;
    @JoinColumn(name = "user_profile_id", referencedColumnName = "user_profile_id")
    @ManyToOne
    private UserProfile userProfileId;

    public UserAvailability() {
    }

    public UserAvailability(Integer userAvailabilityId) {
        this.userAvailabilityId = userAvailabilityId;
    }

    public Integer getUserAvailabilityId() {
        return userAvailabilityId;
    }

    public void setUserAvailabilityId(Integer userAvailabilityId) {
        this.userAvailabilityId = userAvailabilityId;
    }

    public Boolean getMonday() {
        return monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    public Boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    public Boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    public Boolean getThursday() {
        return thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    public Boolean getFriday() {
        return friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    public Boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
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
        hash += (userAvailabilityId != null ? userAvailabilityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAvailability)) {
            return false;
        }
        UserAvailability other = (UserAvailability) object;
        if ((this.userAvailabilityId == null && other.userAvailabilityId != null) || (this.userAvailabilityId != null && !this.userAvailabilityId.equals(other.userAvailabilityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.UserAvailability[ userAvailabilityId=" + userAvailabilityId + " ]";
    }

}
