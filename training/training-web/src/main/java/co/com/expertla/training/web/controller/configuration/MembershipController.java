package co.com.expertla.training.web.controller.configuration;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.MembershipDTO;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.entities.Membership;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.configuration.MembershipService;
import co.com.expertla.training.web.enums.StatusResponse;
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
* Membership Controller <br>
* Info. Creación: <br>
* fecha 21/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class MembershipController {

    @Autowired
    MembershipService membershipService;  

    /**
     * Crea membership <br>
     * Info. Creación: <br>
     * fecha 21/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param membership
     * @return
     */
    @RequestMapping(value = "membership/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createMembership(@RequestBody Membership membership) {
            ResponseService responseService = new ResponseService();
        try {  
            Membership membershipName = new Membership();
            membershipName.setName(membership.getName());
            List<Membership> listMembershipName = membershipService.findByName(membershipName);
            
            if(listMembershipName != null && !listMembershipName.isEmpty()) {
                responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.membership", "msgNombreExiste"));
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            
            membership.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            membership.setCreationDate(new Date());
            membershipService.create(membership);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.membership", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MembershipController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica membership <br>
     * Info. Creación: <br>
     * fecha 21/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param membership
     * @return
     */
    @RequestMapping(value = "membership/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateMembership(@RequestBody Membership membership) {
            ResponseService responseService = new ResponseService();
        try {    
            Membership membershipName = new Membership();
            membershipName.setName(membership.getName());
            List<Membership> listMembershipName = membershipService.findByName(membershipName);
            
            if(listMembershipName != null && !listMembershipName.isEmpty()) {
                boolean existName = false;
                for (Membership membership1 : listMembershipName) {
                    if (!membership1.getMembershipId().equals(membership.getMembershipId())) {
                        existName = true;
                    }
                }

                if (existName) {
                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.membership", "msgNombreExiste"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }                
            }
            
            membershipService.store(membership);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.membership", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MembershipController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina membership <br>
     * Info. Creación: <br>
     * fecha 21/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param membership
     * @return
     */
    @RequestMapping(value = "membership/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteMembership(@RequestBody Membership membership) {
            ResponseService responseService = new ResponseService();
        try {           
            membershipService.remove(membership);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.membership", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MembershipController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta membership <br>
     * Info. Creación: <br>
     * fecha 21/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/membership/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<MembershipDTO> membershipList = membershipService.findAllActive();
            responseService.setOutput(membershipList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MembershipController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta membership paginado <br>
     * Info. Creación: <br>
     * fecha 21/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/membership/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<MembershipDTO> membershipList = membershipService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(),
                paginateDto.getOrder(), paginateDto.getFilter());
            responseService.setOutput(membershipList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MembershipController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
