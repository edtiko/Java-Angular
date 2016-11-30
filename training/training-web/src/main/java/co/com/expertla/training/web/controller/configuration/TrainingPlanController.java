package co.com.expertla.training.web.controller.configuration;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.dto.TrainingPlanDTO;
import co.com.expertla.training.model.entities.TrainingPlan;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.configuration.TrainingPlanService;
import co.com.expertla.training.web.enums.StatusResponse;
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
* TrainingPlan Controller <br>
* Info. Creación: <br>
* fecha 23/11/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class TrainingPlanController {

    @Autowired
    TrainingPlanService trainingPlanService;  

    /**
     * Crea trainingPlan <br>
     * Info. Creación: <br>
     * fecha 23/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlan
     * @return
     */
    @RequestMapping(value = "trainingPlan/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createTrainingPlan(@RequestBody TrainingPlan trainingPlan) {
            ResponseService responseService = new ResponseService();
        try {  
            TrainingPlan trainingPlanName = new TrainingPlan();
            trainingPlanName.setName(trainingPlan.getName());
            List<TrainingPlan> listTrainingPlanName = trainingPlanService.findByName(trainingPlanName);
            
            if(listTrainingPlanName != null && !listTrainingPlanName.isEmpty()) {
                responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.trainingplan", "msgNombreExiste"));
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            
            trainingPlan.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            trainingPlan.setCreationDate(new Date());
            trainingPlanService.create(trainingPlan);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.trainingplan", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica trainingPlan <br>
     * Info. Creación: <br>
     * fecha 23/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlan
     * @return
     */
    @RequestMapping(value = "trainingPlan/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateTrainingPlan(@RequestBody TrainingPlan trainingPlan) {
            ResponseService responseService = new ResponseService();
        try {    
            TrainingPlan trainingPlanName = new TrainingPlan();
            trainingPlanName.setName(trainingPlan.getName());
            List<TrainingPlan> listTrainingPlanName = trainingPlanService.findByName(trainingPlanName);
            
            if(listTrainingPlanName != null && !listTrainingPlanName.isEmpty()) {
                boolean existName = false;
                for (TrainingPlan trainingPlan1 : listTrainingPlanName) {
                    if (!trainingPlan1.getTrainingPlanId().equals(trainingPlan.getTrainingPlanId())) {
                        existName = true;
                    }
                }

                if (existName) {
                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.trainingplan", "msgNombreExiste"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }                
            }
            
            trainingPlan.setLastUpdate(new Date());
            trainingPlanService.store(trainingPlan);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.trainingplan", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina trainingPlan <br>
     * Info. Creación: <br>
     * fecha 23/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlan
     * @return
     */
    @RequestMapping(value = "trainingPlan/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteTrainingPlan(@RequestBody TrainingPlan trainingPlan) {
            ResponseService responseService = new ResponseService();
        try {           
            trainingPlanService.remove(trainingPlan);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.trainingplan", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta trainingPlan <br>
     * Info. Creación: <br>
     * fecha 23/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/trainingPlan/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<TrainingPlan> trainingPlanList = trainingPlanService.findAllActive();
            responseService.setOutput(trainingPlanList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta trainingPlan paginado <br>
     * Info. Creación: <br>
     * fecha 23/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/trainingPlan/paginated/{typePlan}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto, @PathVariable String typePlan) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<TrainingPlanDTO> trainingPlanList = trainingPlanService.findPaginate(paginateDto.getPage(), 
                    paginateDto.getLimit(),
                paginateDto.getOrder(), paginateDto.getFilter(), typePlan);
            responseService.setOutput(trainingPlanList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta trainingPlan <br>
     * Info. Creación: <br>
     * fecha 23/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param typeUser
     * @return
     */
    @RequestMapping(value = "/trainingPlan/get/all/plataform/{typeUser}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> listPlataform(@PathVariable String typeUser) {
        ResponseService responseService = new ResponseService();
        try {     
            List<TrainingPlan> trainingPlanList = trainingPlanService.findPlaformAllActive(typeUser);
            responseService.setOutput(trainingPlanList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
