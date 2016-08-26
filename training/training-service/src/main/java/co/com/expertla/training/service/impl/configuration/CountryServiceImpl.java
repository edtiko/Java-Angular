package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.configuration.dao.CountryDao;
import co.com.expertla.training.service.configuration.CountryService;
import co.com.expertla.training.model.entities.Country;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Country Service Impl <br>
* Info. Creación: <br>
* fecha 19/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("countryService")
@Transactional
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryDao countryDao;

    @Override
    public Country create(Country country) throws Exception {
        return countryDao.create(country);
    }

    @Override
    public Country store(Country country) throws Exception {
       return countryDao.merge(country);
    }

    @Override
    public void remove(Country country) throws Exception {
        countryDao.remove(country, country.getCountryId());
    }

    @Override
    public List<Country> findAll() throws Exception {
        return countryDao.findAll();
    }

    @Override
    public List<Country> findAllActive() throws Exception {
        return countryDao.findAllActive();
    }

    @Override
    public List<Country> findByCountry(Country country) throws Exception {
        return countryDao.findByCountry(country);
    }

    @Override
    public List<Country> findByFiltro(Country country) throws Exception {
        return countryDao.findByFiltro(country);
    }

}