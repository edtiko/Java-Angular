package co.expertic.training.dao.impl.user;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.user.UserProfileDao;
import co.expertic.training.model.dto.DashboardDTO;
import co.expertic.training.model.dto.UserProfileDTO;
import java.util.List;

import org.springframework.stereotype.Repository;

import co.expertic.training.model.entities.UserProfile;
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
        sql.append("SELECT new co.expertic.training.model.dto.UserProfileDTO(u.userProfileId, u.indPulsometer, u.indPower, ");
        sql.append("u.ageSport,u.ppm,u.power,u.sportsAchievements,u.aboutMe,u.userId.userId, u.userId.indMetricSys, ");
        sql.append(" o.objectiveId, u.modalityId.modalityId, u.vo2Running, u.vo2Ciclismo, u.environmentId.environmentId, u.weatherId.weatherId,");
        sql.append(" u.userId.weight, u.userId.height, u.injuryId, u.disease) ");
        sql.append("FROM UserProfile u ");
        sql.append("LEFT JOIN u.objectiveId o ");
        sql.append("WHERE u.userId.userId = :id ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
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
    
    @Override
    public DashboardDTO findDashboardDTOByUserId(Integer id) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.DashboardDTO(u.userId.userId,u.userId.name,u.userId.secondName, u.userId.lastName, ");
        sql.append("u.userId.email,u.userId.birthDate,u.userId.sex,u.userId.weight,u.userId.height, u.userId.phone,u.userId.cellphone,u.userId.address, ");
        sql.append("u.userId.postalCode,u.userId.profilePhoto,u.userId.facebookPage, u.userId.indMetricSys,u.userId.cityId, ");
        sql.append("u.userId.countryId.name,u.ageSport, u.ppm, u.power, u.sportsAchievements, u.aboutMe, o.name, u.modalityId.name, ");
        sql.append("u.userId.twitterPage, u.userId.instagramPage, u.userId.webPage, u.vo2Running, u.vo2Ciclismo, u.injuryId, u.disease, u.environmentId, u.weatherId )");
        sql.append("FROM UserProfile u ");
        sql.append("LEFT JOIN u.objectiveId o ");
        sql.append("WHERE u.userId.userId = :id ");
        
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        List<DashboardDTO> list = query.getResultList();
        
        if(list == null || list.isEmpty()) {
            sql = new StringBuilder();
            sql.append("SELECT new co.expertic.training.model.dto.DashboardDTO(u.userId, u.name,u.secondName, u.lastName, ");
            sql.append("u.email,u.birthDate,u.sex,u.weight,u.phone,u.cellphone,u.address, ");
            sql.append("u.postalCode,u.profilePhoto,u.facebookPage, u.indMetricSys, u.countryId, u.cityId");
            sql.append(" ) ");
            sql.append("FROM User u ");
            sql.append("WHERE u.userId = :id ");
            query = getEntityManager().createQuery(sql.toString());
            query.setParameter("id", id);
            list = query.getResultList();
        }
        
        return (list == null || list.isEmpty()) ?  null :list.get(0);
    }

}