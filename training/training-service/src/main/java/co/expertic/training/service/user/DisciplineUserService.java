package co.expertic.training.service.user;

import co.expertic.training.model.entities.DisciplineUser;

/**
 * Service for Sport Discipline User <br>
 * Creation Date : <br>
 * date 02/08/2016 <br>
 *
 * @author Andres Felipe Lopez
 */
public interface DisciplineUserService {

    /**
     * crea el registros de discipline user <br>
     * Creation Date : <br>
     * date 02/08/2016 <br>
     * @author Andres Felipe Lopez
     * @param disciplineUser
     * @throws Exception
     * @return
     */
    public DisciplineUser create(DisciplineUser disciplineUser) throws Exception;
    
    /**
     * edita el registros de discipline user <br>
     * Creation Date : <br>
     * date 02/08/2016 <br>
     * @author Andres Felipe Lopez
     * @param disciplineUser
     * @throws Exception
     * @return
     */
    public DisciplineUser store(DisciplineUser disciplineUser) throws Exception;
    
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
