package co.com.expertla.training.web.controller.configuration;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.model.dto.ConfigurationPlanDTO;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.entities.ConfigurationPlan;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.configuration.ConfigurationPlanService;
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
 * ConfigurationPlan Controller <br>
 * Info. Creación: <br>
 * fecha 24/11/2016 <br>
 *
 * @author Andres Felipe Lopez Rodriguez
*
 */
@RestController
@RequestMapping("configurationPlan")
public class ConfigurationPlanController {

    @Autowired
    ConfigurationPlanService configurationPlanService;

    /**
     * Crea configurationPlan <br>
     * Info. Creación: <br>
     * fecha 24/11/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param configurationPlan
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createConfigurationPlan(@RequestBody ConfigurationPlan configurationPlan) {
        ResponseService responseService = new ResponseService();
        try {
            configurationPlan.setCreationDate(new Date());
            configurationPlanService.create(configurationPlan);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.configurationplan", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConfigurationPlanController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica configurationPlan <br>
     * Info. Creación: <br>
     * fecha 24/11/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param configurationPlan
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateConfigurationPlan(@RequestBody ConfigurationPlan configurationPlan) {
        ResponseService responseService = new ResponseService();
        try {
            configurationPlan.setLastUpdate(new Date());
            configurationPlanService.store(configurationPlan);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.configurationplan", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConfigurationPlanController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina configurationPlan <br>
     * Info. Creación: <br>
     * fecha 24/11/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param configurationPlan
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteConfigurationPlan(@RequestBody ConfigurationPlan configurationPlan) {
        ResponseService responseService = new ResponseService();
        try {
            configurationPlanService.remove(configurationPlan);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.configurationplan", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConfigurationPlanController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta configurationPlan <br>
     * Info. Creación: <br>
     * fecha 24/11/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {
            List<ConfigurationPlan> configurationPlanList = configurationPlanService.findAllActive();
            responseService.setOutput(configurationPlanList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConfigurationPlanController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta configurationPlan paginado <br>
     * Info. Creación: <br>
     * fecha 24/11/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param planId
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/paginated/{planId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@PathVariable("planId") Integer planId,
            @RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        List<ConfigurationPlanDTO> configurationPlanList = new ArrayList<>();
        try {
            paginateDto.setPage((paginateDto.getPage() - 1) * paginateDto.getLimit());
            if (planId <= 0) {
                configurationPlanList = configurationPlanService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(),
                        paginateDto.getOrder(), paginateDto.getFilter());
            } else {
                configurationPlanList = configurationPlanService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(),
                        paginateDto.getOrder(), paginateDto.getFilter(), planId);
            }

            responseService.setOutput(configurationPlanList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConfigurationPlanController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get/plan/type", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> planTypeList() {
        ResponseService responseService = new ResponseService();
        try {
            List<ConfigurationPlan> configurationPlanList = configurationPlanService.findAllActive();
            responseService.setOutput(configurationPlanList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConfigurationPlanController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
