package co.com.expertla.training.user.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.user.dao.UserProfileDao;
import co.com.expertla.training.enums.SportEquipmentTypeEnum;
import co.com.expertla.training.model.dto.UserProfileDTO;
import java.util.List;

import org.springframework.stereotype.Repository;

import co.com.expertla.training.model.entities.UserProfile;
import javax.persistence.Query;

/**
* Dao Implementation for User Profile <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
@Repository
public class UserProfileDaoImpl extends BaseDAOImpl<UserProfile> implements UserProfileDao {

    @Override
    public UserProfileDTO findDTOByUserId(Integer id) throws Exception {       
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.UserProfileDTO(u.userProfileId, u.indPulsometer, u.indPower, ");
        sql.append("u.ageSport,u.ppm,u.power,u.sportsAchievements,u.aboutMe,u.userId.userId, u.userId.indMetricSys, du.discipline.disciplineId, ");
        sql.append("s.sportId.sportId,eq.sportEquipmentId.sportEquipmentId, u.objetiveId.objetiveId, u.modalityId.modalityId) ");
        sql.append("FROM UserProfile u, DisciplineUser du, UserSport s, EquipmentUserProfile eq, Objetive o, Modality m ");
        sql.append("WHERE u.userId.userId = :id ");
        sql.append("AND du.userId.userId = u.userId.userId ");
        sql.append("AND s.userProfileId.userProfileId = u.userProfileId ");
        sql.append("AND eq.userProfileId.userProfileId = u.userProfileId ");
        sql.append("AND o.objetiveId = u.objetiveId.objetiveId ");
        sql.append("AND m.modalityId = u.modalityId.modalityId ");
        sql.append("AND eq.sportEquipmentId.sportEquipmentTypeId.sportEquipmentTypeId = :runningShoes");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        query.setParameter("runningShoes", SportEquipmentTypeEnum.RUNNING_SHOES.getId());
        List<UserProfileDTO> list = query.getResultList();
        return (list == null || list.isEmpty()) ?  null :list.get(0);
    }
    
    @Override
    public UserProfile findByUserId(Integer id) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u ");
        sql.append("FROM UserProfile u ");
        sql.append("WHERE u.userId.userId = :id ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        List<UserProfile> list = query.getResultList();
        return (list == null || list.isEmpty()) ?  null :list.get(0);
    }

}
