package co.com.expertla.training.dao.plan;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.PlanMessageDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.ScriptVideo;
import java.util.List;

/**
* ScriptVideo Dao <br>
* Info. Creación: <br>
* fecha 11/10/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface ScriptVideoDao extends BaseDAO<ScriptVideo> {
    
    
    /**
     * Obtiene todos los registros de scriptVideo <br>
     * Info. Creación: <br>
     * fecha 11/10/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<ScriptVideo> findAll() throws Exception;
    


    /**
     * Obtiene todos los registros de scriptVideo por su id <br>
     * Info. Creación: <br>
     * fecha 11/10/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param scriptVideo
     * @return
     * @throws Exception 
     */
    public List<ScriptVideo> findByScriptVideo(ScriptVideo scriptVideo) throws Exception;   

    /**
     * Obtiene todos los registros de scriptVideo por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 11/10/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param scriptVideo
     * @return
     * @throws Exception 
     */
    public List<ScriptVideo> findByFiltro(ScriptVideo scriptVideo) throws Exception; 
    
    /**
     * Obtiene todos los registros de scriptVideo por usuario que lo envio <br>
     * Info. Creación: <br>
     * fecha 11/10/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return
     * @throws Exception 
     */
    public List<ScriptVideo> getScriptVideoByUserId(Integer userId) throws Exception; 

    /**
     * Consulta los tiempos de respuesta de los mails <br>
     * Info. Creación: <br>
     * fecha Sep 28, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @param users
     * @return 
     * @throws Exception 
     */
    public List<PlanMessageDTO> getResponseTimeScripts(Integer userId, List<UserDTO> users)throws  Exception;
    
    /**
     * Consulta los tiempos de respuesta en horas <br>
     * Info. Creación: <br>
     * fecha Sep 28, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @param users
     * @return 
     * @throws Exception 
     */
    public List<PlanMessageDTO> getResponseCountScripts(Integer userId,List<UserDTO> users) throws Exception;
}
