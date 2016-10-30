package co.com.expertla.training.dao.impl.configuration;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.configuration.ModalityDao;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.ModalityDTO;
import co.com.expertla.training.model.entities.Modality;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
* Modality Dao Impl <br>
* Info. Creación: <br>
* fecha Sep 5, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Repository
public class ModalityDaoImpl extends BaseDAOImpl<Modality> implements ModalityDao {

    public ModalityDaoImpl() {
    }
    
    @Override
    public List<Modality> findAllActive() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Modality a ");
        builder.append("WHERE a.stateId = :active ");
        builder.append("order by a.name ");
        setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        return createQuery(builder.toString());
    }

    @Override
    public List<ModalityDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        filter = filter.toUpperCase();
        if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("select new co.com.expertla.training.model.dto.ModalityDTO(a.modalityId,");
        builder.append("a.name,a.disciplineId,a.stateId, a.creationDate, a.lastUpdate,");
        builder.append("(select u.login FROM User u WHERE a.userCreate = u.userId), (select u.login FROM User u WHERE a.userUpdate = u.userId),");
        builder.append("(select u.userId FROM User u WHERE a.userCreate = u.userId), (select u.userId FROM User u WHERE a.userUpdate = u.userId)");
        builder.append(") from Modality a ");
        
        if(filter != null && !filter.trim().isEmpty()) {
            builder.append("WHERE ( UPPER(a.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(a.disciplineId.name) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(select u.name FROM State u WHERE u.stateId = a.stateId) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(select u.login FROM User u WHERE a.userCreate = u.userId) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append("OR UPPER(select u.login FROM User u WHERE a.userUpdate = u.userId) like '%");
            builder.append(filter);
            builder.append("%'");
            builder.append(")");
        }
        
        builder.append("order by a.");
        builder.append(order);
        
        int count = this.getEntityManager().createQuery(builder.toString()).getResultList().size();
        Query query = this.getEntityManager().createQuery(builder.toString());
        query.setFirstResult(first);
        query.setMaxResults(max);
        List<ModalityDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }

    
    @Override
    public List<Modality> findByModality(Modality modality) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Modality a ");
        builder.append("WHERE a.modalityId = :id ");
        setParameter("id", modality.getModalityId());
        return createQuery(builder.toString());
    }

    @Override
    public List<Modality> findByFiltro(Modality modality) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("select a from Modality a ");
        builder.append("WHERE 1=1 ");
        
        if(modality.getName() != null && !modality.getName().trim().isEmpty()) {
            builder.append("AND lower(a.name) like lower(:name) ");
            setParameter("name", modality.getName() );
        }



        if(modality.getDisciplineId() != null && modality.getDisciplineId().getDisciplineId() != null) {
            builder.append("AND a.disciplineId.disciplineId = :discipline ");
            setParameter("discipline", modality.getDisciplineId().getDisciplineId());
        }



        if(modality.getStateId() != null) {
            builder.append("AND a.stateId = :state ");
            setParameter("state", modality.getStateId());
        }
        
            builder.append(" ORDER BY a.name ");

        return createQuery(builder.toString());
    }
    
    @Override
    public List<ModalityDTO> findAll() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.ModalityDTO(m.modalityId,m.name) ");
        sql.append("FROM Modality m ");
        sql.append("order by m.name ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }
    
    @Override
    public List<ModalityDTO> findByDisciplineId(Integer id) throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.ModalityDTO(m.modalityId,m.name) ");
        sql.append("FROM Modality m ");
        sql.append("WHERE m.disciplineId.disciplineId = :id");
        sql.append("order by m.name ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        return query.getResultList();
    }
    
    @Override
    public List<ModalityDTO> findByObjectiveId(Integer objectiveId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT new co.com.expertla.training.model.dto.ModalityDTO(m.modalityId.modalityId,m.modalityId.name) ");
        sql.append("FROM Dcf m ");
        sql.append("WHERE m.objectiveId.objectiveId = :id ");
        sql.append(" ORDER BY m.modalityId.name ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", objectiveId);
        return query.getResultList();
    }

}