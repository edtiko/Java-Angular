/**
 *
 */
package co.com.expertla.training.user.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import java.util.List;

import org.springframework.stereotype.Repository;

import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.user.dao.UserDao;

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
     * @param id
     * @return 
     * @since 11/07/2016
     * @see co.com.expertla.training.dao.UserDao#findById(long)
     */
    @Override
    public User findById(Integer id) {

        try {
            String qlString = "SELECT u FROM User u WHERE u.userId = :id";
            setParameter("id", id);
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
}
