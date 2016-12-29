package co.com.expertla.training.service.plan;

import co.com.expertla.training.model.entities.UserTrainingOrder;
import java.util.List;

/**
* UserTrainingOrder Service <br>
* Info. Creación: <br>
* fecha 12/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface UserTrainingOrderService {
    

    /**
     * Crea userTrainingOrder <br>
     * Info. Creación: <br>
     * fecha 12/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userTrainingOrder
     * @return 
     * @throws Exception 
     */
    public UserTrainingOrder create(UserTrainingOrder userTrainingOrder) throws Exception;
    /**
     * Modifica userTrainingOrder <br>
     * Info. Creación: <br>
     * fecha 12/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userTrainingOrder
     * @return 
     * @throws Exception 
     */
    public UserTrainingOrder store(UserTrainingOrder userTrainingOrder) throws Exception;
    /**
     * Elimina userTrainingOrder<br>
     * Info. Creación: <br>
     * fecha 12/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userTrainingOrder
     * @throws Exception 
     */
    public void remove(UserTrainingOrder userTrainingOrder) throws Exception;
    /**
     * Obtiene todos los registros de userTrainingOrder <br>
     * Info. Creación: <br>
     * fecha 12/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<UserTrainingOrder> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de userTrainingOrder <br>
     * Info. Creación: <br>
     * fecha 12/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<UserTrainingOrder> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de userTrainingOrder paginados <br>
     * Info. Creación: <br>
     * fecha 12/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userTrainingOrder
     * @return
     * @throws Exception 
     */
    public List<UserTrainingOrder> findByUserTrainingOrder(UserTrainingOrder userTrainingOrder) throws Exception;   

    /**
     * Obtiene todos los registros de userTrainingOrder por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 12/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userTrainingOrder
     * @return
     * @throws Exception 
     */
    public List<UserTrainingOrder> findByFiltro(UserTrainingOrder userTrainingOrder) throws Exception; 
    
    /**
     * Obtiene el plan comprado por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 12/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userTrainingOrder
     * @return
     * @throws Exception 
     */
    public String getPlanIdByOrder(UserTrainingOrder userTrainingOrder) throws Exception; 

    public UserTrainingOrder findByUserId(Integer userId) throws Exception; 

    public boolean isSuscribed(Integer userId)throws Exception; 

    
}