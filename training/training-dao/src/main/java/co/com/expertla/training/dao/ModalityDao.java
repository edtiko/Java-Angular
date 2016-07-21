package co.com.expertla.training.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.ModalityDTO;
import co.com.expertla.training.model.entities.Modality;
import java.util.List;

/**
* Dao for Modality <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
public interface ModalityDao extends BaseDAO<Modality>{
	
    /**
     * Trae todos los registros de modality <br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<ModalityDTO> findAll() throws Exception;
    
    /**
     * Trae todos los registros de Modality  por discipline id<br>
     * Creation Date : <br>
     * date 18/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<ModalityDTO> findByDisciplineId(Integer id) throws Exception;
        
}
