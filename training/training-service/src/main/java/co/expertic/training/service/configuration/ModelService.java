package co.expertic.training.service.configuration;

import co.expertic.training.model.dto.ModelDTO;
import co.expertic.training.model.entities.Model;
import java.util.List;

/**
* Model Service <br>
* Info. Creación: <br>
* fecha Oct 19, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface ModelService {
    

    /**
     * Crea model <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param model
     * @return 
     * @throws Exception 
     */
    public Model create(Model model) throws Exception;
    /**
     * Modifica model <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param model
     * @return 
     * @throws Exception 
     */
    public Model store(Model model) throws Exception;
    /**
     * Elimina model<br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param model
     * @throws Exception 
     */
    public void remove(Model model) throws Exception;
    /**
     * Obtiene todos los registros de model <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Model> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de model <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Model> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de model paginados <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<ModelDTO> findPaginate(int first, int max, String order, String filter) throws Exception;
    
    /**
     * Obtiene todos los registros de model paginados <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param model
     * @return
     * @throws Exception 
     */
    public List<Model> findByModel(Model model) throws Exception;   

    /**
     * Obtiene todos los registros de model por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param model
     * @return
     * @throws Exception 
     */
    public List<Model> findByFiltro(Model model) throws Exception; 


    /**
    * Obtiene todos los registros de model por nombre <br>
    * Info. Creación: <br>
    * fecha Oct 19, 2016 <br>
    * @author Andres Felipe Lopez Rodriguez
    * @param model
    * @return
    * @throws Exception 
    */
    public List<Model> findByName(Model model) throws Exception;  

    
}