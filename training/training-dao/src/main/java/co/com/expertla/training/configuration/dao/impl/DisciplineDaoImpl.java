package co.com.expertla.training.configuration.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.configuration.dao.DisciplineDao;
import co.com.expertla.training.model.dto.DisciplineDTO;
import co.com.expertla.training.model.entities.Discipline;
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
public class DisciplineDaoImpl extends BaseDAOImpl<Discipline> implements DisciplineDao {

    @Override
    public List<DisciplineDTO> findAll() throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.DisciplineDTO(d.disciplineId, d.name, d.description) ");
        sql.append("FROM Discipline d ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }

    @Override
    public List<DisciplineDTO> findByUserId(Integer userd) throws Exception {
           try {
             StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.DisciplineDTO(d.discipline.disciplineId, d.discipline.name, d.discipline.description) ");
        sql.append("FROM DisciplineUser d ");
            Query query = getEntityManager().createQuery(sql.toString());
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}