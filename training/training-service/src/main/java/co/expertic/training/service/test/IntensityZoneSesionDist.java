/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.test;

/**
 *
 * @author Edwin G
 */
public class IntensityZoneSesionDist {

    private Integer sesion;
    private Integer zone;
    private Integer percentaje;
    private Double time;

    public IntensityZoneSesionDist() {

    }

    public IntensityZoneSesionDist(Integer sesion, Integer zone, Integer percentaje) {
        this.sesion = sesion;
        this.zone = zone;
        this.percentaje = percentaje;
    }

    public Integer getSesion() {
        return sesion;
    }

    public void setSesion(Integer sesion) {
        this.sesion = sesion;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }

    public Integer getPercentaje() {
        return percentaje;
    }

    public void setPercentaje(Integer percentaje) {
        this.percentaje = percentaje;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
    
    

}
