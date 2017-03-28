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
public class SerieGenerada {
    
    private Integer zona;
    private Integer sesion;
    private Double tiempo;
    private Integer numSesiones;
    private Integer semana;
    
    public SerieGenerada(Integer semana, Integer zona, Integer sesion, Double tiempo, Integer numSesiones) {
        this.zona = zona;
        this.sesion = sesion;
        this.tiempo = tiempo;
        this.numSesiones = numSesiones;
        this.semana = semana;
    }

    public SerieGenerada(Double tiempo, Long numSesiones) {
        this.tiempo = tiempo;
        this.numSesiones = numSesiones.intValue();
    }

    public SerieGenerada() {

    }


    public Integer getZona() {
        return zona;
    }

    public void setZona(Integer zona) {
        this.zona = zona;
    }

    public Integer getSesion() {
        return sesion;
    }

    public void setSesion(Integer sesion) {
        this.sesion = sesion;
    }

    public Double getTiempo() {
        return tiempo;
    }

    public void setTiempo(Double tiempo) {
        this.tiempo = tiempo;
    }

    public Integer getNumSesiones() {
        return numSesiones;
    }

    public void setNumSesiones(Integer numSesiones) {
        this.numSesiones = numSesiones;
    }

    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
    }
    
    
    
    
}
