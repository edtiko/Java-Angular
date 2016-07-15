package co.com.expertla.training.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.training.dao.UserProfileDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import co.com.expertla.training.model.entities.UserProfile;

/**
 * 
 * @author Angela Ramirez
 */
@Repository
public class UserProfileDaoImpl extends BaseDAOImpl<UserProfile> implements UserProfileDao {

    @Override
    public UserProfile findById(Integer id) {

        try {
            String qlString = "SELECT u FROM UserProfile u WHERE u.userProfileId = :id";
            setParameter("id", id);
            List<UserProfile> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            System.out.println("Crap");
            return null;
        }
    }

}
