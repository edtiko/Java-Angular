package co.com.expertla.training.dao.plan;

import co.com.expertla.base.jpa.BaseDAO;
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

}
