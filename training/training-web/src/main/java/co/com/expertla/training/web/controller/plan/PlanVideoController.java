package co.com.expertla.training.web.controller.plan;

import co.com.expertla.base.util.DateUtil;
import co.com.expertla.training.enums.RoleEnum;
import co.com.expertla.training.model.dto.ChartReportDTO;
import co.com.expertla.training.model.dto.PlanVideoDTO;
import co.com.expertla.training.model.entities.CoachAssignedPlan;
import co.com.expertla.training.model.entities.CoachExtAthlete;
import co.com.expertla.training.model.entities.PlanVideo;
import co.com.expertla.training.model.entities.ScriptVideo;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.configuration.StorageService;
import co.com.expertla.training.service.plan.CoachExtAthleteService;
import co.com.expertla.training.service.plan.PlanVideoService;
import co.com.expertla.training.service.plan.ScriptVideoService;
import co.com.expertla.training.service.user.UserService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@RequestMapping("/video")
public class PlanVideoController {

    private static final Logger LOGGER = Logger.getLogger(PlanVideoController.class);
    private static final String ROOT = "e:/upload-training/";
    private static final String COACH_INTERNO = "IN";
    private static final String COACH_EXTERNO = "EXT";

    private final StorageService storageService;
    
    @Autowired
    private CoachExtAthleteService coachExtAthleteService;

    @Autowired
    private PlanVideoService planVideoService;

    @Autowired
    private ScriptVideoService scriptVideoService;

    @Autowired
    private UserService userService;

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

    @RequestMapping(value = "/upload/{toUserId}/{fromUserId}/{planId}/{dateString}/{tipoPlan}/{roleSelected}", method = RequestMethod.POST)
    public @ResponseBody
    Response uploadVideo(@RequestParam("fileToUpload") MultipartFile file, @PathVariable Integer toUserId,
            @PathVariable Integer fromUserId, @PathVariable Integer planId,
            @PathVariable String dateString, @PathVariable String tipoPlan, @PathVariable Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        int availableVideos = 0;
        int emergencyVideos = 0;
        if (!file.isEmpty()) {
            try {
                if (tipoPlan.equals(COACH_INTERNO)) {
                    availableVideos = planVideoService.getCountVideoByPlan(planId, fromUserId, roleSelected);
                    emergencyVideos = planVideoService.getCountVideoEmergencyIn(planId, fromUserId, roleSelected);
                } else if (tipoPlan.equals(COACH_EXTERNO)) {
                    availableVideos = planVideoService.getCountVideoByPlanExt(planId, fromUserId);
                    emergencyVideos = planVideoService.getCountVideoEmergencyExt(planId, fromUserId);
                }
                if (availableVideos == 0 && emergencyVideos > 0) {
                    responseService.setOutput("Video cargado correctamente, se estan consumiendo los videos de emergencia (" + emergencyVideos + ")");
                } else if (availableVideos == 0 && emergencyVideos == 0) {
                    strResponse.append("Ya consumió el limite de videos permitidos para su plan.");
                    responseService.setOutput(strResponse);
                    responseService.setStatus(StatusResponse.FAIL.getName());
                    return Response.status(Response.Status.OK).entity(responseService).build();
                } else {
                    responseService.setOutput("Video mensaje cargado correctamente.");
                }
                String fileName = dateString + "_" + fromUserId + "_" + toUserId;
                File directory = new File(ROOT);
                File archivo = new File(ROOT + fileName);
                if (!directory.exists()) {
                    if (directory.mkdir()) {
                        Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));
                        //storageService.store(file);
                    }
                } else if (!archivo.exists()) {
                    Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));
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
                    if (roleSelected != -1 && roleSelected == RoleEnum.COACH_INTERNO.getId()) {  // Coach Interno
                        video.setToStar(Boolean.FALSE);
                    } else if (roleSelected != -1 && roleSelected == RoleEnum.ESTRELLA.getId()) { // Coach Estrella
                        video.setToStar(Boolean.TRUE);
                    }
                    if (tipoPlan.equals(COACH_INTERNO)) {
                        video.setCoachAssignedPlanId(new CoachAssignedPlan(planId));
                    } else if (tipoPlan.equals(COACH_EXTERNO)) {
                        video.setCoachExtAthleteId(new CoachExtAthlete(planId));
                    }
                    dto = planVideoService.create(video);
                    simpMessagingTemplate.convertAndSend("/queue/video/" + planId, dto);
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

    @RequestMapping(value = "/uploadScript/{toUserId}/{fromUserId}/{coachAssignedPlanId}/{dateString}/{tipoPlan}/{guion}", method = RequestMethod.POST)
    public @ResponseBody
    Response uploadScriptVideo(@RequestParam("fileToUpload") MultipartFile file,
            @PathVariable Integer toUserId, @PathVariable Integer fromUserId,
            @PathVariable Integer coachAssignedPlanId, @PathVariable String dateString,
            @PathVariable String tipoPlan, @PathVariable String guion) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        if (!file.isEmpty()) {
            try {
                String fileName = dateString + "_" + fromUserId + "_" + toUserId;
                File directory = new File(ROOT);
                File archivo = new File(ROOT + fileName);
                if (!directory.exists()) {
                    if (directory.mkdir()) {
                        Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));
                        //storageService.store(file);
                    }
                } else if (!archivo.exists()) {
                    Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));
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
                    video.setCoachAssignedPlanId(new CoachAssignedPlan(coachAssignedPlanId));
                    dto = planVideoService.create(video);
                    ScriptVideo script = new ScriptVideo();
                    script.setGuion(guion);
                    script.setCreationDate(new Date());
                    script.setPlanVideoId(new PlanVideo(dto.getId()));
                    scriptVideoService.create(script);
                   simpMessagingTemplate.convertAndSend("/queue/video/" + coachAssignedPlanId, dto);
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

    @RequestMapping(value = "/uploadVideo/{toUserId}/{fromUserId}", method = RequestMethod.POST)
    public @ResponseBody
    Response uploadPlanVideoToUserFromUser(@RequestParam("fileToUpload") MultipartFile file,
            @PathVariable Integer toUserId,
            @PathVariable Integer fromUserId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        if (!file.isEmpty()) {
            try {

                String fileName = DateUtil.getCurrentDate("ddMMyyyyHHmm") + "_" + fromUserId + "_" + toUserId;
                File directory = new File(ROOT);
                File archivo = new File(ROOT + fileName);
                if (!directory.exists()) {
                    if (directory.mkdir()) {
                        Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));
                        //storageService.store(file);
                    }
                } else if (!archivo.exists()) {
                    Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));
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
                    dto = planVideoService.create(video);
                }
                //strResponse.append("video cargado correctamente.");
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                responseService.setOutput("Video Cargado Correctamente.");
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

    @RequestMapping(value = "/uploadVideo/star/to/coach/{toUserId}/{fromUserId}/{fromPlanVideoId}", method = RequestMethod.POST)
    public @ResponseBody
    Response uploadPlanVideoStarToCoach(@RequestParam("fileToUpload") MultipartFile file,
            @PathVariable Integer toUserId,
            @PathVariable Integer fromUserId,
            @PathVariable Integer fromPlanVideoId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        if (!file.isEmpty()) {
            try {

                String fileName = DateUtil.getCurrentDate("ddMMyyyyHHmm") + "_" + fromUserId + "_" + toUserId;
                File directory = new File(ROOT);
                File archivo = new File(ROOT + fileName);
                if (!directory.exists()) {
                    if (directory.mkdir()) {
                        Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));
                        //storageService.store(file);
                    }
                } else if (!archivo.exists()) {
                    Files.copy(file.getInputStream(), Paths.get(ROOT, fileName));
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
                    if (fromPlanVideoId != null && fromPlanVideoId > 0) {
                        video.setFromPlanVideoId(new PlanVideo(fromPlanVideoId));
                    }

                    dto = planVideoService.create(video);
                }
                //strResponse.append("video cargado correctamente.");
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                responseService.setOutput("Video Cargado Correctamente.");
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

    @RequestMapping(value = "/get/videos/{coachAssignedPlanId}/{userId}/{fromto}/{tipoPlan}/{roleSelected}", method = RequestMethod.GET)
    public @ResponseBody
    Response getVideosByUser(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("userId") Integer userId, 
                             @PathVariable("fromto") String fromto, @PathVariable("tipoPlan") String tipoPlan, @PathVariable("roleSelected") Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("planId", coachAssignedPlanId);
            parameters.put("userId", userId);
            parameters.put("fromto", fromto);
            parameters.put("tipoPlan", tipoPlan);
            parameters.put("roleSelected", roleSelected);
            
            List<PlanVideoDTO> videos = planVideoService.getVideosByUser(parameters);
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
    
    /*    @RequestMapping(value = "/get/videos/{coachAssignedPlanId}/{userId}/{fromto}/{tipoPlan}/{roleSelected}", method = RequestMethod.GET)
    public @ResponseBody
    Response getVideosAthlete(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("userId") Integer userId, 
                             @PathVariable("fromto") String fromto, @PathVariable("tipoPlan") String tipoPlan, @PathVariable("roleSelected") Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("planId", coachAssignedPlanId);
            parameters.put("userId", userId);
            parameters.put("fromto", fromto);
            parameters.put("tipoPlan", tipoPlan);
            
            if (roleSelected != -1 && roleSelected == RoleEnum.COACH_INTERNO.getId()) {  // Coach Interno
                parameters.put("roleSelected", Boolean.FALSE);
            } else if (roleSelected != -1 && roleSelected == RoleEnum.ESTRELLA.getId()) { // Coach Estrella
                parameters.put("roleSelected", Boolean.TRUE);
            }

            List<PlanVideoDTO> videos = planVideoService.getVideosByUser(parameters);
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

    }*/

    @RequestMapping(value = "/files/{videoPath}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String videoPath) {

        Resource file = storageService.loadAsResource(videoPath);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @RequestMapping(value = "/get/count/available/{coachAssignedPlanId}/{userId}/{tipoPlan}/{roleSelected}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAvailableMessages(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("userId") Integer userId, 
                                  @PathVariable("tipoPlan") String tipoPlan, @PathVariable("roleSelected") Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        Integer count = 0;
        Integer emergency = 0;
        try {
            if (tipoPlan.equals(COACH_INTERNO)) {
                count = planVideoService.getCountVideoByPlan(coachAssignedPlanId, userId, roleSelected);
                emergency = planVideoService.getCountVideoEmergencyIn(userId, userId, roleSelected);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                count = planVideoService.getCountVideoByPlanExt(coachAssignedPlanId, userId);
                emergency = planVideoService.getCountVideoEmergencyExt(coachAssignedPlanId, userId);
            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(count==0?emergency:count);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/get/count/received/{coachAssignedPlanId}/{userId}/{tipoPlan}/{roleSelected}", method = RequestMethod.GET)
    public @ResponseBody
    Response getMessagesReceived(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("userId") Integer userId, 
                                 @PathVariable("tipoPlan") String tipoPlan, @PathVariable("roleSelected") Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        Integer count = 0;
        try {
            if (tipoPlan.equals(COACH_INTERNO)) {
                count = planVideoService.getCountVideosReceived(coachAssignedPlanId, userId, roleSelected);
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
    Response readVideos(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("userId") Integer userId, @PathVariable("tipoPlan") String tipoPlan) {
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
    Response readVideo(@PathVariable("planVideoId") Integer planVideoId) {
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

    @RequestMapping(value = "/planVideo/get/planVideoStarByCoach/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getPlanVideoStarByCoach(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanVideo> planVideoList = planVideoService.getPlanVideoStarByCoach(userId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(planVideoList);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/planVideo/get/response/count/video/{userId}/{roleId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getResponseCountVideo(@PathVariable("userId") Integer userId, @PathVariable("roleId") Integer roleId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<ChartReportDTO> planVideoList = planVideoService.getResponseCountVideo(userId, roleId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(planVideoList);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/planVideo/get/timeResponse/{userId}/{roleId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getTimeResponse(@PathVariable("userId") Integer userId, @PathVariable("roleId") Integer roleId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanVideoDTO> planVideoList = planVideoService.getResponseTimeVideos(userId, roleId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(planVideoList);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "/rejected/atlethe/{planVideoId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> rejectedVideo(@PathVariable("planVideoId") Integer planVideoId) {
        ResponseService responseService = new ResponseService();
        try {
            planVideoService.rejectedVideo(planVideoId);
            PlanVideo plan = planVideoService.getPlanVideoById(planVideoId);
            User user = plan.getFromUserId();
            
            String message = 
            "Sr(a) " + user.getName() + " " + user.getLastName()
                + ", Su video ha sido rechazado ";
            boolean envio = coachExtAthleteService.sendEmail(message, user.getEmail());
            
            System.out.println("envio " + envio);
            System.out.println("message " + message + " email " + user.getEmail());
            
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Video rechazado exitosamente");
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput("Error al rechazar video");
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/send/video/atlethe/to/star/{planVideoId}/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> sendVideoAtletheToStar(@PathVariable("planVideoId") Integer planVideoId,
            @PathVariable("userId") Integer userId, @RequestParam("guion") String guion) {
        ResponseService responseService = new ResponseService();
        try {
            PlanVideoDTO video = planVideoService.getVideoById(planVideoId);
            User starId = userService.getStarFromAtlethe(video.getFromUser().getUserId());
            PlanVideo planVideo = new PlanVideo();
            planVideo.setFromUserId(new User(userId));
            planVideo.setVideoPath(video.getName());
            planVideo.setName(video.getName());
            planVideo.setToUserId(starId);
            planVideo.setCreationDate(new Date());
            planVideoService.create(planVideo);
            ScriptVideo script = new ScriptVideo();
            script.setGuion(guion);
            script.setCreationDate(new Date());
            script.setPlanVideoId(planVideo);
            script.setFromPlanVideoId(new PlanVideo(planVideoId));
            scriptVideoService.create(script);
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

    @RequestMapping(value = "/send/video/to/atlethe/{planVideoId}/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> sendVideoAtletheToStar(@PathVariable("planVideoId") Integer planVideoId,
            @PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        try {
            PlanVideo video = planVideoService.getPlanVideoById(planVideoId);
            PlanVideo fromPlan = video.getFromPlanVideoId();
            PlanVideo planVideo = new PlanVideo();
            planVideo.setFromUserId(new User(userId));
            planVideo.setVideoPath(video.getName());
            planVideo.setName(video.getName());
            planVideo.setToUserId(fromPlan.getFromUserId());
            planVideo.setCreationDate(new Date());
            planVideo.setCoachAssignedPlanId(fromPlan.getCoachAssignedPlanId());
            planVideoService.create(planVideo);
            fromPlan.setIndRejected(0);
            planVideoService.store(fromPlan);
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
