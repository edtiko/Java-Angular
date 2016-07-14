/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "video_user")
@NamedQueries({
    @NamedQuery(name = "VideoUser.findAll", query = "SELECT v FROM VideoUser v"),
    @NamedQuery(name = "VideoUser.findByVideoUserId", query = "SELECT v FROM VideoUser v WHERE v.videoUserId = :videoUserId"),
    @NamedQuery(name = "VideoUser.findByUrl", query = "SELECT v FROM VideoUser v WHERE v.url = :url"),
    @NamedQuery(name = "VideoUser.findByCreationDate", query = "SELECT v FROM VideoUser v WHERE v.creationDate = :creationDate")})
public class VideoUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "video_user_id")
    private Integer videoUserId;
    @Basic(optional = false)
    @Column(name = "url")
    private String url;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "state_id", referencedColumnName = "state_id")
    @ManyToOne
    private State stateId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User userId;

    public VideoUser() {
    }

    public VideoUser(Integer videoUserId) {
        this.videoUserId = videoUserId;
    }

    public VideoUser(Integer videoUserId, String url) {
        this.videoUserId = videoUserId;
        this.url = url;
    }

    public Integer getVideoUserId() {
        return videoUserId;
    }

    public void setVideoUserId(Integer videoUserId) {
        this.videoUserId = videoUserId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public State getStateId() {
        return stateId;
    }

    public void setStateId(State stateId) {
        this.stateId = stateId;
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
        hash += (videoUserId != null ? videoUserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VideoUser)) {
            return false;
        }
        VideoUser other = (VideoUser) object;
        if ((this.videoUserId == null && other.videoUserId != null) || (this.videoUserId != null && !this.videoUserId.equals(other.videoUserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.VideoUser[ videoUserId=" + videoUserId + " ]";
    }
    
}
