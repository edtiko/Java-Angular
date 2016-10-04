package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.plan.DcfDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.DcfDTO;
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
    public Dcf findByObjectiveIdAndModalityId(Integer objectiveId, Integer modalityId) throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT d ");
        sql.append("FROM Dcf d ");
        sql.append("WHERE d.objectiveId.objectiveId = :objectiveId ");
        sql.append("AND d.modalityId.modalityId = :modalityId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("objectiveId",objectiveId);
        query.setParameter("modalityId",modalityId);
        List<Dcf> list = query.getResultList();
        return  (list == null || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    public List<Dcf> findAll() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Dcf a ");
        builder.append("order by a.dcfId desc ");
        Query query = this.getEntityManager().createQuery(builder.toString());
        return query.getResultList();
    }


    @Override
    public List<Dcf> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Dcf a ");
        builder.append("WHERE a.stateIdId = :active ");

        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<DcfDTO> findPaginate(int first, int max, String order) throws Exception {
        
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.DcfDTO(a.dcfId,");
        builder.append("a.objectiveId.objectiveId,a.objectiveId.name,a.modalityId.modalityId,a.modalityId.name,a.pattern,a.sessions,a.stateIdId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from Dcf a ");
        builder.append("order by a.");
        builder.append(order);
        int count = findAll().size();
        
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setFirstResult(first);
        query.setMaxResults(max);
        List<DcfDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<Dcf> findByDcf(Dcf dcf) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Dcf a ");
        builder.append("WHERE a.dcfId = :id ");
        setParameter("id", dcf.getDcfId());
        return createQuery(builder.toString());
    }

    @Override
    public List<Dcf> findByFiltro(Dcf dcf) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Dcf a ");
        builder.append("WHERE 1=1 ");
        

        if(dcf.getObjectiveId() != null && dcf.getObjectiveId().getObjectiveId() != null) {
            builder.append("AND a.objectiveId.objectiveId = :objective ");
            setParameter("objective", dcf.getObjectiveId().getObjectiveId());
        }


        if(dcf.getModalityId() != null && dcf.getModalityId().getModalityId() != null) {
            builder.append("AND a.modalityId.modalityId = :modality ");
            setParameter("modality", dcf.getModalityId().getModalityId());
        }

        if(dcf.getPattern() != null && !dcf.getPattern().trim().isEmpty()) {
            builder.append("AND lower(a.pattern) like lower(:pattern) ");
            setParameter("pattern", "%" + dcf.getPattern() + "%");
        }






        if(dcf.getStateId() != null) {
            builder.append("AND a.stateIdId = :stateId ");
            setParameter("stateId", dcf.getStateId());
        }

        return createQuery(builder.toString());
    }
}
