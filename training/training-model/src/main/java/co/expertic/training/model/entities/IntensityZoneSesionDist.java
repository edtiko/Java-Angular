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
@Table(name = "intensity_zone_sesion_dist")
@NamedQueries({
    @NamedQuery(name = "IntensityZoneSesionDist.findAll", query = "SELECT i FROM IntensityZoneSesionDist i")})
public class IntensityZoneSesionDist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "intensity_zone_sesion_dist_id")
    private Integer intensityZoneSesionDistId;
    @Column(name = "num_zone")
    private Integer numZone;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "zone_percentaje")
    private Double zonePercentaje;
    @JoinColumn(name = "intensity_zone_sesion_id", referencedColumnName = "intensity_zone_sesion_id")
    @ManyToOne(optional = false)
    private IntensityZoneSesion intensityZoneSesionId;

    public IntensityZoneSesionDist() {
    }

    public IntensityZoneSesionDist(Integer intensityZoneSesionDistId) {
        this.intensityZoneSesionDistId = intensityZoneSesionDistId;
    }

    public Integer getIntensityZoneSesionDistId() {
        return intensityZoneSesionDistId;
    }

    public void setIntensityZoneSesionDistId(Integer intensityZoneSesionDistId) {
        this.intensityZoneSesionDistId = intensityZoneSesionDistId;
    }

    public Integer getNumZone() {
        return numZone;
    }

    public void setNumZone(Integer numZone) {
        this.numZone = numZone;
    }

    public Double getZonePercentaje() {
        return zonePercentaje;
    }

    public void setZonePercentaje(Double zonePercentaje) {
        this.zonePercentaje = zonePercentaje;
    }

    public IntensityZoneSesion getIntensityZoneSesionId() {
        return intensityZoneSesionId;
    }

    public void setIntensityZoneSesionId(IntensityZoneSesion intensityZoneSesionId) {
        this.intensityZoneSesionId = intensityZoneSesionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intensityZoneSesionDistId != null ? intensityZoneSesionDistId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntensityZoneSesionDist)) {
            return false;
        }
        IntensityZoneSesionDist other = (IntensityZoneSesionDist) object;
        if ((this.intensityZoneSesionDistId == null && other.intensityZoneSesionDistId != null) || (this.intensityZoneSesionDistId != null && !this.intensityZoneSesionDistId.equals(other.intensityZoneSesionDistId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.expertic.training.model.entities.IntensityZoneSesionDist[ intensityZoneSesionDistId=" + intensityZoneSesionDistId + " ]";
    }
    
}
