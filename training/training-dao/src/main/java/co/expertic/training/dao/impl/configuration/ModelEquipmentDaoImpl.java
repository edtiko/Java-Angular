package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.model.dto.ModelEquipmentDTO;
import co.expertic.training.model.entities.ModelEquipment;
import co.expertic.training.dao.configuration.ModelEquipmentDao;
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
        sql.append("SELECT new co.expertic.training.model.dto.ModelEquipmentDTO(me.modelEquipmentId, ");
        sql.append("me.name, me.sportEquipmentId.sportEquipmentId) ");
        sql.append("FROM ModelEquipment me ");
        sql.append("WHERE me.sportEquipmentId.sportEquipmentId =:sportEquipmentId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("sportEquipmentId", sportEquipmentId);
        return query.getResultList();
    }
    
}
