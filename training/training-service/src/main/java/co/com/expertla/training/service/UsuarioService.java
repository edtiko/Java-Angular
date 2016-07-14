/**
 * 
 */
package co.com.expertla.training.service;

import java.util.List;

import co.com.expertla.training.model.entities.Usuario;

/**
 * @author Edwin G
 *
 */
public interface UsuarioService {

	Usuario findById(Integer id);

	Usuario findByName(String name);
	
	Usuario findUserByUsername(String username);

	Integer saveUser(Usuario user);

	int updateUser(Usuario user);

	Integer deleteUserById(Integer id);

	List<Usuario> findAllUsers();

	void deleteAllUsers();

	public boolean isUserExist(Usuario user);
}
