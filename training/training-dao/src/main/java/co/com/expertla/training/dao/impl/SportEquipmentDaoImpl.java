package co.com.expertla.training.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.SportEquipmentDao;
import co.com.expertla.training.enums.SportEquipmentTypeEnum;
import co.com.expertla.training.model.dto.SportEquipmentDTO;
import co.com.expertla.training.model.entities.SportEquipment;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
* Dao Implementation for Sport Equipment <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class SportEquipmentDaoImpl extends BaseDAOImpl<SportEquipment> implements SportEquipmentDao {

    @Override
    public List<SportEquipmentDTO> findAll() throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SportEquipmentDTO(s.sportEquipmentId, s.name, s.brandId.name) ");
        sql.append("FROM SportEquipment s ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }

    @Override
    public List<SportEquipmentDTO> findAllRunningShoes() throws Exception {     
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SportEquipmentDTO(s.sportEquipmentId, s.name, s.brandId.name) ");
        sql.append("FROM SportEquipment s ");
        sql.append("WHERE s.sportEquipmentTypeId.sportEquipmentTypeId = :type");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("type", SportEquipmentTypeEnum.RUNNING_SHOES.getId());
        return query.getResultList();
    }
    
    @Override
    public List<SportEquipmentDTO> findAllBikes() throws Exception {    
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SportEquipmentDTO(s.sportEquipmentId, s.name, s.brandId.name) ");
        sql.append("FROM SportEquipment s ");
        sql.append("WHERE s.sportEquipmentTypeId.sportEquipmentTypeId = :type");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("type", SportEquipmentTypeEnum.BIKES.getId());
        return query.getResultList();
    }
    
    @Override
    public List<SportEquipmentDTO> findAllPulsometers() throws Exception {    
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SportEquipmentDTO(s.sportEquipmentId, s.name, s.brandId.name) ");
        sql.append("FROM SportEquipment s ");
        sql.append("WHERE s.sportEquipmentTypeId.sportEquipmentTypeId = :type");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("type", SportEquipmentTypeEnum.PULSOMETER.getId());
        return query.getResultList();
    }
    
    @Override
    public List<SportEquipmentDTO> findAllPotentiometers() throws Exception {    
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SportEquipmentDTO(s.sportEquipmentId, s.name, s.brandId.name) ");
        sql.append("FROM SportEquipment s ");
        sql.append("WHERE s.sportEquipmentTypeId.sportEquipmentTypeId = :type");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("type", SportEquipmentTypeEnum.POTENTIOMETER.getId());
        return query.getResultList();
    }
}
