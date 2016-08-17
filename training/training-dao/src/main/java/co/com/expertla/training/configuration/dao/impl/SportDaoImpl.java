package co.com.expertla.training.configuration.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.configuration.dao.SportDao;
import co.com.expertla.training.model.dto.EnvironmentDTO;
import co.com.expertla.training.model.dto.SportDTO;
import co.com.expertla.training.model.dto.WeatherDTO;
import co.com.expertla.training.model.entities.Sport;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
* Dao Implementation for Discipline <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class SportDaoImpl extends BaseDAOImpl<Sport> implements SportDao {

    @Override
    public List<SportDTO> findAll() throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SportDTO(s.sportId,s.name) ");        
        sql.append("FROM Sport s ");
        sql.append("Where s.indDisciplineSport = 0 ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }
    
      @Override
    public List<EnvironmentDTO> findEntornos() throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.EnvironmentDTO(e.environmentId,e.name) ");
        sql.append("FROM Environment e ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }
    
    @Override
    public List<WeatherDTO> findClimas() throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.WeatherDTO(e.weatherId,e.name) ");
        sql.append("FROM Weather e ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }

}
