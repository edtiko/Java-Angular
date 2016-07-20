package co.com.expertla.training.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.SportDao;
import co.com.expertla.training.model.dto.SportDTO;
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
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }

}
