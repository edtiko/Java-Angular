/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao;

import co.com.expertla.training.model.entities.Country;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface CountryDao {

    Country findById(Integer id);

    List<Country> findAllCountries();

}
