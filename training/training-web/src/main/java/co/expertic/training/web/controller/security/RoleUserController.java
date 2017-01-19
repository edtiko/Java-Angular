package co.expertic.training.web.controller.security;

import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.RoleUser;
import java.util.List;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.security.RoleUserService;
import co.expertic.training.web.enums.StatusResponse;
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
* RoleUser Controller <br>
* Info. Creaci贸n: <br>
* fecha 10/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
@RequestMapping("roleuser")
public class RoleUserController {

    @Autowired
    RoleUserService roleUserService;  

    /**
     * Crea roleUser <br>
     * Info. Creaci贸n: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param roleUser
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createRoleUser(@RequestBody RoleUser roleUser) {
            ResponseService responseService = new ResponseService();
        try {           
            roleUserService.create(roleUser);
            responseService.setOutput("RoleUser creado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RoleUserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear roleuser");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica roleUser <br>
     * Info. Creaci贸n: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param roleUser
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateRoleUser(@RequestBody RoleUser roleUser) {
            ResponseService responseService = new ResponseService();
        try {           
            roleUserService.store(roleUser);
            responseService.setOutput("RoleUser editado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RoleUserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar roleuser");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina roleUser <br>
     * Info. Creaci贸n: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param roleUser
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteRoleUser(@RequestBody RoleUser roleUser) {
            ResponseService responseService = new ResponseService();
        try {           
            roleUserService.remove(roleUser);
            responseService.setOutput("RoleUser eliminado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RoleUserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar roleuser");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta roleUser <br>
     * Info. Creaci贸n: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<RoleUser> roleUserList = roleUserService.findAll();
            responseService.setOutput(roleUserList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RoleUserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar roleuser");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta roleUser <br>
     * Info. Creaci髇: <br>
     * fecha 19/01/2017 <br>
     *
     * @author Edwin G髆ez
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/users/by/role/{roleId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> usersByRole(@PathVariable("roleId") Integer roleId) {
        ResponseService responseService = new ResponseService();
        try {
            List<UserDTO> list = roleUserService.getUsersByRole(roleId);
            responseService.setOutput(list);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RoleUserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar roleuser");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
