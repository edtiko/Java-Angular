package co.com.expertla.training.service.plan;

import co.com.expertla.training.model.entities.ScriptVideo;
import java.util.List;

/**
* ScriptVideo Service <br>
* Info. Creación: <br>
* fecha 11/10/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface ScriptVideoService {
    

    /**
     * Crea scriptVideo <br>
     * Info. Creación: <br>
     * fecha 11/10/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param scriptVideo
     * @return 
     * @throws Exception 
     */
    public ScriptVideo create(ScriptVideo scriptVideo) throws Exception;
    /**
     * Modifica scriptVideo <br>
     * Info. Creación: <br>
     * fecha 11/10/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param scriptVideo
     * @return 
     * @throws Exception 
     */
    public ScriptVideo store(ScriptVideo scriptVideo) throws Exception;
    /**
     * Elimina scriptVideo<br>
     * Info. Creación: <br>
     * fecha 11/10/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param scriptVideo
     * @throws Exception 
     */
    public void remove(ScriptVideo scriptVideo) throws Exception;
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
     * Obtiene todos los registros de scriptVideo paginados <br>
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
    
    



    
}