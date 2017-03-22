/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.Table;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "intensity_zone_dist")
@NamedQueries({
    @NamedQuery(name = "IntensityZoneDist.findAll", query = "SELECT i FROM IntensityZoneDist i")})
public class IntensityZoneDist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "intensity_zone_dist_id")
    private Integer intensityZoneDistId;
    @Column(name = "num_zone")
    private Integer numZone;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "percentaje")
    private Double percentaje;
    @JoinColumn(name = "intensity_zone_id", referencedColumnName = "intensity_zone_id")
    @ManyToOne
    private IntensityZone intensityZoneId;

    public IntensityZoneDist() {
    }

    public IntensityZoneDist(Integer intensityZoneDistId) {
        this.intensityZoneDistId = intensityZoneDistId;
    }

    public Integer getIntensityZoneDistId() {
        return intensityZoneDistId;
    }

    public void setIntensityZoneDistId(Integer intensityZoneDistId) {
        this.intensityZoneDistId = intensityZoneDistId;
    }

    public Integer getNumZone() {
        return numZone;
    }

    public void setNumZone(Integer numZone) {
        this.numZone = numZone;
    }

    public Double getPercentaje() {
        return percentaje;
    }

    public void setPercentaje(Double percentaje) {
        this.percentaje = percentaje;
    }

    public IntensityZone getIntensityZoneId() {
        return intensityZoneId;
    }

    public void setIntensityZoneId(IntensityZone intensityZoneId) {
        this.intensityZoneId = intensityZoneId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intensityZoneDistId != null ? intensityZoneDistId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntensityZoneDist)) {
            return false;
        }
        IntensityZoneDist other = (IntensityZoneDist) object;
        if ((this.intensityZoneDistId == null && other.intensityZoneDistId != null) || (this.intensityZoneDistId != null && !this.intensityZoneDistId.equals(other.intensityZoneDistId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.IntensityZoneDist[ intensityZoneDistId=" + intensityZoneDistId + " ]";
    }
    
}
