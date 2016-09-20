/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.controller.plan;

import co.com.expertla.training.model.dto.PlanVideoDTO;
import co.com.expertla.training.model.entities.PlanVideo;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.configuration.StorageService;
import co.com.expertla.training.service.plan.PlanVideoService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
  
   private final StorageService storageService;


    @Autowired
    private PlanVideoService planVideoService;
    
        @Autowired
    public PlanVideoController(StorageService storageService) {
        this.storageService = storageService;
    }
    
    
    
     @RequestMapping(value = "/upload/{toUserId}/{fromUserId}/{dateString}", method = RequestMethod.POST)
    public @ResponseBody
    Response uploadVideo(@RequestParam("fileToUpload") MultipartFile file, @RequestParam String filename, @PathVariable Integer toUserId, @PathVariable Integer fromUserId, @PathVariable String dateString) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        if (!file.isEmpty()) {
            try {
        
                String fileName = dateString +"_"+fromUserId+"_"+toUserId;
                File directory = new File(ROOT+fileName);
                if (!directory.exists()) {
                    if (directory.mkdir()) {
                        Files.copy(file.getInputStream(), Paths.get(ROOT+fileName, filename));
                    } 
                }else{
                     Files.copy(file.getInputStream(), Paths.get(ROOT+fileName, filename));
                }

                Integer count =  planVideoService.countByVideoPath(fileName);
                
                if(count == 0){
                PlanVideo video = new PlanVideo();
                video.setFromUserId(new User(fromUserId));
                video.setName(fileName);
                video.setToUserId(new User(toUserId));
                video.setCreationDate(Calendar.getInstance().getTime());
                video.setVideoPath(fileName);
                planVideoService.create(video);
                }
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
    
      @RequestMapping(value = "/get/videos/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getVideosByUser(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanVideoDTO> videos = planVideoService.getVideosByUser(userId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(videos);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }
    
    
    @RequestMapping(value = "/get/video/{videoPath}", method = RequestMethod.GET)
    public @ResponseBody
    Response getVideoBySrc(@PathVariable("videoPath") String videoPath) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            File file = new File(ROOT+videoPath);
            if(file.exists()){
                
            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("");
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }
    
    @RequestMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }
    
}
