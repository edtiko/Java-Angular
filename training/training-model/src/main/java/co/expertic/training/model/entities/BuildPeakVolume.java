/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

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

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "build_peak_volume")
@NamedQueries({
    @NamedQuery(name = "BuildPeakVolume.findAll", query = "SELECT b FROM BuildPeakVolume b")})
public class BuildPeakVolume implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "build_peak_volume_build_peak_volume_id_seq", sequenceName = "build_peak_volume_build_peak_volume_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "build_peak_volume_build_peak_volume_id_seq")
    @Column(name = "build_peak_volume_id")
    private Integer buildPeakVolumeId;
    @Basic(optional = false)
    @Column(name = "week")
    private int week;
    @Basic(optional = false)
    @Column(name = "volume")
    private int volume;

    @Column(name = "user_create")
    private Integer userCreate;
    @Column(name = "user_update")
    private Integer userUpdate;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Column(name = "state_id")
    private Integer stateId;
    @JoinColumn(name = "modality_id", referencedColumnName = "modality_id")
    @ManyToOne(optional = false)
    private Modality modalityId;
    @JoinColumn(name = "training_load_type_id", referencedColumnName = "training_load_type_id")
    @ManyToOne(optional = false)
    private TrainingLoadType trainingLoadTypeId;

    public BuildPeakVolume() {
    }

    public BuildPeakVolume(Integer buildPeakVolumeId) {
        this.buildPeakVolumeId = buildPeakVolumeId;
    }

    public BuildPeakVolume(Integer buildPeakVolumeId, int week, int volume) {
        this.buildPeakVolumeId = buildPeakVolumeId;
        this.week = week;
        this.volume = volume;
    }

    public Integer getBuildPeakVolumeId() {
        return buildPeakVolumeId;
    }

    public void setBuildPeakVolumeId(Integer buildPeakVolumeId) {
        this.buildPeakVolumeId = buildPeakVolumeId;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Modality getModalityId() {
        return modalityId;
    }

    public void setModalityId(Modality modalityId) {
        this.modalityId = modalityId;
    }

    public TrainingLoadType getTrainingLoadTypeId() {
        return trainingLoadTypeId;
    }

    public void setTrainingLoadTypeId(TrainingLoadType trainingLoadTypeId) {
        this.trainingLoadTypeId = trainingLoadTypeId;
    }
    
   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (buildPeakVolumeId != null ? buildPeakVolumeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BuildPeakVolume)) {
            return false;
        }
        BuildPeakVolume other = (BuildPeakVolume) object;
        if ((this.buildPeakVolumeId == null && other.buildPeakVolumeId != null) || (this.buildPeakVolumeId != null && !this.buildPeakVolumeId.equals(other.buildPeakVolumeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.BuildPeakVolume[ buildPeakVolumeId=" + buildPeakVolumeId + " ]";
    }
    
}
