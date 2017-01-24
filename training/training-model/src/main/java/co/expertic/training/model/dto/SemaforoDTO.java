/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

/**
 *
 * @author Andres Lopez
 */
public class SemaforoDTO {
    private String tipoAccion;
    private int horas;

    public SemaforoDTO(String tipoAccion, int horas) {
        this.tipoAccion = tipoAccion;
        this.horas = horas;
    }

    public SemaforoDTO() {
    }
    
    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }
    
    
}
