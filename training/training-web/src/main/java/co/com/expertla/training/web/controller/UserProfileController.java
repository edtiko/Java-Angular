package co.com.expertla.training.web.controller;

import co.com.expertla.training.model.dto.UserProfileDTO;
import co.com.expertla.training.model.entities.ResponseService;
import co.com.expertla.training.model.entities.UserProfile;
import co.com.expertla.training.service.UserProfileService;
import co.com.expertla.training.web.enums.StatusResponse;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for User Profile
 * @author Angela Ramírez
 */
//@RestController(value = "userProfile")
@RestController()
public class UserProfileController {
    
    @Autowired
    private UserProfileService userProfileService;
    
    @RequestMapping(value = "userProfile/get/by/id/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response findById(@RequestBody UserProfileDTO userProfile) {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            System.out.println("asdfasdf");
//            if (userProfile == null) {
//                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
//                return Response.status(Response.Status.OK).entity(responseService).build();
//            }
            
            UserProfile user = userProfileService.findById(userProfile.getUserProfileId());
            responseService.setOutput(user);
            return Response.status(Response.Status.OK).entity(responseService).build();
        }   catch (Exception e) {
//            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
}
