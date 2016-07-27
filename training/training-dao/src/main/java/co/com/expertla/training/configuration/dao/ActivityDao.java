package co.com.expertla.training.configuration.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.Activity;
import java.util.List;

/**
* Dao for Activity <br>
* Creation Date : <br>
* date 21/07/2016 <br>
* @author Angela Ramírez
**/
public interface ActivityDao extends BaseDAO<Activity>{
	
    /**
     * Trae todos los registros de activity <br>
     * Creation Date : <br>
     * date 21/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<Activity> findAll() throws Exception;
    
    /**
     * Trae todos los registros de Activity  por modality Id y objetive Id<br>
     * Creation Date : <br>
     * date 21/07/2016 <br>
     * @author Angela Ramírez
     * @param objetiveId
     * @param modalityId
     * @throws Exception
     * @return
     */
    public List<Activity> findByObjetiveIdAndModalityId(Integer objetiveId,Integer modalityId) throws Exception;
        
}
