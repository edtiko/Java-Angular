package co.com.expertla.training.web.configuration.controller;

import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.ResponseService;
import co.com.expertla.training.model.entities.VisibleFieldsUser;
import co.com.expertla.training.user.service.VisibleFieldsUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import co.com.expertla.training.web.enums.StatusResponse;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
* Controller for Visible fields User<br>
* Creation Date : <br>
* date 10/08/2016 <br>
* @author Angela Ramírez
**/  
@RestController
public class VisibleFieldsUserController {
  
    
    private static final Logger LOGGER = Logger.getLogger(VisibleFieldsUserController.class);  
    
    @Autowired
    private VisibleFieldsUserService visibleFieldsUserService;
    
    @RequestMapping(value = "visibleFieldsUser/get/by/userId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response findById(@RequestBody UserDTO user) {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            List<VisibleFieldsUser> fields = visibleFieldsUserService.findByUserId(user.getUserId());
            responseService.setOutput(fields);
            return Response.status(Response.Status.OK).entity(responseService).build();
        }   catch (Exception e) {
            LOGGER.log(Priority.ERROR, e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    
    @RequestMapping(value = "visibleFieldsUser/create/{userId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response create(@RequestBody List<VisibleFieldsUser> fields,@PathVariable("userId") Integer userId) {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            List<VisibleFieldsUser> listCreated= visibleFieldsUserService.createList(fields, userId);
            responseService.setOutput(listCreated);
            return Response.status(Response.Status.OK).entity(responseService).build();
        }   catch (Exception e) {
            LOGGER.log(Priority.ERROR, e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
      
}