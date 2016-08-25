package co.com.expertla.training.configuration.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.configuration.dao.CountryDao;
import co.com.expertla.training.model.entities.Country;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
* Country Dao Impl <br>
* Info. Creación: <br>
* fecha 19/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class CountryDaoImpl extends BaseDAOImpl<Country> implements CountryDao {

    public CountryDaoImpl() {
    }
    
    @Override
    public List<Country> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Country a ");
        builder.append("order by a.name asc ");
        return createQuery(builder.toString());
    }


    @Override
    public List<Country> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Country a ");
        return createQuery(builder.toString());
    }

    
    @Override
    public List<Country> findByCountry(Country country) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Country a ");
        builder.append("WHERE a.countryId = :id ");
        setParameter("id", country.getCountryId());
        return createQuery(builder.toString());
    }

    @Override
    public List<Country> findByFiltro(Country country) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Country a ");
        builder.append("WHERE 1=1 ");
        
        if(country.getName() != null && !country.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", "%" + country.getName() + "%");
        }



        return createQuery(builder.toString());
    }

}