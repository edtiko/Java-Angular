package co.com.expertla.training.web.user.controller;

import co.com.expertla.training.model.entities.UserZone;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.user.UserZoneService;
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
* UserZone Controller <br>
* Date Creation: <br>
* date Aug 29, 2016 <br>
* @author Angela Ramirez
**/

@RestController
public class UserZoneController {

    @Autowired
    UserZoneService userZoneService;  

    /**
     * Crea userZone <br>
     * Creation Date: <br>
     * fecha Aug 29, 2016 <br>
     * @author Angela Ramirez
     * @param userZone
     * @return
     */
    @RequestMapping(value = "userzone/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createUserZone(@RequestBody UserZone userZone) {
            ResponseService responseService = new ResponseService();
        try {           
            userZoneService.create(userZone);
            responseService.setOutput("UserZone creado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserZoneController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear userzone");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica userZone <br>
     * Creation Date: <br>
     * fecha Aug 29, 2016 <br>
     * @author Angela Ramirez
     * @param userZone
     * @return
     */
    @RequestMapping(value = "userzone/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateUserZone(@RequestBody UserZone userZone) {
            ResponseService responseService = new ResponseService();
        try {           
            userZoneService.store(userZone);
            responseService.setOutput("UserZone editado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserZoneController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar userzone");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina userZone <br>
     * Creation Date: <br>
     * fecha Aug 29, 2016 <br>
     * @author Angela Ramirez
     * @param userZone
     * @return
     */
    @RequestMapping(value = "userzone/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteUserZone(@RequestBody UserZone userZone) {
            ResponseService responseService = new ResponseService();
        try {           
            userZoneService.remove(userZone);
            responseService.setOutput("UserZone eliminado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserZoneController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar userzone");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta userZone <br>
     * Creation Date: <br>
     * fecha Aug 29, 2016 <br>
     * @author Angela Ramirez
     * @param userZone
     * @return
     */
    @RequestMapping(value = "/userzone/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<UserZone> userZoneList = userZoneService.findAll();
            responseService.setOutput(userZoneList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserZoneController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar userzone");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
