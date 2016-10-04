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
@Table(name = "weather")
@NamedQueries({
    @NamedQuery(name = "Weather.findAll", query = "SELECT w FROM Weather w"),
    @NamedQuery(name = "Weather.findByWeatherId", query = "SELECT w FROM Weather w WHERE w.weatherId = :weatherId"),
    @NamedQuery(name = "Weather.findByName", query = "SELECT w FROM Weather w WHERE w.name = :name"),
    @NamedQuery(name = "Weather.findByStateId", query = "SELECT w FROM Weather w WHERE w.stateId = :stateId"),
    @NamedQuery(name = "Weather.findByCreationDate", query = "SELECT w FROM Weather w WHERE w.creationDate = :creationDate")})
public class Weather implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "weather_id")
    private Integer weatherId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "state_id")
    private Integer stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column(name = "percentage")
    private Integer percentage;


    public Weather() {
    }

    public Weather(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public Weather(Integer weatherId, String name) {
        this.weatherId = weatherId;
        this.name = name;
    }

    public Integer getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weatherId != null ? weatherId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Weather)) {
            return false;
        }
        Weather other = (Weather) object;
        if ((this.weatherId == null && other.weatherId != null) || (this.weatherId != null && !this.weatherId.equals(other.weatherId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Weather[ weatherId=" + weatherId + " ]";
    }
    
}
