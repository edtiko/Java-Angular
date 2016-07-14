/**
 *
 */
package co.com.expertla.training.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import java.util.List;

import org.springframework.stereotype.Repository;

import co.com.expertla.training.dao.UsuarioDao;
import co.com.expertla.training.model.entities.Usuario;

/**
 *
 * @author <a href="mailto:edwin.gomez@expertla.com">Edwin Gomez</a>
 * @project training-dao
 * @class UsuarioDaoImpl
 * @since 11/07/2016
 *
 */
@Repository
public class UsuarioDaoImpl extends BaseDAOImpl<Usuario> implements UsuarioDao {

    /**
     * @author <a href="mailto:edwin.gomez@expertla.com">Edwin Gomez</a>
     * @param id
     * @return 
     * @since 11/07/2016
     * @see co.com.expertla.training.dao.UsuarioDao#findById(long)
     */
    @Override
    public Usuario findById(Integer id) {

        try {
            String qlString = "SELECT u FROM Usuario u WHERE u.id = :id";
            setParameter("id", id);
            List<Usuario> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Usuario> findAllUsers() {
        try {
            String qlString = "SELECT u FROM Usuario u ";
            List<Usuario> query = createQuery(qlString);
            return query;
        } catch (Exception e) {
            return null;
        }
    }
}
