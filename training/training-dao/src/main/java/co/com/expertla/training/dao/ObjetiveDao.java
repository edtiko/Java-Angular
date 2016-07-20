package co.com.expertla.training.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.ObjetiveDTO;
import co.com.expertla.training.model.entities.Objetive;
import java.util.List;

/**
* Dao for Objetive <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
public interface ObjetiveDao extends BaseDAO<Objetive>{
	
    /**
     * Trae todos los registros de Objetive <br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<ObjetiveDTO> findAll() throws Exception;
        
}
