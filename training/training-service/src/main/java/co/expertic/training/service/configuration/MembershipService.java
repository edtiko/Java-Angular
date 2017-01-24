package co.expertic.training.service.configuration;

import co.expertic.training.model.dto.MembershipDTO;
import co.expertic.training.model.entities.Membership;
import java.util.List;

/**
* Membership Service <br>
* Info. Creación: <br>
* fecha 21/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface MembershipService {
    

    /**
     * Crea membership <br>
     * Info. Creación: <br>
     * fecha 21/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param membership
     * @return 
     * @throws Exception 
     */
    public Membership create(Membership membership) throws Exception;
    /**
     * Modifica membership <br>
     * Info. Creación: <br>
     * fecha 21/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param membership
     * @return 
     * @throws Exception 
     */
    public Membership store(Membership membership) throws Exception;
    /**
     * Elimina membership<br>
     * Info. Creación: <br>
     * fecha 21/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param membership
     * @throws Exception 
     */
    public void remove(Membership membership) throws Exception;
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
     * Obtiene todos los registros de membership paginados <br>
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