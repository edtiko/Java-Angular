package co.expertic.training.dao.plan;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.dto.PlanMessageDTO;
import co.expertic.training.model.dto.PlanVideoDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.ScriptVideo;
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
    public List<ScriptVideo> getScriptVideoToStarId(Integer userId) throws Exception; 
    
    /**
     * Obtiene todos los registros de scriptVideo por usuario que lo envio <br>
     * Info. Creación: <br>
     * fecha 11/10/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return
     * @throws Exception 
     */
    public List<ScriptVideo> getScriptVideoByStarId(Integer userId) throws Exception; 

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

    public List<PlanVideoDTO> getByPlan(Integer planId) throws Exception;

    public ScriptVideo findByPlanVideoId(Integer id)throws Exception;
}
