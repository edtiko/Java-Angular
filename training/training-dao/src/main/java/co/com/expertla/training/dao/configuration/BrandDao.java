package co.com.expertla.training.dao.configuration;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.BrandDTO;
import co.com.expertla.training.model.entities.Brand;
import java.util.List;

/**
* Brand Dao <br>
* Info. Creación: <br>
* fecha Sep 9, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface BrandDao extends BaseDAO<Brand> {
    
    
    /**
     * Obtiene todos los registros de brand <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Brand> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de brand <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Brand> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de brand paginados <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<BrandDTO> findPaginate(int first, int max, String order, String filter) throws Exception;

    /**
     * Obtiene todos los registros de brand por su id <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param brand
     * @return
     * @throws Exception 
     */
    public List<Brand> findByBrand(Brand brand) throws Exception;   

    /**
     * Obtiene todos los registros de brand por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha Sep 9, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param brand
     * @return
     * @throws Exception 
     */
    public List<Brand> findByFiltro(Brand brand) throws Exception; 

    /**
    * Obtiene todos los registros de brand por nombre <br>
    * Info. Creación: <br>
    * fecha Sep 9, 2016 <br>
    * @author Andres Felipe Lopez Rodriguez
    * @param brand
    * @return
    * @throws Exception 
    */
    public List<Brand> findByName(Brand brand) throws Exception;  
}
