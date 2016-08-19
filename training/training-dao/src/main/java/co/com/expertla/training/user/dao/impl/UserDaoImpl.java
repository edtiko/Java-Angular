package co.com.expertla.training.user.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.UserDTO;
import java.util.List;

import org.springframework.stereotype.Repository;

import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.user.dao.UserDao;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;

/**
 *
 * @author <a href="mailto:edwin.gomez@expertla.com">Edwin Gomez</a>
 * @project training-dao
 * @class UsuarioDaoImpl
 * @since 11/07/2016
 *
 */
@Repository
public class UserDaoImpl extends BaseDAOImpl<User> implements UserDao {

    /**
     * @author <a href="mailto:edwin.gomez@expertla.com">Edwin Gomez</a>
     * @param userId
     * @param id
     * @return
     * @since 11/07/2016
     * @see co.com.expertla.training.dao.UserDao#findById(long)
     */
    @Override
    public User findById(Integer userId) {

        try {
            String qlString = "SELECT u FROM User u WHERE u.userId = :userId";
            setParameter("userId", userId);
            List<User> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<User> findAllUsers() {
        try {
            String qlString = "SELECT u FROM User u ";
            List<User> query = createQuery(qlString);
            return query;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Integer saveUser(User user) throws Exception {
        return create(user).getUserId();
    }

    @Override
    public Integer updateUser(User user) {
        try {
            return merge(user).getUserId();
        } catch (DAOException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteUser(User user) {
        try {
            remove(user, user.getUserId());
        } catch (DAOException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean isUser(String username, String password) {
        try {
            String qlString = "SELECT u FROM User u WHERE u.login = :login AND u.password = :password";
            setParameter("login", username);
            setParameter("password", password);
            List<User> query = createQuery(qlString);
            return (query.get(0) != null);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User findUserByUsername(String userName) {
        try {
            String qlString = "SELECT u FROM User u WHERE u.login = :login";
            setParameter("login", userName);
            List<User> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void saveProfilePhoto(byte[] bytes, Integer userId) {
        try {
            User user = findById(userId);
            user.setProfilePhoto(bytes);
            merge(user);
        } catch (DAOException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @Override
    public List<UserDTO> findAllUsersWithDiscipline() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.UserDTO(u.userId, u.login, u.name, u.secondName, u.lastName, ");
        sql.append("u.email,u.sex,u.phone,du.discipline.disciplineId,u.stateId, r.roleId.roleId )");
        sql.append("FROM User u, DisciplineUser du, RoleUser r ");
        sql.append("WHERE u.userId = du.userId.userId "); 
        sql.append("AND u.userId = r.userId.userId ");
        Query query = getEntityManager().createQuery(sql.toString());
        List<UserDTO> list = query.getResultList();
        return list;
    }
}
