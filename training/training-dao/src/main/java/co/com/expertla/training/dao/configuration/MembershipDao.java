package co.com.expertla.training.dao.configuration;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.MembershipDTO;
import co.com.expertla.training.model.entities.Membership;
import java.util.List;

/**
* Membership Dao <br>
* Info. Creación: <br>
* fecha 21/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface MembershipDao extends BaseDAO<Membership> {
    
    
    /**
     * Obtiene todos los registros de membership <br>
     * Info. Creación: <br>
     * fecha 21/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Membership> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de membership <br>
     * Info. Creación: <br>
     * fecha 21/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<MembershipDTO> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de membership paginados <br>
     * Info. Creación: <br>
     * fecha 21/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<MembershipDTO> findPaginate(int first, int max, String order, String filter) throws Exception;

    /**
     * Obtiene todos los registros de membership por su id <br>
     * Info. Creación: <br>
     * fecha 21/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param membership
     * @return
     * @throws Exception 
     */
    public List<Membership> findByMembership(Membership membership) throws Exception;   

    /**
     * Obtiene todos los registros de membership por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 21/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param membership
     * @return
     * @throws Exception 
     */
    public List<Membership> findByFiltro(Membership membership) throws Exception; 

    /**
    * Obtiene todos los registros de membership por nombre <br>
    * Info. Creación: <br>
    * fecha 21/09/2016 <br>
    * @author Andres Felipe Lopez Rodriguez
    * @param membership
    * @return
    * @throws Exception 
    */
    public List<Membership> findByName(Membership membership) throws Exception;  
}
