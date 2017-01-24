package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.configuration.SportDao;
import co.expertic.training.model.dto.EnvironmentDTO;
import co.expertic.training.model.dto.SportDTO;
import co.expertic.training.model.dto.WeatherDTO;
import co.expertic.training.model.entities.Sport;
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
        sql.append("SELECT new co.expertic.training.model.dto.SportDTO(s.sportId,s.name) ");        
        sql.append("FROM Sport s ");
        sql.append("Where s.indDisciplineSport = :sport ");
        sql.append("ORDER BY s.name ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("sport", "0");
        return query.getResultList();
    }
    
      @Override
    public List<EnvironmentDTO> findEntornos() throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.EnvironmentDTO(e.environmentId,e.name) ");
        sql.append("FROM Environment e ");
        sql.append("ORDER BY e.name ");
        
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }
    
    @Override
    public List<WeatherDTO> findClimas() throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.WeatherDTO(e.weatherId,e.name) ");
        sql.append("FROM Weather e ");
        sql.append("ORDER BY e.name ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }
    
    @Override
    public List<SportDTO> findSportDisciplines() throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.SportDTO(s.sportId,s.name) ");        
        sql.append("FROM Sport s ");
        sql.append("Where s.indDisciplineSport = :sport ");
        sql.append("ORDER BY s.name ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("sport", "1");
        return query.getResultList();
    }

}
