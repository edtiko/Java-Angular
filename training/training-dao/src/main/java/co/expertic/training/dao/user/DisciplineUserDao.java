package co.expertic.training.dao.user;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.entities.DisciplineUser;
import java.util.List;

/**
* Dao for Discipline User <br>
* Creation Date : <br>
* date 21/07/2016 <br>
* @author Angela Ramírez
**/
public interface DisciplineUserDao extends BaseDAO<DisciplineUser>{
	
    /**
     * Trae todos los registros de discipline user por user Id <br>
     * Creation Date : <br>
     * date 21/07/2016 <br>
     * @author Angela Ramírez
     * @param id
     * @throws Exception
     * @return
     */
    public DisciplineUser findByUserId(Integer id) throws Exception;
        
}
