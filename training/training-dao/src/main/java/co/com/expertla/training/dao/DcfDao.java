package co.com.expertla.training.dao;

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
     * Trae todos los registros de dcf por objetive id <br>
     * Creation Date : <br>
     * date 19/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public List<Dcf> findByObjetiveId(Integer id) throws Exception;
        
}
