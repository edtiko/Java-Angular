package co.com.expertla.training.web.controller.security;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.enums.StateEnum;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.ModuleDTO;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.entities.Module;
import co.com.expertla.training.model.entities.Option;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.security.ModuleService;
import co.com.expertla.training.service.security.OptionService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.util.ArrayList;
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
 * Module Controller <br>
 * Info. Creación: <br>
 * fecha 26/08/2016 <br>
 *
 * @author Andres Felipe Lopez Rodriguez
*
 */
@RestController
public class ModuleController {

    @Autowired
    ModuleService moduleService;
    
    @Autowired
    OptionService optionService;


    /**
     * Crea module <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param module
     * @return
     */
    @RequestMapping(value = "module/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createModule(@RequestBody Module module) {
        ResponseService responseService = new ResponseService();
        try {
            Module moduleName = new Module();
            moduleName.setName(module.getName());
            List<Module> listModuleName = moduleService.findByFiltro(moduleName);

            if (listModuleName != null && !listModuleName.isEmpty()) {
                responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.module", "msgNombreExiste"));
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            
            module.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            module.setCreationDate(new Date());
            moduleService.create(module);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.module", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica module <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param module
     * @return
     */
    @RequestMapping(value = "module/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateModule(@RequestBody Module module) {
        ResponseService responseService = new ResponseService();
        try {

            Module moduleName = new Module();
            moduleName.setName(module.getName());
            List<Module> listModuleName = moduleService.findByFiltro(moduleName);

            if (listModuleName != null && !listModuleName.isEmpty()) {
                boolean existName = false;
                for (Module module1 : listModuleName) {
                    if (!module1.getModuleId().equals(module.getModuleId())) {
                        existName = true;
                    }
                }

                if (existName) {
                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.module", "msgNombreExiste"));
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                }
            }

            module.setLastUpdate(new Date());
            moduleService.store(module);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.module", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina module <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param module
     * @return
     */
    @RequestMapping(value = "module/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteModule(@RequestBody Module module) {
        ResponseService responseService = new ResponseService();
        try {
            moduleService.remove(module);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.module", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta module <br>
     * Info. Creación: <br>
     * fecha 26/08/2016 <br>
     *
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
     * Consulta modulos por usuario <br>
     * Info. Creacion: <br>
     * fecha 14/09/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return
     */
    @RequestMapping(value = "/module/get/by/userId/{userId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> listModuleByUser(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        try {
            List<Module> moduleList = moduleService.findByUserId(userId);
            List<ModuleDTO> moduleDTOList = new ArrayList();
            for (Module module : moduleList) {
                ModuleDTO moduleDTO = new ModuleDTO();
                moduleDTO.setModuleId(module.getModuleId());
                moduleDTO.setName(module.getName());
                Option option = new Option();
                option.setModuleId(module);
                option.setStateId(Short.valueOf(StateEnum.ACTIVE.getId().toString()));
                moduleDTO.setOptionCollection(optionService.findByFiltro(option));
                moduleDTOList.add(moduleDTO);
            }
            responseService.setOutput(moduleDTOList);
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
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/module/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<ModuleDTO> moduleList = moduleService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(), paginateDto.getOrder());
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
}
