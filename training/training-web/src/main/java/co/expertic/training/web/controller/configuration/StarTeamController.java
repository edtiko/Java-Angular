package co.expertic.training.web.controller.configuration;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.enums.RoleEnum;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.StarTeamDTO;
import co.expertic.training.model.entities.StarTeam;
import co.expertic.training.model.entities.User;
import java.util.List;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.StartTeamService;
import co.expertic.training.service.user.UserService;
import co.expertic.training.web.enums.StatusResponse;
import java.util.Date;
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
* StartTeam Controller <br>
* Info. Creación: <br>
* fecha 1/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class StarTeamController {

    @Autowired
    StartTeamService startTeamService;
    
    @Autowired
    UserService userService;
    
    

    /**
     * Crea startTeam <br>
     * Info. Creación: <br>
     * fecha 1/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param startTeam
     * @return
     */
    @RequestMapping(value = "starTeam/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createStartTeam(@RequestBody StarTeam startTeam) {
            ResponseService responseService = new ResponseService();
        try {  
            StarTeam startTeamName = new StarTeam();
            startTeamName.setStarUserId(startTeam.getStarUserId());
            startTeamName.setCoachUserId(startTeam.getCoachUserId());
            List<StarTeam> listStartTeamName = startTeamService.findByFiltro(startTeamName);
            
            if(listStartTeamName != null && !listStartTeamName.isEmpty()) {
                responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.startteam", "msgRegistroExiste"));
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            
            startTeam.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            startTeam.setCreationDate(new Date());
            startTeamService.create(startTeam);
            responseService.setOutput(startTeam);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(StarTeamController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica startTeam <br>
     * Info. Creación: <br>
     * fecha 1/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param startTeam
     * @return
     */
    @RequestMapping(value = "starTeam/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateStartTeam(@RequestBody StarTeam startTeam) {
            ResponseService responseService = new ResponseService();
        try {    
            StarTeam startTeamName = new StarTeam();
            startTeamName.setStarUserId(startTeam.getStarUserId());
            startTeamName.setCoachUserId(startTeam.getCoachUserId());
            List<StarTeam> listStartTeamName = startTeamService.findByFiltro(startTeamName);
            
            if(listStartTeamName != null && !listStartTeamName.isEmpty()) {
                boolean existName = false;
                for (StarTeam startTeam1 : listStartTeamName) {
                    if (!startTeam1.getStarTeamId().equals(startTeam.getStarTeamId())) {
                        existName = true;
                    }
                }

                if (existName) {
                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.startteam", "msgRegistroExiste"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }                
            }
            
            startTeam.setLastUpdate(new Date());
            startTeamService.store(startTeam);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.startteam", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(StarTeamController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina startTeam <br>
     * Info. Creación: <br>
     * fecha 1/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param startTeam
     * @return
     */
    @RequestMapping(value = "starTeam/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteStartTeam(@RequestBody StarTeam startTeam) {
            ResponseService responseService = new ResponseService();
        try {           
            startTeamService.remove(startTeam);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.startteam", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(StarTeamController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta startTeam <br>
     * Info. Creación: <br>
     * fecha 1/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/starTeam/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<StarTeam> startTeamList = startTeamService.findAllActive();
            responseService.setOutput(startTeamList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(StarTeamController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta startTeam paginado <br>
     * Info. Creación: <br>
     * fecha 1/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/starTeam/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<StarTeamDTO> startTeamList = startTeamService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(), paginateDto.getOrder());
            responseService.setOutput(startTeamList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(StarTeamController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta startTeam <br>
     * Info. Creación: <br>
     * fecha 1/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/starUser/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> starUserAll() {
        ResponseService responseService = new ResponseService();
        try {     
            List<User> startTeamList = userService.findUserByRole(RoleEnum.ESTRELLA.getId());
            responseService.setOutput(startTeamList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(StarTeamController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta startTeam <br>
     * Info. Creación: <br>
     * fecha 1/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/coachUser/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> coachUserAll() {
        ResponseService responseService = new ResponseService();
        try {     
            List<User> startTeamList = userService.findUserByRole(RoleEnum.COACH_INTERNO.getId());
            responseService.setOutput(startTeamList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(StarTeamController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
