package co.expertic.training.web.controller.configuration;
 

import co.expertic.base.util.MessageUtil;
import co.expertic.training.model.dto.DisciplineDTO;
import co.expertic.training.model.entities.ResponseService;
import co.expertic.training.service.configuration.DisciplineService;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.ModalityDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.entities.Discipline;
import co.expertic.training.model.entities.Modality;
import java.util.List;
import co.expertic.training.service.configuration.ModalityService;
import co.expertic.training.web.enums.StatusResponse;
import java.util.Date;
import java.util.logging.Level;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
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
* Modality Controller <br>
* Info. Creación: <br>
* fecha Sep 5, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
 
@RestController
public class ModalityController {
 
    @Autowired
    private ModalityService modalityService;
    @Autowired
    private DisciplineService disciplineService;

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
                responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.modality", "msgNombreExiste"));
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            
            modality.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            modality.setCreationDate(new Date());
            modalityService.create(modality);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.modality", "msgRegistroCreado"));
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
                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.modality", "msgNombreExiste"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }                
            }
            
            modality.setLastUpdate(new Date());
            modalityService.store(modality);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.modality", "msgRegistroEditado"));
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
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.modality", "msgRegistroEliminado"));
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
    
    @RequestMapping(value = "modality/get/by/userId/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getByUser(@PathVariable("userId") Integer userId) {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        List<ModalityDTO> modalities = null;
        try {
            DisciplineDTO discipline = disciplineService.findByUserId(userId);
            if (discipline != null) {
                modalities = modalityService.findByDisciplineId(discipline.getDisciplineId());
            }
            responseService.setOutput(modalities);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModalityController.class.getName()).log(Level.SEVERE, null, ex);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
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
            List<ModalityDTO> modalityList = modalityService.findPaginate(paginateDto.getPage(), 
                    paginateDto.getLimit(), paginateDto.getOrder(), paginateDto.getFilter());
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
    
    @RequestMapping(value = "modality/get/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> getAll() {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            List<ModalityDTO> modalities = modalityService.findAll();
            responseService.setOutput(modalities);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }   catch (Exception e) {
            Logger.getLogger(ModalityController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "modality/get/by/disciplineId/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> getAll(@PathVariable("id") Integer id) {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            List<ModalityDTO> modalities = modalityService.findByDisciplineId(id);
            responseService.setOutput(modalities);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }   catch (Exception e) {
            Logger.getLogger(ModalityController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "modality/get/by/objectiveId/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> getByObjective(@PathVariable("id") Integer id) {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            List<ModalityDTO> modalities = modalityService.findByObjectiveId(id);
            responseService.setOutput(modalities);
            responseService.setStatus(StatusResponse.SUCCESS.getName());

            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }   catch (Exception e) {
            Logger.getLogger(ModalityController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
