package co.expertic.training.dao.plan;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.dto.DcfDTO;
import co.expertic.training.model.entities.Dcf;
import java.util.List;

/**
* Dao for Dcf (Distribucion porcentual de Capacidades Fisiologicas) <br>
* Creation Date : <br>
* date 19/07/2016 <br>
* @author Angela Ramírez
**/
public interface DcfDao extends BaseDAO<Dcf>{
	
    /**
     * Trae todos los registros de dcf por objective id y modality Id <br>
     * Creation Date : <br>
     * date 19/07/2016 <br>
     * @author Angela Ramírez
     * @param objectiveId
     * @param modalityId
     * @throws Exception
     * @return
     */
    public Dcf findByObjectiveIdAndModalityId(Integer objectiveId, Integer modalityId) throws Exception;
        
    /**
     * Obtiene todos los registros de dcf <br>
     * Info. Creación: <br>
     * fecha Sep 6, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Dcf> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de dcf <br>
     * Info. Creación: <br>
     * fecha Sep 6, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Dcf> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de dcf paginados <br>
     * Info. Creación: <br>
     * fecha Sep 6, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @return
     * @throws Exception 
     */
    public List<DcfDTO> findPaginate(int first, int max, String order) throws Exception;

    /**
     * Obtiene todos los registros de dcf por su id <br>
     * Info. Creación: <br>
     * fecha Sep 6, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param dcf
     * @return
     * @throws Exception 
     */
    public List<Dcf> findByDcf(Dcf dcf) throws Exception;   

    /**
     * Obtiene todos los registros de dcf por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha Sep 6, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param dcf
     * @return
     * @throws Exception 
     */
    public List<Dcf> findByFiltro(Dcf dcf) throws Exception; 
}
