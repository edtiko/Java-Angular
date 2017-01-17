package co.expertic.training.service.user;

import co.expertic.training.model.entities.VideoUser;
import java.util.List;

/**
* VideoUser Service <br>
* Creation Date: <br>
* date 24/08/2016 <br>
* @author Angela Ramirez
**/
public interface VideoUserService {
    

    /**
     * Crea videoUser <br>
     * Creation Date: <br>
     * date 24/08/2016 <br>
     * @author Angela Ramirez
     * @param videoUser
     * @return 
     * @throws Exception 
     */
    public VideoUser create(VideoUser videoUser) throws Exception;
    /**
     * Modifica videoUser <br>
     * Creation Date: <br>
     * date 24/08/2016 <br>
     * @author Angela Ramirez
     * @param videoUser
     * @return 
     * @throws Exception 
     */
    public VideoUser store(VideoUser videoUser) throws Exception;
    /**
     * Elimina videoUser<br>
     * Creation Date: <br>
     * date 24/08/2016 <br>
     * @author Angela Ramirez
     * @param videoUser
     * @throws Exception 
     */
    public void remove(VideoUser videoUser) throws Exception;
    /**
     * Obtiene todos los registros de videoUser <br>
     * Creation Date: <br>
     * date 24/08/2016 <br>
     * @author Angela Ramirez
     * @return
     * @throws Exception 
     */
    public List<VideoUser> findAll() throws Exception;

    /**
     * Obtiene el video user por estado activo y usuario <br>
     * Creation Date: <br>
     * fecha 24/08/2016 <br>
     * @author Angela Ramirez
     * @param userId
     * @return 
     * @throws Exception 
     */
    public List<VideoUser> getByUser(Integer userId) throws Exception;
 
}