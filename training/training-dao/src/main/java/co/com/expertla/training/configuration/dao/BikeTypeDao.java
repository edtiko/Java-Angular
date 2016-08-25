package co.com.expertla.training.configuration.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.BikeType;
import java.util.List;

/**
* Dao for BikeType <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
public interface BikeTypeDao extends BaseDAO<BikeType>{
	
    /**
     * Trae todos los registros de bike type <br>
     * Creation Date : <br>
     * date 19/08/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<BikeType> findAll() throws Exception;
        
}
