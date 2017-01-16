package co.com.expertla.training.dao.impl.configuration;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.configuration.SportEquipmentDao;
import co.com.expertla.training.enums.SportEquipmentTypeEnum;
import co.com.expertla.training.model.dto.MarketingDTO;
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
    
    @Override
    public List<SportEquipmentDTO> findBikesByBikeTypeId(Integer id) throws Exception {    
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SportEquipmentDTO(s.sportEquipmentId, s.name, s.brandId.name) ");
        sql.append("FROM SportEquipment s ");
        sql.append("WHERE s.bikeTypeId.bikeTypeId = :type");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("type", id);
        return query.getResultList();
    }

    @Override
    public List<MarketingDTO> findMarketingPaginate(int first, int max, String order, Object filterDto) throws Exception {
         if(order.contains("-")) {
            order = order.replaceAll("-", "") + " desc";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append(" select t.name as sport_equipment, b.name as brand , m.name as model, count(s.sport_equipment_id)\n"
                + "\n"
                + "from sport_equipment s,\n"
                + "     model_equipment m,\n"
                + "     sport_equipment_type t,\n"
                + "     brand b,\n"
                + "     equipment_user_profile eup,\n"
                + "     user_profile up,\n"
                + "     user_training u,\n"
                + "     role_user ru\n"
                + "     \n"
                + "where s.sport_equipment_id = m.sport_equipment_id\n"
                + "and   b.brand_id = s.brand_id\n"
                + "and   t.sport_equipment_type_id = s.sport_equipment_type_id \n"
                + "and   eup.sport_equipment_id = s.sport_equipment_id\n"
                + "and   eup.model_equipment_id = m.model_equipment_id\n"
                + "and   up.user_profile_id = eup.user_profile_id\n"
                + "and   up.user_id = u.user_id\n"
                + "and   ru.user_id = u.user_id\n");
     
        
         MarketingDTO filter = (MarketingDTO) filterDto;
        if(filter != null) {
           
            builder.append("WHERE ( 1!=1 ");
            builder.append(" AND t.sportEquipmentTypeId.sportEquipmentTypeId = ");
            builder.append(filter.getSportEquipmentType());
            
     
        }

              
        builder.append(" group by t.name, b.name, m.name, s.sport_equipment_id ");
        builder.append("order by a.");
        builder.append(order);
        int count = findAll().size();
        
        Query query = this.getEntityManager().createNativeQuery(builder.toString(), MarketingDTO.class);
        query.setFirstResult(first);
        query.setMaxResults(max);
        List<MarketingDTO> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            list.get(0).setCount(count);
        }
        
        return list;
    }
    
}
