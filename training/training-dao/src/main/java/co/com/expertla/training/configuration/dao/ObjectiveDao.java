package co.com.expertla.training.configuration.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.ObjectiveDTO;
import co.com.expertla.training.model.entities.Objective;
import java.util.List;

/**
* Dao for Objective <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
public interface ObjectiveDao extends BaseDAO<Objective>{
	
    /**
     * Trae todos los registros de Objective <br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<ObjectiveDTO> findAll() throws Exception;
        
}
