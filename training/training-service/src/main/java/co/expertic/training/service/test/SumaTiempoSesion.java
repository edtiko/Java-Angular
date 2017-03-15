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
public class SumaTiempoSesion {
    
    private Integer sesion;
    private Double tiempo;
    
    public SumaTiempoSesion(){
        
    }
    
     public SumaTiempoSesion(Integer sesion, Double tiempo){
        this.sesion = sesion;
        this.tiempo = tiempo;
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
    
    
    
    
}
