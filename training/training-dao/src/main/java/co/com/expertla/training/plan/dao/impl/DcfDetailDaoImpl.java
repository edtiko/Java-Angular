package co.com.expertla.training.plan.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.plan.dao.DcfDetailDao;
import co.com.expertla.training.model.entities.DcfDetail;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
* Dao Implementation for Dcf Detail <br>
* Creation Date : <br>
* date 21/07/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class DcfDetailDaoImpl extends BaseDAOImpl<DcfDetail> implements DcfDetailDao {

    @Override
    public List<DcfDetail> findByDcfId(Integer dcfId) throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT d ");
        sql.append("FROM DcfDetail d ");
        sql.append("WHERE d.dcfId.dcfId = : dcfId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("dcfId",dcfId);
        return  query.getResultList();
    }

}
