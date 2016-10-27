/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "plan_video")
@NamedQueries({
    @NamedQuery(name = "PlanVideo.findAll", query = "SELECT p FROM PlanVideo p"),
    @NamedQuery(name = "PlanVideo.findByPlanVideoId", query = "SELECT p FROM PlanVideo p WHERE p.planVideoId = :planVideoId"),
    @NamedQuery(name = "PlanVideo.findByName", query = "SELECT p FROM PlanVideo p WHERE p.name = :name"),
    @NamedQuery(name = "PlanVideo.findByDuration", query = "SELECT p FROM PlanVideo p WHERE p.duration = :duration"),
    @NamedQuery(name = "PlanVideo.findByVideoPath", query = "SELECT p FROM PlanVideo p WHERE p.videoPath = :videoPath"),
    @NamedQuery(name = "PlanVideo.findByCreationDate", query = "SELECT p FROM PlanVideo p WHERE p.creationDate = :creationDate")})
public class PlanVideo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "plan_video_plan_video_id_seq", sequenceName = "plan_video_plan_video_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_video_plan_video_id_seq")
    @Column(name = "plan_video_id")
    private Integer planVideoId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "duration")
    private Integer duration;
    @Basic(optional = false)
    @Column(name = "video_path")
    private String videoPath;
    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @JoinColumn(name = "from_user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User fromUserId;
    @JoinColumn(name = "to_user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User toUserId;
    @JoinColumn(name = "coach_assigned_plan_id", referencedColumnName = "coach_assigned_plan_id")
    @ManyToOne(optional = false)
    private CoachAssignedPlan coachAssignedPlanId;
    @OneToMany(mappedBy = "planVideoId")
    private Collection<ScriptVideo> scriptVideoCollection;
    @JoinColumn(name = "coach_ext_athlete_id", referencedColumnName = "coach_ext_athlete_id")
    @ManyToOne(optional = false)
    private CoachExtAthlete coachExtAthleteId;
    @Column(name = "ind_rejected")
    private Integer indRejected;


    public PlanVideo() {
    }

    public PlanVideo(Integer planVideoId) {
        this.planVideoId = planVideoId;
    }

    public PlanVideo(Integer planVideoId, String name, String videoPath, Date creationDate) {
        this.planVideoId = planVideoId;
        this.name = name;
        this.videoPath = videoPath;
        this.creationDate = creationDate;
    }

    public Integer getPlanVideoId() {
        return planVideoId;
    }

    public void setPlanVideoId(Integer planVideoId) {
        this.planVideoId = planVideoId;
    }
    @JsonIgnore
    public Collection<ScriptVideo> getScriptVideoCollection() {
        return scriptVideoCollection;
    }

    public void setScriptVideoCollection(Collection<ScriptVideo> scriptVideoCollection) {
        this.scriptVideoCollection = scriptVideoCollection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(User fromUserId) {
        this.fromUserId = fromUserId;
    }

    public User getToUserId() {
        return toUserId;
    }

    public void setToUserId(User toUserId) {
        this.toUserId = toUserId;
    }

    public CoachAssignedPlan getCoachAssignedPlanId() {
        return coachAssignedPlanId;
    }

    public void setCoachAssignedPlanId(CoachAssignedPlan coachAssignedPlanId) {
        this.coachAssignedPlanId = coachAssignedPlanId;
    }

    public CoachExtAthlete getCoachExtAthleteId() {
        return coachExtAthleteId;
    }

    public void setCoachExtAthleteId(CoachExtAthlete coachExtAthleteId) {
        this.coachExtAthleteId = coachExtAthleteId;
    }

    public Integer getIndRejected() {
        return indRejected;
    }

    public void setIndRejected(Integer indRejected) {
        this.indRejected = indRejected;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planVideoId != null ? planVideoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanVideo)) {
            return false;
        }
        PlanVideo other = (PlanVideo) object;
        if ((this.planVideoId == null && other.planVideoId != null) || (this.planVideoId != null && !this.planVideoId.equals(other.planVideoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.PlanVideo[ planVideoId=" + planVideoId + " ]";
    }
    
}
