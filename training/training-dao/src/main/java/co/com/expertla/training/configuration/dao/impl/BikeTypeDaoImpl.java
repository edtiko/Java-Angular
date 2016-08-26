package co.com.expertla.training.configuration.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.configuration.dao.BikeTypeDao;
import co.com.expertla.training.model.entities.BikeType;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
* Dao Implementation for BikeType <br>
* Creation Date : <br>
* date 19/08/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class BikeTypeDaoImpl extends BaseDAOImpl<BikeType> implements BikeTypeDao {

    @Override
    public List<BikeType> findAll() throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT d ");
        sql.append("FROM BikeType d ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }

}
