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
import co.expertic.training.model.dto.IntensityZoneSesionDTO;
import co.expertic.training.model.dto.IntervaloTiempo;
import co.expertic.training.model.dto.PlanWorkoutDTO;
import co.expertic.training.model.dto.SerieGenerada;
import co.expertic.training.model.entities.IntensityZone;
import co.expertic.training.model.entities.IntensityZoneDist;
import co.expertic.training.model.entities.IntensityZoneSesion;
import co.expertic.training.model.entities.MonthlyVolume;
import co.expertic.training.model.entities.Objective;
import co.expertic.training.model.entities.PlanWorkoutObjective;
import co.expertic.training.model.entities.WeeklyVolume;
import co.expertic.training.service.configuration.ObjectiveService;
import co.expertic.training.service.plan.TrainingPlanWorkoutService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * TrainingPlanWorkoutService <br>
 * Info. Creación: <br>
 * fecha 15/07/2016 <br>
 *
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

    public static Integer getNumSesiones(UserProfile userProfile) {
        Integer res = 0;
        Collection<UserAvailability> availabilty = userProfile.getUserAvailabilityCollection();
        Integer minSesion = userProfile.getObjectiveId().getMinSesion();
        Integer maxSesion = userProfile.getObjectiveId().getMaxSesion();

        return maxSesion;
    }

    public void generatePlan(Integer userId, Date fromDate, Date toDate) throws Exception {
        UserProfile userProfile = userProfileDao.findByUserId(userId);
        WeeklyVolume weekVolume = null;
        MonthlyVolume monthVolume = null;
        
        if (userProfile.getModalityId() != null) {
            weekVolume = trainingPlanWorkoutDao.getWeeklyVolume(userProfile.getModalityId().getModalityId());
            monthVolume = trainingPlanWorkoutDao.getMonthlyVolume(userProfile.getModalityId().getModalityId());
        } else {
            throw new Exception("No se puede generar plan, no existe una modalidad registrada");
        }

        if (userProfile.getObjectiveId() != null && weekVolume != null && monthVolume != null) {

            Integer availableTime = userProfile.getAvailableTime();
            Integer numSesiones = userProfile.getObjectiveId().getMaxSesion();
            Integer numSemanas = userProfile.getObjectiveId().getMaxWeekPlan();
            generatePlan(availableTime, numSesiones, numSemanas, weekVolume, monthVolume, userProfile.getObjectiveId().getTrainingLevelId());
        } else {
            throw new Exception("No se puede generar plan, no existe una objetivo registrado");
        }

    }

    public List<SerieGenerada> generatePlan(Integer horas_entrenamiento, Integer num_sesiones, Integer num_semanas, WeeklyVolume weekVolume, MonthlyVolume monthVolume, Integer trainingLevelId) throws Exception {

        //calcula los minutos semanales de acuerdo a las horas de entrenamiento
        Integer min_semanales = horas_entrenamiento * 60;

        //volumen sesión
        Map<Integer, Integer> carga_intensidad_sesion = new HashMap<>();
        carga_intensidad_sesion.put(1, 110);
        carga_intensidad_sesion.put(2, 100);
        carga_intensidad_sesion.put(3, 100);
        carga_intensidad_sesion.put(4, 90);

        List<IntensityZoneSesion> intesityZoneSesionDist = trainingPlanWorkoutDao.getIntensityZoneSesion(num_sesiones);
        IntensityZone intesityZoneDist = trainingPlanWorkoutDao.getIntensityZone(trainingLevelId);
                
        Integer volInicialSemana = weekVolume.getInitialValue();
        Integer iSemana = weekVolume.getIncrease();
        Integer weekDischarge = weekVolume.getDischarge();

        Integer volInicialMes = monthVolume.getInitialValue();
        Integer iMes = monthVolume.getIncrease();
        Integer monthDischarge = monthVolume.getDischarge();


        Map<Integer, Integer> dist_int_zona = new HashMap<>();
        dist_int_zona.put(1, 0);
        dist_int_zona.put(2, 40);
        dist_int_zona.put(3, 28);
        dist_int_zona.put(4, 22);
        dist_int_zona.put(5, 8);
        dist_int_zona.put(6, 2);
        
        Collection<IntensityZoneDist> intensityZone = intesityZoneDist.getIntensityZoneDistCollection();
        


        List<IntensityZoneSesionDTO> dist = new ArrayList<>();
        dist.add(new IntensityZoneSesionDTO(1, 1, 0));
        dist.add(new IntensityZoneSesionDTO(1, 2, 0));
        dist.add(new IntensityZoneSesionDTO(1, 3, 0));
        dist.add(new IntensityZoneSesionDTO(1, 4, 0));
        dist.add(new IntensityZoneSesionDTO(1, 5, 0));
        dist.add(new IntensityZoneSesionDTO(1, 6, 0));
        dist.add(new IntensityZoneSesionDTO(2, 1, 0));
        dist.add(new IntensityZoneSesionDTO(2, 2, 0));
        dist.add(new IntensityZoneSesionDTO(2, 3, 70));
        dist.add(new IntensityZoneSesionDTO(2, 4, 35));
        dist.add(new IntensityZoneSesionDTO(2, 5, 0));
        dist.add(new IntensityZoneSesionDTO(2, 6, 0));
        dist.add(new IntensityZoneSesionDTO(3, 1, 0));
        dist.add(new IntensityZoneSesionDTO(3, 2, 0));
        dist.add(new IntensityZoneSesionDTO(3, 3, 30));
        dist.add(new IntensityZoneSesionDTO(3, 4, 65));
        dist.add(new IntensityZoneSesionDTO(3, 5, 20));
        dist.add(new IntensityZoneSesionDTO(3, 6, 0));
        dist.add(new IntensityZoneSesionDTO(4, 1, 0));
        dist.add(new IntensityZoneSesionDTO(4, 2, 0));
        dist.add(new IntensityZoneSesionDTO(4, 3, 0));
        dist.add(new IntensityZoneSesionDTO(4, 4, 0));
        dist.add(new IntensityZoneSesionDTO(4, 5, 80));
        dist.add(new IntensityZoneSesionDTO(4, 6, 100));

        //Map<Integer, Double> min_x_semana = new HashMap<>();
        Map<Integer, Double> dist_int_tiempo_sesion = new HashMap<>();
        Map<Integer, Double> dist_int_tiempo_zona = new HashMap<>();
        List<SerieGenerada> resultado = new ArrayList<>();

        Integer volSemana = volInicialSemana;
        Integer volMes = volInicialMes;

        /*1, 5, 9,  13 primer carga
        2, 6, 10, 14 segunda carga
        3, 7, 11, 15 tercer carga
        4, 8, 12, 16 cuarta carga */
        for (int w = 1; w <= num_semanas; w++) {

            double base = (((min_semanales * volSemana) / 100) * volMes) / 100;
            double min_x_dia = base / num_sesiones;
            //System.out.println("base:" + base + " minutos/dia:" + min_x_dia);

            intesityZoneSesionDist.stream().forEach((izonesesion) -> {
                double min_x_sesion = (min_x_dia * izonesesion.getDailyPercentaje()) / 100;
                dist_int_tiempo_sesion.put(izonesesion.getSesion(), min_x_sesion);
            });
            /*carga_intensidad_sesion.keySet().stream().forEach((Integer k) -> {
                double min_x_sesion = (min_x_dia * carga_intensidad_sesion.get(k)) / 100;
                dist_int_tiempo_sesion.put(k, min_x_sesion);
            });*/
            
            intensityZone.stream().forEach((izone)->{
                 double tiempo_x_zona = (base * izone.getPercentaje()) / 100;
                dist_int_tiempo_zona.put(izone.getNumZone(), tiempo_x_zona);

                dist.stream().filter((intensityZoneSesionDist) -> (Objects.equals(izone.getNumZone(), intensityZoneSesionDist.getZone()) && intensityZoneSesionDist.getZone() != 2)).forEach((intensityZoneSesionDist) -> {
                    double min_zona_sesion = (intensityZoneSesionDist.getPercentaje() * tiempo_x_zona) / 100;
                    intensityZoneSesionDist.setTime(min_zona_sesion);
                });
            });

            /*dist_int_zona.keySet().stream().forEach((k) -> {
                double tiempo_x_zona = (base * dist_int_zona.get(k)) / 100;
                dist_int_tiempo_zona.put(k, tiempo_x_zona);

                dist.stream().filter((intensityZoneSesionDist) -> (Objects.equals(k, intensityZoneSesionDist.getZone()) && intensityZoneSesionDist.getZone() != 2)).forEach((intensityZoneSesionDist) -> {
                    double min_zona_sesion = (intensityZoneSesionDist.getPercentaje() * tiempo_x_zona) / 100;
                    intensityZoneSesionDist.setTime(min_zona_sesion);
                });
            });*/

            Map<Integer, Double> sum_tiempo_sesion = new HashMap<>();

            for (int i = 1; i <= num_sesiones; i++) {
                Double sum_min_zona_sesion = 0.0;
                for (IntensityZoneSesionDTO item : dist) {
                    if (item.getSesion() == i && item.getTime() != null) {
                        sum_min_zona_sesion += item.getTime();
                    }
                }
                sum_tiempo_sesion.put(i, sum_min_zona_sesion);
            }

            dist_int_tiempo_sesion.keySet().stream().forEach((Integer s) -> {
                dist.stream().filter((intensityZoneSesionDist) -> (Objects.equals(intensityZoneSesionDist.getSesion(), s) && intensityZoneSesionDist.getZone() == 2)).forEach((intensityZoneSesionDist) -> {
                    intensityZoneSesionDist.setTime(dist_int_tiempo_sesion.get(s) - sum_tiempo_sesion.get(s));
                });

            });

            for (IntensityZoneSesionDTO d : dist) {
                SerieGenerada res = getSerieTime(d.getTime(), d.getZone());
                resultado.add(new SerieGenerada(w, d.getZone(), d.getSesion(), res.getTiempo(), res.getNumSesiones()));
            }

            System.out.println("vol semana: " + volSemana + " vol mes: " + volMes);
            if (w % 4 == 0) {
                volMes = volMes + iMes;
            }

            volSemana = volSemana + iSemana;

        }

        return resultado;

    }

    public static SerieGenerada getSerieTime(Double tiempo, Integer zona) {

        List<IntervaloTiempo> list = new ArrayList<>();
        list.add(new IntervaloTiempo(1, 15.00));
        list.add(new IntervaloTiempo(1, 30.00));
        list.add(new IntervaloTiempo(1, 45.00));
        list.add(new IntervaloTiempo(1, 60.00));
        list.add(new IntervaloTiempo(1, 75.00));
        list.add(new IntervaloTiempo(1, 90.00));

        list.add(new IntervaloTiempo(2, 15.00));
        list.add(new IntervaloTiempo(2, 30.00));
        list.add(new IntervaloTiempo(2, 45.00));
        list.add(new IntervaloTiempo(2, 60.00));
        list.add(new IntervaloTiempo(2, 75.00));
        list.add(new IntervaloTiempo(2, 90.00));
        list.add(new IntervaloTiempo(2, 105.00));
        list.add(new IntervaloTiempo(2, 120.00));
        list.add(new IntervaloTiempo(2, 135.00));
        list.add(new IntervaloTiempo(2, 150.00));
        list.add(new IntervaloTiempo(2, 165.00));
        list.add(new IntervaloTiempo(2, 180.00));

        list.add(new IntervaloTiempo(3, 20.00));
        list.add(new IntervaloTiempo(3, 25.00));
        list.add(new IntervaloTiempo(3, 30.00));
        list.add(new IntervaloTiempo(3, 35.00));
        list.add(new IntervaloTiempo(3, 40.00));
        list.add(new IntervaloTiempo(3, 45.00));
        list.add(new IntervaloTiempo(3, 50.00));
        list.add(new IntervaloTiempo(3, 55.00));
        list.add(new IntervaloTiempo(3, 60.00));
        list.add(new IntervaloTiempo(3, 65.00));
        list.add(new IntervaloTiempo(3, 70.00));
        list.add(new IntervaloTiempo(3, 75.00));
        list.add(new IntervaloTiempo(3, 80.00));
        list.add(new IntervaloTiempo(3, 85.00));
        list.add(new IntervaloTiempo(3, 90.00));

        list.add(new IntervaloTiempo(4, 10.00));
        list.add(new IntervaloTiempo(4, 15.00));
        list.add(new IntervaloTiempo(4, 20.00));
        list.add(new IntervaloTiempo(4, 25.00));
        list.add(new IntervaloTiempo(4, 30.00));
        list.add(new IntervaloTiempo(4, 35.00));
        list.add(new IntervaloTiempo(4, 40.00));
        list.add(new IntervaloTiempo(4, 45.00));
        list.add(new IntervaloTiempo(4, 50.00));
        list.add(new IntervaloTiempo(4, 55.00));
        list.add(new IntervaloTiempo(4, 60.00));
        list.add(new IntervaloTiempo(4, 65.00));

        list.add(new IntervaloTiempo(5, 10.00));
        list.add(new IntervaloTiempo(5, 12.00));
        list.add(new IntervaloTiempo(5, 14.00));
        list.add(new IntervaloTiempo(5, 16.00));
        list.add(new IntervaloTiempo(5, 18.00));
        list.add(new IntervaloTiempo(5, 20.00));
        list.add(new IntervaloTiempo(5, 22.00));
        list.add(new IntervaloTiempo(5, 24.00));
        list.add(new IntervaloTiempo(5, 26.00));
        list.add(new IntervaloTiempo(5, 28.00));
        list.add(new IntervaloTiempo(5, 30.00));
        list.add(new IntervaloTiempo(5, 32.00));

        list.add(new IntervaloTiempo(6, 2.00));
        list.add(new IntervaloTiempo(6, 2.50));
        list.add(new IntervaloTiempo(6, 3.00));
        list.add(new IntervaloTiempo(6, 3.50));
        list.add(new IntervaloTiempo(6, 4.00));
        list.add(new IntervaloTiempo(6, 4.50));
        list.add(new IntervaloTiempo(6, 5.00));
        list.add(new IntervaloTiempo(6, 5.50));
        list.add(new IntervaloTiempo(6, 6.00));
        list.add(new IntervaloTiempo(6, 6.50));
        list.add(new IntervaloTiempo(6, 7.00));
        list.add(new IntervaloTiempo(6, 7.50));

        Double res = 0.0;
        Long numSeries = 0l;
        //Double sum = list.stream().min(IntervaloTiempo::getTiempo);
        Double min = list.stream().filter(i -> Objects.equals(i.getZona(), zona)).collect(Collectors.summarizingDouble(IntervaloTiempo::getTiempo)).getMin();
        //Double max = list.stream().filter(i-> Objects.equals(i.getZona(), zona)).collect(Collectors.summarizingDouble(IntervaloTiempo::getTiempo)).getMax();
        //Double average = list.stream().filter(i-> Objects.equals(i.getZona(), zona)).collect(Collectors.averagingDouble(IntervaloTiempo::getTiempo));

        for (IntervaloTiempo intervalo : list) {

            if (Objects.equals(intervalo.getZona(), zona)) {

                if (zona < 4) {

                    if (Objects.equals(tiempo, intervalo.getTiempo())) {
                        res = intervalo.getTiempo();
                        numSeries = 1L;
                    } else if (tiempo.compareTo(intervalo.getTiempo()) == 1) {
                        if (res.compareTo(intervalo.getTiempo()) == -1) {
                            res = intervalo.getTiempo();
                            numSeries = 1L;
                        }
                    }
                } else if (Objects.equals(tiempo, intervalo.getTiempo())) {
                    res = min;
                    numSeries = Math.round(tiempo / min);
                    break;
                } else if (tiempo.compareTo(intervalo.getTiempo()) == 1) {
                    res = min;
                    numSeries = Math.round(tiempo / min);
                    break;
                }
            }
        }

        return new SerieGenerada(res, numSeries);
    }

    @Override
    public void generatePlan(Integer userId, Date fromDate, Date toDate, Boolean isApproved) throws Exception {
        UserProfile userProfile = userProfileDao.findByUserId(userId);
        Dcf dcf = null;
        Integer objectiveId = null;
        TrainingPlanUser trainingPlanUser = trainingPlanUserDao.getTrainingPlanUserByUser(new User(userId));

        if (trainingPlanUser == null) {
            throw new Exception("No existe un plan valido para el usuario actual");
        }
        if (isApproved) {
            objectiveId = objectiveService.findNextObjective(trainingPlanUser.getTrainingPlanUserId());
        } else {
            objectiveId = objectiveService.findCurrentObjective(trainingPlanUser.getTrainingPlanUserId());
        }
        if (userProfile.getModalityId() != null && objectiveId != null) {
            dcf = dcfDao.findByObjectiveIdAndModalityId(objectiveId, userProfile.getModalityId().getModalityId());
        }

        if (dcf == null) {
            throw new Exception("No se puede generar plan, no existe configuración para el objetivo y la modalidad seleccionada");
        }

        List<UserAvailability> userAvailabilityList = userAvailabilityDao.findByUserId(userId);
        UserAvailability userAvailability = (userAvailabilityList == null || userAvailabilityList.isEmpty()) ? null : userAvailabilityList.get(0);
        fillAvailableDays(userAvailability);
        // Determina la cantidad de dias disponibles del atleta
        int daysAvailable = getAvailableDays();
        // determina la cantidad de sesiones semanales
        int weeklySession = dcf.getSessions() / 4;
        if (weeklySession > 7) {
            weeklySession = 7;
        }
        ArrayList<Date> dates = new ArrayList<>();
        // Determina los posibles casos, sin son iguales, deja la asignacion como la definio el usuario
        // en caso que sea diferente asigna mas disponibilidad o resta disponibilidad
        if (daysAvailable != weeklySession) {
            // Si la disponibilidad del atleta es menor que la necesaria, se debe incrementar la disponibilidad
            if (daysAvailable < weeklySession) {
                // Modifica la disponibilidad del atleta hasta completar las sesiones requeridas
                addSessions(daysAvailable, weeklySession);
                // Se establece la bandera que fueron adicionadas sesiones para ser usada en la informacion al usuario
                setSessionsAdded(true);
                dates = getDatesPlan(fromDate);
                assignActivities(fromDate, toDate, userProfile, dcf, dates, trainingPlanUser, objectiveId);
            } else {
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
     *
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
            if (z == length) {
                break;
            } else if (z < length) {
                code = parts[z];
            }

            if (activityList.get(index).getPhysiologicalCapacityId().getCode().equals(code)) {
                act = activityList.get(index);
                list.add(act);
                z++;
                index++;
                if (z < length) {
                    for (Activity obj : activityList) {
                        if (obj.getPhysiologicalCapacityId().getCode().equals(parts[z])) {
                            indexCount = 0;
                        }
                    }
                }
                continue;
            }

            index++;
            if (act.getActivityId() == null && indexCount.equals(activityList.size())) {
                throw new Exception("No hay actividad configurada de " + code + " para el objetivo "
                        + userProfile.getObjectiveId().getDescription() + " y la modalidad " + userProfile.getModalityId().getName());
            }
            if (index.equals(activityList.size())) {
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

    private ArrayList<DayDto> avalabiltyDays = new ArrayList<>();
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
    public ArrayList<Date> getDatesPlan(Date startDate) {
        ArrayList<Date> dates = new ArrayList<>(); //Lista donde se retornan las fechas del plan

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
            for (int j = startDay; j < 8; j++) {
                // Se determina si tiene disponibilidad
                if (avalabiltyDays.get(j - 1).isSelected()) {
                    // Adciona la fecha a la lista
                    dates.add(dtIteratorDate);
                }
                Calendar clFecha = Calendar.getInstance();
                clFecha.setTime(dtIteratorDate);
                clFecha.add(Calendar.DAY_OF_MONTH, 1);
                dtIteratorDate = clFecha.getTime();
                if (dtIteratorDate.equals(dtPlanFinish)) {
                    break;
                }
            }

            startDay = 1; //se pone el dia nuevamente en domingo
        }
        return dates;
    }

    // Determina la cantidad de dias disponibles del Atleta
    private int getAvailableDays() {
        int counter = 0;
        for (DayDto dia : avalabiltyDays) {
            if (dia.isSelected()) {
                counter++;
            }
        }
        return counter;
    }

    // Aumenta la disponibilidad del atleta de acuerdo a numero dado
    private void addSessions(int availableDays, int requiredSessions) {
        // Determina el primer dia seleccionado y a partir de ahi lo hace d�a de por medio
        // para mejorar la distribuci�n de las sesiones
        int weekday = 0;
        int i = 0;
        for (DayDto dia : avalabiltyDays) {
            if (dia.isSelected()) {
                weekday = i + 1;
                break;
            }
            i++;
        }
        // Itera hasta que se alcance la cantidad de sesiones requeridas
        int iteratorDay = weekday;
        while (availableDays < requiredSessions) {
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
    private void subsessions(int availableDays, int requiredSessions) {
        // Determina el primer dia seleccionado y a partir de ahi lo hace dia de por medio
        // para mejorar la distribucion de las sesiones
        int weekday = 0;
        int i = 0;
        for (DayDto dia : avalabiltyDays) {
            if (dia.isSelected()) {
                weekday = i + 1;
                break;
            }
            i++;
        }
        // Itera hasta que se alcance la cantidad de sesiones requeridas
        int iteratorDay = weekday;
        while (requiredSessions < availableDays) {
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
    public void setAvailability(int intWeekday) {
        avalabiltyDays.get(intWeekday - 1).setSelected(true);
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

    private void assignActivities(Date startDate, Date endDate, UserProfile userProfile, Dcf dcf,
            ArrayList<Date> dates, TrainingPlanUser planUser, Integer objectiveId) throws Exception {
        List<Activity> activityList = activityDao.findByObjectiveIdAndModalityIdAndEnvironmentId(objectiveId,
                userProfile.getModalityId().getModalityId(), userProfile.getEnvironmentId().getEnvironmentId());
        if (activityList.isEmpty()) {
            throw new Exception("No hay actividades configuradas para el objetivo "
                    + userProfile.getObjectiveId().getDescription() + " , la modalidad " + userProfile.getModalityId().getName()
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
            if (list.isEmpty()) {
                throw new Exception("No hay actividad configurada de " + code + " para el objetivo "
                        + userProfile.getObjectiveId().getDescription() + " y la modalidad " + userProfile.getModalityId().getName());
            } else {
                for (Activity act : list) {
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
            if (null != planWorkoutDTO.getActivityId()) {
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
                userProfile.getModalityId().getModalityId(), userProfile.getEnvironmentId().getEnvironmentId());
        int countActivity = activityList.size();
        int minPercentaje = (countActivity * 80) / 100;

        for (Activity activity : activityList) {
            countApproved = list.stream().filter((workout) -> (Objects.equals(activity.getActivityId(), workout.getActivityId())
                    && workout.getExecutedDistance() >= activity.getPlannedDistance()
                    && workout.getExecutedTime() >= activity.getPlannedTime())).map((_item) -> 1).reduce(countApproved, Integer::sum);
        }

        if (countApproved >= minPercentaje) {
            res = true;
        }

        return res;

    }

}
