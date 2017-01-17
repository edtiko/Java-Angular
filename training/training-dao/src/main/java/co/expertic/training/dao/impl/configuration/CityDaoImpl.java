/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.configuration.CityDao;
import co.expertic.training.model.entities.City;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class CityDaoImpl extends BaseDAOImpl<City> implements CityDao{

    @Override
    public City findById(Integer cityId) {
            try {
            String qlString = "SELECT c FROM City c WHERE c.cityId = :cityId";
            setParameter("cityId", cityId);
            List<City> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<City> findCitiesByState(Integer federalStateId) {
              try {
            String qlString = "SELECT c FROM City c WHERE c.federalStateId.federalStateId = :federalStateId";
            setParameter("federalStateId", federalStateId);
            List<City> query = createQuery(qlString);
            return query;
        } catch (Exception e) {
            return null;
        } 
    }
    
}
