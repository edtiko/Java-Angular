package co.com.expertla.training.web.controller.configuration;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.dto.PhysiologicalCapacityDTO;
import co.com.expertla.training.model.entities.PhysiologicalCapacity;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.configuration.PhysiologicalCapacityService;
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
* PhysiologicalCapacity Controller <br>
* Info. Creación: <br>
* fecha Sep 2, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class PhysiologicalCapacityController {

    @Autowired
    PhysiologicalCapacityService physiologicalCapacityService;  

    /**
     * Crea physiologicalCapacity <br>
     * Info. Creación: <br>
     * fecha Sep 2, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param physiologicalCapacity
     * @return
     */
    @RequestMapping(value = "physiologicalCapacity/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createPhysiologicalCapacity(@RequestBody PhysiologicalCapacity physiologicalCapacity) {
            ResponseService responseService = new ResponseService();
        try {  
            PhysiologicalCapacity physiologicalCapacityName = new PhysiologicalCapacity();
            physiologicalCapacityName.setName(physiologicalCapacity.getName());
            List<PhysiologicalCapacity> listPhysiologicalCapacityName = physiologicalCapacityService.findByFiltro(physiologicalCapacityName);
            
            if(listPhysiologicalCapacityName != null && !listPhysiologicalCapacityName.isEmpty()) {
                responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.physiologicalcapacity", "msgNombreExiste"));
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            
            physiologicalCapacity.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            physiologicalCapacity.setCreationDate(new Date());
            physiologicalCapacityService.create(physiologicalCapacity);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.physiologicalcapacity", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PhysiologicalCapacityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica physiologicalCapacity <br>
     * Info. Creación: <br>
     * fecha Sep 2, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param physiologicalCapacity
     * @return
     */
    @RequestMapping(value = "physiologicalCapacity/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updatePhysiologicalCapacity(@RequestBody PhysiologicalCapacity physiologicalCapacity) {
            ResponseService responseService = new ResponseService();
        try {    
            PhysiologicalCapacity physiologicalCapacityName = new PhysiologicalCapacity();
            physiologicalCapacityName.setName(physiologicalCapacity.getName());
            List<PhysiologicalCapacity> listPhysiologicalCapacityName = physiologicalCapacityService.findByFiltro(physiologicalCapacityName);
            
            if(listPhysiologicalCapacityName != null && !listPhysiologicalCapacityName.isEmpty()) {
                boolean existName = false;
                for (PhysiologicalCapacity physiologicalCapacity1 : listPhysiologicalCapacityName) {
                    if (!physiologicalCapacity1.getPhysiologicalCapacityId().equals(physiologicalCapacity.getPhysiologicalCapacityId())) {
                        existName = true;
                    }
                }

                if (existName) {
                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.physiologicalcapacity", "msgNombreExiste"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }                
            }
            
            physiologicalCapacity.setLastUpdate(new Date());
            physiologicalCapacityService.store(physiologicalCapacity);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.physiologicalcapacity", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PhysiologicalCapacityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina physiologicalCapacity <br>
     * Info. Creación: <br>
     * fecha Sep 2, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param physiologicalCapacity
     * @return
     */
    @RequestMapping(value = "physiologicalCapacity/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deletePhysiologicalCapacity(@RequestBody PhysiologicalCapacity physiologicalCapacity) {
            ResponseService responseService = new ResponseService();
        try {           
            physiologicalCapacityService.remove(physiologicalCapacity);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.physiologicalcapacity", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PhysiologicalCapacityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta physiologicalCapacity <br>
     * Info. Creación: <br>
     * fecha Sep 2, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/physiologicalCapacity/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<PhysiologicalCapacity> physiologicalCapacityList = physiologicalCapacityService.findAllActive();
            responseService.setOutput(physiologicalCapacityList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PhysiologicalCapacityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta physiologicalCapacity paginado <br>
     * Info. Creación: <br>
     * fecha Sep 2, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/physiologicalCapacity/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<PhysiologicalCapacityDTO> physiologicalCapacityList = physiologicalCapacityService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(), paginateDto.getOrder());
            responseService.setOutput(physiologicalCapacityList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PhysiologicalCapacityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/physiologicalCapacity/get/available", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> listAvailable() {
        ResponseService responseService = new ResponseService();
        try {
            List<PhysiologicalCapacity> physiologicalCapacityList = physiologicalCapacityService.findAllAvailable();
            responseService.setOutput(physiologicalCapacityList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PhysiologicalCapacityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
