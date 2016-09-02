package co.com.expertla.training.web.controller.security;

import co.com.expertla.training.model.dto.RoleDTO;
import co.com.expertla.training.model.entities.Role;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.security.RoleService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.util.logging.Level;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* Role Controller <br>
* Creation Date: <br>
* date 19/08/2016 <br>
* @author Angela Ramirez O
**/

@RestController
public class RoleController {

    @Autowired
    RoleService roleService;  

    /**
     * Crea role <br>
     * Creation Date: <br>
     * date 19/08/2016 <br>
     * @author Angela Ramirez O
     * @param role
     * @return
     */
    @RequestMapping(value = "role/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createRole(@RequestBody Role role) {
            ResponseService responseService = new ResponseService();
        try {           
            roleService.create(role);
            responseService.setOutput("Role creado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RoleController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear role");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica role <br>
     * Creation Date: <br>
     * date 19/08/2016 <br>
     * @author Angela Ramirez O
     * @param role
     * @return
     */
    @RequestMapping(value = "role/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateRole(@RequestBody Role role) {
            ResponseService responseService = new ResponseService();
        try {           
            roleService.store(role);
            responseService.setOutput("Role editado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RoleController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar role");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina role <br>
     * Creation Date: <br>
     * date 19/08/2016 <br>
     * @author Angela Ramirez O
     * @param role
     * @return
     */
    @RequestMapping(value = "role/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteRole(@RequestBody Role role) {
            ResponseService responseService = new ResponseService();
        try {           
            roleService.remove(role);
            responseService.setOutput("Role eliminado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RoleController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar role");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta role <br>
     * Creation Date: <br>
     * date 19/08/2016 <br>
     * @author Angela Ramirez O
     * @return
     */
    @RequestMapping(value = "role/get/all", method = RequestMethod.GET)
    public Response list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<Role> roleList = roleService.findAllActive();
            responseService.setOutput(roleList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RoleController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al obtener los roles");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
}