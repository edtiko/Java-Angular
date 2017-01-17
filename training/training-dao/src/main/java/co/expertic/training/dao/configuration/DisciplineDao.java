package co.expertic.training.dao.configuration;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.dto.DisciplineDTO;
import co.expertic.training.model.entities.Discipline;
import java.util.List;

/**
* Discipline Dao <br>
* Info. Creación: <br>
* fecha 30/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface DisciplineDao extends BaseDAO<Discipline> {
    
    
    /**
     * Obtiene todos los registros de discipline <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<DisciplineDTO> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de discipline <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Discipline> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de discipline paginados <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @return
     * @throws Exception 
     */
    public List<DisciplineDTO> findPaginate(int first, int max, String order) throws Exception;

    /**
     * Obtiene todos los registros de discipline por su id <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param discipline
     * @return
     * @throws Exception 
     */
    public List<Discipline> findByDiscipline(Discipline discipline) throws Exception;   

    /**
     * Obtiene todos los registros de discipline por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param discipline
     * @return
     * @throws Exception 
     */
    public List<Discipline> findByFiltro(Discipline discipline) throws Exception; 
    
    public List<DisciplineDTO> findByUserId(Integer userId)throws Exception;

}
