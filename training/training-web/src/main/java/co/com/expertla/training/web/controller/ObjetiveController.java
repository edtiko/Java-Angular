package co.com.expertla.training.web.controller;

import co.com.expertla.training.model.dto.ObjetiveDTO;
import co.com.expertla.training.model.entities.ResponseService;
import co.com.expertla.training.service.ObjetiveService;
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
* Controller for Objetive <br>
* Creation Date : <br>
* date 18/07/2016 <br>
* @author Angela Ramírez
**/
@RestController()
public class ObjetiveController {
    
    @Autowired
    private ObjetiveService objetiveService;
    
    @RequestMapping(value = "objetive/get/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAll() {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            List<ObjetiveDTO> objetives = objetiveService.findAll();
            responseService.setOutput(objetives);
            return Response.status(Response.Status.OK).entity(responseService).build();
        }   catch (Exception e) {
            Logger.getLogger(ObjetiveController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

}
