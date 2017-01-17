package co.expertic.training.dao.impl.configuration;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.configuration.SportEquipmentDao;
import co.expertic.training.enums.SportEquipmentTypeEnum;
import co.expertic.training.model.dto.MarketingDTO;
import co.expertic.training.model.dto.SportEquipmentDTO;
import co.expertic.training.model.entities.SportEquipment;
import java.util.ArrayList;
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
        sql.append("SELECT new co.expertic.training.model.dto.SportEquipmentDTO(s.sportEquipmentId, s.name, s.brandId.name) ");
        sql.append("FROM SportEquipment s ");
        Query query = getEntityManager().createQuery(sql.toString());
        return query.getResultList();
    }

    @Override
    public List<SportEquipmentDTO> findAllRunningShoes() throws Exception {     
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.SportEquipmentDTO(s.sportEquipmentId, s.name, s.brandId.name) ");
        sql.append("FROM SportEquipment s ");
        sql.append("WHERE s.sportEquipmentTypeId.sportEquipmentTypeId = :type");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("type", SportEquipmentTypeEnum.RUNNING_SHOES.getId());
        return query.getResultList();
    }
    
    @Override
    public List<SportEquipmentDTO> findAllBikes() throws Exception {    
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.SportEquipmentDTO(s.sportEquipmentId, s.name, s.brandId.name) ");
        sql.append("FROM SportEquipment s ");
        sql.append("WHERE s.sportEquipmentTypeId.sportEquipmentTypeId = :type");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("type", SportEquipmentTypeEnum.BIKES.getId());
        return query.getResultList();
    }
    
    @Override
    public List<SportEquipmentDTO> findAllPulsometers() throws Exception {    
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.SportEquipmentDTO(s.sportEquipmentId, s.name, s.brandId.name) ");
        sql.append("FROM SportEquipment s ");
        sql.append("WHERE s.sportEquipmentTypeId.sportEquipmentTypeId = :type");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("type", SportEquipmentTypeEnum.PULSOMETER.getId());
        return query.getResultList();
    }
    
    @Override
    public List<SportEquipmentDTO> findAllPotentiometers() throws Exception {    
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.SportEquipmentDTO(s.sportEquipmentId, s.name, s.brandId.name) ");
        sql.append("FROM SportEquipment s ");
        sql.append("WHERE s.sportEquipmentTypeId.sportEquipmentTypeId = :type");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("type", SportEquipmentTypeEnum.POTENTIOMETER.getId());
        return query.getResultList();
    }
    
    @Override
    public List<SportEquipmentDTO> findBikesByBikeTypeId(Integer id) throws Exception {    
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.SportEquipmentDTO(s.sportEquipmentId, s.name, s.brandId.name) ");
        sql.append("FROM SportEquipment s ");
        sql.append("WHERE s.bikeTypeId.bikeTypeId = :type");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("type", id);
        return query.getResultList();
    }

    
      public int findAllMarketing(MarketingDTO filterDto) throws Exception {      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.SportEquipmentDTO(s.sportEquipmentId, s.name, s.brandId.name) ");
        sql.append("FROM SportEquipment s ");
        Query query = getEntityManager().createQuery(sql.toString());
        return 1;
    }
      
    @Override
    public List<MarketingDTO> findMarketingPaginate(MarketingDTO filterDto) throws Exception {
        String order = filterDto.getOrder();
        if (filterDto.getOrder().contains("-")) {
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
                + "     role_user ru, \n"
                 + "    discipline_user du\n"
                + "     \n"
                + "where s.sport_equipment_id = m.sport_equipment_id\n"
                + "and   b.brand_id = s.brand_id\n"
                + "and   du.user_id = u.user_id\n"
                + "and   t.sport_equipment_type_id = s.sport_equipment_type_id \n"
                + "and   eup.sport_equipment_id = s.sport_equipment_id\n"
                + "and   eup.model_equipment_id = m.model_equipment_id\n"
                + "and   up.user_profile_id = eup.user_profile_id\n"
                + "and   up.user_id = u.user_id\n"
                + "and   ru.user_id = u.user_id\n");

        builder.append(" and s.sport_equipment_type_id = ");
        builder.append(filterDto.getSportEquipmentType());
        
        if (filterDto.getInitDate() != null && filterDto.getEndDate() != null) {

        }

        if (filterDto.getAge() > 0) {
            builder.append(" and EXTRACT(YEAR from AGE(u.birth_date)) = ").append(filterDto.getAge());
        }
        if (filterDto.getCountryId() != null) {
            builder.append(" and u.country_id = ").append(filterDto.getCountryId());
        }
        if (filterDto.getBike() != null) {
           builder.append(" and s.sport_equipment_id = ").append(filterDto.getBike());
        }
        if (filterDto.getBikeModel() != null) {
            builder.append(" and m.model_equipment_id = ").append(filterDto.getBikeModel());
        }
        if (filterDto.getDiscipline() != null) {
            builder.append(" and du.discipline_id = ").append(filterDto.getDiscipline());
        }
        if (filterDto.getRole() != null) {
            builder.append(" and ru.role_id = ").append(filterDto.getRole());
        }
        if (filterDto.getPotentiometer() != null) {
            builder.append(" and s.sport_equipment_id = ").append(filterDto.getPotentiometer());
        }
        if (filterDto.getPotentiometerModel() != null) {
            builder.append(" and m.model_equipment_id = ").append(filterDto.getPotentiometerModel());
        }
        if (filterDto.getPulsometer() != null) {
            builder.append(" and s.sport_equipment_id = ").append(filterDto.getPulsometer());
        }
        if (filterDto.getPulsometerModel() != null) {
            builder.append(" and m.model_equipment_id = ").append(filterDto.getPulsometerModel());
        }
        if (filterDto.getSex() != null) {
            builder.append(" and u.sex = ").append(filterDto.getSex());
        }
        if (filterDto.getShoe() != null) {
            builder.append(" and s.sport_equipment_id = ").append(filterDto.getShoe());
        }

        builder.append(" group by t.name, b.name, m.name, s.sport_equipment_id ");
        builder.append(" order by t.");
        builder.append(order);
        int count = findAllMarketing(filterDto);

        Query query = this.getEntityManager().createNativeQuery(builder.toString());
        query.setFirstResult(filterDto.getPage());
        query.setMaxResults(filterDto.getLimit());
        List<Object[]> list = query.getResultList();
        List<MarketingDTO> res = new ArrayList<>();
        
        
        list.stream().forEach((r) -> {
            res.add(new MarketingDTO((String)r[0], (String)r[1], (String)r[2], (Long)r[3]));
        });

        if (res != null && !res.isEmpty()) {
            res.get(0).setCount(count);
        }

        return res;
    }
    
}
