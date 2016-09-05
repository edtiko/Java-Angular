package co.com.expertla.training.dao.configuration;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.ActivityDTO;
import co.com.expertla.training.model.entities.Activity;
import java.util.List;

/**
 * Activity Dao <br>
 * Info. Creación: <br>
 * fecha 5/08/2016 <br>
 *
 * @author Andres Felipe Lopez Rodriguez
*
 */
public interface ActivityDao extends BaseDAO<Activity> {

    /**
     * Obtiene todos los registros de activity <br>
     * Info. Creación: <br>
     * fecha 5/08/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception
     */
    public List<Activity> findAll() throws Exception;

    /**
     * Obtiene todos los registros activos de activity <br>
     * Info. Creación: <br>
     * fecha 5/08/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception
     */
    public List<Activity> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de activity por su id <br>
     * Info. Creación: <br>
     * fecha 5/08/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param activity
     * @return
     * @throws Exception
     */
    public List<Activity> findByActivity(Activity activity) throws Exception;

    /**
     * Obtiene todos los registros de activity por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 5/08/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param activity
     * @return
     * @throws Exception
     */
    public List<Activity> findByFiltro(Activity activity) throws Exception;

    /**
     * Trae todos los registros de Activity por modality Id y objective Id<br>
     * Creation Date : <br>
     * date 21/07/2016 <br>
     *
     * @author Angela Ramírez
     * @param objectiveId
     * @param modalityId
     * @throws Exception
     * @return
     */
    public List<Activity> findByObjectiveIdAndModalityId(Integer objectiveId, Integer modalityId) throws Exception;
    
    
    /**
     * Obtiene todos los registros de activity por la disciplina del usuario <br>
     * Info. Creación: <br>
     * fecha 08/08/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param usuarioId
     * @return
     * @throws Exception
     */
    public List<ActivityDTO> findByUserDiscipline(Integer usuarioId) throws Exception;

}
