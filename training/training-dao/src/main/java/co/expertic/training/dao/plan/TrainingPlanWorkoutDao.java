package co.expertic.training.dao.plan;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.dto.TrainingPlanWorkoutDto;
import co.expertic.training.model.entities.IntensityZone;
import co.expertic.training.model.entities.IntensityZoneSesion;
import co.expertic.training.model.entities.MonthlyVolume;
import co.expertic.training.model.entities.TrainingPlanWorkout;
import co.expertic.training.model.entities.WeeklyVolume;
import co.expertic.training.model.entities.ZoneTimeSerie;
import java.util.Date;
import java.util.List;

/**
 * TrainingPlanWorkoutDao <br>
 * Info. Creación: <br>
 * fecha 15/07/2016 <br>
 *
 * @author Andres Felipe Lopez Rodriguez
 *
 */
public interface TrainingPlanWorkoutDao extends BaseDAO<TrainingPlanWorkout> {

    /**
     * Obtiene el plan de entrenamiento por usuario <br>
     * Info. Creación: <br>
     * fecha 15/07/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @param fromDate
     * @param toDate
     * @return
     * @throws Exception
     */
    public List<TrainingPlanWorkoutDto> getPlanWorkoutByUser(Integer userId, Date fromDate, Date toDate) throws Exception;

    /**
     * Crea la lista de training plan workout Creation Date : <br>
     * date 25/07/2016 <br>
     *
     * @author Angela Ramírez
     * @param list
     * @return
     * @throws Exception
     */
    public List<TrainingPlanWorkout> createList(List<TrainingPlanWorkout> list) throws Exception;

    /**
     * crea TrainingPlanWorkout <br>
     * Info. Creación: <br>
     * fecha 05/08/2016 <br>
     *
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
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanWorkout
     * @return
     * @throws Exception
     */
    public List<TrainingPlanWorkout> getById(TrainingPlanWorkout trainingPlanWorkout) throws Exception;

    public void deleteByManualActivityId(Integer manualActivityId) throws Exception;

    public TrainingPlanWorkoutDto getPlanWorkoutById(Integer trainingPlanWorkoutId) throws Exception;

    public TrainingPlanWorkoutDto getPlanWorkoutByUser(Integer userId) throws Exception;

    public WeeklyVolume getWeeklyVolume(Integer modalityId) throws Exception;

    public MonthlyVolume getMonthlyVolume(Integer modalityId) throws Exception;

    public List<IntensityZoneSesion> getIntensityZoneSesion(Integer numSesion, Integer trainingLevelId) throws Exception;

    public IntensityZone getIntensityZone(Integer trainingLevelId, Integer typeLoadId) throws Exception;

    public ZoneTimeSerie getZoneTimeSerie(Integer zone, Integer trainingLevelId) throws Exception;
}
