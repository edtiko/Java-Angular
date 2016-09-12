/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.controller.plan;

import co.com.expertla.training.model.entities.PlanVideo;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.plan.PlanVideoService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Edwin G
 */
@RestController
@RequestMapping("/video")
public class PlanVideoController {
    
  private static final Logger LOGGER = Logger.getLogger(PlanVideoController.class);
  private static final String ROOT = "c:/upload-video/";

    @Autowired
    private PlanVideoService planVideoService;
    
    
    
     @RequestMapping(value = "/upload/{toUserId}/{fromUserId}", method = RequestMethod.POST)
    public @ResponseBody
    Response uploadVideo(@RequestParam("fileToUpload") MultipartFile file, @RequestParam String filename, @PathVariable Integer toUserId, @PathVariable Integer fromUserId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        if (!file.isEmpty()) {
            try {
                //byte[] bytes = file.getBytes();
                String fileName = Calendar.getInstance().getTime().toString()+"_"+toUserId+"_"+fromUserId;
                Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));
                PlanVideo video = new PlanVideo();
                video.setFromUserId(new User(fromUserId));
                video.setToUserId(new User(toUserId));
                video.setCreationDate(Calendar.getInstance().getTime());
                video.setVideoPath(fileName);
                planVideoService.create(video);
                strResponse.append("video cargado correctamente.");
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                responseService.setOutput(strResponse);
                return Response.status(Response.Status.OK).entity(responseService).build();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                responseService.setDetail(e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
            }
        } else {
            strResponse.append("Video cargado esta vacio.");
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        }
    }
    
}
