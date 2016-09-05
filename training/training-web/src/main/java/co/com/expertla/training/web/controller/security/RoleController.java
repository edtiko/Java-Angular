package co.com.expertla.training.web.controller.security;

<<<<<<< HEAD
import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.PaginateDto;
=======
>>>>>>> origin/configuracion_sprint2
import co.com.expertla.training.model.dto.RoleDTO;
import co.com.expertla.training.model.entities.Role;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.security.RoleService;
import co.com.expertla.training.web.enums.StatusResponse;
<<<<<<< HEAD
import java.util.Date;
import java.util.logging.Level;
=======
import java.util.logging.Level;
import javax.ws.rs.core.Response;
>>>>>>> origin/configuracion_sprint2
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD

/**
* Role Controller <br>
* Info. Creación: <br>
* fecha 30/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
=======
/**
* Role Controller <br>
* Creation Date: <br>
* date 19/08/2016 <br>
* @author Angela Ramirez O
>>>>>>> origin/configuracion_sprint2
**/

@RestController
public class RoleController {

    @Autowired
    RoleService roleService;  

    /**
     * Crea role <br>
<<<<<<< HEAD
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
=======
     * Creation Date: <br>
     * date 19/08/2016 <br>
     * @author Angela Ramirez O
>>>>>>> origin/configuracion_sprint2
     * @param role
     * @return
     */
    @RequestMapping(value = "role/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createRole(@RequestBody Role role) {
            ResponseService responseService = new ResponseService();
<<<<<<< HEAD
        try {  
            Role roleName = new Role();
            roleName.setName(role.getName());
            List<Role> listRoleName = roleService.findByFiltro(roleName);
            
            if(listRoleName != null && !listRoleName.isEmpty()) {
                responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.role", "msgNombreExiste"));
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            
            role.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            role.setCreationDate(new Date());
            roleService.create(role);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.role", "msgRegistroCreado"));
=======
        try {           
            roleService.create(role);
            responseService.setOutput("Role creado correctamente");
>>>>>>> origin/configuracion_sprint2
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RoleController.class.getName()).log(Level.SEVERE, null, ex);
<<<<<<< HEAD
            responseService.setOutput("Error al crear registro");
=======
            responseService.setOutput("Error al crear role");
>>>>>>> origin/configuracion_sprint2
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica role <br>
<<<<<<< HEAD
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
=======
     * Creation Date: <br>
     * date 19/08/2016 <br>
     * @author Angela Ramirez O
>>>>>>> origin/configuracion_sprint2
     * @param role
     * @return
     */
    @RequestMapping(value = "role/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateRole(@RequestBody Role role) {
            ResponseService responseService = new ResponseService();
<<<<<<< HEAD
        try {    
            Role roleName = new Role();
            roleName.setName(role.getName());
            List<Role> listRoleName = roleService.findByFiltro(roleName);
            
            if(listRoleName != null && !listRoleName.isEmpty()) {
                boolean existName = false;
                for (Role role1 : listRoleName) {
                    if (!role1.getRoleId().equals(role.getRoleId())) {
                        existName = true;
                    }
                }

                if (existName) {
                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.role", "msgNombreExiste"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }                
            }
            
            role.setLastUpdate(new Date());
            roleService.store(role);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.role", "msgRegistroEditado"));
=======
        try {           
            roleService.store(role);
            responseService.setOutput("Role editado correctamente");
>>>>>>> origin/configuracion_sprint2
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RoleController.class.getName()).log(Level.SEVERE, null, ex);
<<<<<<< HEAD
            responseService.setOutput("Error al modificar registro");
=======
            responseService.setOutput("Error al modificar role");
>>>>>>> origin/configuracion_sprint2
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina role <br>
<<<<<<< HEAD
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
=======
     * Creation Date: <br>
     * date 19/08/2016 <br>
     * @author Angela Ramirez O
>>>>>>> origin/configuracion_sprint2
     * @param role
     * @return
     */
    @RequestMapping(value = "role/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteRole(@RequestBody Role role) {
            ResponseService responseService = new ResponseService();
        try {           
            roleService.remove(role);
<<<<<<< HEAD
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.role", "msgRegistroEliminado"));
=======
            responseService.setOutput("Role eliminado correctamente");
>>>>>>> origin/configuracion_sprint2
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RoleController.class.getName()).log(Level.SEVERE, null, ex);
<<<<<<< HEAD
            responseService.setOutput("Error al eliminar registro");
=======
            responseService.setOutput("Error al eliminar role");
>>>>>>> origin/configuracion_sprint2
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta role <br>
<<<<<<< HEAD
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/role/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
=======
     * Creation Date: <br>
     * date 19/08/2016 <br>
     * @author Angela Ramirez O
     * @return
     */
    @RequestMapping(value = "role/get/all", method = RequestMethod.GET)
    public Response list() {
>>>>>>> origin/configuracion_sprint2
        ResponseService responseService = new ResponseService();
        try {     
            List<Role> roleList = roleService.findAllActive();
            responseService.setOutput(roleList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
<<<<<<< HEAD
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RoleController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta role paginado <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/role/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {     
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<RoleDTO> roleList = roleService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(), paginateDto.getOrder());
            responseService.setOutput(roleList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RoleController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
=======
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
>>>>>>> origin/configuracion_sprint2
