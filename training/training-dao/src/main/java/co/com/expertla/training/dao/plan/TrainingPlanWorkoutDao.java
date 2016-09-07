package co.com.expertla.training.dao.plan;

import co.com.expertla.base.jpa.BaseDAO;
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
public interface TrainingPlanWorkoutDao extends BaseDAO<TrainingPlanWorkout> {
     
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
    
    /**
     * crea TrainingPlanWorkout <br>
     * Info. Creación: <br>
     * fecha 05/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanWorkout
     * @return 
     * @throws Exception 
     */
    public TrainingPlanWorkout createTrainingPlanWorkout(TrainingPlanWorkout trainingPlanWorkout) throws Exception;
    
    /**
     * Obtiene el plan de entrenamiento por id <br>
     * Info. Creación: <br>
     * fecha 08/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanWorkout
     * @return 
     * @throws Exception 
     */
    public List<TrainingPlanWorkout> getById(TrainingPlanWorkout trainingPlanWorkout) throws Exception;
    
    public void deleteByManualActivityId(Integer manualActivityId)throws Exception;
}