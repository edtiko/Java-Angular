/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import co.com.expertla.training.model.entities.Role;
import co.com.expertla.training.model.entities.TrainingPlan;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "configuration_plan")
@NamedQueries({
    @NamedQuery(name = "ConfigurationPlan.findAll", query = "SELECT c FROM ConfigurationPlan c"),
    @NamedQuery(name = "ConfigurationPlan.findByConfigurationPlanId", query = "SELECT c FROM ConfigurationPlan c WHERE c.configurationPlanId = :configurationPlanId"),
    @NamedQuery(name = "ConfigurationPlan.findByVideoCount", query = "SELECT c FROM ConfigurationPlan c WHERE c.videoCount = :videoCount"),
    @NamedQuery(name = "ConfigurationPlan.findByVideoEmergency", query = "SELECT c FROM ConfigurationPlan c WHERE c.videoEmergency = :videoEmergency"),
    @NamedQuery(name = "ConfigurationPlan.findByVideoDuration", query = "SELECT c FROM ConfigurationPlan c WHERE c.videoDuration = :videoDuration"),
    @NamedQuery(name = "ConfigurationPlan.findByMessageCount", query = "SELECT c FROM ConfigurationPlan c WHERE c.messageCount = :messageCount"),
    @NamedQuery(name = "ConfigurationPlan.findByMessageEmergency", query = "SELECT c FROM ConfigurationPlan c WHERE c.messageEmergency = :messageEmergency"),
    @NamedQuery(name = "ConfigurationPlan.findByEmailCount", query = "SELECT c FROM ConfigurationPlan c WHERE c.emailCount = :emailCount"),
    @NamedQuery(name = "ConfigurationPlan.findByEmailEmergency", query = "SELECT c FROM ConfigurationPlan c WHERE c.emailEmergency = :emailEmergency"),
    @NamedQuery(name = "ConfigurationPlan.findByAudioCount", query = "SELECT c FROM ConfigurationPlan c WHERE c.audioCount = :audioCount"),
    @NamedQuery(name = "ConfigurationPlan.findByAudioEmergency", query = "SELECT c FROM ConfigurationPlan c WHERE c.audioEmergency = :audioEmergency"),
    @NamedQuery(name = "ConfigurationPlan.findByAudioDuration", query = "SELECT c FROM ConfigurationPlan c WHERE c.audioDuration = :audioDuration"),
    @NamedQuery(name = "ConfigurationPlan.findByCreationDate", query = "SELECT c FROM ConfigurationPlan c WHERE c.creationDate = :creationDate"),
    @NamedQuery(name = "ConfigurationPlan.findByUserCreate", query = "SELECT c FROM ConfigurationPlan c WHERE c.userCreate = :userCreate"),
    @NamedQuery(name = "ConfigurationPlan.findByLastUpdate", query = "SELECT c FROM ConfigurationPlan c WHERE c.lastUpdate = :lastUpdate"),
    @NamedQuery(name = "ConfigurationPlan.findByUserUpdate", query = "SELECT c FROM ConfigurationPlan c WHERE c.userUpdate = :userUpdate")})
public class ConfigurationPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "configuration_plan_id")
    private Integer configurationPlanId;
    @Basic(optional = false)
    @Column(name = "video_count")
    private int videoCount;
    @Basic(optional = false)
    @Column(name = "video_emergency")
    private int videoEmergency;
    @Basic(optional = false)
    @Column(name = "video_duration")
    private int videoDuration;
    @Basic(optional = false)
    @Column(name = "message_count")
    private int messageCount;
    @Basic(optional = false)
    @Column(name = "message_emergency")
    private int messageEmergency;
    @Basic(optional = false)
    @Column(name = "email_count")
    private int emailCount;
    @Basic(optional = false)
    @Column(name = "email_emergency")
    private int emailEmergency;
    @Basic(optional = false)
    @Column(name = "audio_count")
    private int audioCount;
    @Basic(optional = false)
    @Column(name = "audio_emergency")
    private int audioEmergency;
    @Basic(optional = false)
    @Column(name = "audio_duration")
    private int audioDuration;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column(name = "user_create")
    private Integer userCreate;
    @Column(name = "last_update")
    @Temporal(TemporalType.DATE)
    private Date lastUpdate;
    @Column(name = "user_update")
    private Integer userUpdate;
    @JoinColumn(name = "communication_role_id", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private Role communicationRoleId;
    @JoinColumn(name = "training_plan_id", referencedColumnName = "training_plan_id")
    @ManyToOne(optional = false)
    private TrainingPlan trainingPlanId;

    public ConfigurationPlan() {
    }

    public ConfigurationPlan(Integer configurationPlanId) {
        this.configurationPlanId = configurationPlanId;
    }

    public ConfigurationPlan(Integer configurationPlanId, int videoCount, int videoEmergency, int videoDuration, int messageCount, int messageEmergency, int emailCount, int emailEmergency, int audioCount, int audioEmergency, int audioDuration) {
        this.configurationPlanId = configurationPlanId;
        this.videoCount = videoCount;
        this.videoEmergency = videoEmergency;
        this.videoDuration = videoDuration;
        this.messageCount = messageCount;
        this.messageEmergency = messageEmergency;
        this.emailCount = emailCount;
        this.emailEmergency = emailEmergency;
        this.audioCount = audioCount;
        this.audioEmergency = audioEmergency;
        this.audioDuration = audioDuration;
    }

    public Integer getConfigurationPlanId() {
        return configurationPlanId;
    }

    public void setConfigurationPlanId(Integer configurationPlanId) {
        this.configurationPlanId = configurationPlanId;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public int getVideoEmergency() {
        return videoEmergency;
    }

    public void setVideoEmergency(int videoEmergency) {
        this.videoEmergency = videoEmergency;
    }

    public int getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(int videoDuration) {
        this.videoDuration = videoDuration;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public int getMessageEmergency() {
        return messageEmergency;
    }

    public void setMessageEmergency(int messageEmergency) {
        this.messageEmergency = messageEmergency;
    }

    public int getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(int emailCount) {
        this.emailCount = emailCount;
    }

    public int getEmailEmergency() {
        return emailEmergency;
    }

    public void setEmailEmergency(int emailEmergency) {
        this.emailEmergency = emailEmergency;
    }

    public int getAudioCount() {
        return audioCount;
    }

    public void setAudioCount(int audioCount) {
        this.audioCount = audioCount;
    }

    public int getAudioEmergency() {
        return audioEmergency;
    }

    public void setAudioEmergency(int audioEmergency) {
        this.audioEmergency = audioEmergency;
    }

    public int getAudioDuration() {
        return audioDuration;
    }

    public void setAudioDuration(int audioDuration) {
        this.audioDuration = audioDuration;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(Integer userCreate) {
        this.userCreate = userCreate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(Integer userUpdate) {
        this.userUpdate = userUpdate;
    }

    public Role getCommunicationRoleId() {
        return communicationRoleId;
    }

    public void setCommunicationRoleId(Role communicationRoleId) {
        this.communicationRoleId = communicationRoleId;
    }

    public TrainingPlan getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(TrainingPlan trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (configurationPlanId != null ? configurationPlanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigurationPlan)) {
            return false;
        }
        ConfigurationPlan other = (ConfigurationPlan) object;
        if ((this.configurationPlanId == null && other.configurationPlanId != null) || (this.configurationPlanId != null && !this.configurationPlanId.equals(other.configurationPlanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.constant.ConfigurationPlan[ configurationPlanId=" + configurationPlanId + " ]";
    }
    
}
