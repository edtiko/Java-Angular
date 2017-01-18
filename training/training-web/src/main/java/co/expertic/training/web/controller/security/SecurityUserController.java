package co.expertic.training.web.controller.security;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.User;
import java.util.List;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.user.UserService;
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
* User Controller <br>
* Info. Creación: <br>
* fecha 25/11/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class SecurityUserController {

    @Autowired
    UserService userService;   

    /**
     * Elimina user <br>
     * Info. Creación: <br>
     * fecha 25/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param user
     * @return
     */
    @RequestMapping(value = "user/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteUser(@RequestBody User user) {
            ResponseService responseService = new ResponseService();
        try {           
            userService.deleteUserById(user.getUserId());
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.user", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SecurityUserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }     

    /**
     * Consulta user paginado <br>
     * Info. Creación: <br>
     * fecha 25/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/user/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<UserDTO> userList = userService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(),
                paginateDto.getOrder(), paginateDto.getFilter());
            responseService.setOutput(userList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SecurityUserController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
