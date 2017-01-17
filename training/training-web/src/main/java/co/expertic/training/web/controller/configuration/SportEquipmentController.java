package co.expertic.training.web.controller.configuration;

import co.expertic.training.model.dto.SportEquipmentDTO;
import co.expertic.training.model.entities.ResponseService;
import co.expertic.training.service.configuration.SportEquipmentService;
import co.expertic.training.model.dto.ModelEquipmentDTO;
import co.expertic.training.service.configuration.ModelEquipmentService;
import co.expertic.training.web.enums.StatusResponse;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* Controller for Sport Equipment <br>
* Creation Date : <br>
* date 14/07/2016 <br>
* @author Angela Ramírez
**/
@RestController()
public class SportEquipmentController {
    
    @Autowired
    private SportEquipmentService sportEquipmentService;
    @Autowired
    private ModelEquipmentService modelEquipmentService;
    
    @RequestMapping(value = "sportEquipment/get/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAll() {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            List<SportEquipmentDTO> sportEquipments = sportEquipmentService.findAll();
            responseService.setOutput(sportEquipments);
            return Response.status(Response.Status.OK).entity(responseService).build();
        }   catch (Exception e) {
            Logger.getLogger(SportEquipmentController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "sportEquipment/get/running/shoes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getRunningShoes() {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            List<SportEquipmentDTO> shoes = sportEquipmentService.findAllRunningShoes();
            responseService.setOutput(shoes);
            return Response.status(Response.Status.OK).entity(responseService).build();
        }   catch (Exception e) {
            Logger.getLogger(SportEquipmentController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    
    @RequestMapping(value = "sportEquipment/get/bikes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> getBikes() {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            List<SportEquipmentDTO> bikes = sportEquipmentService.findAllBikes();
            responseService.setOutput(bikes);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }   catch (Exception e) {
            Logger.getLogger(SportEquipmentController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "sportEquipment/get/pulsometers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getPulsometers() {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            List<SportEquipmentDTO> shoes = sportEquipmentService.findAllPulsometers();
            responseService.setOutput(shoes);
            return Response.status(Response.Status.OK).entity(responseService).build();
        }   catch (Exception e) {
            Logger.getLogger(SportEquipmentController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    
    @RequestMapping(value = "sportEquipment/get/potentiometers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getPotentiometers() {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            List<SportEquipmentDTO> shoes = sportEquipmentService.findAllPotentiometers();
            responseService.setOutput(shoes);
            return Response.status(Response.Status.OK).entity(responseService).build();
        }   catch (Exception e) {
            Logger.getLogger(SportEquipmentController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    
       @RequestMapping(value = "sportEquipment/get/potentiometers/model/{sportEquipmentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getPotentiometers(@PathVariable("sportEquipmentId") Integer sportEquipmentId) {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            List<ModelEquipmentDTO> models = modelEquipmentService.findBySportEquipmentId(sportEquipmentId);
            responseService.setOutput(models);
            return Response.status(Response.Status.OK).entity(responseService).build();
        }   catch (Exception e) {
            Logger.getLogger(SportEquipmentController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    
    @RequestMapping(value = "sportEquipment/get/bikes/by/bikeTypeId/{bikeTypeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getBikesByBikeTypeId(@PathVariable("bikeTypeId") Integer bikeTypeId) {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            List<SportEquipmentDTO> bikes = sportEquipmentService.findBikesByBikeTypeId(bikeTypeId);
            responseService.setOutput(bikes);
            return Response.status(Response.Status.OK).entity(responseService).build();
        }   catch (Exception e) {
            Logger.getLogger(SportEquipmentController.class.getName()).log(Priority.FATAL, null, e);
            strResponse.append("Error interno del servidor");
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    
}
