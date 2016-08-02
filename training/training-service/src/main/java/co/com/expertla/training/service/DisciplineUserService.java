package co.com.expertla.training.service;

import co.com.expertla.training.model.entities.DisciplineUser;

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
}
