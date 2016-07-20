package co.com.expertla.training.web.controller;

import co.com.expertla.training.model.dto.SportEquipmentDTO;
import co.com.expertla.training.model.entities.ResponseService;
import co.com.expertla.training.service.SportEquipmentService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public Response getBikes() {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            List<SportEquipmentDTO> shoes = sportEquipmentService.findAllBikes();
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
    
}
