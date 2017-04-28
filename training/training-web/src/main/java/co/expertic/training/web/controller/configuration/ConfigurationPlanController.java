package co.expertic.training.web.controller.configuration;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.enums.RoleEnum;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.ConfigurationPlanDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.entities.Characteristic;
import co.expertic.training.model.entities.ConfigurationPlan;
import co.expertic.training.model.entities.TrainingPlanCharact;
import java.util.List;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.ConfigurationPlanService;
import co.expertic.training.service.configuration.TrainingPlanCharactService;
import co.expertic.training.web.enums.StatusResponse;
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

    @Autowired
    TrainingPlanCharactService trainingPlanCharactService;

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

            if (configurationPlan.getCommunicationRoleId().getRoleId().equals(RoleEnum.COACH_INTERNO.getId()) || 
                    configurationPlan.getCommunicationRoleId().getRoleId().equals(RoleEnum.ESTRELLA.getId())
                    
                    ||configurationPlan.getCommunicationRoleId().getRoleId().equals(RoleEnum.ATLETA.getId()) || 
                    configurationPlan.getCommunicationRoleId().getRoleId().equals(RoleEnum.COACH.getId())
                    ) {
                TrainingPlanCharact trainingPlanCharact = new TrainingPlanCharact();
                trainingPlanCharact.setCharacteristicId(new Characteristic(13));
                trainingPlanCharact.setTrainingPlanId(configurationPlan.getTrainingPlanId());
                trainingPlanCharact.setStateId(Short.valueOf(Status.ACTIVE.getId()));
                trainingPlanCharact.setCreationDate(new Date());
                trainingPlanCharact.setValue(configurationPlan.getEmailCount() + "");
                trainingPlanCharactService.create(trainingPlanCharact);

                trainingPlanCharact = new TrainingPlanCharact();
                trainingPlanCharact.setCharacteristicId(new Characteristic(14));
                trainingPlanCharact.setTrainingPlanId(configurationPlan.getTrainingPlanId());
                trainingPlanCharact.setStateId(Short.valueOf(Status.ACTIVE.getId()));
                trainingPlanCharact.setCreationDate(new Date());
                trainingPlanCharact.setValue(configurationPlan.getMessageCount() + "");
                trainingPlanCharactService.create(trainingPlanCharact);

                trainingPlanCharact = new TrainingPlanCharact();
                trainingPlanCharact.setCharacteristicId(new Characteristic(15));
                trainingPlanCharact.setTrainingPlanId(configurationPlan.getTrainingPlanId());
                trainingPlanCharact.setStateId(Short.valueOf(Status.ACTIVE.getId()));
                trainingPlanCharact.setCreationDate(new Date());
                trainingPlanCharact.setValue(configurationPlan.getVideoCount() + "");
                trainingPlanCharactService.create(trainingPlanCharact);

                trainingPlanCharact = new TrainingPlanCharact();
                trainingPlanCharact.setCharacteristicId(new Characteristic(16));
                trainingPlanCharact.setTrainingPlanId(configurationPlan.getTrainingPlanId());
                trainingPlanCharact.setStateId(Short.valueOf(Status.ACTIVE.getId()));
                trainingPlanCharact.setValue(configurationPlan.getAudioCount() + "");
                trainingPlanCharact.setCreationDate(new Date());
                trainingPlanCharactService.create(trainingPlanCharact);
            }

            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.configurationplan", "msgRegistroCreado"));
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
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.configurationplan", "msgRegistroEditado"));
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
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.configurationplan", "msgRegistroEliminado"));
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
