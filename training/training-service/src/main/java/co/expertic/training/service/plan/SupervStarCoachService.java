package co.expertic.training.service.plan;

import co.expertic.training.model.dto.SupervStarCoachDTO;
import co.expertic.training.model.dto.UserAssignedSupervisorDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.SupervStarCoach;
import java.util.List;

/**
* SupervStarCoach Service <br>
* Info. Creación: <br>
* fecha Sep 13, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface SupervStarCoachService {
    

    /**
     * Crea supervStarCoach <br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param supervStarCoach
     * @return 
     * @throws Exception 
     */
    public SupervStarCoach create(SupervStarCoach supervStarCoach) throws Exception;
    /**
     * Modifica supervStarCoach <br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param supervStarCoach
     * @return 
     * @throws Exception 
     */
    public SupervStarCoach store(SupervStarCoach supervStarCoach) throws Exception;
    /**
     * Elimina supervStarCoach<br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param supervStarCoach
     * @throws Exception 
     */
    public void remove(SupervStarCoach supervStarCoach) throws Exception;
    /**
     * Obtiene todos los registros de supervStarCoach <br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<SupervStarCoach> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de supervStarCoach <br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<SupervStarCoach> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de supervStarCoach paginados <br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<SupervStarCoachDTO> findPaginate(int first, int max, String order, String filter) throws Exception;
    
    /**
     * Obtiene todos los registros de supervStarCoach paginados <br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param supervStarCoach
     * @return
     * @throws Exception 
     */
    public List<SupervStarCoach> findBySupervStarCoach(SupervStarCoach supervStarCoach) throws Exception;   

    /**
     * Obtiene todos los registros de supervStarCoach por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param supervStarCoach
     * @return
     * @throws Exception 
     */
    public List<SupervStarCoach> findByFiltro(SupervStarCoach supervStarCoach) throws Exception; 

    /**
     * Obtiene todos los registros de supervStarCoach por el coach Id<br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param coachId
     * @return
     * @throws Exception 
     */
    public List<SupervStarCoach> findByCoachId(Integer coachId) throws Exception; 
    
    /**
     * Obtiene todos los registros de supervStarCoach por el supervisor Id<br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return
     * @throws Exception 
     */
    public List<UserAssignedSupervisorDTO> findBySupervisorId(Integer userId) throws Exception; 
    
    
    /**
     * Obtiene todos los registros de supervStarCoach por el supervisor Id<br>
     * Info. Creación: <br>
     * fecha 24/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return
     * @throws Exception 
     */
    public List<UserAssignedSupervisorDTO> findAtleteCoachBySupervisorId(Integer userId) throws Exception;
    
    /**
     * Obtiene todos los registros de users por el supervisor Id<br>
     * Info. Creación: <br>
     * fecha 24/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return
     * @throws Exception 
     */
    public List<UserDTO> findUsersBySupervisorId(Integer userId) throws Exception;
    
    /**
     * Obtiene todos los supervisores asociados a la estrella  Id<br>
     * Info. Creación: <br>
     * fecha 24/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return
     * @throws Exception 
     */
    public List<UserDTO> findByStarId(Integer userId) throws Exception;

    
}