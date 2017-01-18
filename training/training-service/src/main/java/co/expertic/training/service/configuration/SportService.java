package co.expertic.training.service.configuration;

import co.expertic.training.model.dto.EnvironmentDTO;
import co.expertic.training.model.dto.SportDTO;
import co.expertic.training.model.dto.WeatherDTO;
import java.util.List;

/**
 * Service for Sport <br>
 * Creation Date : <br>
 * date 15/07/2016 <br>
 * @author Angela Ramírez
*
 */
public interface SportService {

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
