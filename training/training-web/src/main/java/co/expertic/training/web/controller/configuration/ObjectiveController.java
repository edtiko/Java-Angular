package co.expertic.training.web.controller.configuration;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.ObjectiveDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.entities.Objective;
import java.util.List;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.ObjectiveService;
import co.expertic.training.web.enums.StatusResponse;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;


/**
* Objective Controller <br>
* Info. Creación: <br>
* fecha 30/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class ObjectiveController {

    @Autowired
    ObjectiveService objectiveService;  

    /**
     * Crea objective <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param objective
     * @return
     */
    @RequestMapping(value = "objective/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createObjective(@RequestBody Objective objective) {
            ResponseService responseService = new ResponseService();
        try {  
            Objective objectiveName = new Objective();
            objectiveName.setName(objective.getName());
            List<Objective> listObjectiveName = objectiveService.findByFiltro(objectiveName);
            
            if(listObjectiveName != null && !listObjectiveName.isEmpty()) {
                responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.objective", "msgNombreExiste"));
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            
            objective.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            objective.setCreationDate(new Date());
            objectiveService.create(objective);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.objective", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ObjectiveController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica objective <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param objective
     * @return
     */
    @RequestMapping(value = "objective/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateObjective(@RequestBody Objective objective) {
            ResponseService responseService = new ResponseService();
        try {    
            Objective objectiveName = new Objective();
            objectiveName.setName(objective.getName());
            List<Objective> listObjectiveName = objectiveService.findByFiltro(objectiveName);
            
            if(listObjectiveName != null && !listObjectiveName.isEmpty()) {
                boolean existName = false;
                for (Objective objective1 : listObjectiveName) {
                    if (!objective1.getObjectiveId().equals(objective.getObjectiveId())) {
                        existName = true;
                    }
                }

                if (existName) {
                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.objective", "msgNombreExiste"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }                
            }
            
            objective.setLastUpdate(new Date());
            objectiveService.store(objective);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.objective", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ObjectiveController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina objective <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param objective
     * @return
     */
    @RequestMapping(value = "objective/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteObjective(@RequestBody Objective objective) {
            ResponseService responseService = new ResponseService();
        try {           
            objectiveService.remove(objective);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.objective", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ObjectiveController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta objective <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/objective/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<Objective> objectiveList = objectiveService.findAllActive();
            responseService.setOutput(objectiveList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ObjectiveController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
     /**
     * Consulta objective por discipline id <br>
     * Creation Date: <br>
     * date 26/08/2016 <br>
     * @author Angela Ramirez
     * @param disciplineId
     * @return
     */
    @RequestMapping(value = "objective/get/by/discipline/{disciplineId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> listByDiscipline(@PathVariable("disciplineId") Integer disciplineId) {
        ResponseService responseService = new ResponseService();
        try {     
            List<ObjectiveDTO> objectives = objectiveService.findByDiscipline(disciplineId);
            responseService.setOutput(objectives);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ObjectiveController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar los objectives");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta objective paginado <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/objective/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<ObjectiveDTO> objectiveList = objectiveService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(), paginateDto.getOrder());
            responseService.setOutput(objectiveList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ObjectiveController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
