package co.com.expertla.training.plan.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.plan.dao.DcfDao;
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
    public Dcf findByObjetiveIdAndModalityId(Integer objetiveId, Integer modalityId) throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT d ");
        sql.append("FROM Dcf d ");
        sql.append("WHERE d.objetiveId.objetiveId = :objetiveId ");
        sql.append("AND d.modalityId.modalityId = :modalityId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("objetiveId",objetiveId);
        query.setParameter("modalityId",modalityId);
        List<Dcf> list = query.getResultList();
        return  (list == null || list.isEmpty()) ? null : list.get(0);
    }

}
