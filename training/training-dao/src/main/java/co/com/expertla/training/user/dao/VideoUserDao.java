package co.com.expertla.training.user.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.VideoUser;
import java.util.List;

/**
* VideoUser Dao <br>
* Creation Date: <br>
* date 24/08/2016 <br>
* @author Angela Ramirez
**/
public interface VideoUserDao extends BaseDAO<VideoUser> {
    
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
