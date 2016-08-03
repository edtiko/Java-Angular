package co.com.expertla.training.configuration.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.DisciplineDTO;
import co.com.expertla.training.model.entities.Discipline;
import java.util.List;

/**
* Dao for Discipline <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
public interface DisciplineDao extends BaseDAO<Discipline>{
	
    /**
     * Trae todos los registros de discipline <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<DisciplineDTO> findAll() throws Exception;

    public List<DisciplineDTO> findByUserId(Integer userId)throws Exception;
        
}
