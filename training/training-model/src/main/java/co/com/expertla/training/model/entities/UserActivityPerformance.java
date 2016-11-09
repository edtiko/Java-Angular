package co.com.expertla.training.model.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
* UserActivityPerformance <br>
* Creation Date : <br>
* date 15/09/2016 <br>
* @author Angela Ramírez
**/
@Entity
@Table(name = "user_activity_performance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserActivityPerformance.findAll", query = "SELECT u FROM UserActivityPerformance u"),
    @NamedQuery(name = "UserActivityPerformance.findByUserActivityPerformanceId", query = "SELECT u FROM UserActivityPerformance u WHERE u.userActivityPerformanceId = :userActivityPerformanceId"),
    @NamedQuery(name = "UserActivityPerformance.findByValue", query = "SELECT u FROM UserActivityPerformance u WHERE u.value = :value"),
    @NamedQuery(name = "UserActivityPerformance.findByExecutedDate", query = "SELECT u FROM UserActivityPerformance u WHERE u.executedDate = :executedDate"),
    @NamedQuery(name = "UserActivityPerformance.findByCreationDate", query = "SELECT u FROM UserActivityPerformance u WHERE u.creationDate = :creationDate")})
public class UserActivityPerformance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "user_activity_performance_user_activity_performance_id_seq", sequenceName = "user_activity_performance_user_activity_performance_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_activity_performance_user_activity_performance_id_seq")
    @Basic(optional = false)
    @Column(name = "user_activity_performance_id")
    private Integer userActivityPerformanceId;
    @Column(name = "value")
    private String value;
    @Column(name = "executed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date executedDate;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @JoinColumn(name = "activity_id", referencedColumnName = "activity_id")
    @ManyToOne
    private Activity activityId;
    @JoinColumn(name = "activity_performance_metafield_id", referencedColumnName = "activity_performance_metafield_id")
    @ManyToOne
    private ActivityPerformanceMetafield activityPerformanceMetafieldId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
    @Column(name = "activity_external_id")
    private String activityExternalId;

    public UserActivityPerformance() {
    }

    public UserActivityPerformance(Integer userActivityPerformanceId) {
        this.userActivityPerformanceId = userActivityPerformanceId;
    }

    public Integer getUserActivityPerformanceId() {
        return userActivityPerformanceId;
    }

    public void setUserActivityPerformanceId(Integer userActivityPerformanceId) {
        this.userActivityPerformanceId = userActivityPerformanceId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getExecutedDate() {
        return executedDate;
    }

    public void setExecutedDate(Date executedDate) {
        this.executedDate = executedDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getActivityExternalId() {
        return activityExternalId;
    }

    public void setActivityExternalId(String activityExternalId) {
        this.activityExternalId = activityExternalId;
    }

    public Activity getActivityId() {
        return activityId;
    }

    public void setActivityId(Activity activityId) {
        this.activityId = activityId;
    }

    public ActivityPerformanceMetafield getActivityPerformanceMetafieldId() {
        return activityPerformanceMetafieldId;
    }

    public void setActivityPerformanceMetafieldId(ActivityPerformanceMetafield activityPerformanceMetafieldId) {
        this.activityPerformanceMetafieldId = activityPerformanceMetafieldId;
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
        hash += (userActivityPerformanceId != null ? userActivityPerformanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserActivityPerformance)) {
            return false;
        }
        UserActivityPerformance other = (UserActivityPerformance) object;
        if ((this.userActivityPerformanceId == null && other.userActivityPerformanceId != null) || (this.userActivityPerformanceId != null && !this.userActivityPerformanceId.equals(other.userActivityPerformanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.UserActivityPerformance[ userActivityPerformanceId=" + userActivityPerformanceId + " ]";
    }

}
