/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.City;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Edwin G
 */
public class CityDTO {
    
    private Integer cityId;
    private String name;
    private Integer federalStateId;
    
     public CityDTO(City city){
       this.cityId = city.getCityId();
       this.federalStateId = city.getFederalStateId() != null ? city.getFederalStateId().getFederalStateId(): null;
       this.name = city.getName();
    }
    
    public static CityDTO mapFromCityEntity(City city) {
        return new CityDTO(city);
    }
    
     public static List<CityDTO> mapFromCountries(List<City> cities) {
        return cities.stream().map((city) -> mapFromCityEntity(city)).collect(Collectors.toList());
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFederalStateId() {
        return federalStateId;
    }

    public void setFederalStateId(Integer federalStateId) {
        this.federalStateId = federalStateId;
    }
    
    
    
}
