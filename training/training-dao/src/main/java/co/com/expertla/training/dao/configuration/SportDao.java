package co.com.expertla.training.dao.configuration;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.dto.EnvironmentDTO;
import co.com.expertla.training.model.dto.SportDTO;
import co.com.expertla.training.model.dto.WeatherDTO;
import co.com.expertla.training.model.entities.Sport;
import java.util.List;

/**
* Dao for Sport <br>
* Creation Date : <br>
* date 15/07/2016 <br>
* @author Angela Ramírez
**/
public interface SportDao extends BaseDAO<Sport>{
	
    /**
     * Trae todos los registros de sport <br>
     * Creation Date : <br>
     * date 15/07/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<SportDTO> findAll() throws Exception;
    
    public List<EnvironmentDTO> findEntornos() throws Exception;
    
    public List<WeatherDTO> findClimas() throws Exception;
    
    /**
     * Trae todos los registros de sport tipo disciplina <br>
     * Creation Date : <br>
     * date 02/09/2016 <br>
     * @author Angela Ramírez
     * @throws Exception
     * @return
     */
    public List<SportDTO> findSportDisciplines() throws Exception;
}
