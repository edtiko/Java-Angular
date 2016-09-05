package co.com.expertla.training.web.controller.configuration;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.ModalityDTO;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.entities.Discipline;
import co.com.expertla.training.model.entities.Modality;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.configuration.ModalityService;
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
* Modality Controller <br>
* Info. Creación: <br>
* fecha Sep 5, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class ModalityController {

    @Autowired
    ModalityService modalityService;  

    /**
     * Crea modality <br>
     * Info. Creación: <br>
     * fecha Sep 5, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param modality
     * @return
     */
    @RequestMapping(value = "modality/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createModality(@RequestBody Modality modality) {
            ResponseService responseService = new ResponseService();
        try {  
            Modality modalityName = new Modality();
            modalityName.setName(modality.getName());
            modalityName.setDisciplineId(new Discipline(modality.getDisciplineId().getDisciplineId()));
            List<Modality> listModalityName = modalityService.findByFiltro(modalityName);
            
            if(listModalityName != null && !listModalityName.isEmpty()) {
                responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.modality", "msgNombreExiste"));
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            
            modality.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            modality.setCreationDate(new Date());
            modalityService.create(modality);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.modality", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModalityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica modality <br>
     * Info. Creación: <br>
     * fecha Sep 5, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param modality
     * @return
     */
    @RequestMapping(value = "modality/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateModality(@RequestBody Modality modality) {
            ResponseService responseService = new ResponseService();
        try {    
            Modality modalityName = new Modality();
            modalityName.setName(modality.getName());
            modalityName.setDisciplineId(new Discipline(modality.getDisciplineId().getDisciplineId()));
            List<Modality> listModalityName = modalityService.findByFiltro(modalityName);
            
            if(listModalityName != null && !listModalityName.isEmpty()) {
                boolean existName = false;
                for (Modality modality1 : listModalityName) {
                    if (!modality1.getModalityId().equals(modality.getModalityId())) {
                        existName = true;
                    }
                }

                if (existName) {
                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.modality", "msgNombreExiste"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }                
            }
            
            modality.setLastUpdate(new Date());
            modalityService.store(modality);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.modality", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModalityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina modality <br>
     * Info. Creación: <br>
     * fecha Sep 5, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param modality
     * @return
     */
    @RequestMapping(value = "modality/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteModality(@RequestBody Modality modality) {
            ResponseService responseService = new ResponseService();
        try {           
            modalityService.remove(modality);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.modality", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModalityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta modality <br>
     * Info. Creación: <br>
     * fecha Sep 5, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/modality/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<Modality> modalityList = modalityService.findAllActive();
            responseService.setOutput(modalityList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModalityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta modality paginado <br>
     * Info. Creación: <br>
     * fecha Sep 5, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/modality/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<ModalityDTO> modalityList = modalityService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(), paginateDto.getOrder());
            responseService.setOutput(modalityList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModalityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
