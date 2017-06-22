package co.expertic.training.service.impl.plan;

import co.expertic.training.dao.configuration.ActivityDao;
import co.expertic.training.dao.plan.BuildPeakVolumeDao;
import co.expertic.training.dao.user.UserAvailabilityDao;
import co.expertic.training.dao.user.UserProfileDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.expertic.training.model.entities.User;
import co.expertic.training.model.dto.TrainingPlanWorkoutDto;
import co.expertic.training.model.entities.Activity;
import co.expertic.training.model.entities.TrainingPlanUser;
import co.expertic.training.model.entities.TrainingPlanWorkout;
import co.expertic.training.model.entities.UserAvailability;
import co.expertic.training.model.entities.UserProfile;
import co.expertic.training.dao.plan.TrainingPlanUserDao;
import co.expertic.training.dao.plan.TrainingPlanWorkoutDao;
import co.expertic.training.dao.plan.TrainingUserSerieDao;
import co.expertic.training.enums.LoadTypeEnum;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.DayDto;
import co.expertic.training.model.dto.IntensityZoneSesionDTO;
import co.expertic.training.model.dto.IntervaloTiempo;
import co.expertic.training.model.dto.PlanWorkoutDTO;
import co.expertic.training.model.dto.SerieGenerada;
import co.expertic.training.model.dto.TrainingUserSerieDTO;
import co.expertic.training.model.entities.BuildPeakVolume;
import co.expertic.training.model.entities.IntensityZone;
import co.expertic.training.model.entities.IntensityZoneDist;
import co.expertic.training.model.entities.IntensityZoneSesion;
import co.expertic.training.model.entities.IntensityZoneSesionDist;
import co.expertic.training.model.entities.MonthlyVolume;
import co.expertic.training.model.entities.TrainingUserSerie;
import co.expertic.training.model.entities.WeeklyVolume;
import co.expertic.training.model.entities.ZoneTimeSerie;
import co.expertic.training.service.plan.TrainingPlanWorkoutService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
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
    private TrainingUserSerieDao trainingUserSerieDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private UserAvailabilityDao userAvailabilityDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private TrainingPlanUserDao trainingPlanUserDao;

    @Autowired
    private BuildPeakVolumeDao buildPeakVolumeDao;

    @Override
    public List<TrainingPlanWorkoutDto> getPlanWorkoutByUser(Integer userId, Date fromDate, Date toDate) throws Exception {
        return trainingUserSerieDao.getPlanWorkoutByUser(userId, fromDate, toDate);
    }

    @Override
    public void generatePlan(Integer userId, Date fromDate) throws Exception {

        UserProfile userProfile = userProfileDao.findByUserId(userId);
        TrainingPlanUser trainingPlanUser = trainingPlanUserDao.getTrainingPlanUserByUser(new User(userId));

        if (trainingPlanUser == null) {
            throw new Exception("No existe un plan valido para el usuario actual");
        } else {
            trainingUserSerieDao.deleteSeriesByTrainingPlanUserId(trainingPlanUser.getTrainingPlanUserId());
        }

        if (userProfile.getModalityId() == null) {
            throw new Exception("No se puede generar plan, no existe una modalidad registrada.");
        }

        if (userProfile.getObjectiveId() == null) {
            throw new Exception("No se puede generar plan, no existe una objetivo registrado.");
        }

        if (userProfile.getAvailableTime() == null || userProfile.getAvailableTime() == 0) {
            throw new Exception("No se puede generar plan, no existe horas de entrenamiento registrado.");
        } else if (userProfile.getAvailableTime() < userProfile.getObjectiveId().getMinHourWeek()) {
            throw new Exception("No se puede generar plan, el nivel " + userProfile.getObjectiveId().getTrainingLevelTypeId().getName()+ " requiere minimo " + userProfile.getObjectiveId().getMinHourWeek() + " horas.");
        } else if (userProfile.getAvailableTime() > userProfile.getObjectiveId().getMaxHourWeek()) {
            throw new Exception("No se puede generar plan, el nivel " + userProfile.getObjectiveId().getTrainingLevelTypeId().getName() + " tiene un máximo de " + userProfile.getObjectiveId().getMaxHourWeek() + " horas.");
        }

        if (userProfile.getCompetenceDate() == null) {
            throw new Exception("No se puede generar plan, no existe fecha de competencia registrada.");
        }

        WeeklyVolume weekVolume = trainingPlanWorkoutDao.getWeeklyVolume(userProfile.getObjectiveId().getTrainingLevelId());
        MonthlyVolume monthVolume = trainingPlanWorkoutDao.getMonthlyVolume(userProfile.getObjectiveId().getTrainingLevelId());

        Integer numSemanasMin = userProfile.getObjectiveId().getMinWeekPlan();
        Integer numSemanasMax = userProfile.getObjectiveId().getMaxWeekPlan();
        Date toDate = userProfile.getCompetenceDate();
        List<UserAvailability> userAvailabilityList = userAvailabilityDao.findByUserId(userId);
        UserAvailability userAvailability = (userAvailabilityList == null || userAvailabilityList.isEmpty()) ? null : userAvailabilityList.get(0);
        fillAvailableDays(userAvailability);
        // Determina la cantidad de dias disponibles del atleta
        int daysAvailable = getAvailableDays();
        int numSesiones = daysAvailable;

        Calendar clstartdate = Calendar.getInstance();
        clstartdate.setTime(fromDate);
        clstartdate.set(Calendar.HOUR_OF_DAY, 0);
        clstartdate.set(Calendar.MINUTE, 0);
        clstartdate.set(Calendar.SECOND, 0);
        clstartdate.set(Calendar.MILLISECOND, 0);
        int startWeek = clstartdate.get(Calendar.WEEK_OF_YEAR);

        Calendar clenddate = Calendar.getInstance();
        clenddate.setTime(toDate);
        clenddate.set(Calendar.HOUR_OF_DAY, 0);
        clenddate.set(Calendar.MINUTE, 0);
        clenddate.set(Calendar.SECOND, 0);
        clenddate.set(Calendar.MILLISECOND, 0);
        int endWeek = clenddate.get(Calendar.WEEK_OF_YEAR);

        int numSemanas = endWeek - startWeek;

        if (daysAvailable < userProfile.getObjectiveId().getMinSesion()) {
            throw new Exception("No se puede generar plan, el nivel " + userProfile.getObjectiveId().getTrainingLevelTypeId().getName() + " requiere minimo " + userProfile.getObjectiveId().getMinSesion() + " de días disponibles. ");
        } else if (daysAvailable == userProfile.getObjectiveId().getMinSesion()) {
            numSesiones = userProfile.getObjectiveId().getMinSesion();
        } else if (daysAvailable > userProfile.getObjectiveId().getMinSesion() || daysAvailable > userProfile.getObjectiveId().getMaxSesion()) {
            numSesiones = userProfile.getObjectiveId().getMaxSesion();
        }

        //obtiene las series de entrenamiento
        List<SerieGenerada> result = getSeries(userProfile, weekVolume, monthVolume, numSesiones, numSemanas);

        if (result == null || result.isEmpty()) {
            throw new Exception("No se generó el plan, hacen faltan datos de configuración.");
        }

        // determina la cantidad de sesiones semanales
        int weeklySession = numSesiones;

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
                dates = getDatesPlan(fromDate, toDate, numSemanasMin, numSemanasMax);
                assignSeries(fromDate, toDate, dates, trainingPlanUser, result, numSesiones, numSemanas);
            } else {
                // Modifica la disponibilidad del atleta restandole de la disponibilidad que tenia
                subsessions(daysAvailable, weeklySession);
                dates = getDatesPlan(fromDate, toDate, numSemanasMin, numSemanasMax);
                assignSeries(fromDate, toDate, dates, trainingPlanUser, result, numSesiones, numSemanas);
            }
        } else {
            dates = getDatesPlan(fromDate, toDate, numSemanasMin, numSemanasMax);
            assignSeries(fromDate, toDate, dates, trainingPlanUser, result, numSesiones, numSemanas);
        }

    }

    public List<SerieGenerada> getSeries(UserProfile userProfile, WeeklyVolume weekVolume, MonthlyVolume monthVolume, Integer numSesiones, Integer numSemanas) throws Exception {

        Integer availableTime = userProfile.getAvailableTime();

        Integer trainingLevelId = userProfile.getObjectiveId().getTrainingLevelId();
        Integer min_semanales = availableTime * 60;

        Double volInicialSemana = 0.0;
        Double iSemana = 0.0;
        Double weekDischarge = 0.0;
        Double volSemana = 0.0;

        Double volInicialMes = 0.0;
        Double iMes = 0.0;
        Double monthDischarge = 0.0;
        Double volMes = 0.0;

        List<IntensityZoneSesion> intesityZoneSesionDist = trainingPlanWorkoutDao.getIntensityZoneSesion(numSesiones, trainingLevelId);
        
        if (intesityZoneSesionDist == null || intesityZoneSesionDist.isEmpty()) {
            throw new Exception("No se generó el plan, hacen faltan datos de configuración.");
        }

        IntensityZone intesityZoneBase = trainingPlanWorkoutDao.getIntensityZone(trainingLevelId, LoadTypeEnum.BASE.getId());

        if (intesityZoneBase == null) {
            throw new Exception("No se generó el plan, hacen faltan datos de configuración.");
        }

        Collection<IntensityZoneDist> intensityZoneDistBase = intesityZoneBase.getIntensityZoneDistCollection();

        IntensityZone intesityZoneBuild = trainingPlanWorkoutDao.getIntensityZone(trainingLevelId, LoadTypeEnum.BUILD.getId());
        
        Collection<IntensityZoneDist> intensityZoneDistBuild = intesityZoneBuild.getIntensityZoneDistCollection();

        IntensityZone intesityZonePeak = trainingPlanWorkoutDao.getIntensityZone(trainingLevelId, LoadTypeEnum.PEAK.getId());

        Collection<IntensityZoneDist> intensityZoneDistPeak = intesityZonePeak.getIntensityZoneDistCollection();

        List<BuildPeakVolume> buildPeakList = buildPeakVolumeDao.findByLevelId(trainingLevelId);
        
        List<ZoneTimeSerie> zoneTimes = trainingPlanWorkoutDao.getZoneTimesByLevel(trainingLevelId);
                
        int weekBuildPeakStart = 0;
        int weekBuildPeak = 1;
        if (buildPeakList != null && !buildPeakList.isEmpty() && numSemanas > buildPeakList.size()) {
            weekBuildPeakStart = numSemanas - buildPeakList.size();
        }

        if (weekVolume != null) {
            volInicialSemana = new Double(weekVolume.getInitialValue());
            iSemana = new Double(weekVolume.getIncrease());
            weekDischarge = new Double(weekVolume.getDischarge());
            volSemana = volInicialSemana;
        }

        if (monthVolume != null) {
            volInicialMes = new Double(monthVolume.getInitialValue());
            iMes = new Double(monthVolume.getIncrease());
            monthDischarge = new Double(monthVolume.getDischarge());
            volMes = volInicialMes;
        }

        List<IntensityZoneSesionDTO> dist = new ArrayList<>();
        List<IntensityZoneSesionDTO> distInicial = new ArrayList<>();
        intesityZoneSesionDist.stream().forEach((izone) -> {
            for (IntensityZoneSesionDist izonedist : izone.getIntensityZoneSesionDistCollection()) {
                dist.add(new IntensityZoneSesionDTO(izone.getSesion(), izonedist.getNumZone(), izonedist.getZonePercentaje()));
            }
        });
        
        //saludar a miguel en media hora 10:10am - 10:40 pm 
        distInicial.addAll(dist);
        Map<Integer, Double> dist_int_tiempo_sesion = new HashMap<>();
        List<SerieGenerada> resultado = new ArrayList<>();
        Integer mayorZona = dist.stream().mapToInt(d-> d.getZone()).max().getAsInt();
        Integer timeWarmUp = zoneTimes.stream().filter(t->Objects.equals(mayorZona, t.getNumZone())).mapToInt(t->t.getWarmUpTime()).findFirst().getAsInt();
        Integer timePullDown = zoneTimes.stream().filter(t->Objects.equals(mayorZona, t.getNumZone())).mapToInt(t->t.getPullDownTime()).findFirst().getAsInt();

        for (int w = 1; w <= numSemanas; w++) {

            if (w % 4 == 0 && weekVolume != null) {
                volSemana = (volSemana * weekDischarge) / 100;
            }

            if (w % 16 == 0 && monthVolume != null) {
                volMes = (volMes * monthDischarge) / 100;
            }

            double base = (((min_semanales * volSemana) / 100) * volMes) / 100;

            double min_x_dia = base / numSesiones;

            intesityZoneSesionDist.stream().forEach((izonesesion) -> {
                double min_x_sesion = (min_x_dia * izonesesion.getDailyPercentaje()) / 100;
                min_x_sesion = min_x_sesion - (timeWarmUp + timePullDown);
                dist_int_tiempo_sesion.put(izonesesion.getSesion(), min_x_sesion);
            });

            if (weekBuildPeakStart > 0 && w > weekBuildPeakStart) {
                for (BuildPeakVolume buildPeak : buildPeakList) {
                    if (buildPeak.getWeek() == weekBuildPeak) {
                        base = (min_semanales * buildPeak.getVolume()) / 100;
                        if (Objects.equals(buildPeak.getTrainingLoadTypeId().getTrainingLoadTypeId(), LoadTypeEnum.BUILD.getId())) {
                            for (IntensityZoneDist izoneBuild : intensityZoneDistBuild) {

                                double tiempo_x_zona = (base * izoneBuild.getPercentaje()) / 100;
                                for (IntensityZoneSesionDTO intensityZoneSesionDist : dist) {
                                    if (Objects.equals(intensityZoneSesionDist.getZone(), izoneBuild.getNumZone())) {
                                        double min_zona_sesion = (intensityZoneSesionDist.getPercentaje() * tiempo_x_zona) / 100;
                                        intensityZoneSesionDist.setTime(min_zona_sesion);
                                    }
                                }

                            }
                        } else if (Objects.equals(buildPeak.getTrainingLoadTypeId().getTrainingLoadTypeId(), LoadTypeEnum.PEAK.getId())) {
                            for (IntensityZoneDist izonePeak : intensityZoneDistPeak) {

                                double tiempo_x_zona = (base * izonePeak.getPercentaje()) / 100;
                                for (IntensityZoneSesionDTO intensityZoneSesionDist : dist) {
                                    if (Objects.equals(intensityZoneSesionDist.getZone(), izonePeak.getNumZone())) {
                                        double min_zona_sesion = (intensityZoneSesionDist.getPercentaje() * tiempo_x_zona) / 100;
                                        intensityZoneSesionDist.setTime(min_zona_sesion);
                                    }
                                }

                            }
                        } else {

                            for (IntensityZoneDist izone : intensityZoneDistBase) {
                                double tiempo_x_zona = (base * izone.getPercentaje()) / 100;

                                for (IntensityZoneSesionDTO intensityZoneSesionDist : dist) {
                                    if (Objects.equals(izone.getNumZone(), intensityZoneSesionDist.getZone()) && intensityZoneSesionDist.getZone() != 2) {
                                        double min_zona_sesion = (intensityZoneSesionDist.getPercentaje() * tiempo_x_zona) / 100;
                                        intensityZoneSesionDist.setTime(min_zona_sesion);
                                    }

                                }

                            }
                        }

                    }
                }
                weekBuildPeak++;
            } else {

                for (IntensityZoneDist izone : intensityZoneDistBase) {
                    double tiempo_x_zona = (base * izone.getPercentaje()) / 100;

                    for (IntensityZoneSesionDTO intensityZoneSesionDist : dist) {
                        if (Objects.equals(izone.getNumZone(), intensityZoneSesionDist.getZone()) && intensityZoneSesionDist.getZone() != 2) {
                            double min_zona_sesion = (intensityZoneSesionDist.getPercentaje() * tiempo_x_zona) / 100;
                            intensityZoneSesionDist.setTime(min_zona_sesion);
                        }

                    }

                }
            }

            Map<Integer, Double> sum_tiempo_sesion = new HashMap<>();

            for (int i = 1; i <= numSesiones; i++) {
                Double sum_min_zona_sesion = 0.0;
                for (IntensityZoneSesionDTO item : dist) {
                    if (item.getSesion() == i && item.getTime() != null) {
                        sum_min_zona_sesion += item.getTime();
                    }
                }
                sum_tiempo_sesion.put(i, sum_min_zona_sesion);
            }

            List<IntensityZoneSesionDTO> zone2timeList = new ArrayList<>();
            boolean exist = false;
            for (Map.Entry<Integer, Double> entry : dist_int_tiempo_sesion.entrySet()) {
                Integer s = entry.getKey();
                for (IntensityZoneSesionDTO intensityZoneDist : dist) {
                    if (Objects.equals(intensityZoneDist.getSesion(), s)) {
                        Double tiempo = dist_int_tiempo_sesion.get(s) - sum_tiempo_sesion.get(s);
                        if (!zone2timeList.isEmpty()) {
                            for (IntensityZoneSesionDTO zone2 : zone2timeList) {
                                if (Objects.equals(zone2.getSesion(), s)) {
                                    zone2.setTime(zone2.getTime() + tiempo);
                                    exist = true;
                                    break;
                                }
                            }
                        }
                        if (!exist) {

                            IntensityZoneSesionDTO zone2time = new IntensityZoneSesionDTO();
                            zone2time.setSesion(intensityZoneDist.getSesion());
                            zone2time.setZone(2);
                            zone2time.setTime(dist_int_tiempo_sesion.get(s) - sum_tiempo_sesion.get(s));
                            zone2timeList.add(zone2time);
                        }
                        exist = false;
                    }
                }

            }
            dist.addAll(zone2timeList);

            for (IntensityZoneSesionDTO d : dist) {
                if (d.getTime() != null) {
                    SerieGenerada res = getSerieTime(d.getTime(), d.getZone(), trainingLevelId);
                    if (res != null && res.getNumSesiones() > 0 && res.getTiempo() != null && res.getTiempo().compareTo(0.0) == 1) {
                        resultado.add(new SerieGenerada(w, d.getZone(), d.getSesion(), res.getTiempo(), res.getNumSesiones()));
                    }
                }
            }

            dist.clear();
            dist.addAll(distInicial);

            if (w % 4 == 0) {
                volMes = volMes + (volMes * iMes) / 100;
                volSemana = volInicialSemana;
            } else if (!((w + 1) % 4 == 0)) {

                volSemana = volSemana + (volSemana * iSemana) / 100;
            }
            
        }
        
        double timeRest = 0d;
        Double timeSesion = 0d;
        for (SerieGenerada serie : resultado) {
            if (serie.getSesion() < numSesiones) {
                timeRest = zoneTimes.stream().filter(t -> Objects.equals(t.getNumZone(), serie.getZona())).mapToInt(t -> t.getRestTime()).findFirst().getAsInt();
                timeRest = (timeRest * serie.getTiempo()) / 100;
                timeSesion = serie.getTiempo() - timeRest;
                if (timeSesion.compareTo(0.0) == 1) {
                    serie.setTiempo(timeSesion);
                    serie.setTiempoDescanso(timeRest);
                }
            }
        }


    
        return resultado;

    }

    public SerieGenerada getSerieTime(Double tiempo, Integer zona, Integer trainingLevelId) throws Exception {

        //1. semana 1 escogemos el que tenga más series, segundo el intermedio, y el tercero el más alto, cuarta cualquiera
        ZoneTimeSerie serie = trainingPlanWorkoutDao.getZoneTimeSerie(zona, trainingLevelId);

        if (serie == null) {
            throw new Exception("No se puede generar plan, no existe zone_time_serie para la zona: " + zona);
        }
        
        
        List<IntervaloTiempo> list = new ArrayList<>();

        for (double i = serie.getNumMin(); i <= serie.getNumMax(); i += serie.getNumInterval()) {
            if (i > 0.0) {
                list.add(new IntervaloTiempo(zona, i));
            }
        }

        if (zona == 2) {
            long tiempol = Math.round(tiempo);
            Double time = list.stream().filter((intervalo) -> (tiempol > intervalo.getTiempo()) || tiempol == intervalo.getTiempo()).collect(Collectors.summarizingDouble(IntervaloTiempo::getTiempo)).getMax();
            if(Double.isInfinite(time)){
                time = (double)tiempol;
            }
            return new SerieGenerada(time, 1l);
        }
        
        Double res = 0.0;
        Long numSeries = 0l;

        Double min = list.stream().collect(Collectors.summarizingDouble(IntervaloTiempo::getTiempo)).getMin();
        //Double max = list.stream().filter(i-> Objects.equals(i.getZona(), zona)).collect(Collectors.summarizingDouble(IntervaloTiempo::getTiempo)).getMax();
        //Double average = list.stream().filter(i-> Objects.equals(i.getZona(), zona)).collect(Collectors.averagingDouble(IntervaloTiempo::getTiempo));
        Random randomGenerator = new Random();
        List<Double> possibleTimes = new ArrayList<>();
        boolean equal = list.stream().filter(t -> Objects.equals(tiempo, t.getTiempo())).count() > 0;
        if (equal) {
            List<IntervaloTiempo> times = list.stream().filter(t -> tiempo % t.getTiempo() == 0).collect(Collectors.toList());
            int index = randomGenerator.nextInt(times.size());
            res = times.get(index).getTiempo();
            numSeries = Math.round(tiempo / res);
        } else {
            list.stream().filter((intervalo) -> (tiempo.compareTo(intervalo.getTiempo()) == 1)).forEach((intervalo) -> {
                List<IntervaloTiempo> times = list.stream().filter(t -> intervalo.getTiempo().compareTo(t.getTiempo()) == 1 && (intervalo.getTiempo() % t.getTiempo() == 0)).collect(Collectors.toList());
                if (times != null && !times.isEmpty()) {
                    possibleTimes.add(intervalo.getTiempo());
                }
            });
            if (!possibleTimes.isEmpty()) {
                int index = randomGenerator.nextInt(possibleTimes.size());
                List<IntervaloTiempo> times = list.stream().filter(t -> possibleTimes.get(index).compareTo(t.getTiempo()) == 1 && (possibleTimes.get(index) % t.getTiempo() == 0)).collect(Collectors.toList());
                int index2 = randomGenerator.nextInt(times.size());
                res = times.get(index2).getTiempo();
                numSeries = Math.round(possibleTimes.get(index) / res);
            } else {
                res = min;
                numSeries = 1l;
            }
        }

        return new SerieGenerada(res, numSeries);
    }

    public Integer getPositionWeek(Integer week, Integer numSemanas) {
        Integer res = 0;
        Integer bloque = 1;
        Integer bloqueSelected = 0;

        Map<Integer, Integer> semanaBloque = new HashMap<>();
        for (int i = 1; i <= numSemanas; i++) {
            semanaBloque.put(i, bloque);
            if (week == i) {
                bloqueSelected = bloque;
            }

            if (i % 4 == 0) {
                bloque++;
            }
        }
        final Integer bloques = bloqueSelected;

        Map<Integer, Integer> collect = semanaBloque.entrySet().stream().filter(map -> Objects.equals(map.getValue(), bloques)).collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));

        return res;
    }

    /*
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
    }*/
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
                        + userProfile.getObjectiveId().getTrainingLevelTypeId().getName() + " y la modalidad " + userProfile.getModalityId().getName());
            }
            if (index.equals(activityList.size())) {
                index = 0;
            }
        }

        return list;
    }

    private TrainingUserSerie buildWorkoutSerie(TrainingPlanUser plan, SerieGenerada serie, Date date) {
        TrainingUserSerie workout = new TrainingUserSerie();
        workout.setTrainingPlanUserId(plan);
        workout.setNumSeries(serie.getNumSesiones());
        workout.setSerieTime(serie.getTiempo());
        workout.setNumZona(serie.getZona());
        workout.setSesion(serie.getSesion());
        workout.setWeek(serie.getSemana());
        workout.setWorkDate(date);
        workout.setRestTime(serie.getTiempoDescanso()!=null?serie.getTiempoDescanso().intValue():null);
        workout.setCreationDate(Calendar.getInstance().getTime());
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
    public TrainingUserSerieDTO getPlanWorkoutById(Integer id) throws Exception {
        return trainingUserSerieDao.getPlanWorkoutById(id);
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
    public ArrayList<Date> getDatesPlan(Date startDate, Date toDate, Integer numSemanasMin, Integer numSemanasMax) throws Exception {
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
        //Date dtstartdateMin = clstartdate.getTime();
        Calendar clstartdateMin = Calendar.getInstance();
        clstartdateMin.setTime(startDate);
        clstartdateMin.set(Calendar.HOUR_OF_DAY, 0);
        clstartdateMin.set(Calendar.MINUTE, 0);
        clstartdateMin.set(Calendar.SECOND, 0);
        clstartdateMin.set(Calendar.MILLISECOND, 0);
        clstartdateMin.add(Calendar.WEEK_OF_MONTH, numSemanasMin);
        Date dtPlanFinishMin = clstartdateMin.getTime();

        //Date dtstartdateMax = clstartdate.getTime();
        Date dtstartdate = clstartdate.getTime();
        Date dtIteratorDate = dtstartdate;

        if (toDate.compareTo(dtPlanFinishMin) < 0) {
            throw new Exception("No se puede generar plan, se deben cumplir al menos " + numSemanasMin + " semanas. Ingrese una fecha de competencia mayor.");
        }

        // Itera hasta que sea menor que la fecha de finalizacion de generacion del plan
        while (dtIteratorDate.before(toDate)) {
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
                if (dtIteratorDate.equals(toDate)) {
                    break;
                }
            }

            startDay = 1; //se pone el dia nuevamente en domingo
        }
        return dates;
    }

    // Determina la cantidad de dias disponibles del Atleta
    private int getAvailableDays() {
        return (int) avalabiltyDays.stream().filter(i -> i.isSelected()).count();
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

    private void assignSeries(Date startDate, Date endDate, ArrayList<Date> dates, TrainingPlanUser trainingPlanUser,
            List<SerieGenerada> series, Integer numSesiones, Integer numSemanas) throws Exception {
        List<TrainingUserSerie> workoutSeries = new ArrayList<>();

        //Start date
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        //End date
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        TrainingUserSerie workout = new TrainingUserSerie();
        int day = 0;

        for (int w = 1; w <= numSemanas; w++) {
            for (int j = 1; j <= numSesiones; j++) {
                for (SerieGenerada serie : series) {
                    if (w == serie.getSemana() && j == serie.getSesion() && day < dates.size()) {
                        workout = buildWorkoutSerie(trainingPlanUser, serie, dates.get(day));
                        workoutSeries.add(workout);

                    }

                }
                day++;
            }

        }

        trainingUserSerieDao.createList(workoutSeries);
    }

    @Override
    public TrainingUserSerie getPlanWorkoutByUser(Integer userId) throws Exception {
        return trainingUserSerieDao.getPlanWorkoutByUser(userId);
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

    @Override
    public List<TrainingUserSerieDTO> getSerieBySesionWeekUser(Integer userId, Integer sesion, Integer week) throws Exception {
        return trainingUserSerieDao.getSerieBySesionWeekUser(userId, sesion, week);
    }
    
    @Override
    public ZoneTimeSerie getZoneTimesByUser(Integer userId) throws Exception{
          return trainingPlanWorkoutDao.getZoneTimesByUser(userId);
    }

}
