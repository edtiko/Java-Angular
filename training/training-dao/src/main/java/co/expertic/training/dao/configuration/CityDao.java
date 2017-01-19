/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.configuration;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.entities.City;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface CityDao extends BaseDAO<City> {
    
    City findById(Integer id);
    
    public List<City> findCitiesByState(Integer stateId);
    
}