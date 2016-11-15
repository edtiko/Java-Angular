package co.com.expertla.training.web.controller.plan;

import co.com.expertla.training.model.dto.PlanAudioDTO;
import co.com.expertla.training.model.entities.CoachAssignedPlan;
import co.com.expertla.training.model.entities.CoachExtAthlete;
import co.com.expertla.training.model.entities.PlanAudio;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.configuration.StorageService;
import co.com.expertla.training.service.plan.PlanAudioService;
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
@RequestMapping("/audio")
public class AudioMessageController {

    private static final Logger LOGGER = Logger.getLogger(AudioMessageController.class);
    private static final String ROOT = "e:/upload-training/audios/";
    private static final String COACH_INTERNO = "IN";
    private static final String COACH_EXTERNO = "EXT";

    private final StorageService storageService;

    @Autowired
    private PlanAudioService planAudioService;

    @Autowired
    public AudioMessageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("voice/{sessionId}")
    //@SendTo("/topic/message")
    public void sendMessage(PlanAudioDTO message, @DestinationVariable("sessionId") Integer sessionId) {
        PlanAudioDTO msg = null;
        try {
            msg = planAudioService.getAudioById(message.getId());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

        }
        simpMessagingTemplate.convertAndSend("/queue/audio/" + sessionId, msg);
        //return new OutputMessage(message, new Date());
    }

    @RequestMapping(value = "upload/{toUserId}/{fromUserId}/{planId}/{dateString}/{tipoPlan}/{toStar}", method = RequestMethod.POST)
    public @ResponseBody
    Response uploadAudio(@RequestParam("fileToUpload") MultipartFile file, @PathVariable Integer toUserId, @PathVariable Integer fromUserId, 
                         @PathVariable Integer planId, @PathVariable String dateString, @PathVariable String tipoPlan,@PathVariable Boolean toStar) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        int availableAudios = 0;
        int emergencyAudios = 0;
        if (!file.isEmpty()) {
            try {
                if (tipoPlan.equals(COACH_INTERNO)) {
                    availableAudios = planAudioService.getCountAudioByPlan(planId, fromUserId);
                    emergencyAudios = planAudioService.getCountAudioEmergencyByPlan(planId, fromUserId);
                } else if (tipoPlan.equals(COACH_EXTERNO)) {
                    availableAudios = planAudioService.getCountAudioByPlanExt(planId, fromUserId);
                    emergencyAudios = planAudioService.getCountAudioByEmergencyPlanExt(planId, fromUserId);
                }
                if(availableAudios == 0 && emergencyAudios > 0){
                     responseService.setOutput("Audio cargado correctamente, se estan consumiendo los audio mensajes de emergencia ("+emergencyAudios+")");
                }
                else if (availableAudios == 0 && emergencyAudios == 0) {
                    strResponse.append("Ya consumió el limite de audios permitidos para su plan.");
                    responseService.setOutput(strResponse);
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return Response.status(Response.Status.OK).entity(responseService).build();
                }else{
                     responseService.setOutput("Audio mensaje cargado correctamente."); 
                }
                String fileName = dateString + "_" + fromUserId + "_" + toUserId+".wav";

                File directory = new File(ROOT);
                File archivo = new File(ROOT + "/" + fileName);
                if (!directory.exists()) {
                    if (directory.mkdir()) {
                        Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));
                    }
                } else if (!archivo.exists()) {
                    Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));
                }
                 PlanAudioDTO dto = planAudioService.getByAudioPath(fileName);
                if (dto == null) {
                    PlanAudio audio = new PlanAudio();
                    audio.setFromUserId(new User(fromUserId));
                    audio.setName(fileName);
                    audio.setToUserId(new User(toUserId));
                    audio.setCreationDate(Calendar.getInstance().getTime());
                    audio.setReaded(Boolean.FALSE);
                    audio.setToStar(toStar);
                    if (tipoPlan.equals(COACH_INTERNO)) {
                        audio.setCoachAssignedPlanId(new CoachAssignedPlan(planId));
                    } else if (tipoPlan.equals(COACH_EXTERNO)) {
                        audio.setCoachExtAthleteId(new CoachExtAthlete(planId));
                    }
                    dto = planAudioService.create(audio);
                 simpMessagingTemplate.convertAndSend("/queue/audio/" + planId, dto);
                }
                

                //strResponse.append("video cargado correctamente.");
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                //responseService.setOutput(dto);
                return Response.status(Response.Status.OK).entity(responseService).build();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                responseService.setDetail(e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
            }
        } else {
            strResponse.append("Audio cargado esta vacio.");
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/get/audios/{planId}/{userId}/{fromto}/{tipoPlan}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAudiosByUser(@PathVariable("planId") Integer planId, @PathVariable("userId") Integer userId, @PathVariable("fromto") String fromto, @PathVariable("tipoPlan") String tipoPlan) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanAudioDTO> audios = planAudioService.getAudiosByUser(planId, userId, fromto, tipoPlan);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(audios);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

    @RequestMapping(value = "/files/{audioPath}/{filename:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String audioPath, @PathVariable String filename) {

        Resource file = storageService.loadAsResource(audioPath + "/" + filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @RequestMapping(value = "/get/count/available/{planId}/{userId}/{tipoPlan}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAvailableAudios(@PathVariable("planId") Integer planId, @PathVariable("userId") Integer userId, @PathVariable("tipoPlan") String tipoPlan) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        Integer count = 0;
        Integer emergency = 0;
        try {
            if (tipoPlan.equals(COACH_INTERNO)) {
                count = planAudioService.getCountAudioByPlan(planId, userId);
                emergency = planAudioService.getCountAudioEmergencyByPlan(planId, userId);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                count = planAudioService.getCountAudioByPlanExt(planId, userId);
                emergency = planAudioService.getCountAudioByEmergencyPlanExt(planId, userId);
            }
            
            
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            
            if (count == 0) {
                responseService.setOutput(emergency);
            } else {
                responseService.setOutput(count);
            }
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

    @RequestMapping(value = "/get/count/received/{planId}/{userId}/{tipoPlan}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAudiosReceived(@PathVariable("planId") Integer planId, @PathVariable("userId") Integer userId, @PathVariable("tipoPlan") String tipoPlan) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        Integer count = 0;
        try {
            if (tipoPlan.equals(COACH_INTERNO)) {
                count = planAudioService.getCountAudiosReceived(planId, userId);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                count = planAudioService.getCountAudiosReceivedExt(planId, userId);
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

    @RequestMapping(value = "/read/all/{planId}/{userId}/{tipoPlan}", method = RequestMethod.GET)
    public @ResponseBody
    Response readAudios(@PathVariable("planId") Integer planId, @PathVariable("userId") Integer userId, @PathVariable("tipoPlan") String tipoPlan) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (tipoPlan.equals(COACH_INTERNO)) {
                planAudioService.readAudios(planId, userId);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                planAudioService.readAudiosExt(planId, userId);
            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Audios Leidos Correctamente.");
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

    @RequestMapping(value = "/read/{planAudioId}", method = RequestMethod.GET)
    public @ResponseBody
    Response readAudio(@PathVariable("planAudioId") Integer planAudioId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            planAudioService.readAudio(planAudioId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Audio Leido Correctamente.");
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
