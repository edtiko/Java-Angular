package co.expertic.training.dao.user;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.entities.VisibleFieldsUser;
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
    
    /**
     * Crea la lista VisibleFieldsUser <br>
     * Creation Date : <br>
     * date 10/08/2016 <br>
     * @author Angela Ramírez
     * @param visibleFieldsUser
     * @return
     * @throws Exception 
     */
    public List<VisibleFieldsUser> createList(List<VisibleFieldsUser> visibleFieldsUser) throws Exception;
    
    /**
     * Elimina los VisibleFieldsUser por user Id <br>
     * Creation Date : <br>
     * date 10/08/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception 
     */
    public void deleteFieldsByUser(Integer id) throws Exception;
        
}
