package co.expertic.training.service.configuration;

import co.expertic.training.model.dto.StarTeamDTO;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.entities.StarTeam;
import java.util.List;

/**
* StartTeam Service <br>
* Info. Creación: <br>
* fecha 1/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface StartTeamService {
    

    /**
     * Crea startTeam <br>
     * Info. Creación: <br>
     * fecha 1/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param startTeam
     * @return 
     * @throws Exception 
     */
    public StarTeam create(StarTeam startTeam) throws Exception;
    /**
     * Modifica startTeam <br>
     * Info. Creación: <br>
     * fecha 1/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param startTeam
     * @return 
     * @throws Exception 
     */
    public StarTeam store(StarTeam startTeam) throws Exception;
    /**
     * Elimina startTeam<br>
     * Info. Creación: <br>
     * fecha 1/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param startTeam
     * @throws Exception 
     */
    public void remove(StarTeam startTeam) throws Exception;
    /**
     * Obtiene todos los registros de startTeam <br>
     * Info. Creación: <br>
     * fecha 1/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<StarTeam> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de startTeam <br>
     * Info. Creación: <br>
     * fecha 1/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<StarTeam> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de startTeam paginados <br>
     * Info. Creación: <br>
     * fecha 1/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @return
     * @throws Exception 
     */
    public List<StarTeamDTO> findPaginate(int first, int max, String order) throws Exception;
    
    /**
     * Obtiene todos los registros de startTeam paginados <br>
     * Info. Creación: <br>
     * fecha 1/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param startTeam
     * @return
     * @throws Exception 
     */
    public List<StarTeam> findByStartTeam(StarTeam startTeam) throws Exception;   

    /**
     * Obtiene todos los registros de startTeam por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 1/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param startTeam
     * @return
     * @throws Exception 
     */
    public List<StarTeam> findByFiltro(StarTeam startTeam) throws Exception; 

    public StarTeam findBySupervisor(Integer supervisorUserId) throws Exception; 
    
    public List<UserResumeDTO> findAsesoresByStarUserId(Integer starUserId)throws Exception;



    
}