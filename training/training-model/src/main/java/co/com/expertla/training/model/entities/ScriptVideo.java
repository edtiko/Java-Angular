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
 *
 * @author Andres Lopez
 */
@Entity
@Table(name = "script_video")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScriptVideo.findAll", query = "SELECT s FROM ScriptVideo s"),
    @NamedQuery(name = "ScriptVideo.findByScriptVideoId", query = "SELECT s FROM ScriptVideo s WHERE s.scriptVideoId = :scriptVideoId"),
    @NamedQuery(name = "ScriptVideo.findByGuion", query = "SELECT s FROM ScriptVideo s WHERE s.guion = :guion"),
    @NamedQuery(name = "ScriptVideo.findByCreationDate", query = "SELECT s FROM ScriptVideo s WHERE s.creationDate = :creationDate")})
public class ScriptVideo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "script_video_script_video_id_seq", sequenceName = "script_video_script_video_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "script_video_script_video_id_seq")
    @Basic(optional = false)
    @Column(name = "script_video_id")
    private Integer scriptVideoId;
    @Basic(optional = false)
    @Column(name = "guion")
    private String guion;
    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @JoinColumn(name = "plan_video_id", referencedColumnName = "plan_video_id")
    @ManyToOne(optional = false)
    private PlanVideo planVideoId;

    public ScriptVideo() {
    }

    public ScriptVideo(Integer scriptVideoId) {
        this.scriptVideoId = scriptVideoId;
    }

    public ScriptVideo(Integer scriptVideoId, String guion, Date creationDate) {
        this.scriptVideoId = scriptVideoId;
        this.guion = guion;
        this.creationDate = creationDate;
    }

    public Integer getScriptVideoId() {
        return scriptVideoId;
    }

    public void setScriptVideoId(Integer scriptVideoId) {
        this.scriptVideoId = scriptVideoId;
    }

    public String getGuion() {
        return guion;
    }

    public void setGuion(String guion) {
        this.guion = guion;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public PlanVideo getPlanVideoId() {
        return planVideoId;
    }

    public void setPlanVideoId(PlanVideo planVideoId) {
        this.planVideoId = planVideoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scriptVideoId != null ? scriptVideoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScriptVideo)) {
            return false;
        }
        ScriptVideo other = (ScriptVideo) object;
        if ((this.scriptVideoId == null && other.scriptVideoId != null) || (this.scriptVideoId != null && !this.scriptVideoId.equals(other.scriptVideoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.ScriptVideo[ scriptVideoId=" + scriptVideoId + " ]";
    }
    
}
