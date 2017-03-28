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
public class IntervaloTiempo {
    
    private Integer id;
    private Integer zona;
    private Double tiempo;
    
    public IntervaloTiempo(){
        
    }
    public IntervaloTiempo(Integer zona, Double tiempo){
        this.zona = zona;
        this.tiempo = tiempo;
    }

    public Integer getZona() {
        return zona;
    }

    public void setZona(Integer zona) {
        this.zona = zona;
    }

    public Double getTiempo() {
        return tiempo;
    }

    public void setTiempo(Double tiempo) {
        this.tiempo = tiempo;
    }
    
    
    
}
