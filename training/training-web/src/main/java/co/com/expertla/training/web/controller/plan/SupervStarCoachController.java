package co.com.expertla.training.web.controller.plan;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.dto.SupervStarCoachDTO;
import co.com.expertla.training.model.entities.SupervStarCoach;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.plan.SupervStarCoachService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
* SupervStarCoach Controller <br>
* Info. Creación: <br>
* fecha Sep 13, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class SupervStarCoachController {

    @Autowired
    SupervStarCoachService supervStarCoachService;  

    /**
     * Crea supervStarCoach <br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param supervStarCoach
     * @return
     */
    @RequestMapping(value = "supervStarCoach/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createSupervStarCoach(@RequestBody SupervStarCoach supervStarCoach) {
            ResponseService responseService = new ResponseService();
        try {  
            supervStarCoach.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            supervStarCoach.setCreationDate(new Date());
            supervStarCoachService.create(supervStarCoach);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.supervstarcoach", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SupervStarCoachController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica supervStarCoach <br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param supervStarCoach
     * @return
     */
    @RequestMapping(value = "supervStarCoach/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateSupervStarCoach(@RequestBody SupervStarCoach supervStarCoach) {
            ResponseService responseService = new ResponseService();
        try {    
//            supervStarCoach.setLastUpdate(new Date());
            supervStarCoachService.store(supervStarCoach);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.supervstarcoach", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SupervStarCoachController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina supervStarCoach <br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param supervStarCoach
     * @return
     */
    @RequestMapping(value = "supervStarCoach/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteSupervStarCoach(@RequestBody SupervStarCoach supervStarCoach) {
            ResponseService responseService = new ResponseService();
        try {           
            supervStarCoachService.remove(supervStarCoach);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.supervstarcoach", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SupervStarCoachController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta supervStarCoach <br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/supervStarCoach/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<SupervStarCoach> supervStarCoachList = supervStarCoachService.findAllActive();
            responseService.setOutput(supervStarCoachList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SupervStarCoachController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta supervStarCoach paginado <br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/supervStarCoach/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<SupervStarCoachDTO> supervStarCoachList = supervStarCoachService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(),
                paginateDto.getOrder(), paginateDto.getFilter());
            responseService.setOutput(supervStarCoachList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SupervStarCoachController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta supervStarCoach por coach Id<br>
     * Info. Creación: <br>
     * fecha Sep 13, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param coachId
     * @return
     */
    @RequestMapping(value = "/supervStarCoach/get/by/coachId/{coachId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> findByCoachId(@PathVariable("coachId") Integer coachId) {
        ResponseService responseService = new ResponseService();
        try {     
            List<SupervStarCoach> supervStarCoachList = supervStarCoachService.findByCoachId(coachId);
            responseService.setOutput(supervStarCoachList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SupervStarCoachController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
