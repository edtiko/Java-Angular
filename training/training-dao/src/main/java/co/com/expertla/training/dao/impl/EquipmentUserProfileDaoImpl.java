package co.com.expertla.training.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.EquipmentUserProfileDao;
import co.com.expertla.training.enums.SportEquipmentTypeEnum;
import co.com.expertla.training.model.dto.SportEquipmentDTO;
import co.com.expertla.training.model.entities.EquipmentUserProfile;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
* Dao Implementation for Equipment User Profile <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class EquipmentUserProfileDaoImpl extends BaseDAOImpl<EquipmentUserProfile> implements EquipmentUserProfileDao {

    @Override
    public List<SportEquipmentDTO> findAllByUserId(Integer id) throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SportEquipmentDTO(eq.sportEquipmentId.sportEquipmentId, ");
        sql.append("eq.sportEquipmentId.name, eq.sportEquipmentId.brandId.name) ");
        sql.append("FROM EquipmentUserProfile eq ");
        sql.append("WHERE eq.userProfileId.userId.userId =:id ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<SportEquipmentDTO> findRunningShoesByUserId(Integer id) throws Exception {     
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SportEquipmentDTO(eq.sportEquipmentId.sportEquipmentId, ");
        sql.append("eq.sportEquipmentId.name, eq.sportEquipmentId.brandId.name) ");
        sql.append("FROM EquipmentUserProfile eq ");
        sql.append("WHERE eq.userProfileId.userId.userId =:id ");
        sql.append("AND eq.sportEquipmentId.sportEquipmentTypeId.sportEquipmentTypeId =:type ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        query.setParameter("type", SportEquipmentTypeEnum.RUNNING_SHOES.getId());
        return query.getResultList();
    }
    
    @Override
    public List<SportEquipmentDTO> findBikesByUserId(Integer id) throws Exception {    
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SportEquipmentDTO(eq.sportEquipmentId.sportEquipmentId, ");
        sql.append("eq.sportEquipmentId.name, eq.sportEquipmentId.brandId.name) ");
        sql.append("FROM EquipmentUserProfile eq ");
        sql.append("WHERE eq.userProfileId.userId.userId =:id ");
        sql.append("AND eq.sportEquipmentId.sportEquipmentTypeId.sportEquipmentTypeId =:type ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        query.setParameter("type", SportEquipmentTypeEnum.BIKES.getId());
        return query.getResultList();
    }
    
    @Override
    public List<SportEquipmentDTO> findPulsometersByUserId(Integer id) throws Exception {    
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SportEquipmentDTO(eq.sportEquipmentId.sportEquipmentId, ");
        sql.append("eq.sportEquipmentId.name, eq.sportEquipmentId.brandId.name) ");
        sql.append("FROM EquipmentUserProfile eq ");
        sql.append("WHERE eq.userProfileId.userId.userId =:id ");
        sql.append("AND eq.sportEquipmentId.sportEquipmentTypeId.sportEquipmentTypeId =:type ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        query.setParameter("type", SportEquipmentTypeEnum.PULSOMETER.getId());
        return query.getResultList();
    }
    
    @Override
    public List<SportEquipmentDTO> findPotentiometersByUserId(Integer id) throws Exception {    
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SportEquipmentDTO(eq.sportEquipmentId.sportEquipmentId, ");
        sql.append("eq.sportEquipmentId.name, eq.sportEquipmentId.brandId.name) ");
        sql.append("FROM EquipmentUserProfile eq ");
        sql.append("WHERE eq.userProfileId.userId.userId =:id ");
        sql.append("AND eq.sportEquipmentId.sportEquipmentTypeId.sportEquipmentTypeId =:type ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        query.setParameter("type", SportEquipmentTypeEnum.POTENTIOMETER.getId());
        return query.getResultList();
    }
}
