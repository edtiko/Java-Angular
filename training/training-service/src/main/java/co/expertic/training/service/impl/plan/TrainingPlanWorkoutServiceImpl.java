package co.expertic.training.service.impl.plan;

import co.expertic.training.dao.configuration.ActivityDao;
import co.expertic.training.dao.plan.DcfDao;
import co.expertic.training.dao.plan.PlanWorkoutObjectiveDao;
import co.expertic.training.dao.user.UserAvailabilityDao;
import co.expertic.training.dao.user.UserProfileDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.expertic.training.model.entities.User;
import co.expertic.training.model.dto.TrainingPlanWorkoutDto;
import co.expertic.training.model.entities.Activity;
import co.expertic.training.model.entities.Dcf;
import co.expertic.training.model.entities.TrainingPlanUser;
import co.expertic.training.model.entities.TrainingPlanWorkout;
import co.expertic.training.model.entities.UserAvailability;
import co.expertic.training.model.entities.UserProfile;
import co.expertic.training.dao.plan.TrainingPlanUserDao;
import co.expertic.training.dao.plan.TrainingPlanWorkoutDao;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.DayDto;
import co.expertic.training.model.dto.PlanWorkoutDTO;
import co.expertic.training.model.entities.Objective;
import co.expertic.training.model.entities.PlanWorkoutObjective;
import co.expertic.training.service.configuration.ObjectiveService;
import co.expertic.training.service.plan.TrainingPlanWorkoutService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * TrainingPlanWorkoutService <br>
 * Info. Creación: <br>
 * fecha 15/07/2016 <br>
 * @author Andres Felipe Lopez Rodriguez
 */
@Service("trainingPlanWorkoutService")
@Transactional
public class TrainingPlanWorkoutServiceImpl implements TrainingPlanWorkoutService {

    @Autowired
    private TrainingPlanWorkoutDao trainingPlanWorkoutDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private DcfDao dcfDao;

    @Autowired
    private UserAvailabilityDao userAvailabilityDao;

    @Autowired
    private ActivityDao activityDao;
    
    @Autowired
    private ObjectiveService objectiveService;

    @Autowired
    private TrainingPlanUserDao trainingPlanUserDao;
    
    @Autowired
    private PlanWorkoutObjectiveDao planWorkoutObjectiveDao;

    @Override
    public List<TrainingPlanWorkoutDto> getPlanWorkoutByUser(Integer userId, Date fromDate, Date toDate) throws Exception {
        return trainingPlanWorkoutDao.getPlanWorkoutByUser(userId, fromDate, toDate);
    }

    @Override
    public void generatePlan(Integer userId, Date fromDate, Date toDate, Boolean isApproved) throws Exception {
        UserProfile userProfile = userProfileDao.findByUserId(userId);
        Dcf dcf = null;
        Integer objectiveId = null;
        TrainingPlanUser trainingPlanUser = trainingPlanUserDao.getTrainingPlanUserByUser(new User(userId));

        if(trainingPlanUser == null) {
            throw new Exception("No existe un plan valido para el usuario actual");
        }
        if(isApproved){
        objectiveId = objectiveService.findNextObjective(trainingPlanUser.getTrainingPlanUserId());
        }else{
            objectiveId = objectiveService.findCurrentObjective(trainingPlanUser.getTrainingPlanUserId());  
        }
        if(userProfile.getModalityId() != null && objectiveId != null) {
            dcf = dcfDao.findByObjectiveIdAndModalityId(objectiveId, userProfile.getModalityId().getModalityId());
        }
        
        if(dcf == null) {
            throw new Exception("No se puede generar plan, no existe configuración para el objetivo y la modalidad seleccionada");
        }
        
        List<UserAvailability> userAvailabilityList = userAvailabilityDao.findByUserId(userId);
        UserAvailability userAvailability = (userAvailabilityList == null || userAvailabilityList.isEmpty()) ? null : userAvailabilityList.get(0);
        fillAvailableDays(userAvailability);
        // Determina la cantidad de dias disponibles del atleta
        int daysAvailable = getAvailableDays();
        // determina la cantidad de sesiones semanales
        int weeklySession = dcf.getSessions()/4;
        if (weeklySession>7){
            weeklySession=7;
        }
        ArrayList<Date> dates = new ArrayList<>();
        // Determina los posibles casos, sin son iguales, deja la asignacion como la definio el usuario
        // en caso que sea diferente asigna mas disponibilidad o resta disponibilidad
        if (daysAvailable!=weeklySession){
            // Si la disponibilidad del atleta es menor que la necesaria, se debe incrementar la disponibilidad
            if(daysAvailable<weeklySession){
                // Modifica la disponibilidad del atleta hasta completar las sesiones requeridas
                addSessions(daysAvailable, weeklySession);
                // Se establece la bandera que fueron adicionadas sesiones para ser usada en la informacion al usuario
                setSessionsAdded(true);
                dates = getDatesPlan(fromDate);
                assignActivities(fromDate, toDate, userProfile, dcf, dates, trainingPlanUser, objectiveId);
            }
            else{
                // Modifica la disponibilidad del atleta restandole de la disponibilidad que tenia
                subsessions(daysAvailable, weeklySession);
                dates = getDatesPlan(fromDate);
                assignActivities(fromDate, toDate, userProfile, dcf, dates, trainingPlanUser, objectiveId);
            }
        } else {
            dates = getDatesPlan(fromDate);
            assignActivities(fromDate, toDate, userProfile, dcf, dates, trainingPlanUser, objectiveId);
        }
        //inactiva el flujo anterior
        planWorkoutObjectiveDao.inactivateOld(trainingPlanUser.getTrainingPlanUserId());
        //crea el flujo del nodo objetivo actual
        PlanWorkoutObjective e = new PlanWorkoutObjective();
        e.setObjectiveId(new Objective(objectiveId));
        e.setTrainingPlanUserId(trainingPlanUser);
        e.setFromDate(fromDate);
        e.setToDate(toDate);
        e.setCreationDate(Calendar.getInstance().getTime());
        e.setActive(Boolean.TRUE);
        planWorkoutObjectiveDao.create(e);
    }
    
    /**
     * Obtains the activities according to the pattern configured
     * @param activityList
     * @param index
     * @param indexCount
     * @param pattern
     * @param userProfile
     * @return
     * @throws Exception 
     */
    private List<Activity> getActivitiesByPC(List<Activity> activityList, Integer index, Integer indexCount, String pattern,
            UserProfile userProfile) throws Exception {
        List<Activity> list = new ArrayList<>();
        Activity act = new Activity();
        String[] parts = pattern.split("\\.");
        int length = parts.length;
        int z = 0;
        String code = "";
        for (int i = index; i < activityList.size(); i++) {
            act = new Activity();
            indexCount++;
            if(z == length) {
                break;
            } else if (z < length) {
                code = parts[z];
            }
            
            if (activityList.get(index).getPhysiologicalCapacityId().getCode().equals(code)) {
                act = activityList.get(index);
                list.add(act);
                z++;
                index++;
                if(z < length) {
                    for (Activity obj : activityList) {
                        if(obj.getPhysiologicalCapacityId().getCode().equals(parts[z])) {
                            indexCount=0;
                        }
                    }
                }
                continue;
            } 
            
            index++;
            if (act.getActivityId() == null && indexCount.equals(activityList.size())) {
                throw new Exception("No hay actividad configurada de " + code + " para el objetivo "+
                        userProfile.getObjectiveId().getName() + " y la modalidad " + userProfile.getModalityId().getName());
            } 
            if (index.equals(activityList.size()) ) {
                index = 0;
            }
        }

        return list;
    }
    
    private TrainingPlanWorkout buildWorkout(TrainingPlanUser plan, Activity activity, Date date) {
        TrainingPlanWorkout workout = new TrainingPlanWorkout();
        workout.setTrainingPlanUserId(plan);
        workout.setActivityId(activity);
        workout.setWorkoutDate(date);
        workout.isDrag(Boolean.FALSE);
        return workout;
    }

	@Override
    public TrainingPlanWorkout create(TrainingPlanWorkout trainingPlanWorkout) throws Exception {
        return trainingPlanWorkoutDao.createTrainingPlanWorkout(trainingPlanWorkout);
    }
    
    @Override
    public void delete(TrainingPlanWorkout trainingPlanWorkout) throws Exception {
        trainingPlanWorkoutDao.remove(trainingPlanWorkout, trainingPlanWorkout.getTrainingPlanWorkoutId());
    }

	@Override
    public List<TrainingPlanWorkout> getById(TrainingPlanWorkout trainingPlanWorkout) throws Exception {
        return trainingPlanWorkoutDao.getById(trainingPlanWorkout);
    }
    
    @Override
     public TrainingPlanWorkoutDto getPlanWorkoutById(Integer trainingPlanWorkoutId) throws Exception {
         return trainingPlanWorkoutDao.getPlanWorkoutById(trainingPlanWorkoutId);
     }
    
    
    private ArrayList<DayDto> avalabiltyDays = new ArrayList<DayDto>();
    private boolean sessionsAdded; // Determina si se adicionaron sesiones debido a la baja disponibilidad del atleta


    public boolean isSessionsAdded() {
        return sessionsAdded;
    }

    public void setSessionsAdded(boolean sessionsAdded) {
        this.sessionsAdded = sessionsAdded;
    }

    // Obtiene las fechas del plan de acuerdo a los parametros dados
    // Recibe:
    // El numero de sesiones mensuales
    // La fecha siguiente a la compra del plan
    public ArrayList<Date> getDatesPlan(Date startDate){
        ArrayList<Date> dates = new ArrayList<Date>(); //Lista donde se retornan las fechas del plan

        // Se determina el dia de la semana de la fecha dada
        Calendar clstartdate = Calendar.getInstance();
        clstartdate.setTime(startDate);
        // Se le quitan las horas a las fechas para evitar errores en la comparacion
        clstartdate.set(Calendar.HOUR_OF_DAY, 0);
        clstartdate.set(Calendar.MINUTE, 0);
        clstartdate.set(Calendar.SECOND, 0);
        clstartdate.set(Calendar.MILLISECOND, 0);
        int startDay = clstartdate.get(Calendar.DAY_OF_WEEK);

        // Se determina la inicial y la fecha final, 1 mes despuis de la inicial
        Date dtstartdate = clstartdate.getTime();
        clstartdate.add(Calendar.MONTH, 1);
        Date dtPlanFinish = clstartdate.getTime();
        
        // Itera hasta que sea menor que la fecha de finalizacion de generacion del plan
        Date dtIteratorDate = dtstartdate;
        while (dtIteratorDate.before(dtPlanFinish)) {
            // Por cada dia de la semana busca si esta disponible para el usuario
            for(int j=startDay;j<8;j++){
                // Se determina si tiene disponibilidad
                if (avalabiltyDays.get(j-1).isSelected()){
                    // Adciona la fecha a la lista
                    dates.add(dtIteratorDate);
                }
                Calendar clFecha = Calendar.getInstance();
                clFecha.setTime(dtIteratorDate);
                clFecha.add(Calendar.DAY_OF_MONTH, 1);
                dtIteratorDate = clFecha.getTime();
                if(dtIteratorDate.equals(dtPlanFinish)) {
                    break;
                }
            }          
                
            startDay = 1; //se pone el dia nuevamente en domingo
        }
        return dates;
    }
    
    // Determina la cantidad de dias disponibles del Atleta
    private int getAvailableDays(){
        int counter=0;
        for(DayDto dia : avalabiltyDays) {
            if (dia.isSelected()) {
                counter++;
            }
        }
        return counter;
    }

    // Aumenta la disponibilidad del atleta de acuerdo a numero dado
    private void addSessions(int availableDays, int requiredSessions){
        // Determina el primer dia seleccionado y a partir de ahi lo hace d�a de por medio
        // para mejorar la distribuci�n de las sesiones
        int weekday = 0;
        int i = 0;
        for(DayDto dia : avalabiltyDays) {
            if (dia.isSelected()) {
                weekday = i + 1;
                break;
            }
            i++;
        }
        // Itera hasta que se alcance la cantidad de sesiones requeridas
        int iteratorDay = weekday;
        while(availableDays < requiredSessions) {
            // Obtiene el siguiente indice dia por medio, controlando los valores
            switch (iteratorDay) {
                case 6:
                    iteratorDay = 1;
                    break;
                case 7:
                    iteratorDay = 2;
                    break;
                default:
                    iteratorDay = iteratorDay + 2;
            }
            // Valida si no esta disponible
            if (!avalabiltyDays.get(iteratorDay - 1).isSelected()) {
                avalabiltyDays.get(iteratorDay - 1).setSelected(true);
                availableDays++;
            }
        }
    }

    // Disminuye la disponibilidad del atleta de acuerdo a numero dado
    private void subsessions(int availableDays, int requiredSessions){
        // Determina el primer dia seleccionado y a partir de ahi lo hace dia de por medio
        // para mejorar la distribucion de las sesiones
        int weekday = 0;
        int i = 0;
        for(DayDto dia : avalabiltyDays) {
            if (dia.isSelected()) {
                weekday = i + 1;
                break;
            }
            i++;
        }
        // Itera hasta que se alcance la cantidad de sesiones requeridas
        int iteratorDay = weekday;
        while(requiredSessions < availableDays) {
            // Obtiene el siguiente indice dia por medio, controlando los valores
            switch (iteratorDay) {
                case 6:
                    iteratorDay = 1;
                    break;
                case 7:
                    iteratorDay = 2;
                    break;
                default:
                    iteratorDay = iteratorDay + 2;
            }
            // Valida si esta disponible
            if (avalabiltyDays.get(iteratorDay - 1).isSelected()) {
                avalabiltyDays.get(iteratorDay - 1).setSelected(false);
                availableDays--;
            }
        }
    }


    // Establece la disponibilidad del dia de la semana
    // entendido que el dia 1 es Domingo y el dia 7 es sabado
    public void setAvailability(int intWeekday){
        avalabiltyDays.get(intWeekday-1).setSelected(true);
    }

    

    // Constructor
    public void fillAvailableDays(UserAvailability userAvailability) {
        setSessionsAdded(false);
        avalabiltyDays = new ArrayList<>();
        // Crea los 7 dias
        DayDto objDay = new DayDto();
        objDay.setSelected(userAvailability.getSunday());
        avalabiltyDays.add(objDay);
        objDay = new DayDto();
        objDay.setSelected(userAvailability.getMonday());
        avalabiltyDays.add(objDay);
        objDay = new DayDto();
        objDay.setSelected(userAvailability.getTuesday());
        avalabiltyDays.add(objDay);
        objDay = new DayDto();
        objDay.setSelected(userAvailability.getWednesday());
        avalabiltyDays.add(objDay);
        objDay = new DayDto();
        objDay.setSelected(userAvailability.getThursday());
        avalabiltyDays.add(objDay);
        objDay = new DayDto();
        objDay.setSelected(userAvailability.getFriday());
        avalabiltyDays.add(objDay);
        objDay = new DayDto();
        objDay.setSelected(userAvailability.getSaturday());
        avalabiltyDays.add(objDay);
    }

    private void assignActivities(Date startDate,Date endDate, UserProfile userProfile, Dcf dcf,
            ArrayList<Date> dates, TrainingPlanUser planUser, Integer objectiveId) throws Exception {
        List<Activity> activityList = activityDao.findByObjectiveIdAndModalityIdAndEnvironmentId(objectiveId,
                userProfile.getModalityId().getModalityId(),userProfile.getEnvironmentId().getEnvironmentId());
        if(activityList.isEmpty()) {
            throw new Exception("No hay actividades configuradas para el objetivo "+
                        userProfile.getObjectiveId().getName() + " , la modalidad " + userProfile.getModalityId().getName()
            + " y entorno geografico " + userProfile.getEnvironmentId().getName());
        }
        List<TrainingPlanWorkout> workouts = new ArrayList<>();
        String pattern = dcf.getPattern();
        //Start date
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        //End date
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        String[] parts = pattern.trim().split("-");
        int length = parts.length;
        String code = "";

        List<Activity> list = new ArrayList<>();

        TrainingPlanWorkout workout = new TrainingPlanWorkout();
        int i = 0;
        int indexActivity = 0;
        int indexCount = 0;
        for (Date date : dates) {
            if (i < length) {
                code = parts[i];
                i++;
            } else {
                i = 0;
                code = parts[i];
                i++;
            }
            list = getActivitiesByPC(activityList, indexActivity, indexCount, code, userProfile);
            if(list.isEmpty()) {
                throw new Exception("No hay actividad configurada de " + code + " para el objetivo "+
                        userProfile.getObjectiveId().getName() + " y la modalidad " + userProfile.getModalityId().getName());
            } else {
                for(Activity act: list) {
                    workout = buildWorkout(planUser, act, date);
                    workouts.add(workout);
                }
            }
        }
        
        trainingPlanWorkoutDao.createList(workouts);
    }

    @Override
    public TrainingPlanWorkoutDto getPlanWorkoutByUser(Integer userId) throws Exception {
        return trainingPlanWorkoutDao.getPlanWorkoutByUser(userId);
    }

    @Override
    public void update(PlanWorkoutDTO planWorkoutDTO) throws Exception {

        TrainingPlanWorkout workout = trainingPlanWorkoutDao.getById(new TrainingPlanWorkout(planWorkoutDTO.getTrainingPlanWorkoutId())).get(0);
        if (workout != null && planWorkoutDTO != null) {
            if(null != planWorkoutDTO.getActivityId()){
            workout.setActivityId(new Activity(planWorkoutDTO.getActivityId()));
            }
                
        }
        trainingPlanWorkoutDao.merge(workout);
    }

    @Override
    public void updateStrava(TrainingPlanWorkoutDto dto, Boolean isStrava) throws Exception {
        TrainingPlanWorkout workout = trainingPlanWorkoutDao.getById(new TrainingPlanWorkout(dto.getId())).get(0);
        
        workout.setExecutedDistance(dto.getExecutedDistance());
        workout.setExecutedTime(dto.getExecutedTime());
        if (isStrava) {
            workout.setIndStrava(new Short(Status.ACTIVE.getId()));
            workout.setLastUpdateStrava(Calendar.getInstance().getTime());
        } else {
            workout.setLastUpdateUser(Calendar.getInstance().getTime());
        }
        trainingPlanWorkoutDao.merge(workout);
        
    }

    @Override
    public boolean isApproved(Integer userId, Date fromDate, Date toDate, Integer objectiveId) throws Exception {
        boolean res = false;
        int countApproved = 0;
        
        UserProfile userProfile = userProfileDao.findByUserId(userId);
        
        List<TrainingPlanWorkoutDto> list = trainingPlanWorkoutDao.getPlanWorkoutByUser(userId, fromDate, toDate);
            List<Activity> activityList = activityDao.findByObjectiveIdAndModalityIdAndEnvironmentId(objectiveId,
                userProfile.getModalityId().getModalityId(),userProfile.getEnvironmentId().getEnvironmentId());
            int countActivity = activityList.size();
            int minPercentaje = (countActivity * 80)/100;
            
            
        for (Activity activity : activityList) { 
            countApproved = list.stream().filter((workout) -> (Objects.equals(activity.getActivityId(), workout.getActivityId())
                    && workout.getExecutedDistance() >= activity.getPlannedDistance()
                    && workout.getExecutedTime() >= activity.getPlannedTime())).map((_item) -> 1).reduce(countApproved, Integer::sum);
        }
        
        if(countApproved >= minPercentaje){
            res = true;
        }

        return res;
        
        
    }
      
}