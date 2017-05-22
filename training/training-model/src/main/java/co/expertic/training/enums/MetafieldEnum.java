/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.enums;

/**
 *
 * @author Edwin G
 */
public enum MetafieldEnum {

    ACTIVIDAD_META(1), CALORIAS_META(2), DISTANCIA_TOTAL_META(3), FRECUENCIA_CARDIACA_MAXIMA_META(4),
    FRECUENCIA_CARDIACA_MEDIA_META(5), RITMO_MEDIO_META(6), POTENCIA_MAXIMA_META(7), POTENCIA_MEDIA_META(8),
    VELOCIDAD_MEDIA(10), RIDE(1), RUN(3);

    private Integer id;

    private MetafieldEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
