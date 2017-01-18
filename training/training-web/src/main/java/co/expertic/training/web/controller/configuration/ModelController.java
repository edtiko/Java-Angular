package co.expertic.training.web.controller.configuration;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.ModelDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.entities.Model;
import java.util.List;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.ModelService;
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
* Model Controller <br>
* Info. Creación: <br>
* fecha Oct 19, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class ModelController {

    @Autowired
    ModelService modelService;  

    /**
     * Crea model <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param model
     * @return
     */
    @RequestMapping(value = "model/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createModel(@RequestBody Model model) {
            ResponseService responseService = new ResponseService();
        try {  
            Model modelName = new Model();
            modelName.setName(model.getName());
            List<Model> listModelName = modelService.findByName(modelName);
            
            if(listModelName != null && !listModelName.isEmpty()) {
                responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.model", "msgNombreExiste"));
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            
            model.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            model.setCreationDate(new Date());
            modelService.create(model);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.model", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica model <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param model
     * @return
     */
    @RequestMapping(value = "model/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateModel(@RequestBody Model model) {
            ResponseService responseService = new ResponseService();
        try {    
            Model modelName = new Model();
            modelName.setName(model.getName());
            List<Model> listModelName = modelService.findByName(modelName);
            
            if(listModelName != null && !listModelName.isEmpty()) {
                boolean existName = false;
                for (Model model1 : listModelName) {
                    if (!model1.getModelId().equals(model.getModelId())) {
                        existName = true;
                    }
                }

                if (existName) {
                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.model", "msgNombreExiste"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }                
            }
            
            model.setLastUpdate(new Date());
            modelService.store(model);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.model", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina model <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param model
     * @return
     */
    @RequestMapping(value = "model/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteModel(@RequestBody Model model) {
            ResponseService responseService = new ResponseService();
        try {           
            modelService.remove(model);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.model", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta model <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/model/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<Model> modelList = modelService.findAllActive();
            responseService.setOutput(modelList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta model paginado <br>
     * Info. Creación: <br>
     * fecha Oct 19, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/model/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<ModelDTO> modelList = modelService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(),
                paginateDto.getOrder(), paginateDto.getFilter());
            responseService.setOutput(modelList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModelController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    
}
