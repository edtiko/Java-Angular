package co.expertic.training.dao.configuration;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.dto.PhysiologicalCapacityDTO;
import co.expertic.training.model.entities.PhysiologicalCapacity;
import java.util.List;

/**
* PhysiologicalCapacity Dao <br>
* Info. Creación: <br>
* fecha Sep 2, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface PhysiologicalCapacityDao extends BaseDAO<PhysiologicalCapacity> {
    
    
    /**
     * Obtiene todos los registros de physiologicalCapacity <br>
     * Info. Creación: <br>
     * fecha Sep 2, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<PhysiologicalCapacity> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de physiologicalCapacity <br>
     * Info. Creación: <br>
     * fecha Sep 2, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<PhysiologicalCapacity> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de physiologicalCapacity paginados <br>
     * Info. Creación: <br>
     * fecha Sep 2, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @return
     * @throws Exception 
     */
    public List<PhysiologicalCapacityDTO> findPaginate(int first, int max, String order) throws Exception;

    /**
     * Obtiene todos los registros de physiologicalCapacity por su id <br>
     * Info. Creación: <br>
     * fecha Sep 2, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param physiologicalCapacity
     * @return
     * @throws Exception 
     */
    public List<PhysiologicalCapacity> findByPhysiologicalCapacity(PhysiologicalCapacity physiologicalCapacity) throws Exception;   

    /**
     * Obtiene todos los registros de physiologicalCapacity por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha Sep 2, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param physiologicalCapacity
     * @return
     * @throws Exception 
     */
    public List<PhysiologicalCapacity> findByFiltro(PhysiologicalCapacity physiologicalCapacity) throws Exception; 

    public List<PhysiologicalCapacity> findAllAvailable() throws Exception; 

}
