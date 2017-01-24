package co.expertic.training.web.controller.configuration;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.DcfDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.entities.Dcf;
import co.expertic.training.model.entities.Modality;
import co.expertic.training.model.entities.Objective;
import java.util.List;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.DcfService;
import co.expertic.training.web.enums.StatusResponse;
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
import org.springframework.web.bind.annotation.RestController;


/**
* Dcf Controller <br>
* Info. Creación: <br>
* fecha Sep 6, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class DcfController {

    @Autowired
    DcfService dcfService;  

    /**
     * Crea dcf <br>
     * Info. Creación: <br>
     * fecha Sep 6, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param dcf
     * @return
     */
    @RequestMapping(value = "dcf/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createDcf(@RequestBody Dcf dcf) {
            ResponseService responseService = new ResponseService();
        try {  
//            Dcf dcfName = new Dcf();
//            dcfName.setName(dcf.getName());
//            List<Dcf> listDcfName = dcfService.findByFiltro(dcfName);
//            
//            if(listDcfName != null && !listDcfName.isEmpty()) {
//                responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.dcf", "msgNombreExiste"));
//                responseService.setStatus(StatusResponse.FAIL.getName());
//                return new ResponseEntity<>(responseService, HttpStatus.OK);
//            }
            
            dcf.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            dcf.setCreationDate(new Date());
            dcfService.create(dcf);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.dcf", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DcfController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica dcf <br>
     * Info. Creación: <br>
     * fecha Sep 6, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param dcf
     * @return
     */
    @RequestMapping(value = "dcf/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateDcf(@RequestBody Dcf dcf) {
            ResponseService responseService = new ResponseService();
        try {    
            Dcf dcfName = new Dcf();
//            dcfName.setName(dcf.getName());
//            List<Dcf> listDcfName = dcfService.findByFiltro(dcfName);
//            
//            if(listDcfName != null && !listDcfName.isEmpty()) {
//                boolean existName = false;
//                for (Dcf dcf1 : listDcfName) {
//                    if (!dcf1.getDcfId().equals(dcf.getDcfId())) {
//                        existName = true;
//                    }
//                }
//
//                if (existName) {
//                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.dcf", "msgNombreExiste"));
//                    responseService.setStatus(StatusResponse.FAIL.getName());
//                    return new ResponseEntity<>(responseService, HttpStatus.OK);
//                }                
//            }
            
            dcf.setLastUpdate(new Date());
            dcfService.store(dcf);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.dcf", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DcfController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina dcf <br>
     * Info. Creación: <br>
     * fecha Sep 6, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param dcf
     * @return
     */
    @RequestMapping(value = "dcf/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteDcf(@RequestBody Dcf dcf) {
            ResponseService responseService = new ResponseService();
        try {           
            dcfService.remove(dcf);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.dcf", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DcfController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta dcf <br>
     * Info. Creación: <br>
     * fecha Sep 6, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/dcf/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<Dcf> dcfList = dcfService.findAllActive();
            responseService.setOutput(dcfList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DcfController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta dcf paginado <br>
     * Info. Creación: <br>
     * fecha Sep 6, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/dcf/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<DcfDTO> dcfList = dcfService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(), paginateDto.getOrder());
            responseService.setOutput(dcfList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DcfController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta dcf por modality y objective <br>
     * Info. Creación: <br>
     * fecha Sep 6, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param modalityId
     * @param objectiveId
     * @return
     */
    @RequestMapping(value = "/dcf/by/modality/objective/{modalityId}/{objectiveId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listByModalityAndObjective(@PathVariable("modalityId") Integer modalityId,
            @PathVariable("objectiveId") Integer objectiveId) {
        ResponseService responseService = new ResponseService();
        try {   
            Dcf objectDcf = new Dcf();
            objectDcf.setModalityId(new Modality(modalityId));
            objectDcf.setObjectiveId(new Objective(objectiveId));
            List<Dcf> dcfList = dcfService.findByFiltro(objectDcf);
            responseService.setOutput(dcfList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DcfController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
