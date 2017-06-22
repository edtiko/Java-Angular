package co.expertic.training.web.controller.plan;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.model.dto.ChartReportDTO;
import co.expertic.training.model.dto.MailCommunicationDTO;
import co.expertic.training.model.dto.MailCommunicationMovilDTO;
import co.expertic.training.model.dto.PlanMessageDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.entities.ColourIndicator;
import co.expertic.training.model.entities.MailCommunication;
import co.expertic.training.model.entities.RoleUser;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.ColourIndicatorService;
import co.expertic.training.service.plan.MailCommunicationService;
import co.expertic.training.service.security.RoleUserService;
import co.expertic.training.service.user.UserService;
import co.expertic.training.web.enums.StatusResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    private static final Logger LOGGER = Logger.getLogger(MailController.class);
    private static final String COACH_INTERNO = "IN";
    private static final String COACH_EXTERNO = "EXT";

    @Autowired
    private MailCommunicationService mailCommunicationService;

    @Autowired
    private ColourIndicatorService colourIndicatorService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    UserService userService;

    @Autowired
    RoleUserService roleUserService;

    /**
     * Consulta los mail por destinatario <br>
     * Info. Creacion: <br>
     * fecha 12/09/2016 <br>
     *
     * @author Angela Ramirez
     * @param userId
     * @return
     */
    @RequestMapping(value = "/get/mails/by/user/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getMails(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<MailCommunicationDTO> mails = mailCommunicationService.getMailsByUserId(userId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(mails);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    /**
     * Consulta los mail por destinatario y remitente <br>
     * Info. Creacion: <br>
     * fecha 12/09/2016 <br>
     *
     * @author Angela Ramirez
     * @param receivingUser
     * @param sendingUser
     * @return
     */
    @RequestMapping(value = "/get/mails/by/receivingUser/from/sendingUser/{receivingUser}/{sendingUser}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getMailsBySendingUser(@PathVariable("receivingUser") Integer receivingUser, @PathVariable("sendingUser") Integer sendingUser) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<MailCommunicationDTO> mails = mailCommunicationService.getMailsByReceivingUserIdFromSendingUser(receivingUser, sendingUser);
            List<ColourIndicator> colours = colourIndicatorService.findAll();

            int firstOrder = 0;
            int secondOrder = 0;
            int thirdOrder = 0;
            String firstColour = "{'background-color':'white'}";
            String secondColour = "{'background-color':'white'}";
            String thirdColour = "{'background-color':'white'}";
            for (ColourIndicator colour : colours) {
                if (colour.getColourOrder().equals(1)) {
                    firstOrder = colour.getHoursSpent();
                    firstColour = colour.getColour();
                }
                if (colour.getColourOrder().equals(2)) {
                    secondOrder = colour.getHoursSpent();
                    secondColour = colour.getColour();
                }
                if (colour.getColourOrder().equals(3)) {
                    thirdOrder = colour.getHoursSpent();
                    thirdColour = colour.getColour();
                }
            }

            for (MailCommunicationDTO mail : mails) {
                mail.setHoursSpent(calculateHourDifference(mail.getCreationDate()));
                if (mail.getHoursSpent() >= 0 && mail.getHoursSpent() <= firstOrder) {
                    mail.setColour(firstColour);
                } else if (mail.getHoursSpent() > firstOrder && mail.getHoursSpent() <= secondOrder) {
                    mail.setColour(secondColour);
                } else {
                    mail.setColour(thirdColour);
                }

            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(mails);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }

    /**
     * Consulta los mail por plan asociado<br>
     * Info. Creacion: <br>
     * fecha 09/11/2016 <br>
     *
     * @author Edwin Gomez
     * @param tipoPlan
     * @param sendingUserId
     * @param receivingUserId
     * @param planId
     * @param roleSelected
     * @param fromto
     * @return
     */
    @RequestMapping(value = "/get/mails/by/plan/{tipoPlan}/{sendingUserId}/{receivingUserId}/{planId}/{roleSelected}/{fromto}", method = RequestMethod.GET)
    public @ResponseBody
    Response getMailsByPlan(@PathVariable("tipoPlan") String tipoPlan, @PathVariable("sendingUserId") Integer sendingUserId,
            @PathVariable("receivingUserId") Integer receivingUserId, @PathVariable("planId") Integer planId,
            @PathVariable("roleSelected") Integer roleSelected, @PathVariable("fromto") String fromto) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<MailCommunicationDTO> mails = mailCommunicationService.getMailsByPlan(tipoPlan, sendingUserId, receivingUserId, planId, roleSelected, fromto);
            List<ColourIndicator> colours = colourIndicatorService.findAll();

            int firstOrder = 0;
            int secondOrder = 0;
            int thirdOrder = 0;
            String firstColour = "{'background-color':'white'}";
            String secondColour = "{'background-color':'white'}";
            String thirdColour = "{'background-color':'white'}";
            for (ColourIndicator colour : colours) {
                if (colour.getColourOrder().equals(1)) {
                    firstOrder = colour.getHoursSpent();
                    firstColour = colour.getColour();
                }
                if (colour.getColourOrder().equals(2)) {
                    secondOrder = colour.getHoursSpent();
                    secondColour = colour.getColour();
                }
                if (colour.getColourOrder().equals(3)) {
                    thirdOrder = colour.getHoursSpent();
                    thirdColour = colour.getColour();
                }
            }

            for (MailCommunicationDTO mail : mails) {
                mail.setHoursSpent(calculateHourDifference(mail.getCreationDate()));
                if (mail.getHoursSpent() >= 0 && mail.getHoursSpent() <= firstOrder) {
                    mail.setColour(firstColour);
                } else if (mail.getHoursSpent() > firstOrder && mail.getHoursSpent() <= secondOrder) {
                    mail.setColour(secondColour);
                } else {
                    mail.setColour(thirdColour);
                }

            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(mails);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

    /**
     * Crea mailComunnication <br>
     * Info. Creacion: <br>
     * fecha 12/09/2016 <br>
     *
     * @author Angela Ramirez
     * @param mailCommunication
     * @return
     */
    @RequestMapping(value = "mailCommunication/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createMailCommunication(@RequestBody MailCommunicationDTO mailCommunication) {
        ResponseService responseService = new ResponseService();
        Integer sessionId = null;
        int availableMails = 0;
        int emergencyMails = 0;
        boolean isPlan = false;
        try {

            if (mailCommunication.getCoachAssignedPlanId() != null) {
                isPlan = true;
                sessionId = mailCommunication.getCoachAssignedPlanId();
                availableMails = mailCommunicationService.getCountMailsByPlan(sessionId, mailCommunication.getSendingUser().getUserId(), mailCommunication.getRoleSelected());
                emergencyMails = mailCommunicationService.getMailsEmergencyByPlan(sessionId, mailCommunication.getSendingUser().getUserId(), mailCommunication.getRoleSelected());
            } else if (mailCommunication.getCoachExtAthleteId() != null) {
                isPlan = true;
                sessionId = mailCommunication.getCoachExtAthleteId();
                availableMails = mailCommunicationService.getCountMailsByPlanExt(sessionId, mailCommunication.getSendingUser().getUserId());
                emergencyMails = mailCommunicationService.getMailsEmergencyByPlanExt(sessionId, mailCommunication.getSendingUser().getUserId());
            } else {
                sessionId = mailCommunication.getReceivingUser().getUserId() + mailCommunication.getSendingUser().getUserId();
            }

            if (isPlan && availableMails == 0 && emergencyMails > 0) {
                responseService.setOutput("Mensaje enviado correctamente, se estan consumiendo los correos de emergencia (" + emergencyMails + ")");
            } else if (isPlan && availableMails == 0 && emergencyMails == 0) {
                responseService.setOutput("Ya consumió el limite de correos permitidos para su plan.");
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            } else {
                responseService.setOutput("Mensaje enviado correctamente.");
            }

            mailCommunicationService.create(mailCommunication);
            if (mailCommunication.getSendingUser() != null) {
                RoleUser roleUserMsg = roleUserService.findByUserId(mailCommunication.getSendingUser().getUserId());
                mailCommunication.getSendingUser().setRoleId(roleUserMsg.getRoleId().getRoleId());
            }
            simpMessagingTemplate.convertAndSend("/queue/mail", mailCommunication);
            //responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.trainingPlan", "msgRegistroCreado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Modifica mail Communication para marcarlos como leidos <br>
     * Info. Creacion: <br>
     * fecha 09/11/2016 <br>
     *
     * @author Edwin Gómez
     * @param id
     * @return
     */
    @RequestMapping(value = "mailCommunication/read/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> updateMailCommunication(@PathVariable("id") Integer id) {
        ResponseService responseService = new ResponseService();
        try {
            MailCommunication mailCommunication = mailCommunicationService.findById(id);
            if (mailCommunication == null) {
                responseService.setOutput("El registro no existe");
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }

            mailCommunication.setRead(Boolean.TRUE);
            mailCommunicationService.store(mailCommunication);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.trainingplan", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "mailCommunication/resend/{id}/{planId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> resendEmail(@PathVariable("id") Integer id, @PathVariable("planId") Integer planId) {
        ResponseService responseService = new ResponseService();
        try {
            MailCommunicationDTO dto = mailCommunicationService.resendEmail(id, planId);
            if (dto.getSendingUser() != null) {
                RoleUser roleUserMsg = roleUserService.findByUserId(dto.getSendingUser().getUserId());
                dto.getSendingUser().setRoleId(roleUserMsg.getRoleId().getRoleId());
            }
            simpMessagingTemplate.convertAndSend("/queue/mail", dto);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.trainingplan", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta los mail por destinatario <br>
     * Info. Creacion: <br>
     * fecha 12/09/2016 <br>
     *
     * @author Andres Felipe Lopez
     * @param userId
     * @return
     */
    @RequestMapping(value = "/get/mails/by/user/movil/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getMailsMovil(@PathVariable("userId") Integer userId, HttpServletRequest request) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            String uri = request.getRequestURL().substring(0, request.getRequestURL().indexOf("/get/mails/by/user/movil"));
            List<MailCommunicationDTO> mails = mailCommunicationService.getMailsByUserId(userId);

            List<MailCommunicationMovilDTO> mailMovil = new ArrayList();
            mails.stream().forEach((mail) -> {
                UserDTO r = new UserDTO();
                r.setUserId(mail.getReceivingUser().getUserId());
                UserDTO s = new UserDTO();
                s.setUserId(mail.getSendingUser().getUserId());
                mail.setReceivingUser(r);
                mail.setSendingUser(s);
                MailCommunicationMovilDTO mailCommunicationMovilDTO = new MailCommunicationMovilDTO(mail.getMailCommunicationId());
                mailCommunicationMovilDTO.setCreationDate(mail.getCreationDate());
                mailCommunicationMovilDTO.setMessage(mail.getMessage());
                mailCommunicationMovilDTO.setRead(mail.getRead());
                if (mail.getReceivingUser() != null) {
                    try {
                        UserDTO userDto = userService.findById(mail.getReceivingUser().getUserId());
                        mailCommunicationMovilDTO.setReceivingUser(mail.getReceivingUser().getUserId());
                        mailCommunicationMovilDTO.setReceivingUserFullName(userDto.getFullName());
                        mailCommunicationMovilDTO.setReceivingUserPhoto(uri + "/user/download/photo/" + mail.getReceivingUser().getUserId());
                    } catch (Exception ex) {
                        java.util.logging.Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (mail.getSendingUser() != null) {
                    try {
                        UserDTO userDto = userService.findById(mail.getSendingUser().getUserId());
                        mailCommunicationMovilDTO.setSendingUser(mail.getSendingUser().getUserId());
                        mailCommunicationMovilDTO.setSendingUserFullName(userDto.getFullName());
                        mailCommunicationMovilDTO.setSendingUserPhoto(uri + "/user/download/photo/" + mail.getSendingUser().getUserId());
                    } catch (Exception ex) {
                        java.util.logging.Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                mailCommunicationMovilDTO.setStateId(mail.getStateId());
                mailCommunicationMovilDTO.setSubject(mail.getSubject());
                mailMovil.add(mailCommunicationMovilDTO);

            });

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(mailMovil);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta los mail por remitente <br>
     * Info. Creacion: <br>
     * fecha 12/09/2016 <br>
     *
     * @author Andres Felipe Lopez
     * @param userId
     * @return
     */
    @RequestMapping(value = "/get/sent/mails/by/user/movil/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getSentMails(@PathVariable("userId") Integer userId, HttpServletRequest request) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            String uri = request.getRequestURL().substring(0, request.getRequestURL().indexOf("/get/sent/mails"));
            List<MailCommunicationDTO> mails = mailCommunicationService.getSentMailsByUserId(userId);
            List<MailCommunicationMovilDTO> mailMovil = new ArrayList();
            mails.stream().forEach((mail) -> {
                UserDTO r = new UserDTO();
                r.setUserId(mail.getReceivingUser().getUserId());
                UserDTO s = new UserDTO();
                s.setUserId(mail.getSendingUser().getUserId());
                mail.setReceivingUser(r);
                mail.setSendingUser(s);
                MailCommunicationMovilDTO mailCommunicationMovilDTO = new MailCommunicationMovilDTO(mail.getMailCommunicationId());
                mailCommunicationMovilDTO.setCreationDate(mail.getCreationDate());
                mailCommunicationMovilDTO.setMessage(mail.getMessage());
                mailCommunicationMovilDTO.setRead(mail.getRead());
                if (mail.getReceivingUser() != null) {
                    try {
                        UserDTO userDto = userService.findById(mail.getReceivingUser().getUserId());
                        mailCommunicationMovilDTO.setReceivingUser(mail.getReceivingUser().getUserId());
                        mailCommunicationMovilDTO.setReceivingUserFullName(userDto.getFullName());
                        mailCommunicationMovilDTO.setReceivingUserPhoto(uri + "/user/download/photo/" + mail.getReceivingUser().getUserId());
                    } catch (Exception ex) {
                        java.util.logging.Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (mail.getSendingUser() != null) {
                    try {
                        UserDTO userDto = userService.findById(mail.getSendingUser().getUserId());
                        mailCommunicationMovilDTO.setSendingUser(mail.getSendingUser().getUserId());
                        mailCommunicationMovilDTO.setSendingUserFullName(userDto.getFullName());
                        mailCommunicationMovilDTO.setSendingUserPhoto(uri + "/user/download/photo/" + mail.getSendingUser().getUserId());
                    } catch (Exception ex) {
                        java.util.logging.Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                mailCommunicationMovilDTO.setStateId(mail.getStateId());
                mailCommunicationMovilDTO.setSubject(mail.getSubject());
                mailMovil.add(mailCommunicationMovilDTO);

            });

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(mailMovil);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta los mail por remitente <br>
     * Info. Creacion: <br>
     * fecha 12/09/2016 <br>
     *
     * @author Angela Ramirez
     * @param userId
     * @return
     */
    @RequestMapping(value = "/get/sent/mails/by/user/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getSentMailsMovil(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<MailCommunicationDTO> mails = mailCommunicationService.getSentMailsByUserId(userId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(mails);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    /**
     * Consulta todos los usuarios a quien puede enviar correos el usuario coach
     * <br>
     * Info. Creacion: <br>
     * fecha 12/09/2016 <br>
     *
     * @author Angela Ramirez
     * @param userId
     * @return
     */
    @RequestMapping(value = "/get/all/recipients/by/coach/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAllRecipientsByCoach(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<UserDTO> mails = mailCommunicationService.getAllRecipientsByCoachId(userId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(mails);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    /**
     * Consulta todos los usuarios a quien puede enviar correos la estrella <br>
     * Info. Creacion: <br>
     * fecha 12/09/2016 <br>
     *
     * @author Angela Ramirez
     * @param userId
     * @return
     */
    @RequestMapping(value = "/get/all/recipients/by/star/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAllRecipientsByStar(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<UserDTO> mails = mailCommunicationService.getAllRecipientsByStarId(userId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(mails);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    private long calculateHourDifference(Date creationDate) {
        Date now = new Date();
        long diff = now.getTime() - creationDate.getTime();
        long hoursSpent = diff / (60 * 60 * 1000);
        return hoursSpent;
    }

    @RequestMapping(value = "mail/get/response/count/mail/{userId}/{roleId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getResponseCountVideo(@PathVariable("userId") Integer userId, @PathVariable("roleId") Integer roleId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<ChartReportDTO> planVideoList = mailCommunicationService.getResponseCountMails(userId, roleId);
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

    @RequestMapping(value = "mail/get/timeResponse/{userId}/{roleId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getTimeResponse(@PathVariable("userId") Integer userId, @PathVariable("roleId") Integer roleId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanMessageDTO> planVideoList = mailCommunicationService.getResponseTimeMails(userId, roleId);
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

    @RequestMapping(value = "get/performance/{userId}/{roleId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getGeneralPerformance(@PathVariable("userId") Integer userId, @PathVariable("roleId") Integer roleId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<ChartReportDTO> planVideoList = mailCommunicationService.getPerformance(userId, roleId);
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

    @RequestMapping(value = "get/timeResponse/{userId}/{roleId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getGeneralResponseTime(@PathVariable("userId") Integer userId, @PathVariable("roleId") Integer roleId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            PlanMessageDTO plan = mailCommunicationService.getResponseTime(userId, roleId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(plan);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }

    @RequestMapping(value = "mail/get/count/available/{planId}/{userId}/{tipoPlan}/{roleSelected}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAvailableMails(@PathVariable("planId") Integer planId, @PathVariable("userId") Integer userId,
            @PathVariable("tipoPlan") String tipoPlan, @PathVariable("roleSelected") Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        Integer count = 0;
        Integer emergency = 0;
        try {
            if (tipoPlan.equals(COACH_INTERNO)) {
                count = mailCommunicationService.getCountMailsByPlan(planId, userId, roleSelected);
                emergency = mailCommunicationService.getMailsEmergencyByPlan(planId, userId, roleSelected);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                count = mailCommunicationService.getCountMailsByPlanExt(planId, userId);
                emergency = mailCommunicationService.getMailsEmergencyByPlanExt(planId, userId);
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

    @RequestMapping(value = "mail/get/count/received/{planId}/{sendingUserId}/{receiveUserId}/{tipoPlan}/{roleSelected}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getReceivedMails(@PathVariable("planId") Integer planId, @PathVariable("sendingUserId") Integer sendingUserId, @PathVariable("receiveUserId") Integer receiveUserId,
            @PathVariable("tipoPlan") String tipoPlan, @PathVariable("roleSelected") Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            Integer count = 0;
            if (tipoPlan.equals(COACH_INTERNO)) {
                count = mailCommunicationService.getCountMailsReceived(planId, sendingUserId, receiveUserId, roleSelected);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                count = mailCommunicationService.getCountMailsReceivedExt(planId, sendingUserId);
            } else if (sendingUserId != -1 && receiveUserId == -1) {
                count = mailCommunicationService.getCountMailsReceivedByUser(sendingUserId);
            } else if (sendingUserId != -1 && receiveUserId != -1) {
                count = mailCommunicationService.getCountMailsReceived(planId, sendingUserId, receiveUserId, roleSelected);
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

    /**
     * Elimina mail del usuario <br>
     * Info. Creacion: <br>
     * fecha 12/09/2016 <br>
     *
     * @author Andres Felipe Lopez
     * @param mailCommunication
     * @return
     */
    @RequestMapping(value = "/delete/mail/by/id", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<ResponseService> deleteMailById(@RequestBody MailCommunication mailCommunication) {
        ResponseService responseService = new ResponseService();
        try {
            mailCommunicationService.remove(mailCommunication);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Correo eliminado correctamente");
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput("Error al eliminar mensaje");
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "get/user/mails/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getUserMails(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<UserResumeDTO> userMessages = mailCommunicationService.getMailsUsersByUserId(userId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(userMessages);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }
}
