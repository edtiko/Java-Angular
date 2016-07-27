package co.com.expertla.training.plan.dao;

import co.com.expertla.training.model.dto.TrainingPlanWorkoutDto;
import co.com.expertla.training.model.entities.TrainingPlanWorkout;
import co.com.expertla.training.model.entities.User;
import java.util.Date;
import java.util.List;

/**
* TrainingPlanWorkoutDao <br>
* Info. Creación: <br>
* fecha 15/07/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface TrainingPlanWorkoutDao {
     
     /**
     * Obtiene el plan de entrenamiento por usuario <br>
     * Info. Creación: <br>
     * fecha 15/07/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param user
     * @param fromDate
     * @param toDate
     * @return 
     * @throws Exception 
     */
    public List<TrainingPlanWorkoutDto> getPlanWorkoutByUser(User user, Date fromDate, Date toDate) throws Exception;
    
    /**
     * Crea la lista de training plan workout
     * Creation Date : <br>
     * date 25/07/2016 <br>
     * @author Angela Ramírez
     * @param list
     * @return
     * @throws Exception 
     */
    public List<TrainingPlanWorkout> createList (List<TrainingPlanWorkout> list) throws Exception;
}