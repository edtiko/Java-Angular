package co.com.expertla.training.user.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.VisibleFieldsUser;
import java.util.List;

/**
* Dao for Visible Fields User <br>
* Creation Date : <br>
* date 10/08/2016 <br>
* @author Angela Ramírez
**/
public interface VisibleFieldsUserDao extends BaseDAO<VisibleFieldsUser>{
	
    /**
     * Trae todos los registros de Visible Fields User por user Id<br>
     * Creation Date : <br>
     * date 10/08/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public List<VisibleFieldsUser> findByUserId(Integer id) throws Exception;
        
}
