/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.dto;

import co.expertic.training.model.entities.Country;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Edwin G
 */
public class CountryDTO {
    
    private Integer countryId;
    private String name;
    
    public CountryDTO(){
        
    }
    
    public CountryDTO(Country country){
       this.countryId = country.getCountryId();
       this.name = country.getName();
    }
    
    public static CountryDTO mapFromCountryEntity(Country country) {
        return new CountryDTO(country);
    }
    
     public static List<CountryDTO> mapFromCountries(List<Country> countries) {
        return countries.stream().map((country) -> mapFromCountryEntity(country)).collect(Collectors.toList());
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    
}
