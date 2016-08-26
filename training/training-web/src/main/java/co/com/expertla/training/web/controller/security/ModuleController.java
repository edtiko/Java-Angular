package co.com.expertla.training.web.controller.security;

import co.com.expertla.training.model.dto.ModuleDTO;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.entities.Module;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.security.ModuleService;
import co.com.expertla.training.web.enums.StatusResponse;
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
* Module Controller <br>
* Info. Creación: <br>
* fecha 26/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class ModuleController {

    @Autowired
    ModuleService moduleService;  

    /**
     * Crea module <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param module
     * @return
     */
    @RequestMapping(value = "module/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createModule(@RequestBody Module module) {
            ResponseService responseService = new ResponseService();
        try {           
            moduleService.create(module);
            responseService.setOutput("Module creado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear module");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica module <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param module
     * @return
     */
    @RequestMapping(value = "module/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateModule(@RequestBody Module module) {
            ResponseService responseService = new ResponseService();
        try {           
            moduleService.store(module);
            responseService.setOutput("Module editado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar module");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina module <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param module
     * @return
     */
    @RequestMapping(value = "module/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteModule(@RequestBody Module module) {
            ResponseService responseService = new ResponseService();
        try {           
            moduleService.remove(module);
            responseService.setOutput("Module eliminado correctamente");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar module");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta module <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/module/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<Module> moduleList = moduleService.findAllActive();
            responseService.setOutput(moduleList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar module");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta module paginado <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/option/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {     
            List<ModuleDTO> moduleList = moduleService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(), paginateDto.getOrder());
            responseService.setOutput(moduleList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(OptionController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar module");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
