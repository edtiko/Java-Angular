package co.com.expertla.training.web.controller.plan;

import co.com.expertla.training.model.entities.TrainingPlanUser;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.plan.TrainingPlanUserService;
import co.com.expertla.training.web.enums.StatusResponse;
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
* TrainingPlanUser Controller <br>
* Info. Creación: <br>
* fecha 10/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class TrainingPlanUserController {

    @Autowired
    TrainingPlanUserService trainingPlanUserService;  

    /**
     * Crea trainingPlanUser <br>
     * Info. Creación: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanUser
     * @return
     */
    @RequestMapping(value = "trainingplanuser/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createTrainingPlanUser(@RequestBody TrainingPlanUser trainingPlanUser) {
            ResponseService responseService = new ResponseService();
        try {           
            trainingPlanUserService.create(trainingPlanUser);
            responseService.setOutput("TrainingPlanUser creado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanUserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear trainingplanuser");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica trainingPlanUser <br>
     * Info. Creación: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanUser
     * @return
     */
    @RequestMapping(value = "trainingplanuser/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateTrainingPlanUser(@RequestBody TrainingPlanUser trainingPlanUser) {
            ResponseService responseService = new ResponseService();
        try {           
            trainingPlanUserService.store(trainingPlanUser);
            responseService.setOutput("TrainingPlanUser editado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanUserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar trainingplanuser");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina trainingPlanUser <br>
     * Info. Creación: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanUser
     * @return
     */
    @RequestMapping(value = "trainingplanuser/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteTrainingPlanUser(@RequestBody TrainingPlanUser trainingPlanUser) {
            ResponseService responseService = new ResponseService();
        try {           
            trainingPlanUserService.remove(trainingPlanUser);
            responseService.setOutput("TrainingPlanUser eliminado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanUserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar trainingplanuser");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta trainingPlanUser <br>
     * Info. Creación: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanUser
     * @return
     */
    @RequestMapping(value = "/trainingplanuser/", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<TrainingPlanUser> trainingPlanUserList = trainingPlanUserService.findAll();
            responseService.setOutput(trainingPlanUserList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanUserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar trainingplanuser");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
