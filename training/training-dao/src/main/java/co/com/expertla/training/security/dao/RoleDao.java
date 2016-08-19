package co.com.expertla.training.security.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.RoleDTO;
import co.com.expertla.training.model.entities.Role;
import java.util.List;

/**
* Role Dao <br>
* Creation Date: <br>
* date 19/08/2016 <br>
* @author Angela Ramirez O
**/
public interface RoleDao extends BaseDAO<Role> {
      
    /**
     * Obtiene todos los registros de role en roleDTO <br>
     * Creation Date: <br>
     * date 19/08/2016 <br>
     * @author Angela Ramirez O
     * @return
     * @throws Exception 
     */
    public List<RoleDTO> findAll() throws Exception;

}
