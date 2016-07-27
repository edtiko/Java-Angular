package co.com.expertla.training.configuration.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.model.dto.ObjectiveDTO;
import co.com.expertla.training.model.entities.Objective;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import co.com.expertla.training.configuration.dao.ObjectiveDao;

/**
* Dao Implementation for Objetive <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class ObjectiveDaoImpl extends BaseDAOImpl<Objective> implements ObjectiveDao {

    @Override
    public List<ObjectiveDTO> findAll() throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.ObjectiveDTO(o.objectiveId,o.name, o.level, o.minSessions,o.maxSessions) ");
        sql.append("FROM Objective o ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }

}
