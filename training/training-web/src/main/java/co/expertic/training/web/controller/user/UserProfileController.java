package co.expertic.training.web.controller.user;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.constant.MessageBundle;
import co.expertic.training.model.dto.DashboardDTO;
import co.expertic.training.model.dto.UserProfileDTO;
import co.expertic.training.model.entities.ResponseService;
import co.expertic.training.service.user.UserProfileService;
import co.expertic.training.web.enums.StatusResponse;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for User Profile <br>
 * Creation Date : <br>
 * date 14/07/2016 <br>
 *
 * @author Angela Ramírez
 *
 */
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            Logger.getLogger(UserProfileController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "userProfile/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response createUserProfile(@RequestBody UserProfileDTO userProfile) {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
//            if (userProfile == null) {
//                strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
//                return Response.status(Response.Status.OK).entity(responseService).build();
//            }

            userProfileService.create(userProfile);
            UserProfileDTO user = userProfileService.findDTOByUserId(userProfile.getUserId());
            responseService.setOutput(user);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(UserProfileController.class.getName()).log(Priority.FATAL, null, e);
//            strResponse.append(MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "dashboard/get/by/id", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response findDashboardById(@RequestBody UserProfileDTO userProfile) {
        StringBuilder strResponse = new StringBuilder();
        ResponseService responseService = new ResponseService();
        try {
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.SUCCESS.getName());

            DashboardDTO dashboard = userProfileService.findDashboardDTOByUserId(userProfile.getUserId());
            responseService.setOutput(dashboard);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            Logger.getLogger(UserProfileController.class.getName()).log(Priority.FATAL, null, e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    
    @RequestMapping(value = "userProfile/get/by/id/movil", method = RequestMethod.POST)
    public ResponseEntity<ResponseService> getUserProfileMovil(@RequestBody UserProfileDTO userProfile) {
        ResponseService responseService = new ResponseService();
        try
        {
            if (userProfile == null) {
                String strResponse = (MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }

            UserProfileDTO user = userProfileService.findDTOByUserId(userProfile.getUserId());
            responseService.setOutput(user);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }catch (Exception e) {
            Logger.getLogger(UserProfileController.class.getName()).log(Priority.FATAL, null, e);
            String strResponse = (MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "userProfile/create/movil", method = RequestMethod.POST)
    public ResponseEntity<ResponseService> createUserProfileMovil(@RequestBody UserProfileDTO userProfile) {
        ResponseService responseService = new ResponseService();
        try
        {
            if (userProfile == null) {
                String strResponse = (MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }

            userProfileService.create(userProfile);
            responseService.setOutput("Datos deportivos creados exitosamente");
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }catch (Exception e) {
            Logger.getLogger(UserProfileController.class.getName()).log(Priority.FATAL, null, e);
            String strResponse = (MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "userProfile/merge/movil", method = RequestMethod.POST)
    public ResponseEntity<ResponseService> mergeUserProfileMovil(@RequestBody UserProfileDTO userProfile) {
        ResponseService responseService = new ResponseService();
        try
        {
            if (userProfile == null) {
                String strResponse = (MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "nullParameters"));
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }

            userProfileService.merge(userProfile);
            responseService.setOutput("Datos deportivos creados exitosamente");
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }catch (Exception e) {
            Logger.getLogger(UserProfileController.class.getName()).log(Priority.FATAL, null, e);
            String strResponse = (MessageUtil.getMessageFromBundle(MessageBundle.GENERAL_PROPERTIES, "internalError"));
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
