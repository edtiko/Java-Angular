package co.com.expertla.training.dao.impl.user;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.user.EquipmentUserProfileDao;
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
    public List<SportEquipmentDTO> findAllDTOByUserId(Integer id) throws Exception {      
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
    public List<SportEquipmentDTO> findDTORunningShoesByUserId(Integer id) throws Exception {     
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
    public List<SportEquipmentDTO> findDTOBikesByUserId(Integer id) throws Exception {    
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SportEquipmentDTO(eq.sportEquipmentId.sportEquipmentId, ");
        sql.append("eq.sportEquipmentId.name, eq.sportEquipmentId.brandId.name, eq.modelEquipmentId, eq.sportEquipmentId.bikeTypeId.bikeTypeId) ");
        sql.append("FROM EquipmentUserProfile eq ");
        sql.append("WHERE eq.userProfileId.userId.userId =:id ");
        sql.append("AND eq.sportEquipmentId.sportEquipmentTypeId.sportEquipmentTypeId =:type ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        query.setParameter("type", SportEquipmentTypeEnum.BIKES.getId());
        return query.getResultList();
    }
    
    @Override
    public List<SportEquipmentDTO> findDTOPulsometersByUserId(Integer id) throws Exception {    
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SportEquipmentDTO(eq.sportEquipmentId.sportEquipmentId, ");
        sql.append("eq.sportEquipmentId.name, eq.sportEquipmentId.brandId.name, eq.modelEquipmentId) ");
        sql.append("FROM EquipmentUserProfile eq ");
        sql.append("WHERE eq.userProfileId.userId.userId =:id ");
        sql.append("AND eq.sportEquipmentId.sportEquipmentTypeId.sportEquipmentTypeId =:type ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        query.setParameter("type", SportEquipmentTypeEnum.PULSOMETER.getId());
        return query.getResultList();
    }
    
    @Override
    public List<SportEquipmentDTO> findDTOPotentiometersByUserId(Integer id) throws Exception {    
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SportEquipmentDTO(eq.sportEquipmentId.sportEquipmentId, ");
        sql.append("eq.sportEquipmentId.name, eq.sportEquipmentId.brandId.name, eq.modelEquipmentId) ");
        sql.append("FROM EquipmentUserProfile eq ");
        sql.append("WHERE eq.userProfileId.userId.userId =:id ");
        sql.append("AND eq.sportEquipmentId.sportEquipmentTypeId.sportEquipmentTypeId =:type ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        query.setParameter("type", SportEquipmentTypeEnum.POTENTIOMETER.getId());
        return query.getResultList();
    }
    
    @Override
    public List<EquipmentUserProfile> findByUserId (Integer userId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT eq ");
        sql.append("FROM EquipmentUserProfile eq ");
        sql.append("WHERE eq.userProfileId.userId.userId =:userId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
