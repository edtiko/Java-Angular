package co.expertic.training.web.controller.configuration;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.model.dto.ActivityPerformanceMetafieldDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.entities.ActivityPerformanceMetafield;
import java.util.List;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.ActivityPerformanceMetafieldService;
import co.expertic.training.web.enums.StatusResponse;
import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
* ActivityPerformanceMetafield Controller <br>
* Info. Creación: <br>
* fecha Sep 15, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class ActivityPerformanceMetafieldController {

    @Autowired
    ActivityPerformanceMetafieldService activityPerformanceMetafieldService;  

    /**
     * Crea activityPerformanceMetafield <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param activityPerformanceMetafield
     * @return
     */
    @RequestMapping(value = "activityPerformanceMetafield/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createActivityPerformanceMetafield(@RequestBody ActivityPerformanceMetafield activityPerformanceMetafield) {
            ResponseService responseService = new ResponseService();
        try {  
            ActivityPerformanceMetafield activityPerformanceMetafieldName = new ActivityPerformanceMetafield();
            activityPerformanceMetafieldName.setName(activityPerformanceMetafield.getName());
            List<ActivityPerformanceMetafield> listActivityPerformanceMetafieldName = activityPerformanceMetafieldService.findByName(activityPerformanceMetafieldName);
            
            if(listActivityPerformanceMetafieldName != null && !listActivityPerformanceMetafieldName.isEmpty()) {
                responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.activityperformancemetafield", "msgNombreExiste"));
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            
            activityPerformanceMetafieldService.create(activityPerformanceMetafield);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.activityperformancemetafield", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityPerformanceMetafieldController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica activityPerformanceMetafield <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param activityPerformanceMetafield
     * @return
     */
    @RequestMapping(value = "activityPerformanceMetafield/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateActivityPerformanceMetafield(@RequestBody ActivityPerformanceMetafield activityPerformanceMetafield) {
            ResponseService responseService = new ResponseService();
        try {    
            ActivityPerformanceMetafield activityPerformanceMetafieldName = new ActivityPerformanceMetafield();
            activityPerformanceMetafieldName.setName(activityPerformanceMetafield.getName());
            List<ActivityPerformanceMetafield> listActivityPerformanceMetafieldName = activityPerformanceMetafieldService.findByName(activityPerformanceMetafieldName);
            
            if(listActivityPerformanceMetafieldName != null && !listActivityPerformanceMetafieldName.isEmpty()) {
                boolean existName = false;
                for (ActivityPerformanceMetafield activityPerformanceMetafield1 : listActivityPerformanceMetafieldName) {
                    if (!activityPerformanceMetafield1.getActivityPerformanceMetafieldId().equals(activityPerformanceMetafield.getActivityPerformanceMetafieldId())) {
                        existName = true;
                    }
                }

                if (existName) {
                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.activityperformancemetafield", "msgNombreExiste"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }                
            }
            
            activityPerformanceMetafieldService.store(activityPerformanceMetafield);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.activityperformancemetafield", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityPerformanceMetafieldController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina activityPerformanceMetafield <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param activityPerformanceMetafield
     * @return
     */
    @RequestMapping(value = "activityPerformanceMetafield/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteActivityPerformanceMetafield(@RequestBody ActivityPerformanceMetafield activityPerformanceMetafield) {
            ResponseService responseService = new ResponseService();
        try {           
            activityPerformanceMetafieldService.remove(activityPerformanceMetafield);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.activityperformancemetafield", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityPerformanceMetafieldController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta activityPerformanceMetafield <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/activityPerformanceMetafield/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<ActivityPerformanceMetafield> activityPerformanceMetafieldList = activityPerformanceMetafieldService.findAllActive();
            responseService.setOutput(activityPerformanceMetafieldList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityPerformanceMetafieldController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta activityPerformanceMetafield paginado <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/activityPerformanceMetafield/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<ActivityPerformanceMetafieldDTO> activityPerformanceMetafieldList = activityPerformanceMetafieldService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(),
                paginateDto.getOrder(), paginateDto.getFilter());
            responseService.setOutput(activityPerformanceMetafieldList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityPerformanceMetafieldController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
