package co.com.expertla.training.web.user.controller;

import co.com.expertla.training.model.dto.UserProfileDTO;
import co.com.expertla.training.model.entities.ResponseService;
import co.com.expertla.training.service.UserProfileService;
import co.com.expertla.training.web.enums.StatusResponse;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* Controller for User Profile <br>
* Creation Date : <br>
* date 14/07/2016 <br>
* @author Angela Ramírez
**/
@RestController()
public class UserProfileController {
    
    @Autowired
    private UserProfileService userProfileService;
    
    @RequestMapping(value = "userProfile/get/by/id", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response findById(@RequestBody UserProfileDTO userProfile) {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
//            if (userProfile == null) {
//                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
//                return Response.status(Response.Status.OK).entity(responseService).build();
//            }
            
            UserProfileDTO user = userProfileService.findDTOByUserId(userProfile.getUserId());
            responseService.setOutput(user);
            return Response.status(Response.Status.OK).entity(responseService).build();
        }   catch (Exception e) {
            Logger.getLogger(UserProfileController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    
    @RequestMapping(value = "userProfile/merge", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateUserProfile(@RequestBody UserProfileDTO userProfile) {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
//            if (userProfile == null) {
//                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
//                return Response.status(Response.Status.OK).entity(responseService).build();
//            }
            
            userProfileService.merge(userProfile);
            UserProfileDTO user = userProfileService.findDTOByUserId(userProfile.getUserId());
            responseService.setOutput(user);
            return Response.status(Response.Status.OK).entity(responseService).build();
        }   catch (Exception e) {
            Logger.getLogger(UserProfileController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
}
