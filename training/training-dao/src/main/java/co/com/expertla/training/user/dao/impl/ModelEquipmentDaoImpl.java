/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.user.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.model.dto.ModelEquipmentDTO;
import co.com.expertla.training.model.entities.ModelEquipment;
import co.com.expertla.training.user.dao.ModelEquipmentDao;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class ModelEquipmentDaoImpl extends BaseDAOImpl<ModelEquipment> implements ModelEquipmentDao{

    @Override
    public List<ModelEquipmentDTO> findBySportEquipmentId(Integer sportEquipmentId) throws Exception {
         StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.ModelEquipmentDTO(me.modelEquipmentId, ");
        sql.append("me.name, me.sportEquipmentId.sportEquipmentId) ");
        sql.append("FROM ModelEquipment me ");
        sql.append("WHERE me.sportEquipmentId.sportEquipmentId =:sportEquipmentId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("sportEquipmentId", sportEquipmentId);
        return query.getResultList();
    }
    
}
