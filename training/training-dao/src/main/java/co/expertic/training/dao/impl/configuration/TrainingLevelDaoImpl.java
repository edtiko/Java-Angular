/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.configuration.TrainingLevelDao;
import co.expertic.training.enums.StateEnum;
import co.expertic.training.model.dto.TrainingLevelDTO;
import co.expertic.training.model.entities.TrainingLevel;
import co.expertic.training.model.entities.TrainingLevelType;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class TrainingLevelDaoImpl extends BaseDAOImpl<TrainingLevel> implements TrainingLevelDao {

    @Override
    public TrainingLevelDTO findById(Integer trainingLevelId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.TrainingLevelDTO(t)");
        sql.append("FROM TrainingLevel t ");
        sql.append("WHERE t.trainingLevelId = :trainingLevelId ");
      
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("trainingLevelId", trainingLevelId);
        List<TrainingLevelDTO> list = query.getResultList();

        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    
        @Override
    public TrainingLevel getById(Integer trainingLevelId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t ");
        sql.append("FROM TrainingLevel t ");
        sql.append("WHERE t.trainingLevelId = :trainingLevelId ");
      
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("trainingLevelId", trainingLevelId);
        List<TrainingLevel> list = query.getResultList();

        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }


    @Override
    public List<TrainingLevelDTO> findByModality(Integer modalityId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.TrainingLevelDTO(o.trainingLevelId,o.trainingLevelTypeId.name) ");
        sql.append("FROM TrainingLevel o ");
        sql.append("WHERE o.modalityId.modalityId = :modalityId ");
        sql.append(" ORDER BY o.trainingLevelId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("modalityId", modalityId);
        return query.getResultList();
    }

    @Override
    public List<TrainingLevelDTO> findAllActive() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.TrainingLevelDTO(t) ");
        sql.append("FROM TrainingLevel t ");
        sql.append(" ORDER BY t.trainingLevelId ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }
    
    
    @Override
    public List<TrainingLevelType> getTrainingLevelTypeActive() throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t ");
        sql.append("FROM TrainingLevelType t ");
        sql.append("WHERE t.stateId = :active ");
        sql.append("ORDER BY t.trainingLevelTypeId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("active", StateEnum.ACTIVE.getId().shortValue());
        List<TrainingLevelType> list = query.getResultList();

        return list;
    }

}
