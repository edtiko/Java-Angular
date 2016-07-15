/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.City;

/**
 *
 * @author Edwin G
 */
public interface CityDao extends BaseDAO<City> {
    
    City findById(Integer id);
    
}
