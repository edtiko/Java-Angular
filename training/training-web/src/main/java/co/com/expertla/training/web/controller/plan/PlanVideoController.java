/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.controller.plan;

import co.com.expertla.training.model.dto.PlanVideoDTO;
import co.com.expertla.training.model.entities.CoachAssignedPlan;
import co.com.expertla.training.model.entities.CoachExtAthlete;
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
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private static final String ROOT = "e:/upload-training/";
    private static final String COACH_INTERNO = "IN";
    private static final String COACH_EXTERNO = "EXT";

    private final StorageService storageService;

    @Autowired
    private PlanVideoService planVideoService;

    @Autowired
    public PlanVideoController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/send/{sessionId}")
    //@SendTo("/topic/message")
    public void sendMessage(PlanVideoDTO message, @DestinationVariable("sessionId") Integer sessionId) {
        PlanVideoDTO msg = null;
        try {
            msg = planVideoService.getVideoById(message.getId());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

        }
        simpMessagingTemplate.convertAndSend("/queue/video/" + sessionId, msg);
        //return new OutputMessage(message, new Date());
    }

    @RequestMapping(value = "/upload/{toUserId}/{fromUserId}/{coachAssignedPlanId}/{dateString}/{tipoPlan}", method = RequestMethod.POST)
    public @ResponseBody
    Response uploadVideo(@RequestParam("fileToUpload") MultipartFile file, @RequestParam String filename, @PathVariable Integer toUserId, @PathVariable Integer fromUserId, @PathVariable Integer coachAssignedPlanId, @PathVariable String dateString, @PathVariable String tipoPlan) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        int availableVideos = 0;
        if (!file.isEmpty()) {
            try {
                if (tipoPlan.equals(COACH_INTERNO)) {
                    availableVideos = planVideoService.getCountVideoByPlan(coachAssignedPlanId, fromUserId);
                } else if (tipoPlan.equals(COACH_EXTERNO)) {
                    availableVideos = planVideoService.getCountVideoByPlanExt(coachAssignedPlanId, fromUserId);
                }
                if (availableVideos == 0) {
                    strResponse.append("Ya consumió el limite de videos permitidos para su plan.");
                    responseService.setOutput(strResponse);
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return Response.status(Response.Status.OK).entity(responseService).build();
                }
                String fileName = dateString + "_" + fromUserId + "_" + toUserId;
                File directory = new File(ROOT + fileName);
                File archivo = new File(ROOT + fileName + "/" + filename);
                if (!directory.exists()) {
                    if (directory.mkdir()) {
                        Files.copy(file.getInputStream(), Paths.get(ROOT + fileName, filename));
                        //storageService.store(file);
                    }
                } else if (!archivo.exists()) {
                    Files.copy(file.getInputStream(), Paths.get(ROOT + fileName, filename));
                    //storageService.store(file);
                }

                PlanVideoDTO dto = planVideoService.getByVideoPath(fileName);
                if (dto == null) {
                    PlanVideo video = new PlanVideo();
                    video.setFromUserId(new User(fromUserId));
                    video.setName(fileName);
                    video.setToUserId(new User(toUserId));
                    video.setCreationDate(Calendar.getInstance().getTime());
                    video.setVideoPath(fileName);
                    if (tipoPlan.equals(COACH_INTERNO)) {
                        video.setCoachAssignedPlanId(new CoachAssignedPlan(coachAssignedPlanId));
                    } else if (tipoPlan.equals(COACH_EXTERNO)) {
                        video.setCoachExtAthleteId(new CoachExtAthlete(coachAssignedPlanId));
                    }
                    dto = planVideoService.create(video);
                }
                //strResponse.append("video cargado correctamente.");
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                responseService.setOutput(dto);
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

    @RequestMapping(value = "/get/videos/{coachAssignedPlanId}/{userId}/{fromto}/{tipoPlan}", method = RequestMethod.GET)
    public @ResponseBody
    Response getVideosByUser(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("userId") Integer userId, @PathVariable("fromto") String fromto, @PathVariable("tipoPlan") String tipoPlan) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanVideoDTO> videos = planVideoService.getVideosByUser(coachAssignedPlanId, userId, fromto, tipoPlan);
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

    @RequestMapping(value = "/files/{videoPath}/{filename:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String videoPath, @PathVariable String filename) {

        Resource file = storageService.loadAsResource(videoPath + "/" + filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @RequestMapping(value = "/get/count/available/{coachAssignedPlanId}/{userId}/{tipoPlan}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAvailableMessages(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("userId") Integer userId, @PathVariable("tipoPlan") String tipoPlan) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        Integer count = 0;
        try {
            if (tipoPlan.equals(COACH_INTERNO)) {
                count = planVideoService.getCountVideoByPlan(coachAssignedPlanId, userId);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                count = planVideoService.getCountVideoByPlanExt(coachAssignedPlanId, userId);
            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(count);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

    @RequestMapping(value = "/get/count/received/{coachAssignedPlanId}/{userId}/{tipoPlan}", method = RequestMethod.GET)
    public @ResponseBody
    Response getMessagesReceived(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("userId") Integer userId, @PathVariable("tipoPlan") String tipoPlan) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        Integer count = 0;
        try {
            if (tipoPlan.equals(COACH_INTERNO)) {
                count = planVideoService.getCountVideosReceived(coachAssignedPlanId, userId);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                count = planVideoService.getCountVideosReceivedExt(coachAssignedPlanId, userId);
            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(count);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

    @RequestMapping(value = "/read/all/{coachAssignedPlanId}/{userId}/{tipoPlan}", method = RequestMethod.GET)
    public @ResponseBody
    Response readMessages(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("userId") Integer userId, @PathVariable("tipoPlan") String tipoPlan) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (tipoPlan.equals(COACH_INTERNO)) {
                planVideoService.readVideos(coachAssignedPlanId, userId);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                planVideoService.readVideosExt(coachAssignedPlanId, userId);
            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Videos Leidos Correctamente.");
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

    @RequestMapping(value = "/read/{planVideoId}", method = RequestMethod.GET)
    public @ResponseBody
    Response readMessage(@PathVariable("planVideoId") Integer planVideoId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            planVideoService.readVideo(planVideoId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Video Leido Correctamente.");
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

}
