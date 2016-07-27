package co.com.expertla.training.plan.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.DcfDetail;
import java.util.List;

/**
* Dao for Dcf Detail <br>
* Creation Date : <br>
* date 21/07/2016 <br>
* @author Angela Ramírez
**/
public interface DcfDetailDao extends BaseDAO<DcfDetail>{
	
    /**
     * Trae todos los registros de DcfDetail por dcf Id <br>
     * Creation Date : <br>
     * date 21/07/2016 <br>
     * @author Angela Ramírez
     * @param dcfId
     * @throws Exception
     * @return
     */
    public List<DcfDetail> findByDcfId(Integer dcfId) throws Exception;
        
}
