package co.com.expertla.training.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.DcfDao;
import co.com.expertla.training.model.entities.Dcf;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
* Dao Implementation for Dcf (Distribucion porcentual de Capacidades Fisiologicas) <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class DcfDaoImpl extends BaseDAOImpl<Dcf> implements DcfDao {

    @Override
    public List<Dcf> findByObjetiveId(Integer id) throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT d ");
        sql.append("FROM Dcf d ");
        sql.append("d.objetiveId.objetiveId = : id");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id",id);
        return query.getResultList();
    }

}
