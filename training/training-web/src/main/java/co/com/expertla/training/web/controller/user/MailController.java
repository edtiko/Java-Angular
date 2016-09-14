package co.com.expertla.training.web.controller.user;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.enums.StateEnum;
import co.com.expertla.training.model.dto.MailCommunicationDTO;
import co.com.expertla.training.model.entities.ColourIndicator;
import co.com.expertla.training.model.entities.MailCommunication;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.configuration.ColourIndicatorService;
import co.com.expertla.training.service.plan.MailCommunicationService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    private static final Logger LOGGER = Logger.getLogger(MailController.class);

    @Autowired
    private MailCommunicationService mailCommunicationService;

    @Autowired
    private ColourIndicatorService colourIndicatorService;

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
    Response getMailsBySendingUser(@PathVariable("receivingUser") Integer receivingUser, @PathVariable("sendingUser") Integer sendingUser) {
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
                if(colour.getColourOrder().equals(1)) {
                    firstOrder = colour.getHoursSpent();
                    firstColour = colour.getColour();
                }
                if(colour.getColourOrder().equals(2)) {
                    secondOrder = colour.getHoursSpent();
                    secondColour = colour.getColour();
                }
                if(colour.getColourOrder().equals(3)) {
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
    public ResponseEntity<ResponseService> createMailCommunication(@RequestBody MailCommunication mailCommunication) {
        ResponseService responseService = new ResponseService();
        try {
            mailCommunication.setRead(Boolean.FALSE);
            mailCommunication.setStateId(StateEnum.ACTIVE.getId());
            mailCommunication.setCreationDate(new Date());
            mailCommunicationService.create(mailCommunication);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.trainingPlan", "msgRegistroCreado"));
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
     * fecha 12/09/2016 <br>
     *
     * @author Angela Ramirez
     * @param mail
     * @return
     */
    @RequestMapping(value = "mailCommunication/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateMailCommunication(@RequestBody MailCommunication mail) {
        ResponseService responseService = new ResponseService();
        try {
            MailCommunication mailCommunication = mailCommunicationService.findById(mail);
            if (mailCommunication == null) {
                responseService.setOutput("El registro no existe");
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            mailCommunication.setMessage(mail.getMessage());
            mailCommunication.setSubject(mail.getSubject());
            mailCommunication.setRead(mail.getRead());
            mailCommunication.setReceivingUser(mail.getReceivingUser());
            mailCommunication.setSendingUser(mail.getSendingUser());
            mailCommunication.setStateId(mail.getStateId());
            mailCommunicationService.store(mailCommunication);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.trainingplan", "msgRegistroEditado"));
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
    Response getSentMails(@PathVariable("userId") Integer userId) {
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
    
    private long calculateHourDifference(Date creationDate) {
        Date now = new Date();
        long diff = now.getTime() - creationDate.getTime();
        long hoursSpent = diff / (60 * 60 * 1000);
        return hoursSpent;
    }
}
