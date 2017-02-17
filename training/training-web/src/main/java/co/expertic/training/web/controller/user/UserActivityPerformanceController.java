package co.expertic.training.web.controller.user;

import co.expertic.base.util.DateUtil;
import co.expertic.base.util.MessageUtil;
import co.expertic.training.model.dto.ChartDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.UserActivityPerformanceDTO;
import co.expertic.training.model.entities.ActivityPerformanceMetafield;
import co.expertic.training.model.entities.UserActivityPerformance;
import java.util.List;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.model.util.UtilDate;
import co.expertic.training.service.user.UserActivityPerformanceService;
import co.expertic.training.web.enums.StatusResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserActivityPerformance Controller <br>
 * Info. Creaci贸n: <br>
 * fecha Sep 15, 2016 <br>
 *
 * @author Andres Felipe Lopez Rodriguez
 *
 */
@RestController
public class UserActivityPerformanceController {

    @Autowired
    UserActivityPerformanceService userActivityPerformanceService;

    /**
     * Crea userActivityPerformance <br>
     * Info. Creaci贸n: <br>
     * fecha Sep 15, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param userActivityPerformance
     * @return
     */
    @RequestMapping(value = "userActivityPerformance/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createUserActivityPerformance(@RequestBody UserActivityPerformance userActivityPerformance) {
        ResponseService responseService = new ResponseService();
        try {
            userActivityPerformance.setCreationDate(new Date());
            userActivityPerformanceService.create(userActivityPerformance);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.useractivityperformance", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserActivityPerformanceController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica userActivityPerformance <br>
     * Info. Creaci贸n: <br>
     * fecha Sep 15, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param userActivityPerformance
     * @return
     */
    @RequestMapping(value = "userActivityPerformance/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateUserActivityPerformance(@RequestBody UserActivityPerformance userActivityPerformance) {
        ResponseService responseService = new ResponseService();
        try {
            userActivityPerformanceService.store(userActivityPerformance);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.useractivityperformance", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserActivityPerformanceController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina userActivityPerformance <br>
     * Info. Creaci贸n: <br>
     * fecha Sep 15, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param userActivityPerformance
     * @return
     */
    @RequestMapping(value = "userActivityPerformance/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteUserActivityPerformance(@RequestBody UserActivityPerformance userActivityPerformance) {
        ResponseService responseService = new ResponseService();
        try {
            userActivityPerformanceService.remove(userActivityPerformance);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.useractivityperformance", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserActivityPerformanceController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta userActivityPerformance <br>
     * Info. Creaci贸n: <br>
     * fecha Sep 15, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/userActivityPerformance/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {
            List<UserActivityPerformance> userActivityPerformanceList = userActivityPerformanceService.findAllActive();
            responseService.setOutput(userActivityPerformanceList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserActivityPerformanceController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta userActivityPerformance paginado <br>
     * Info. Creaci贸n: <br>
     * fecha Sep 15, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/userActivityPerformance/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {
            paginateDto.setPage((paginateDto.getPage() - 1) * paginateDto.getLimit());
            List<UserActivityPerformanceDTO> userActivityPerformanceList = userActivityPerformanceService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(),
                    paginateDto.getOrder(), paginateDto.getFilter());
            responseService.setOutput(userActivityPerformanceList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserActivityPerformanceController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta userActivityPerformance por rango de fechas y user Id <br>
     * Info. Creaci贸n: <br>
     * fecha Sep 15, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param user
     * @param fromDate
     * @param toDate
     * @return
     */
    @RequestMapping(value = "/userActivityPerformance/get/by/userId/rangeDate/{userId}/{fromDate}/{toDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listByRangeDateAndUser(@PathVariable("userId") Integer user, @PathVariable("fromDate") String fromDate,
            @PathVariable("toDate") String toDate) {
        ResponseService responseService = new ResponseService();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdf.parse(fromDate);
            Date d2 = sdf.parse(toDate);
            List<UserActivityPerformanceDTO> userActivityPerformanceList = userActivityPerformanceService.findByDateRangeAndUserId(d1, d2, user);
            responseService.setOutput(userActivityPerformanceList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserActivityPerformanceController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta userActivityPerformance por rango de fechas y user Id y
     * metafield<br>
     * Info. Creaci贸n: <br>
     * fecha Sep 15, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param user
     * @param fromDate
     * @param toDate
     * @param metafield
     * @return
     */
    @RequestMapping(value = "/userActivityPerformance/get/by/userId/rangeDate/metafield/{userId}/{fromDate}/{toDate}/{metafield}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listByRangeDateAndUser(@PathVariable("userId") Integer user, @PathVariable("fromDate") String fromDate,
            @PathVariable("toDate") String toDate, @PathVariable("metafield") Integer metafield) {
        ResponseService responseService = new ResponseService();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdf.parse(fromDate);
            Date d2 = sdf.parse(toDate);
            List<ChartDTO> userActivityPerformanceList = userActivityPerformanceService.findByDateRangeAndUserIdAndMetaField(d1, d2, user, metafield);
            responseService.setOutput(userActivityPerformanceList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserActivityPerformanceController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta userActivityPerformance por rango de fechas y user Id y
     * metafield<br>
     * Info. Creaci贸n: <br>
     * fecha Sep 15, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param user
     * @param fromDate
     * @param toDate
     * @param metafield
     * @param days
     * @param weekly
     * @return
     */
    @RequestMapping(value = "/userActivityPerformance/get/by/userId/rangeDate/metafield/days/weekly/{userId}/{fromDate}/{toDate}/{metafield}/{days}/{weekly}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listByRangeDateAndUser(@PathVariable("userId") Integer user, @PathVariable("fromDate") String fromDate,
            @PathVariable("toDate") String toDate, @PathVariable("metafield") Integer metafield, @PathVariable("days") Integer days, @PathVariable("weekly") boolean weekly) {
        ResponseService responseService = new ResponseService();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdf.parse(fromDate);
            Date d2 = sdf.parse(toDate);
            List<ChartDTO> userActivityPerformanceList = userActivityPerformanceService.findByDateRangeAndUserIdAndMetaField(d1, d2, user, metafield, days, weekly);
            responseService.setOutput(userActivityPerformanceList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserActivityPerformanceController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta userActivityPerformance el reporte de actividades por user id y
     * la semana<br>
     * Info. Creaci贸n: <br>
     * fecha Sep 15, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param user
     * @param fromDate
     * @param toDate
     * @return
     */
    @RequestMapping(value = "/userActivityPerformance/get/activities/by/userId/rangeDate/{userId}/{fromDate}/{toDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listActivities(@PathVariable("userId") Integer user, @PathVariable("fromDate") String fromDate,
            @PathVariable("toDate") String toDate) {
        ResponseService responseService = new ResponseService();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdf.parse(fromDate);
            Date d2 = sdf.parse(toDate);
            List<ChartDTO> userActivityPerformanceList = userActivityPerformanceService.findActivitiesByDateRangeAndUserId(d1, d2, user);
            responseService.setOutput(userActivityPerformanceList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserActivityPerformanceController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta userActivityPerformance por rango de fechas y user Id <br>
     * Info. Creaci贸n: <br>
     * fecha Sep 15, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/userActivityPerformance/get/by/userId/movil/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listByRangeDateAndUserMovil(@PathVariable("userId") Integer user, 
            HttpServletRequest request
    ) {
        ResponseService responseService = new ResponseService();
        try {
            String uri = request.getRequestURL().substring(0, request.getRequestURL().indexOf("/userActivityPerformance/get"));
            uri += "/static/img/";
            Date d1 = DateUtil.sumDaysToDate(new Date(), -7);
            Date d2 = new Date();
            List<UserActivityPerformanceDTO> userActivityPerformanceList = userActivityPerformanceService.findConsolidationByDateRangeAndUserId(d1, d2, user);

            for (UserActivityPerformanceDTO userActivityPerformanceDTO : userActivityPerformanceList) {
                if (userActivityPerformanceDTO.getActivityPerformanceMetafieldId() != null
                        && userActivityPerformanceDTO.getActivityPerformanceMetafieldId().getActivityPerformanceMetafieldId() != null) {
                    Integer graphicId = userActivityPerformanceDTO.getActivityPerformanceMetafieldId().getActivityPerformanceMetafieldId();
                    userActivityPerformanceDTO.setTextLastExecution("趌timos 7 d韆s");
                    if (graphicId.equals(1)) {
                        userActivityPerformanceDTO.setPhotoUrl(uri + "checked-list.png");    
                        userActivityPerformanceDTO.getActivityPerformanceMetafieldId().setName("Actividades");
                    }

                    if (graphicId.equals(2)) {
                        try {
                            userActivityPerformanceDTO.setValue(String.format("%.2f", Double.valueOf(userActivityPerformanceDTO.getValue())));
                        } catch (NumberFormatException n) {
                            userActivityPerformanceDTO.setValue("0");
                        }
                        
                        userActivityPerformanceDTO.setPhotoUrl(uri + "fire.png");
                        userActivityPerformanceDTO.setMeasure("cal");
                        userActivityPerformanceDTO.getActivityPerformanceMetafieldId().setName("Calorias");
                    }

                    if (graphicId.equals(3)) {
                        try {
                            userActivityPerformanceDTO.setValue(String.format("%.2f", Double.valueOf(userActivityPerformanceDTO.getValue())));
                        } catch (NumberFormatException n) {
                            userActivityPerformanceDTO.setValue("0");
                        }
                        userActivityPerformanceDTO.setPhotoUrl(uri + "distance.png");
                        userActivityPerformanceDTO.setMeasure("mts");
                        userActivityPerformanceDTO.getActivityPerformanceMetafieldId().setName("Distancia");
                    }

                    if (graphicId.equals(4)) {
                        userActivityPerformanceDTO.setPhotoUrl(uri + "heart-beats_read.png");
                        userActivityPerformanceDTO.setMeasure("ppm");
                        userActivityPerformanceDTO.getActivityPerformanceMetafieldId().setName("Frecuencia Max");
                    }

                    if (graphicId.equals(5)) {
                        userActivityPerformanceDTO.setPhotoUrl(uri + "heart-beats_blue.png");
                        userActivityPerformanceDTO.setMeasure("ppm");
                        userActivityPerformanceDTO.getActivityPerformanceMetafieldId().setName("Frecuencia Med");
                    }

                    if (graphicId.equals(6)) {
                        userActivityPerformanceDTO.setPhotoUrl(uri + "line-chart.png");
                        userActivityPerformanceDTO.setMeasure("seg/mts");
                        userActivityPerformanceDTO.getActivityPerformanceMetafieldId().setName("Ritmo Medio");
                    }
                }
            }

            responseService.setOutput(userActivityPerformanceList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserActivityPerformanceController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta userActivityPerformance por rango de fechas y user Id y
     * metafield<br>
     * Info. Creaci贸n: <br>
     * fecha Sep 15, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param user
     * @param countDays
     * @param metafield
     * @return
     */
    @RequestMapping(value = "/userActivityPerformance/get/by/userId/rangeDate/metafield/days/{userId}/{countDays}/{metafield}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listByRangeDateAndUserMovil(@PathVariable("userId") Integer user,
            @PathVariable("countDays") int countDays, @PathVariable("metafield") Integer metafield
    ) {
        ResponseService responseService = new ResponseService();
        try {
            Date d1 = DateUtil.sumDaysToDate(new Date(), -countDays);
            Date d2 = new Date();
            boolean week = true;
            
            if(countDays > 100) {
                week = false;
            }
            List<ChartDTO> userActivityPerformanceList = userActivityPerformanceService.findByDateRangeAndUserIdAndMetaField(d1, d2, user, metafield, countDays, week);
            
            if(week && userActivityPerformanceList != null && userActivityPerformanceList.size() > 4) {
                userActivityPerformanceList.remove(0);
            }
            
            responseService.setOutput(userActivityPerformanceList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserActivityPerformanceController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
   /** Consulta getProgressReport <br>
     * Info. Creacion: <br>
     * fecha Feb 17, 2017 <br>
     *
     * @author Edwin G髆ez
     * @param date
     * @param activity
     * @return
     */
    @RequestMapping(value = "get/userActivityPerformance/progress/{date}/{activity}/{userId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> getProgressReport(@PathVariable("date") Integer date, @PathVariable("activity") Integer activity) {
        ResponseService responseService = new ResponseService();
        try {
            List<UserActivityPerformance> userActivityPerformanceList = userActivityPerformanceService.getProgressReport(date, activity, userId);
            responseService.setOutput(userActivityPerformanceList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserActivityPerformanceController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
