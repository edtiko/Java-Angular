package co.com.expertla.training.web.controller.configuration;

import co.com.expertla.training.model.dto.ObjectiveDTO;
import co.com.expertla.training.model.entities.ResponseService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import co.com.expertla.training.service.configuration.ObjectiveService;
import java.util.logging.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

/**
* Controller for Objective <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
@RestController()
public class ObjectiveController {
    
    @Autowired
    private ObjectiveService objectiveService;
    
    @RequestMapping(value = "objective/get/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAll() {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            List<ObjectiveDTO> objectives = objectiveService.findAll();
            responseService.setOutput(objectives);
            return Response.status(Response.Status.OK).entity(responseService).build();
        }   catch (Exception e) {
            Logger.getLogger(ObjectiveController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
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

}
