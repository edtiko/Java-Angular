/**
 * 
 */
package co.com.expertla.training.dao;

import co.com.expertla.base.jpa.BaseDAO;
import java.util.List;

import co.com.expertla.training.model.Usuario;

/**
 * @author Edwin G
 *
 */
public interface UsuarioDao extends BaseDAO<Usuario>{
	
	Usuario findById(Integer id);
        
        List<Usuario> findAllUsers();

}
