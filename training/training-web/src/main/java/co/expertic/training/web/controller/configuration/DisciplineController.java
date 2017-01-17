package co.expertic.training.web.controller.configuration;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.DisciplineDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.entities.Discipline;
import java.util.List;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.DisciplineService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
* Discipline Controller <br>
* Info. Creación: <br>
* fecha 30/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class DisciplineController {

    @Autowired
    DisciplineService disciplineService;  
    
    @RequestMapping(value = "sportDiscipline/get/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAll() {
        StringBuilder strResponse = new StringBuilder();
        co.expertic.training.model.entities.ResponseService responseService = new co.expertic.training.model.entities.ResponseService();
        try {
            List<DisciplineDTO> disciplines = disciplineService.findAll();
            responseService.setOutput(disciplines);
            return Response.status(Response.Status.OK).entity(responseService).build();
        }   catch (Exception e) {
            Logger.getLogger(DisciplineController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    /** 
     * Crea discipline <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param discipline
     * @return
     */
    @RequestMapping(value = "discipline/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createDiscipline(@RequestBody Discipline discipline) {
            ResponseService responseService = new ResponseService();
        try {  
            Discipline disciplineName = new Discipline();
            disciplineName.setName(discipline.getName());
            List<Discipline> listDisciplineName = disciplineService.findByFiltro(disciplineName);
            
            if(listDisciplineName != null && !listDisciplineName.isEmpty()) {
                responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.discipline", "msgNombreExiste"));
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            
            discipline.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            discipline.setCreationDate(new Date());
            disciplineService.create(discipline);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.discipline", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DisciplineController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica discipline <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param discipline
     * @return
     */
    @RequestMapping(value = "discipline/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateDiscipline(@RequestBody Discipline discipline) {
            ResponseService responseService = new ResponseService();
        try {    
            Discipline disciplineName = new Discipline();
            disciplineName.setName(discipline.getName());
            List<Discipline> listDisciplineName = disciplineService.findByFiltro(disciplineName);
            
            if(listDisciplineName != null && !listDisciplineName.isEmpty()) {
                boolean existName = false;
                for (Discipline discipline1 : listDisciplineName) {
                    if (!discipline1.getDisciplineId().equals(discipline.getDisciplineId())) {
                        existName = true;
                    }
                }

                if (existName) {
                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.discipline", "msgNombreExiste"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }                
            }
            
            discipline.setLastUpdate(new Date());
            disciplineService.store(discipline);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.discipline", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DisciplineController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina discipline <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param discipline
     * @return
     */
    @RequestMapping(value = "discipline/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteDiscipline(@RequestBody Discipline discipline) {
            ResponseService responseService = new ResponseService();
        try {           
            disciplineService.remove(discipline);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.discipline", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DisciplineController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta discipline <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/discipline/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<Discipline> disciplineList = disciplineService.findAllActive();
            responseService.setOutput(disciplineList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DisciplineController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta discipline paginado <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/discipline/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<DisciplineDTO> disciplineList = disciplineService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(), paginateDto.getOrder());
            responseService.setOutput(disciplineList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DisciplineController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * 
     * @return 
     */
    @RequestMapping(value = "/discipline/get/movil", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> listMovil() {
        ResponseService responseService = new ResponseService();
        try {     
            List<Discipline> disciplineList = disciplineService.findAllActive();
            responseService.setOutput(disciplineList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DisciplineController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
