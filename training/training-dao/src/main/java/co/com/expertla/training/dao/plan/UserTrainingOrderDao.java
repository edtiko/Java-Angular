package co.com.expertla.training.dao.plan;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.UserTrainingOrder;
import java.util.List;

/**
* UserTrainingOrder Dao <br>
* Info. Creación: <br>
* fecha 12/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface UserTrainingOrderDao extends BaseDAO<UserTrainingOrder> {
    
    
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
     * Obtiene todos los registros de userTrainingOrder por su id <br>
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

}
