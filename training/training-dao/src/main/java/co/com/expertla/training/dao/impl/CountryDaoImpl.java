/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.CountryDao;
import co.com.expertla.training.model.entities.Country;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class CountryDaoImpl extends BaseDAOImpl<Country> implements CountryDao{

    @Override
    public Country findById(Integer countryId) {

        try {
            String qlString = "SELECT u FROM Country u WHERE u.countryId = :countryId";
            setParameter("countryId", countryId);
            List<Country> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Country> findAllCountries() {
        try {
            String qlString = "SELECT u FROM Country u order by u.name";
            List<Country> query = createQuery(qlString);
            return query;
        } catch (Exception e) {
            return null;
        }
    }
    
}
