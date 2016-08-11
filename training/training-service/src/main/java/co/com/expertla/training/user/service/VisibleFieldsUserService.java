package co.com.expertla.training.user.service;

import co.com.expertla.training.model.entities.VisibleFieldsUser;
import java.util.List;

/**
 * Service for VisibleFieldsUser <br>
 * Creation Date : <br>
 * date 10/08/2016 <br>
 * @author Angela Ramírez
 */
public interface VisibleFieldsUserService {
    
    /**
     * Trae todos los registros de VisibleFieldsUser por user Id <br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public List<VisibleFieldsUser> findByUserId(Integer id) throws Exception;
    
    /**
     * Crea VisibleFieldsUser <br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @param visibleFieldsUser
     * @return
     * @throws Exception 
     */
    public VisibleFieldsUser create(VisibleFieldsUser visibleFieldsUser) throws Exception;
    
    /**
     * Modifica VisibleFieldsUser<br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @param visibleFieldsUser
     * @throws Exception
     * @return
     */
    public VisibleFieldsUser merge(VisibleFieldsUser visibleFieldsUser) throws Exception;
}
