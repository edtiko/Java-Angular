package co.expertic.training.dao.configuration;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.dto.ColourIndicatorDTO;
import co.expertic.training.model.entities.ColourIndicator;
import java.util.List;

/**
* ColourIndicator Dao <br>
* Info. Creación: <br>
* fecha Sep 14, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface ColourIndicatorDao extends BaseDAO<ColourIndicator> {
    
    
    /**
     * Obtiene todos los registros de colourIndicator <br>
     * Info. Creación: <br>
     * fecha Sep 14, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<ColourIndicator> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de colourIndicator <br>
     * Info. Creación: <br>
     * fecha Sep 14, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<ColourIndicator> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de colourIndicator paginados <br>
     * Info. Creación: <br>
     * fecha Sep 14, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<ColourIndicatorDTO> findPaginate(int first, int max, String order, String filter) throws Exception;

    /**
     * Obtiene todos los registros de colourIndicator por su id <br>
     * Info. Creación: <br>
     * fecha Sep 14, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param colourIndicator
     * @return
     * @throws Exception 
     */
    public List<ColourIndicator> findByColourIndicator(ColourIndicator colourIndicator) throws Exception;   

    /**
     * Obtiene todos los registros de colourIndicator por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha Sep 14, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param colourIndicator
     * @return
     * @throws Exception 
     */
    public List<ColourIndicator> findByFiltro(ColourIndicator colourIndicator) throws Exception; 

    /**
    * Obtiene todos los registros de colourIndicator por nombre <br>
    * Info. Creación: <br>
    * fecha Sep 14, 2016 <br>
    * @author Andres Felipe Lopez Rodriguez
    * @param colourIndicator
    * @return
    * @throws Exception 
    */
    public List<ColourIndicator> findByName(ColourIndicator colourIndicator) throws Exception;  
}
