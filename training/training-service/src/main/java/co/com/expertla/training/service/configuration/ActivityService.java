package co.com.expertla.training.service.configuration;

import co.com.expertla.training.model.dto.ActivityDTO;
import co.com.expertla.training.model.entities.Activity;
import java.util.List;

/**
* Activity Service <br>
* Info. Creación: <br>
* fecha 5/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface ActivityService {
    

    /**
     * Crea activity <br>
     * Info. Creación: <br>
     * fecha 5/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param activity
     * @return 
     * @throws Exception 
     */
    public Activity create(Activity activity) throws Exception;
    /**
     * Modifica activity <br>
     * Info. Creación: <br>
     * fecha 5/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param activity
     * @throws Exception 
     */
    public Activity store(Activity activity) throws Exception;
    /**
     * Elimina activity<br>
     * Info. Creación: <br>
     * fecha 5/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param activity
     * @return 
     * @throws Exception 
     */
    public void remove(Activity activity) throws Exception;
    /**
     * Obtiene todos los registros de activity <br>
     * Info. Creación: <br>
     * fecha 5/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Activity> findAll() throws Exception;

    /**
     * Obtiene todos los registros activos de activity <br>
     * Info. Creación: <br>
     * fecha 5/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Activity> findAllActive() throws Exception;
    /**
     * Obtiene todos los registros de activity por su id <br>
     * Info. Creación: <br>
     * fecha 5/08/2016 <br>
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
     * @author Andres Felipe Lopez Rodriguez
     * @param activity
     * @return
     * @throws Exception 
     */
    public List<Activity> findByFiltro(Activity activity) throws Exception; 
    
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


    public Integer createManualActivity(ActivityDTO activity) throws Exception;
     
    public List<ActivityDTO> findManualActivitiesByUserId(Integer userId) throws Exception;

    
}