package co.com.expertla.training.plan.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.Dcf;
import java.util.List;

/**
* Dao for Dcf (Distribucion porcentual de Capacidades Fisiologicas) <br>
* Creation Date : <br>
* date 19/07/2016 <br>
* @author Angela Ramírez
**/
public interface DcfDao extends BaseDAO<Dcf>{
	
    /**
     * Trae todos los registros de dcf por objetive id y modality Id <br>
     * Creation Date : <br>
     * date 19/07/2016 <br>
     * @author Angela Ramírez
     * @param objetiveId
     * @param modalityId
     * @throws Exception
     * @return
     */
    public Dcf findByObjetiveIdAndModalityId(Integer objetiveId, Integer modalityId) throws Exception;
        
}
