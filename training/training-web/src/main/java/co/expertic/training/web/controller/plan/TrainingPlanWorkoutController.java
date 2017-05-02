package co.expertic.training.web.controller.plan;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.model.dto.CalendarEventDto;
import co.expertic.training.model.dto.PlanWorkoutDTO;
import co.expertic.training.model.dto.TrainingPlanWorkoutDto;
import co.expertic.training.model.dto.TrainingSesionDTO;
import co.expertic.training.model.dto.TrainingUserSerieDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.Activity;
import co.expertic.training.model.entities.ManualActivity;
import co.expertic.training.model.entities.TrainingPlanUser;
import co.expertic.training.model.entities.TrainingPlanWorkout;
import co.expertic.training.model.entities.TrainingUserSerie;
import co.expertic.training.model.entities.User;
import co.expertic.training.model.entities.UserZone;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.plan.TrainingPlanUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import co.expertic.training.service.plan.TrainingPlanWorkoutService;
import co.expertic.training.service.user.UserService;
import co.expertic.training.service.user.UserZoneService;
import co.expertic.training.web.enums.StatusResponse;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Servicio Rest para plan de entremaniento <br>
 * Info. Creación: <br>
 * fecha 15/07/2016 <br>
 *
 * @author Andres Felipe Lopez Rodriguez
 *
 */
@RestController
public class TrainingPlanWorkoutController {

    @Autowired
    TrainingPlanWorkoutService trainingPlanWorkoutService;  //Service which will do all data retrieval/manipulation work

    @Autowired
    TrainingPlanUserService trainingPlanUserService;

    @Autowired
    UserZoneService userZoneService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/trainingPlanWorkout/get/planWorkout/by/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CalendarEventDto getPlanWorkoutByUser(@PathVariable("userId") Integer user, @RequestParam("from") long from,
            @RequestParam("to") long to) {
        StringBuilder strResponse = new StringBuilder();
        CalendarEventDto calendarEventDto = new CalendarEventDto();
        try {
            if (user == null) {
                strResponse.append(MessageUtil.getMessageFromBundle("comun", "nullParameters"));
                calendarEventDto.setSuccess(1);
                return calendarEventDto;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(from);
            Date fromDate = calendar.getTime();

            calendar.setTimeInMillis(to);
            Date toDate = calendar.getTime();

            List<TrainingSesionDTO> list = getTrainingPlanWorkoutByIntervalDateUserId(user, fromDate, toDate);
            calendarEventDto.setSuccess(1);
            calendarEventDto.setResult(list);
            return calendarEventDto;
        } catch (Exception e) {
            Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, e);
            calendarEventDto.setSuccess(0);
            calendarEventDto.setResult(null);
            return calendarEventDto;
        }
    }

    /**
     *
     * @param user
     * @param from
     * @param to
     * @return
     * @throws Exception
     */
    private List<TrainingSesionDTO> getTrainingPlanWorkoutByIntervalDateUserId(Integer user,
            Date fromDate, Date toDate) throws Exception {
        List<TrainingPlanWorkoutDto> list = trainingPlanWorkoutService.getPlanWorkoutByUser(user, fromDate, toDate);
        Map<Date, List<TrainingPlanWorkoutDto>> filtered = list.stream().collect(Collectors.groupingBy(TrainingPlanWorkoutDto::getWorkoutDate));
        List<TrainingSesionDTO> result = new ArrayList<>();

        for (Map.Entry<Date, List<TrainingPlanWorkoutDto>> entry : filtered.entrySet()) {
            Date workoutDate = entry.getKey();
            List<TrainingPlanWorkoutDto> series = entry.getValue();
            TrainingSesionDTO sesionDTO = new TrainingSesionDTO();
            StringBuilder title = new StringBuilder();
            for (TrainingPlanWorkoutDto serie : series) {
                title.append(serie.getNumSeries() + " series en z" + serie.getZone() + " \n");
            }
            sesionDTO.setSesion(series.get(0).getSesion());
            sesionDTO.setWeek(series.get(0).getWeek());
            sesionDTO.setStart(workoutDate.getTime());
            sesionDTO.setEnd(workoutDate.getTime());
            sesionDTO.setClassName(series.get(0).getSportIcon());
            sesionDTO.setDiscipline(series.get(0).getDiscipline());
            sesionDTO.setTitle(title.toString());
            result.add(sesionDTO);
        }

        return result;
    }

    @RequestMapping(value = "trainingPlanWorkout/validate/planWorkout/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> validatePlan(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        try {
            TrainingUserSerie userSerie = trainingPlanWorkoutService.getPlanWorkoutByUser(userId);
            if (userSerie != null) {
                responseService.setOutput("Ya tienes un plan generado y  se perderá al generar el nuevo.");
            } else {
                responseService.setOutput("");
            }

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, e);
            responseService.setOutput(e.getMessage());
            responseService.setDetail(e.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "trainingPlanWorkout/generate/planWorkout/for/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> generatePlanWorkoutByUser(@PathVariable("userId") Integer userId,
            HttpSession session) {
        ResponseService responseService = new ResponseService();
        try {

            Calendar startCal = Calendar.getInstance();
            startCal.setTime(new Date());
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            Date startDate = startCal.getTime();
            trainingPlanWorkoutService.generatePlan(userId, startDate);

            UserDTO userDTO = userService.findById(userId);
            userDTO.setIndLoginFirstTime(0);
            userService.updateUser(userDTO);

            TrainingPlanUser trainingPlanUser = trainingPlanUserService.getTrainingPlanUserByUser(new User(userId));

            if (session.getAttribute("user") != null) {
                UserDTO userSession = (UserDTO) session.getAttribute("user");
                userSession.setIndLoginFirstTime(0);

                if (trainingPlanUser != null) {
                    userSession.setPlanActiveId(trainingPlanUser.getTrainingPlanId().getTrainingPlanId());
                    userSession.setTrainingPlanUserId(trainingPlanUser.getTrainingPlanUserId());
                }
            }

            responseService.setOutput("Plan de Entrenamiento generado satisfactoriamente.");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, e);
            responseService.setOutput(e.getMessage());
            responseService.setDetail(e.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "trainingPlanWorkout/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createActivityPlanWorkout(@RequestBody PlanWorkoutDTO planWorkoutDTO) {
        ResponseService responseService = new ResponseService();
        try {
            List<TrainingPlanUser> listTrainingPlanUser = trainingPlanUserService.getPlanWorkoutByUser(planWorkoutDTO.getUserId());

            if (listTrainingPlanUser != null && !listTrainingPlanUser.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date activityDate = dateFormat.parse(planWorkoutDTO.getActivityDate());
                responseService.setOutput("actividad creada en el plan exitosamente");
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                TrainingPlanWorkout planWorkout = new TrainingPlanWorkout();
                if (planWorkoutDTO.getActivityId() != null) {
                    planWorkout.setActivityId(new Activity(planWorkoutDTO.getActivityId()));
                    planWorkout.isDrag(Boolean.TRUE);
                } else if (planWorkoutDTO.getManualActivityId() != null) {
                    planWorkout.setManualActivityId(new ManualActivity(planWorkoutDTO.getManualActivityId()));
                    planWorkout.isDrag(Boolean.FALSE);
                }
                planWorkout.setTrainingPlanUserId(listTrainingPlanUser.get(0));
                planWorkout.setWorkoutDate(activityDate);
                trainingPlanWorkoutService.create(planWorkout);
            }

            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear plan");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "trainingPlanWorkout/createPlan", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createPlanWorkout(@RequestBody PlanWorkoutDTO planWorkoutDTO) {
        ResponseService responseService = new ResponseService();
        try {
            TrainingPlanWorkout trainingPlanWorkout = new TrainingPlanWorkout();
            trainingPlanWorkout.setTrainingPlanWorkoutId(planWorkoutDTO.getTrainingPlanWorkoutId());
            List<TrainingPlanWorkout> listTrainingPlanUser = trainingPlanWorkoutService.getById(trainingPlanWorkout);

            if (listTrainingPlanUser != null && !listTrainingPlanUser.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date activityDate = dateFormat.parse(planWorkoutDTO.getActivityDate());
                responseService.setOutput("actividad creada en el plan exitosamente");
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                TrainingPlanWorkout planWorkout = new TrainingPlanWorkout();
                if (listTrainingPlanUser.get(0).getActivityId() != null) {
                    planWorkout.setActivityId(listTrainingPlanUser.get(0).getActivityId());
                } else if (listTrainingPlanUser.get(0).getManualActivityId() != null) {
                    planWorkout.setManualActivityId(listTrainingPlanUser.get(0).getManualActivityId());
                }
                planWorkout.setTrainingPlanUserId(listTrainingPlanUser.get(0).getTrainingPlanUserId());
                planWorkout.setWorkoutDate(activityDate);
                trainingPlanWorkoutService.create(planWorkout);
            }

            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear plan");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "trainingPlanWorkout/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deletePlanWorkout(@RequestBody TrainingPlanWorkout trainingPlanWorkout) {
        ResponseService responseService = new ResponseService();
        try {
            trainingPlanWorkoutService.delete(trainingPlanWorkout);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear plan");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/trainingPlanWorkout/get/by/user/intervalDate/movil", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> getTrainingPlanWorkoutByUserInterval(@RequestBody PlanWorkoutDTO trainingPlanWorkout, HttpServletRequest request) {
        ResponseService responseService = new ResponseService();
        try {

            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);

            Calendar calendarTo = Calendar.getInstance(Locale.getDefault());
            calendarTo.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DAY_OF_MONTH));
            calendarTo.set(Calendar.MINUTE, 0);
            calendarTo.set(Calendar.SECOND, 0);
            calendarTo.set(Calendar.HOUR_OF_DAY, 0);

            if (trainingPlanWorkout.getYear() > 0) {
                calendar.set(Calendar.YEAR, trainingPlanWorkout.getYear());
                calendarTo.set(Calendar.YEAR, trainingPlanWorkout.getYear());
            }

            if (trainingPlanWorkout.getMonth() > 0) {
                calendar.set(Calendar.MONTH, trainingPlanWorkout.getMonth() - 1);
                calendarTo.set(Calendar.MONTH, trainingPlanWorkout.getMonth() - 1);
            }

            /* if (trainingPlanWorkout.getWeekMonth() > 0) {
                //calendarTo.set(Calendar.MONTH, month);
                calendar.set(Calendar.WEEK_OF_YEAR, trainingPlanWorkout.getWeekMonth()+1);
                calendarTo.set(Calendar.WEEK_OF_YEAR, trainingPlanWorkout.getWeekMonth()+1);
            }*/
            Date fromDate = calendar.getTime();
            Date toDate = calendarTo.getTime();

            List<TrainingPlanWorkoutDto> list = trainingPlanWorkoutService.getPlanWorkoutByUser(trainingPlanWorkout.getUserId(),
                    fromDate, toDate);

            for (TrainingPlanWorkoutDto trainingPlanWorkoutDto : list) {
                trainingPlanWorkoutDto.setStart(trainingPlanWorkoutDto.getWorkoutDate().getTime());
                trainingPlanWorkoutDto.setEnd(trainingPlanWorkoutDto.getWorkoutDate().getTime());
                String url = request.getRequestURL().substring(0, request.getRequestURL().indexOf("trainingPlanWorkout"));
                url += "static/img/";
                trainingPlanWorkoutDto.setUrlIcon(url + "/" + trainingPlanWorkoutDto.getSportIcon());
            }

            if (list == null) {
                list = new ArrayList();
            }

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(list);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar plan de entrenamiento");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "trainingPlanWorkout/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updatePlanWorkout(@RequestBody PlanWorkoutDTO planWorkoutDTO) {
        ResponseService responseService = new ResponseService();
        try {
            trainingPlanWorkoutService.update(planWorkoutDTO);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Actualizado correctamente");
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear plan");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "trainingPlanWorkout/update/workout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updatePlanWorkoutStrava(@RequestBody TrainingPlanWorkoutDto dto) {
        ResponseService responseService = new ResponseService();
        try {
            trainingPlanWorkoutService.updateStrava(dto, false);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Actualizado correctamente");
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear plan");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "trainingPlanWorkout/get/current/week", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> getCurrentWeek() {
        ResponseService responseService = new ResponseService();
        try {
            ZoneId zoneId = ZoneId.of("America/Bogota");
            ZonedDateTime now = ZonedDateTime.now(zoneId);
            int weekOfYear = now.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(weekOfYear);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al obtener el número de la semana");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "trainingPlanWorkout/get/activities/week/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> getActivitiesByWeek(@PathVariable("userId") Integer userId, HttpServletRequest request) {
        ResponseService responseService = new ResponseService();
        try {
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            Calendar calendarTo = Calendar.getInstance(Locale.getDefault());
            calendarTo.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            calendarTo.set(Calendar.MINUTE, 0);
            calendarTo.set(Calendar.SECOND, 0);
            calendarTo.set(Calendar.HOUR_OF_DAY, 0);
            Date fromDate = calendar.getTime();
            Date toDate = calendarTo.getTime();

            List<TrainingPlanWorkoutDto> list = trainingPlanWorkoutService.getPlanWorkoutByUser(userId, fromDate, toDate);

            list.stream().forEach((trainingPlanWorkoutDto) -> {
                String url = request.getRequestURL().substring(0, request.getRequestURL().indexOf("trainingPlanWorkout"));
                url += "static/img/";
                trainingPlanWorkoutDto.setUrlIcon(url + "/" + trainingPlanWorkoutDto.getSportIcon());
            });

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(list);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al obtener las actividades de la semana");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "trainingPlanWorkout/get/serie/{sesion}/{week}/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> getWorkoutSerie(@PathVariable("sesion") Integer sesion, @PathVariable("week") Integer week, @PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        try {

            List<UserZone> listUserZone = userZoneService.findByUserId(userId);
            List<TrainingUserSerieDTO> series = trainingPlanWorkoutService.getSerieBySesionWeekUser(userId, sesion, week);
            String ppm = "";
            String pace = "";
            for (TrainingUserSerieDTO serie : series) {
                
            
            for (UserZone userZone : listUserZone) {
                if (userZone.getZoneType().equals("1")) {
                    switch (serie.getNumZone()) {
                        case 1:
                            ppm = userZone.getZoneOne();
                            break;
                        case 2:
                            ppm = userZone.getZoneTwo();
                            break;
                        case 3:
                            ppm = userZone.getZoneThree();
                            break;
                        case 4:
                            ppm = userZone.getZoneFour();
                            break;
                        case 5:
                            ppm = userZone.getZoneFive();
                            break;
                        case 6:
                            ppm = userZone.getZoneSix();
                            break;
                        case 7:
                            ppm = userZone.getZoneSeven();
                            break;
                    }
                } else if (userZone.getZoneType().equals("2")) {
                    switch (serie.getNumZone()) {
                        case 1:
                            pace = userZone.getZoneOne();
                            break;
                        case 2:
                            pace = userZone.getZoneTwo();
                            break;
                        case 3:
                            pace = userZone.getZoneThree();
                            break;
                        case 4:
                            pace = userZone.getZoneFour();
                            break;
                        case 5:
                            pace = userZone.getZoneFive();
                            break;
                        case 6:
                            pace = userZone.getZoneSix();
                            break;
                        case 7:
                            pace = userZone.getZoneSeven();
                            break;
                    }
                }
            }
            
            long iPart = serie.getSerieTime().longValue();
            double fPart = serie.getSerieTime() - iPart;
            Integer seconds = (int) Math.round(fPart * 60);
            String secondstr = seconds.toString();
            if (seconds == 0) {
                secondstr = "00";
            }
            if (ppm != null && pace != null) {
                ppm = ppm + " ppm";
                pace = pace + " min/km";
                serie.setDescription(serie.getNumSeries() + " series de " + iPart + ":" + secondstr + " minutos en z" + serie.getNumZone() + " (" + pace + " o " + ppm + ")");
            } else if (ppm != null) {
                ppm = ppm + " ppm";
                serie.setDescription(serie.getNumSeries() + " series de " + iPart + ":" + secondstr + " minutos en z" + serie.getNumZone() + " (" + ppm + ")");
            } else if (pace != null) {
                pace = pace + " min/km";
                serie.setDescription(serie.getNumSeries() + " series de " + iPart + ":" + secondstr + " minutos en z" + serie.getNumZone() + " (" + pace + ")");
            } else {
                serie.setDescription(serie.getNumSeries() + " series de " + iPart + ":" + secondstr + " minutos en z" + serie.getNumZone());
            }
            }
            responseService.setOutput(series);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear plan");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

}
