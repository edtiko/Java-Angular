package co.com.expertla.training.service.configuration;

import co.com.expertla.training.model.dto.DcfDTO;
import co.com.expertla.training.model.entities.Dcf;
import java.util.List;

/**
* Dcf Service <br>
* Info. Creación: <br>
* fecha Sep 6, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface DcfService {
    

    /**
     * Crea dcf <br>
     * Info. Creación: <br>
     * fecha Sep 6, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param dcf
     * @return 
     * @throws Exception 
     */
    public Dcf create(Dcf dcf) throws Exception;
    /**
     * Modifica dcf <br>
     * Info. Creación: <br>
     * fecha Sep 6, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param dcf
     * @return 
     * @throws Exception 
     */
    public Dcf store(Dcf dcf) throws Exception;
    /**
     * Elimina dcf<br>
     * Info. Creación: <br>
     * fecha Sep 6, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param dcf
     * @throws Exception 
     */
    public void remove(Dcf dcf) throws Exception;
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
     * Obtiene todos los registros de dcf paginados <br>
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