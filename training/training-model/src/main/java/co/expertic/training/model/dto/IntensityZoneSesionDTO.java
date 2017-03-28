/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

/**
 *
 * @author Edwin G
 */
public class IntensityZoneSesionDTO {

    private Integer sesion;
    private Integer zone;
    private Double percentaje;
    private Double time;

    public IntensityZoneSesionDTO() {

    }

    public IntensityZoneSesionDTO(Integer sesion, Integer zone, Double percentaje) {
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

    public Double getPercentaje() {
        return percentaje;
    }

    public void setPercentaje(Double percentaje) {
        this.percentaje = percentaje;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
    
    

}
