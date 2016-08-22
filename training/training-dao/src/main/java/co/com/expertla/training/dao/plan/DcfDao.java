package co.com.expertla.training.dao.plan;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.Dcf;

/**
* Dao for Dcf (Distribucion porcentual de Capacidades Fisiologicas) <br>
* Creation Date : <br>
* date 19/07/2016 <br>
* @author Angela Ramírez
**/
public interface DcfDao extends BaseDAO<Dcf>{
	
    /**
     * Trae todos los registros de dcf por objective id y modality Id <br>
     * Creation Date : <br>
     * date 19/07/2016 <br>
     * @author Angela Ramírez
     * @param objectiveId
     * @param modalityId
     * @throws Exception
     * @return
     */
    public Dcf findByObjectiveIdAndModalityId(Integer objectiveId, Integer modalityId) throws Exception;
        
}
