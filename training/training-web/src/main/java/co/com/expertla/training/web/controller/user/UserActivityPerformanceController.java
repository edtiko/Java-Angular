package co.com.expertla.training.web.controller.user;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.model.dto.ChartDTO;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.dto.UserActivityPerformanceDTO;
import co.com.expertla.training.model.entities.UserActivityPerformance;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.user.UserActivityPerformanceService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
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
* Info. Creación: <br>
* fecha Sep 15, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class UserActivityPerformanceController {

    @Autowired
    UserActivityPerformanceService userActivityPerformanceService;  

    /**
     * Crea userActivityPerformance <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
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
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.useractivityperformance", "msgRegistroCreado"));
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
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userActivityPerformance
     * @return
     */
    @RequestMapping(value = "userActivityPerformance/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateUserActivityPerformance(@RequestBody UserActivityPerformance userActivityPerformance) {
            ResponseService responseService = new ResponseService();
        try {    
            userActivityPerformanceService.store(userActivityPerformance);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.useractivityperformance", "msgRegistroEditado"));
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
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userActivityPerformance
     * @return
     */
    @RequestMapping(value = "userActivityPerformance/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteUserActivityPerformance(@RequestBody UserActivityPerformance userActivityPerformance) {
            ResponseService responseService = new ResponseService();
        try {           
            userActivityPerformanceService.remove(userActivityPerformance);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.useractivityperformance", "msgRegistroEliminado"));
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
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
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
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/userActivityPerformance/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
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
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param user
     * @param fromDate
     * @param toDate
     * @return
     */
    @RequestMapping(value = "/userActivityPerformance/get/by/userId/rangeDate/{userId}/{fromDate}/{toDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listByRangeDateAndUser(@PathVariable("userId") Integer user,@PathVariable("fromDate") String fromDate,
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
     * Consulta userActivityPerformance por rango de fechas y user Id y metafield<br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param user
     * @param fromDate
     * @param toDate
     * @param metafield
     * @return
     */
    @RequestMapping(value = "/userActivityPerformance/get/by/userId/rangeDate/metafield/{userId}/{fromDate}/{toDate}/{metafield}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listByRangeDateAndUser(@PathVariable("userId") Integer user,@PathVariable("fromDate") String fromDate,
            @PathVariable("toDate") String toDate,@PathVariable("metafield") Integer metafield) {
        ResponseService responseService = new ResponseService();
        try {   
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdf.parse(fromDate);
            Date d2 = sdf.parse(toDate);
            List<ChartDTO> userActivityPerformanceList = userActivityPerformanceService.findByDateRangeAndUserIdAndMetaField(d1, d2, user,metafield);
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
     * Consulta userActivityPerformance por rango de fechas y user Id y metafield<br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
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
    public ResponseEntity<ResponseService> listByRangeDateAndUser(@PathVariable("userId") Integer user,@PathVariable("fromDate") String fromDate,
            @PathVariable("toDate") String toDate,@PathVariable("metafield") Integer metafield,@PathVariable("days") Integer days,@PathVariable("weekly") boolean weekly) {
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
     * Consulta userActivityPerformance el reporte de actividades por user id y la semana<br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param user
     * @param fromDate
     * @param toDate
     * @return
     */
    @RequestMapping(value = "/userActivityPerformance/get/activities/by/userId/rangeDate/{userId}/{fromDate}/{toDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listActivities(@PathVariable("userId") Integer user,@PathVariable("fromDate") String fromDate,
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
}
