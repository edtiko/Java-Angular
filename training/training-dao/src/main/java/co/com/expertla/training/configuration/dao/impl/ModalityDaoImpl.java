package co.com.expertla.training.configuration.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.configuration.dao.ModalityDao;
import co.com.expertla.training.model.dto.ModalityDTO;
import co.com.expertla.training.model.entities.Modality;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
* Dao Implementation for Modality <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class ModalityDaoImpl extends BaseDAOImpl<Modality> implements ModalityDao {

    @Override
    public List<ModalityDTO> findAll() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.ModalityDTO(m.modalityId,m.name) ");
        sql.append("FROM Modality m ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }
    
    @Override
    public List<ModalityDTO> findByDisciplineId(Integer id) throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.ModalityDTO(m.modalityId,m.name) ");
        sql.append("FROM Modality m ");
        sql.append("WHERE m.disciplineId.disciplineId = :id");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        return query.getResultList();
    }

}
