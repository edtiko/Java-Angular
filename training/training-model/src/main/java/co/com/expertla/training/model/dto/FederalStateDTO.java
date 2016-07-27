/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.model.entities.FederalState;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Edwin G
 */
public class FederalStateDTO {
    
    private Integer federalStateId;
    private Integer countryId;
    private String name;
    
    public FederalStateDTO(){
        
    }
    
    public FederalStateDTO(FederalState state){
       this.federalStateId = state.getFederalStateId();
       this.name = state.getName();
       this.countryId = state.getCountryId() != null?state.getCountryId().getCountryId():null;
    }
    
    public static FederalStateDTO mapFromStateEntity(FederalState state) {
        return new FederalStateDTO(state);
    }
    
     public static List<FederalStateDTO> mapFromStates(List<FederalState> states) {
        return states.stream().map((state) -> mapFromStateEntity(state)).collect(Collectors.toList());
    }

    public Integer getFederalStateId() {
        return federalStateId;
    }

    public void setFederalStateId(Integer federalStateId) {
        this.federalStateId = federalStateId;
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
