package co.com.expertla.training.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.ObjetiveDao;
import co.com.expertla.training.model.dto.ObjetiveDTO;
import co.com.expertla.training.model.entities.Objetive;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
* Dao Implementation for Objetive <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class ObjetiveDaoImpl extends BaseDAOImpl<Objetive> implements ObjetiveDao {

    @Override
    public List<ObjetiveDTO> findAll() throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.ObjetiveDTO(o.objetiveId,o.name, o.level, o.minSessions,o.maxSessions) ");
        sql.append("FROM Objetive o ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }

}
