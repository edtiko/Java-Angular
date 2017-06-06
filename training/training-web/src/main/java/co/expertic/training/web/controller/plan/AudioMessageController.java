package co.expertic.training.web.controller.plan;

import co.expertic.training.enums.RoleEnum;
import co.expertic.training.enums.StateEnum;
import co.expertic.training.model.dto.PlanAudioDTO;
import co.expertic.training.model.entities.CoachAssignedPlan;
import co.expertic.training.model.entities.CoachExtAthlete;
import co.expertic.training.model.entities.PlanAudio;
import co.expertic.training.model.entities.User;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.StorageService;
import co.expertic.training.service.plan.PlanAudioService;
import co.expertic.training.web.enums.StatusResponse;
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
import org.springframework.http.HttpStatus;
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

    @MessageMapping("voice")
    //@SendTo("/topic/message")
    public void sendMessage(PlanAudioDTO message) {
        PlanAudioDTO msg = null;
        try {
            msg = planAudioService.getAudioById(message.getId());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

        }
        simpMessagingTemplate.convertAndSend("/queue/audio", msg);
        //return new OutputMessage(message, new Date());
    }

    @RequestMapping(value = "upload/{toUserId}/{fromUserId}/{planId}/{dateString}/{tipoPlan}/{roleSelected}", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<ResponseService> uploadAudio(@RequestParam("fileToUpload") MultipartFile file, @PathVariable Integer toUserId, @PathVariable Integer fromUserId,
            @PathVariable Integer planId, @PathVariable String dateString, @PathVariable String tipoPlan, @PathVariable Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        int availableAudios = 0;
        int emergencyAudios = 0;
        if (!file.isEmpty()) {
            try {
                if (tipoPlan.equals(COACH_INTERNO)) {
                    availableAudios = planAudioService.getCountAudioByPlan(planId, fromUserId, toUserId, roleSelected);
                    emergencyAudios = planAudioService.getCountAudioEmergencyByPlan(planId, fromUserId, roleSelected);
                } else if (tipoPlan.equals(COACH_EXTERNO)) {
                    availableAudios = planAudioService.getCountAudioByPlanExt(planId, fromUserId);
                    emergencyAudios = planAudioService.getCountAudioByEmergencyPlanExt(planId, fromUserId);
                }
                if (availableAudios == 0 && emergencyAudios > 0) {
                    responseService.setMessage("Audio cargado correctamente, se estan consumiendo los audio mensajes de emergencia (" + emergencyAudios + ")");
                } else if (availableAudios == 0 && emergencyAudios == 0) {
                    responseService.setMessage("Ya consumió el limite de audios permitidos para su plan.");
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return new ResponseEntity<>(responseService, HttpStatus.OK);
                } else {
                    responseService.setMessage("Audio mensaje cargado correctamente.");
                }
                String fileName = dateString + "_" + fromUserId + "_" + toUserId + ".wav";

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
                    if (roleSelected != -1 && roleSelected == RoleEnum.COACH_INTERNO.getId()) {
                        audio.setToStar(Boolean.FALSE);
                    }
                    if (roleSelected != -1 && roleSelected == RoleEnum.ESTRELLA.getId()) {
                        audio.setToStar(Boolean.TRUE);
                        audio.setStateId(StateEnum.PENDING.getId().shortValue());
                    }
                    if (tipoPlan.equals(COACH_INTERNO)) {
                        audio.setCoachAssignedPlanId(new CoachAssignedPlan(planId));
                    } else if (tipoPlan.equals(COACH_EXTERNO)) {
                        audio.setCoachExtAthleteId(new CoachExtAthlete(planId));
                    }
                    dto = planAudioService.create(audio);
                    dto.setFromUserId(fromUserId);
                    dto.setRoleSelected(roleSelected);
                    responseService.setOutput(dto);
                    simpMessagingTemplate.convertAndSend("/queue/audio", dto);
                }

                //strResponse.append("video cargado correctamente.");
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                //responseService.setOutput(dto);
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                responseService.setDetail(e.getMessage());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
        } else {
            responseService.setMessage("Audio cargado esta vacio.");
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get/audios/{planId}/{userId}/{receivingUserId}/{fromto}/{tipoPlan}/{roleSelected}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getAudiosByUser(@PathVariable("planId") Integer planId, @PathVariable("userId") Integer userId,
            @PathVariable("receivingUserId") Integer receivingUserId, @PathVariable("fromto") String fromto,
            @PathVariable("tipoPlan") String tipoPlan, @PathVariable("roleSelected") Integer roleSelected) {

        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanAudioDTO> audios = planAudioService.getAudiosByUser(planId, userId, receivingUserId, fromto, tipoPlan, roleSelected);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(audios);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
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

    @RequestMapping(value = "/get/count/available/{planId}/{userId}/{toUserId}/{tipoPlan}/{roleSelected}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAvailableAudios(@PathVariable("planId") Integer planId, @PathVariable("userId") Integer userId,
            @PathVariable("toUserId") Integer toUserId,
            @PathVariable("tipoPlan") String tipoPlan, @PathVariable("roleSelected") Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        Integer count = 0;
        Integer emergency = 0;
        try {
            if (tipoPlan.equals(COACH_INTERNO)) {
                count = planAudioService.getCountAudioByPlan(planId, userId, toUserId, roleSelected);
                emergency = planAudioService.getCountAudioEmergencyByPlan(planId, userId, roleSelected);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                count = planAudioService.getCountAudioByPlanExt(planId, userId);
                emergency = planAudioService.getCountAudioByEmergencyPlanExt(planId, userId);
            }

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(count == 0 ? emergency : count);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

    @RequestMapping(value = "/get/count/received/{planId}/{userId}/{toUserId}/{tipoPlan}/{roleSelected}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getAudiosReceived(@PathVariable("planId") Integer planId, @PathVariable("userId") Integer userId,
            @PathVariable("toUserId") Integer toUserId,
            @PathVariable("tipoPlan") String tipoPlan, @PathVariable("roleSelected") Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        Integer count = 0;
        try {
            if (tipoPlan.equals(COACH_INTERNO)) {
                count = planAudioService.getCountAudiosReceived(planId, userId, toUserId, roleSelected);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                count = planAudioService.getCountAudiosReceivedExt(planId, userId);
            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(count);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/read/all/{planId}/{userId}/{tipoPlan}", method = RequestMethod.GET)
    public @ResponseBody
    Response readAudios(@PathVariable("planId") Integer planId, @PathVariable("userId") Integer userId,
            @PathVariable("tipoPlan") String tipoPlan, @PathVariable("roleSelected") Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (tipoPlan.equals(COACH_INTERNO)) {
                planAudioService.readAudios(planId, userId, roleSelected);
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
    ResponseEntity<ResponseService> readAudio(@PathVariable("planAudioId") Integer planAudioId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            planAudioService.readAudio(planAudioId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Audio Leido Correctamente.");
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/approve/{planAudioId}/{planId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> approveAudio(@PathVariable("planAudioId") Integer planAudioId, @PathVariable("planId") Integer planId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            PlanAudioDTO dto = planAudioService.approveAudio(planAudioId, planId);
            dto.setRoleSelected(RoleEnum.ESTRELLA.getId());
            simpMessagingTemplate.convertAndSend("/queue/audio", dto);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Audio aprobado correctamente.");
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/reject/{planAudioId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> rejectAudio(@PathVariable("planAudioId") Integer planAudioId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            planAudioService.rejectAudio(planAudioId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Audio rechazado correctamente.");
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/send/star/to/athlete/{planAudioId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> sendAudioStarToAThlete(@PathVariable("planAudioId") Integer planAudioId) {
        ResponseService responseService = new ResponseService();
        try {

            PlanAudioDTO dto = planAudioService.sendAudioStarToAThlete(planAudioId);
            dto.setRoleSelected(RoleEnum.ESTRELLA.getId());
            simpMessagingTemplate.convertAndSend("/queue/audio", dto);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Video enviado exitosamente");
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput("Error al enviar video");
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

}
